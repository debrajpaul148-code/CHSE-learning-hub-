package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.model.StudyGoal
import com.example.model.TimetableItem
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyPlannerScreen(
  onBack: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  val goals by StudentRepository.goals.collectAsState()
  val profile by StudentRepository.profile.collectAsState()
  val timetable = ChseDataProvider.timetable

  // Pomodoro Focus Timer State
  var timerSeconds by remember { mutableIntStateOf(25 * 60) }
  var isTimerRunning by remember { mutableStateOf(false) }
  var isBreakMode by remember { mutableStateOf(false) }

  // Add goal dialog state
  var showAddGoalDialog by remember { mutableStateOf(false) }
  var newGoalTitle by remember { mutableStateOf("") }
  var newGoalSubject by remember { mutableStateOf("Physics") }
  var newGoalMinutes by remember { mutableStateOf("45") }

  // Timer Tick
  LaunchedEffect(isTimerRunning, timerSeconds) {
    if (isTimerRunning && timerSeconds > 0) {
      delay(1000L)
      timerSeconds--
    } else if (isTimerRunning && timerSeconds == 0) {
      isTimerRunning = false
      if (!isBreakMode) {
        isBreakMode = true
        timerSeconds = 5 * 60
      } else {
        isBreakMode = false
        timerSeconds = 25 * 60
      }
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    Surface(
      color = MaterialTheme.colorScheme.surface,
      shadowElevation = 2.dp
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (onBack != null) {
          IconButton(onClick = onBack) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
          }
        }
        Column {
          Text(
            text = "CHSE Smart Study Planner",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "Timetable, Daily Targets & Focus Study Timer",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // 1. Exam Countdown Card
      item {
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.Event, contentDescription = null, tint = Color.White)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "CHSE Board Examination 2027",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimaryContainer
              )
              Text(
                text = "104 Days Remaining • Target: ${profile.targetPercentage}%",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
              )
            }

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = BrandGold
            ) {
              Text(
                text = "${profile.streakDays}d Streak",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color.Black)
              )
            }
          }
        }
      }

      // 2. Pomodoro Focus Study Timer
      item {
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = if (isBreakMode) "5-Min Refresh Break" else "25-Min Focus Study Session",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = if (isBreakMode) BrandGreen else MaterialTheme.colorScheme.primary
              )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Time Display
            val m = timerSeconds / 60
            val s = timerSeconds % 60
            Text(
              text = String.format("%02d:%02d", m, s),
              style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
              )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
              Button(
                onClick = { isTimerRunning = !isTimerRunning },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = if (isTimerRunning) BrandRed else MaterialTheme.colorScheme.primary
                )
              ) {
                Icon(
                  imageVector = if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                  contentDescription = null
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(if (isTimerRunning) "Pause Session" else "Start Focus")
              }

              OutlinedButton(
                onClick = {
                  isTimerRunning = false
                  isBreakMode = false
                  timerSeconds = 25 * 60
                },
                shape = RoundedCornerShape(12.dp)
              ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset")
                Spacer(modifier = Modifier.width(6.dp))
                Text("Reset")
              }
            }
          }
        }
      }

      // 3. Daily Goals List
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Daily Study Targets",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          TextButton(onClick = { showAddGoalDialog = true }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Add Target")
          }
        }
      }

      items(goals) { goal ->
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Checkbox(
              checked = goal.isDone,
              onCheckedChange = { StudentRepository.toggleGoal(goal.id) },
              colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = goal.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = if (goal.isDone) FontWeight.Normal else FontWeight.SemiBold
                ),
                color = if (goal.isDone) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "${goal.subject} • ${goal.targetMinutes} minutes target",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            if (goal.isDone) {
              Icon(imageVector = Icons.Default.Check, contentDescription = "Done", tint = BrandGreen)
            }
          }
        }
      }

      // 4. Weekly Timetable
      item {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = "Weekly Study Timetable (CHSE Syllabus)",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
      }

      items(timetable) { item ->
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = MaterialTheme.colorScheme.primaryContainer
            ) {
              Text(
                text = item.day.take(3),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.primary
                )
              )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "${item.subject}: ${item.chapterTitle}",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
              )
              Text(
                text = item.timeSlot,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      }
    }
  }

  // Add Goal Dialog
  if (showAddGoalDialog) {
    AlertDialog(
      onDismissRequest = { showAddGoalDialog = false },
      title = { Text("Add Study Target") },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          OutlinedTextField(
            value = newGoalTitle,
            onValueChange = { newGoalTitle = it },
            label = { Text("Goal Title / Topic") },
            modifier = Modifier.fillMaxWidth()
          )

          OutlinedTextField(
            value = newGoalSubject,
            onValueChange = { newGoalSubject = it },
            label = { Text("Subject (Physics, Chem, Math, Bio...)") },
            modifier = Modifier.fillMaxWidth()
          )

          OutlinedTextField(
            value = newGoalMinutes,
            onValueChange = { newGoalMinutes = it },
            label = { Text("Target Minutes") },
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(onClick = {
          if (newGoalTitle.isNotBlank()) {
            val mins = newGoalMinutes.toIntOrNull() ?: 30
            StudentRepository.addGoal(newGoalTitle, newGoalSubject, mins)
            newGoalTitle = ""
            showAddGoalDialog = false
          }
        }) {
          Text("Add")
        }
      },
      dismissButton = {
        TextButton(onClick = { showAddGoalDialog = false }) {
          Text("Cancel")
        }
      }
    )
  }
}
