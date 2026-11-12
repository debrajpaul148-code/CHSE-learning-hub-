package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.model.Subject
import com.example.ui.components.ClassLevelSelector
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
  onOpenPlanner: () -> Unit,
  onOpenTests: () -> Unit,
  modifier: Modifier = Modifier
) {
  val profile by StudentRepository.profile.collectAsState()
  val selectedLevel by StudentRepository.selectedClassLevel.collectAsState()
  val isAdminMode by StudentRepository.isAdminMode.collectAsState()

  var showAddAnnouncementDialog by remember { mutableStateOf(false) }
  var announcementTitle by remember { mutableStateOf("") }
  var announcementDesc by remember { mutableStateOf("") }

  val subjects = remember(selectedLevel) {
    ChseDataProvider.subjects.filter { it.classLevel == selectedLevel }
  }

  val totalChapters = subjects.sumOf { it.chaptersCount }
  val completedChapters = subjects.sumOf { it.completedChaptersCount }
  val overallProgress = if (totalChapters > 0) completedChapters.toFloat() / totalChapters.toFloat() else 0f

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    Surface(
      color = MaterialTheme.colorScheme.surface,
      shadowElevation = 2.dp
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Student Learning Profile",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
          )

          // Admin Mode Toggle Chip
          FilterChip(
            selected = isAdminMode,
            onClick = { StudentRepository.toggleAdminMode() },
            label = {
              Text(if (isAdminMode) "Admin ON" else "Admin Mode", style = MaterialTheme.typography.labelSmall)
            },
            leadingIcon = {
              Icon(imageVector = Icons.Default.AdminPanelSettings, contentDescription = null, modifier = Modifier.size(16.dp))
            },
            shape = RoundedCornerShape(10.dp)
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
      // 1. Profile Header Card
      item {
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(56.dp)
                  .clip(CircleShape)
                  .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "OS",
                  style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                  )
                )
              }

              Spacer(modifier = Modifier.width(14.dp))

              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = profile.name,
                  style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                  text = "+2 Science Aspirant • Class $selectedLevel",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
              }

              Surface(
                shape = RoundedCornerShape(12.dp),
                color = BrandGold
              ) {
                Text(
                  text = "${profile.targetPercentage}% Target",
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color.Black)
                )
              }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Stats Row
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              ProfileStatItem(label = "Study Hours", value = "${profile.totalStudyHours}h")
              ProfileStatItem(label = "Streak", value = "${profile.streakDays} Days")
              ProfileStatItem(label = "Completed", value = "$completedChapters/$totalChapters")
              ProfileStatItem(label = "Accuracy", value = "92%")
            }
          }
        }
      }

      // 2. Syllabus Completion Tracking
      item {
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
              Text(
                text = "Overall Syllabus Completion",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "${(overallProgress * 100).toInt()}%",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
              progress = { overallProgress },
              modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
              color = MaterialTheme.colorScheme.primary,
              trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Subject Breakdown Bars
            subjects.forEach { subj ->
              val subjProg = if (subj.chaptersCount > 0) subj.completedChaptersCount.toFloat() / subj.chaptersCount else 0f
              Column(modifier = Modifier.padding(vertical = 4.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(text = subj.name, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium))
                  Text(text = "${subj.completedChaptersCount}/${subj.chaptersCount}", style = MaterialTheme.typography.labelSmall)
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                  progress = { subjProg },
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                  color = Color(subj.colorHex),
                  trackColor = Color(subj.colorHex).copy(alpha = 0.15f)
                )
              }
            }
          }
        }
      }

      // 3. Strong & Weak Chapters Analysis
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "Academic Strength & Weakness Analysis",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = BrandGreen.copy(alpha = 0.1f),
                modifier = Modifier.weight(1f)
              ) {
                Column(modifier = Modifier.padding(12.dp)) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = BrandGreen, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Strong Chapters", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandGreen))
                  }
                  Spacer(modifier = Modifier.height(6.dp))
                  Text(text = "• Matrices & Determinants\n• Electric Charges & Fields\n• Solutions (Colligative)", style = MaterialTheme.typography.bodySmall)
                }
              }

              Surface(
                shape = RoundedCornerShape(12.dp),
                color = BrandRed.copy(alpha = 0.1f),
                modifier = Modifier.weight(1f)
              ) {
                Column(modifier = Modifier.padding(12.dp)) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.TrendingDown, contentDescription = null, tint = BrandRed, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Needs Revision", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandRed))
                  }
                  Spacer(modifier = Modifier.height(6.dp))
                  Text(text = "• Ray Optics & Instruments\n• Aldehydes & Ketones\n• Calculus Integrals", style = MaterialTheme.typography.bodySmall)
                }
              }
            }
          }
        }
      }

      // 4. Language Preferences
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "Study Language Preference",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(10.dp))

            listOf("English", "Odia (ଓଡ଼ିଆ)", "Hindi (हिन्दी)").forEach { lang ->
              val isSelected = profile.languagePref.startsWith(lang.take(3))
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp)
                  .clickable { StudentRepository.setLanguagePreference(lang) },
                verticalAlignment = Alignment.CenterVertically
              ) {
                RadioButton(
                  selected = isSelected,
                  onClick = { StudentRepository.setLanguagePreference(lang) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = lang, style = MaterialTheme.typography.bodyMedium)
              }
            }
          }
        }
      }

      // 5. Admin Panel (When toggled ON)
      if (isAdminMode) {
        item {
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BrandGold.copy(alpha = 0.12f)),
            border = androidx.compose.foundation.BorderStroke(1.dp, BrandGold.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.AdminPanelSettings, contentDescription = null, tint = BrandGold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "CHSE Administrator Console",
                  style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFFB45309))
                )
              }

              Spacer(modifier = Modifier.height(8.dp))

              Text(
                text = "Manage syllabus, publish official CHSE board notifications, upload authentic question papers, and organize study modules.",
                style = MaterialTheme.typography.bodySmall
              )

              Spacer(modifier = Modifier.height(12.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                Button(
                  onClick = { showAddAnnouncementDialog = true },
                  shape = RoundedCornerShape(10.dp),
                  modifier = Modifier.weight(1f)
                ) {
                  Icon(imageVector = Icons.Default.Campaign, contentDescription = null, modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("Post Notice")
                }

                OutlinedButton(
                  onClick = { /* Simulated syllabus sync */ },
                  shape = RoundedCornerShape(10.dp),
                  modifier = Modifier.weight(1f)
                ) {
                  Icon(imageVector = Icons.Default.Sync, contentDescription = null, modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("Sync Bureau")
                }
              }
            }
          }
        }
      }
    }
  }

  // Add announcement dialog
  if (showAddAnnouncementDialog) {
    AlertDialog(
      onDismissRequest = { showAddAnnouncementDialog = false },
      title = { Text("Publish Board Announcement") },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          OutlinedTextField(
            value = announcementTitle,
            onValueChange = { announcementTitle = it },
            label = { Text("Announcement Title") },
            modifier = Modifier.fillMaxWidth()
          )

          OutlinedTextField(
            value = announcementDesc,
            onValueChange = { announcementDesc = it },
            label = { Text("Notification Content & Details") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4
          )
        }
      },
      confirmButton = {
        Button(onClick = {
          if (announcementTitle.isNotBlank()) {
            StudentRepository.addAnnouncement(announcementTitle, "Board Notice", announcementDesc)
            announcementTitle = ""
            announcementDesc = ""
            showAddAnnouncementDialog = false
          }
        }) {
          Text("Publish")
        }
      },
      dismissButton = {
        TextButton(onClick = { showAddAnnouncementDialog = false }) {
          Text("Cancel")
        }
      }
    )
  }
}

@Composable
fun ProfileStatItem(label: String, value: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
      text = value,
      style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
      color = MaterialTheme.colorScheme.onPrimaryContainer
    )
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
    )
  }
}
