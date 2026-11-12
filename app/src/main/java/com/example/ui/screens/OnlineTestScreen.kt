package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ChseDataProvider
import com.example.data.StudentRepository
import com.example.model.McqItem
import com.example.model.MockTest
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnlineTestScreen(
  activeTestId: String? = null,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val mockTests by StudentRepository.mockTests.collectAsState()
  var currentTestId by remember { mutableStateOf(activeTestId) }

  if (currentTestId == null) {
    TestLobbyView(
      tests = mockTests,
      onSelectTest = { currentTestId = it }
    )
  } else {
    val test = mockTests.find { it.id == currentTestId } ?: mockTests.first()
    ActiveTestExamView(
      test = test,
      onFinishTest = { score ->
        StudentRepository.recordTestResult(test.id, score)
      },
      onExit = { currentTestId = null }
    )
  }
}

// 1. Tests Lobby
@Composable
private fun TestLobbyView(
  tests: List<MockTest>,
  onSelectTest: (String) -> Unit
) {
  var selectedTab by remember { mutableStateOf("All") }
  val tabs = listOf("All", "Chapter Test", "Unit Test", "Half-Yearly Mock", "Annual Mock")

  val filteredTests = remember(selectedTab, tests) {
    if (selectedTab == "All") tests else tests.filter { it.testType == selectedTab }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    Surface(
      color = MaterialTheme.colorScheme.surface,
      shadowElevation = 2.dp
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = "CHSE Online Examination Center",
          style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
          text = "Board-pattern mock tests with timer & negative marking analysis",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(tabs) { tab ->
            FilterChip(
              selected = selectedTab == tab,
              onClick = { selectedTab = tab },
              label = { Text(tab) },
              shape = RoundedCornerShape(10.dp)
            )
          }
        }
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      contentPadding = PaddingValues(vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      items(filteredTests) { test ->
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("test_card_${test.id}")
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.primaryContainer
              ) {
                Text(
                  text = test.testType,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                )
              }

              if (test.isCompleted) {
                Surface(
                  shape = RoundedCornerShape(10.dp),
                  color = BrandGreen.copy(alpha = 0.15f)
                ) {
                  Text(
                    text = "Score: ${test.lastScore}/${test.totalMarks}",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandGreen)
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = test.title,
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = "${test.questions.size} Questions • ${test.durationMinutes} Mins • ${test.totalMarks} Marks",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
              onClick = { onSelectTest(test.id) },
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
              Spacer(modifier = Modifier.width(6.dp))
              Text(if (test.isCompleted) "Retake Examination" else "Start Examination")
            }
          }
        }
      }
    }
  }
}

// 2. Active Examination View
@Composable
private fun ActiveTestExamView(
  test: MockTest,
  onFinishTest: (Int) -> Unit,
  onExit: () -> Unit
) {
  var isSubmitted by remember { mutableStateOf(false) }
  var currentQuestionIndex by remember { mutableIntStateOf(0) }
  var userAnswers by remember { mutableStateOf(mutableMapOf<Int, Int>()) } // qIndex -> optionIndex
  var markedForReview by remember { mutableStateOf(mutableSetOf<Int>()) }
  var remainingSeconds by remember { mutableIntStateOf(test.durationMinutes * 60) }
  var showConfirmSubmit by remember { mutableStateOf(false) }

  BackHandler {
    if (!isSubmitted) {
      showConfirmSubmit = true
    } else {
      onExit()
    }
  }

  // Timer countdown
  LaunchedEffect(isSubmitted) {
    if (!isSubmitted) {
      while (remainingSeconds > 0) {
        delay(1000L)
        remainingSeconds--
      }
      isSubmitted = true
    }
  }

  val totalQuestions = test.questions.size
  val currentQuestion = test.questions[currentQuestionIndex]

  if (isSubmitted) {
    // Score calculation
    val correctCount = userAnswers.count { (qIdx, optIdx) ->
      test.questions[qIdx].correctIndex == optIdx
    }
    val score = (correctCount * (test.totalMarks / totalQuestions.coerceAtLeast(1)))

    LaunchedEffect(Unit) {
      onFinishTest(score)
    }

    TestResultView(
      test = test,
      score = score,
      correctCount = correctCount,
      totalQuestions = totalQuestions,
      userAnswers = userAnswers,
      onRetake = {
        userAnswers = mutableMapOf()
        markedForReview = mutableSetOf()
        remainingSeconds = test.durationMinutes * 60
        currentQuestionIndex = 0
        isSubmitted = false
      },
      onBackToLobby = onExit
    )
  } else {
    Scaffold(
      topBar = {
        Surface(color = MaterialTheme.colorScheme.surface, shadowElevation = 3.dp) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = test.title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                maxLines = 1
              )
              Text(
                text = "Question ${currentQuestionIndex + 1} of $totalQuestions",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            // Countdown Timer Pill
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = if (remainingSeconds < 300) BrandRed.copy(alpha = 0.15f) else MaterialTheme.colorScheme.primaryContainer
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.Timer,
                  contentDescription = null,
                  tint = if (remainingSeconds < 300) BrandRed else MaterialTheme.colorScheme.primary,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                val minutes = remainingSeconds / 60
                val seconds = remainingSeconds % 60
                Text(
                  text = String.format("%02d:%02d", minutes, seconds),
                  style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (remainingSeconds < 300) BrandRed else MaterialTheme.colorScheme.primary
                  )
                )
              }
            }
          }
        }
      },
      bottomBar = {
        Surface(color = MaterialTheme.colorScheme.surface, shadowElevation = 6.dp) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            OutlinedButton(
              onClick = { if (currentQuestionIndex > 0) currentQuestionIndex-- },
              enabled = currentQuestionIndex > 0
            ) {
              Text("Previous")
            }

            // Mark for review toggle
            FilterChip(
              selected = markedForReview.contains(currentQuestionIndex),
              onClick = {
                val updated = markedForReview.toMutableSet()
                if (updated.contains(currentQuestionIndex)) updated.remove(currentQuestionIndex) else updated.add(currentQuestionIndex)
                markedForReview = updated
              },
              label = { Text("Review") },
              leadingIcon = {
                Icon(imageVector = Icons.Default.Flag, contentDescription = null, modifier = Modifier.size(14.dp))
              }
            )

            if (currentQuestionIndex < totalQuestions - 1) {
              Button(onClick = { currentQuestionIndex++ }) {
                Text("Next")
              }
            } else {
              Button(
                onClick = { showConfirmSubmit = true },
                colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)
              ) {
                Text("Submit")
              }
            }
          }
        }
      }
    ) { padding ->
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(padding)
          .padding(16.dp)
      ) {
        // Question Palette Scroll
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(totalQuestions) { idx ->
            val isAnswered = userAnswers.containsKey(idx)
            val isMarked = markedForReview.contains(idx)
            val isCurrent = currentQuestionIndex == idx

            val bgColor = when {
              isCurrent -> MaterialTheme.colorScheme.primary
              isMarked -> BrandPurple.copy(alpha = 0.2f)
              isAnswered -> BrandGreen.copy(alpha = 0.2f)
              else -> MaterialTheme.colorScheme.surfaceVariant
            }

            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(bgColor)
                .border(
                  width = if (isCurrent) 2.dp else 1.dp,
                  color = if (isCurrent) MaterialTheme.colorScheme.primary else Color.Transparent,
                  shape = CircleShape
                )
                .clickable { currentQuestionIndex = idx },
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "${idx + 1}",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (isCurrent) Color.White else MaterialTheme.colorScheme.onSurface
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Question Details Card
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "Question ${currentQuestionIndex + 1}:",
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = currentQuestion.question,
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 4 Options
            currentQuestion.options.forEachIndexed { optIndex, optionText ->
              val isSelected = userAnswers[currentQuestionIndex] == optIndex

              Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                border = androidx.compose.foundation.BorderStroke(
                  1.dp,
                  if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 5.dp)
                  .clickable {
                    val updated = userAnswers.toMutableMap()
                    updated[currentQuestionIndex] = optIndex
                    userAnswers = updated
                  }
              ) {
                Row(
                  modifier = Modifier.padding(14.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = "${('A' + optIndex)}. ",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                  )
                  Text(
                    text = optionText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                  )
                  RadioButton(
                    selected = isSelected,
                    onClick = {
                      val updated = userAnswers.toMutableMap()
                      updated[currentQuestionIndex] = optIndex
                      userAnswers = updated
                    }
                  )
                }
              }
            }
          }
        }
      }
    }
  }

  // Confirmation dialog
  if (showConfirmSubmit) {
    AlertDialog(
      onDismissRequest = { showConfirmSubmit = false },
      title = { Text("Submit Examination?") },
      text = {
        Text("You have answered ${userAnswers.size} of $totalQuestions questions. Are you sure you want to finalize and calculate your score?")
      },
      confirmButton = {
        Button(onClick = {
          showConfirmSubmit = false
          isSubmitted = true
        }) {
          Text("Yes, Submit")
        }
      },
      dismissButton = {
        TextButton(onClick = { showConfirmSubmit = false }) {
          Text("Cancel")
        }
      }
    )
  }
}

// 3. Test Results View
@Composable
private fun TestResultView(
  test: MockTest,
  score: Int,
  correctCount: Int,
  totalQuestions: Int,
  userAnswers: Map<Int, Int>,
  onRetake: () -> Unit,
  onBackToLobby: () -> Unit
) {
  val accuracy = if (userAnswers.isNotEmpty()) (correctCount * 100) / userAnswers.size else 0

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Icon(
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = null,
            tint = BrandGold,
            modifier = Modifier.size(56.dp)
          )

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "Examination Completed!",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
          )

          Text(
            text = test.title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
          )

          Spacer(modifier = Modifier.height(18.dp))

          Text(
            text = "$score / ${test.totalMarks}",
            style = MaterialTheme.typography.displaySmall.copy(
              fontWeight = FontWeight.ExtraBold,
              color = MaterialTheme.colorScheme.primary
            )
          )
          Text(
            text = "Accuracy: $accuracy% • $correctCount Correct • ${userAnswers.size - correctCount} Incorrect",
            style = MaterialTheme.typography.bodyMedium
          )

          Spacer(modifier = Modifier.height(20.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            OutlinedButton(
              onClick = onRetake,
              modifier = Modifier.weight(1f)
            ) {
              Text("Retake Test")
            }

            Button(
              onClick = onBackToLobby,
              modifier = Modifier.weight(1f)
            ) {
              Text("Back to Tests")
            }
          }
        }
      }
    }

    item {
      Text(
        text = "Detailed Answer Analysis",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
    }

    itemsIndexed(test.questions) { index, q ->
      val userChoice = userAnswers[index]
      val isCorrect = userChoice == q.correctIndex

      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Question ${index + 1}",
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = if (isCorrect) "✓ Correct" else if (userChoice != null) "✗ Incorrect" else "Unattempted",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = if (isCorrect) BrandGreen else if (userChoice != null) BrandRed else MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }

          Spacer(modifier = Modifier.height(6.dp))
          Text(text = q.question, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Correct Answer: ${('A' + q.correctIndex)}. ${q.options[q.correctIndex]}",
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = BrandGreen)
          )

          Spacer(modifier = Modifier.height(6.dp))
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
          ) {
            Text(
              text = "Explanation: ${q.explanation}",
              modifier = Modifier.padding(10.dp),
              style = MaterialTheme.typography.bodySmall
            )
          }
        }
      }
    }
  }
}
