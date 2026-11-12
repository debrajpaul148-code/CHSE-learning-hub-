package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ChseDataProvider
import com.example.data.StudentRepository
import com.example.model.ChseAnnouncement
import com.example.model.Subject
import com.example.ui.components.ClassLevelSelector
import com.example.ui.components.SubjectCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  onNavigateToSubject: (String) -> Unit,
  onNavigateToChapter: (String) -> Unit,
  onOpenBooks: () -> Unit,
  onOpenVideos: () -> Unit,
  onOpenTests: () -> Unit,
  onOpenPyq: () -> Unit,
  onOpenFormulas: () -> Unit,
  onOpenPlanner: () -> Unit,
  onOpenDoubtSolver: () -> Unit,
  modifier: Modifier = Modifier
) {
  val selectedLevel by StudentRepository.selectedClassLevel.collectAsState()
  val profile by StudentRepository.profile.collectAsState()
  val goals by StudentRepository.goals.collectAsState()
  val announcements by StudentRepository.announcements.collectAsState()
  val activeChapterId by StudentRepository.currentActiveChapterId.collectAsState()
  val isAdminMode by StudentRepository.isAdminMode.collectAsState()

  var searchQuery by remember { mutableStateOf("") }
  var selectedAnnouncement by remember { mutableStateOf<ChseAnnouncement?>(null) }

  val filteredSubjects = remember(selectedLevel, searchQuery) {
    ChseDataProvider.subjects.filter {
      it.classLevel == selectedLevel &&
          (searchQuery.isBlank() || it.name.contains(searchQuery, ignoreCase = true) || it.odiaName.contains(searchQuery))
    }
  }

  val activeChapter = remember(activeChapterId) {
    ChseDataProvider.chapters.find { it.id == activeChapterId } ?: ChseDataProvider.chapters.first()
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background),
    contentPadding = PaddingValues(bottom = 24.dp)
  ) {
    // 1. Top Brand Banner with Class Switcher
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              colors = listOf(BrandPurple, BrandIndigo)
            )
          )
          .padding(top = 16.dp, bottom = 20.dp, start = 16.dp, end = 16.dp)
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "CHSE Learning Hub",
                  style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                  )
                )
                if (isAdminMode) {
                  Spacer(modifier = Modifier.width(8.dp))
                  Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = BrandGold
                  ) {
                    Text(
                      text = "ADMIN",
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                      style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                      )
                    )
                  }
                }
              }
              Text(
                text = "Your +2 Science Learning Platform (Odisha Board)",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.85f))
              )
            }

            // Streak Badge
            Surface(
              shape = RoundedCornerShape(20.dp),
              color = Color.White.copy(alpha = 0.2f),
              modifier = Modifier.clickable { onOpenPlanner() }
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.LocalFireDepartment,
                  contentDescription = "Streak",
                  tint = BrandGold,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "${profile.streakDays}d Streak",
                  style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Class 11 / Class 12 Switcher
          ClassLevelSelector(
            selectedLevel = selectedLevel,
            onLevelSelected = { StudentRepository.setClassLevel(it) },
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Search Bar
          OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search subjects, chapters, formulas, PYQs...") },
            leadingIcon = {
              Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = BrandIndigo)
            },
            trailingIcon = {
              if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { searchQuery = "" }) {
                  Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                }
              }
            },
            shape = RoundedCornerShape(14.dp),
            colors = TextFieldDefaults.colors(
              focusedContainerColor = MaterialTheme.colorScheme.surface,
              unfocusedContainerColor = MaterialTheme.colorScheme.surface,
              focusedIndicatorColor = Color.Transparent,
              unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("search_field")
          )
        }
      }
    }

    // 2. Continue Learning Hero Card
    item {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Continue Learning",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
          )
          TextButton(onClick = { onNavigateToChapter(activeChapter.id) }) {
            Text("View Chapter", style = MaterialTheme.typography.labelMedium)
          }
        }

        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToChapter(activeChapter.id) }
            .testTag("continue_learning_card"),
          elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
              ) {
                Text(
                  text = "Chapter ${activeChapter.chapterNumber} • ${activeChapter.weightageMarks} Marks Weightage",
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                  )
                )
              }

              Text(
                text = "${activeChapter.progressPercent}% Completed",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = activeChapter.title,
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
              text = activeChapter.odiaTitle,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = activeChapter.summary,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f),
              maxLines = 2,
              overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(14.dp))

            LinearProgressIndicator(
              progress = { activeChapter.progressPercent / 100f },
              modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
              color = MaterialTheme.colorScheme.primary,
              trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
              onClick = { onNavigateToChapter(activeChapter.id) },
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("resume_learning_button")
            ) {
              Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Resume Learning (12 Study Modules)")
            }
          }
        }
      }
    }

    // 3. Quick Action Grid (PW Books, Videos, Doubt Solver, Tests, PYQ, Planner)
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
          text = "Study Hub Services",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          QuickActionCard(
            title = "Digital Books",
            subtitle = "PW-Style Reader",
            icon = Icons.Default.MenuBook,
            color = BrandPurple,
            onClick = onOpenBooks,
            modifier = Modifier.weight(1f)
          )
          QuickActionCard(
            title = "Video Classes",
            subtitle = "CHSE Lectures",
            icon = Icons.Default.VideoLibrary,
            color = BrandBlue,
            onClick = onOpenVideos,
            modifier = Modifier.weight(1f)
          )
          QuickActionCard(
            title = "AI Doubt Solver",
            subtitle = "Ask in Odia/Eng",
            icon = Icons.Default.Psychology,
            color = BrandIndigo,
            onClick = onOpenDoubtSolver,
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          QuickActionCard(
            title = "Mock Tests",
            subtitle = "Board Pattern",
            icon = Icons.Default.Quiz,
            color = BrandGreen,
            onClick = onOpenTests,
            modifier = Modifier.weight(1f)
          )
          QuickActionCard(
            title = "PYQ Papers",
            subtitle = "2019-2024",
            icon = Icons.Default.HistoryEdu,
            color = BrandGold,
            onClick = onOpenPyq,
            modifier = Modifier.weight(1f)
          )
          QuickActionCard(
            title = "Formulas",
            subtitle = "Handy Revision",
            icon = Icons.Default.Functions,
            color = Color(0xFFD97706),
            onClick = onOpenFormulas,
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    // 4. Daily Study Goals & Today's Progress
    item {
      Column(modifier = Modifier.padding(16.dp)) {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = "Today's Study Goals",
                  style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                val completedCount = goals.count { it.isDone }
                Text(
                  text = "$completedCount of ${goals.size} tasks done • ${profile.todayMinutes}/${profile.dailyGoalMinutes} mins",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }

              IconButton(onClick = onOpenPlanner) {
                Icon(
                  imageVector = Icons.Default.EditCalendar,
                  contentDescription = "Planner",
                  tint = MaterialTheme.colorScheme.primary
                )
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            val completedRatio = if (goals.isNotEmpty()) goals.count { it.isDone }.toFloat() / goals.size else 0f
            LinearProgressIndicator(
              progress = { completedRatio },
              modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
              color = BrandGreen,
              trackColor = BrandGreen.copy(alpha = 0.2f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Show first 3 goals
            goals.take(3).forEach { goal ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Checkbox(
                  checked = goal.isDone,
                  onCheckedChange = { StudentRepository.toggleGoal(goal.id) },
                  colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                )
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = goal.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                      fontWeight = if (goal.isDone) FontWeight.Normal else FontWeight.Medium
                    ),
                    color = if (goal.isDone) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                  )
                  Text(
                    text = "${goal.subject} • ${goal.targetMinutes} mins",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              }
            }
          }
        }
      }
    }

    // 5. Main Subjects Section
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (selectedLevel == 12) "Class 12 Science Subjects" else "Class 11 Science Subjects",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
          )
          Text(
            text = "${filteredSubjects.size} Subjects",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Spacer(modifier = Modifier.height(10.dp))
      }
    }

    items(filteredSubjects) { subject ->
      Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
        SubjectCard(
          subject = subject,
          onClick = { onNavigateToSubject(subject.id) },
          modifier = Modifier.testTag("subject_card_${subject.id}")
        )
      }
    }

    // 6. CHSE Board Exam Countdown Banner
    item {
      Box(modifier = Modifier.padding(16.dp)) {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
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
                .background(MaterialTheme.colorScheme.secondary),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.HourglassTop,
                contentDescription = null,
                tint = Color.White
              )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "CHSE Annual Exam 2027",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSecondaryContainer
              )
              Text(
                text = "Target Percentage: ${profile.targetPercentage}% | 104 Days Remaining",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.85f)
              )
            }

            Button(
              onClick = onOpenPlanner,
              shape = RoundedCornerShape(10.dp),
              contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
              Text("Plan")
            }
          }
        }
      }
    }

    // 7. Official CHSE Odisha Announcements
    item {
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Campaign,
              contentDescription = null,
              tint = BrandPurple,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Official CHSE Announcements",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        announcements.forEach { ann ->
          Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp)
              .clickable { selectedAnnouncement = ann },
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
          ) {
            Row(
              modifier = Modifier.padding(14.dp),
              verticalAlignment = Alignment.Top
            ) {
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = if (ann.isImportant) BrandRed.copy(alpha = 0.15f) else BrandIndigo.copy(alpha = 0.15f)
              ) {
                Text(
                  text = ann.category,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (ann.isImportant) BrandRed else BrandIndigo
                  )
                )
              }

              Spacer(modifier = Modifier.width(10.dp))

              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = ann.title,
                  style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                  maxLines = 2,
                  overflow = TextOverflow.Ellipsis
                )
                Text(
                  text = ann.date,
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }
      }
    }
  }

  // Dialog for full announcement
  selectedAnnouncement?.let { ann ->
    AlertDialog(
      onDismissRequest = { selectedAnnouncement = null },
      title = {
        Text(text = ann.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
      },
      text = {
        Column {
          Text(text = "Date: ${ann.date} • ${ann.category}", style = MaterialTheme.typography.labelSmall, color = BrandPurple)
          Spacer(modifier = Modifier.height(8.dp))
          Text(text = ann.description, style = MaterialTheme.typography.bodyMedium)
        }
      },
      confirmButton = {
        TextButton(onClick = { selectedAnnouncement = null }) {
          Text("Close")
        }
      }
    )
  }
}

@Composable
fun QuickActionCard(
  title: String,
  subtitle: String,
  icon: ImageVector,
  color: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    onClick = onClick,
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Box(
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(color.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(imageVector = icon, contentDescription = title, tint = color, modifier = Modifier.size(22.dp))
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = title,
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1
      )

      Text(
        text = subtitle,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1
      )
    }
  }
}
