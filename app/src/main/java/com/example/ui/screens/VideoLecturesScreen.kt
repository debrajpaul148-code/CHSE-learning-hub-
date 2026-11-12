package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.data.ChseDataProvider
import com.example.model.VideoLecture
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoLecturesScreen(
  onOpenChapter: (String) -> Unit,
  onBack: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  val lectures = ChseDataProvider.videoLectures
  var selectedLecture by remember { mutableStateOf(lectures.first()) }
  var isPlaying by remember { mutableStateOf(false) }
  var playbackSpeed by remember { mutableStateOf("1.0x") }
  var progressFraction by remember { mutableFloatStateOf(0.25f) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    // Top Bar
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
            text = "CHSE Video Masterclasses",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "Subject Expert Lectures for +2 Science",
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
      // Video Player Stage
      item {
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
          elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            // Player Display Window
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF1E293B)),
              contentAlignment = Alignment.Center
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                  onClick = { isPlaying = !isPlaying },
                  modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(BrandPurple)
                ) {
                  Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Play/Pause",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                  )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                  text = if (isPlaying) "Playing at $playbackSpeed" else "Tap to Play Class",
                  style = MaterialTheme.typography.labelMedium.copy(color = Color.White)
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Progress Slider
            Slider(
              value = progressFraction,
              onValueChange = { progressFraction = it },
              colors = SliderDefaults.colors(
                thumbColor = BrandPurple,
                activeTrackColor = BrandPurple,
                inactiveTrackColor = Color.White.copy(alpha = 0.2f)
              )
            )

            // Playback Controls Row
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "${(progressFraction * 45).toInt()}:00 / ${selectedLecture.duration}",
                style = MaterialTheme.typography.labelSmall.copy(color = Color.White.copy(alpha = 0.8f))
              )

              Row(verticalAlignment = Alignment.CenterVertically) {
                // Speed Selector
                listOf("1.0x", "1.25x", "1.5x", "2.0x").forEach { spd ->
                  Text(
                    text = spd,
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = if (playbackSpeed == spd) FontWeight.Bold else FontWeight.Normal,
                      color = if (playbackSpeed == spd) BrandGold else Color.White.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier
                      .clip(RoundedCornerShape(4.dp))
                      .clickable { playbackSpeed = spd }
                      .padding(horizontal = 6.dp, vertical = 2.dp)
                  )
                }
              }
            }
          }
        }
      }

      // Current Lecture Info & Notes Button
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = selectedLecture.title,
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Instructor: ${selectedLecture.tutorName}",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = selectedLecture.keySummary,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
              onClick = { onOpenChapter(selectedLecture.chapterId) },
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Icon(imageVector = Icons.Default.Description, contentDescription = null)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Open Accompanying Chapter Notes & Formulas")
            }
          }
        }
      }

      // Timestamps Jump
      item {
        Text(
          text = "Key Timestamps & Topics",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
      }

      items(selectedLecture.timestamps) { (time, topic) ->
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = MaterialTheme.colorScheme.surface,
          modifier = Modifier
            .fillMaxWidth()
            .clickable {
              progressFraction = 0.5f
              isPlaying = true
            }
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = MaterialTheme.colorScheme.primaryContainer
            ) {
              Text(
                text = time,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
              text = topic,
              style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
            )
          }
        }
      }

      // More Lectures in Playlist
      item {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = "CHSE Science Playlists (${lectures.size})",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
      }

      items(lectures) { lec ->
        Card(
          onClick = {
            selectedLecture = lec
            isPlaying = true
          },
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (selectedLecture.id == lec.id) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface
          ),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = if (selectedLecture.id == lec.id) Icons.Default.PlayCircleFilled else Icons.Default.VideoFile,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = lec.title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 1
              )
              Text(
                text = "${lec.tutorName} • ${lec.duration}",
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
