package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.ChseDataProvider
import com.example.data.StudentRepository
import com.example.model.Subject
import com.example.ui.components.ClassLevelSelector
import com.example.ui.components.SubjectIconBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksScreen(
  onOpenChapter: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val selectedLevel by StudentRepository.selectedClassLevel.collectAsState()
  var searchQuery by remember { mutableStateOf("") }
  var selectedSubjectFilter by remember { mutableStateOf<String?>(null) }
  var downloadedBooks by remember { mutableStateOf(setOf("ch_phy12_1", "ch_chem12_1")) }

  val subjects = remember(selectedLevel) {
    ChseDataProvider.subjects.filter { it.classLevel == selectedLevel }
  }

  val chapters = remember(selectedLevel, selectedSubjectFilter, searchQuery) {
    ChseDataProvider.chapters.filter { ch ->
      ch.classLevel == selectedLevel &&
          (selectedSubjectFilter == null || ch.subjectId == selectedSubjectFilter) &&
          (searchQuery.isBlank() || ch.title.contains(searchQuery, ignoreCase = true) || ch.odiaTitle.contains(searchQuery))
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    // Header
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
          Column {
            Text(
              text = "CHSE Digital Book Library",
              style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Odisha State Textbook Bureau Official Editions",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = BrandPurple.copy(alpha = 0.15f)
          ) {
            Text(
              text = "PW Books Style",
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandPurple)
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Class Switcher
        ClassLevelSelector(
          selectedLevel = selectedLevel,
          onLevelSelected = { StudentRepository.setClassLevel(it) },
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Search Bar
        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          placeholder = { Text("Search textbook chapters, topics...") },
          leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
          },
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Filter by subject chips
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          item {
            FilterChip(
              selected = selectedSubjectFilter == null,
              onClick = { selectedSubjectFilter = null },
              label = { Text("All Subjects") },
              shape = RoundedCornerShape(10.dp)
            )
          }
          items(subjects) { subj ->
            FilterChip(
              selected = selectedSubjectFilter == subj.id,
              onClick = { selectedSubjectFilter = subj.id },
              label = { Text(subj.name) },
              shape = RoundedCornerShape(10.dp)
            )
          }
        }
      }
    }

    // Book Chapters List
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      contentPadding = PaddingValues(vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      item {
        Text(
          text = "Available Textbooks & Modules (${chapters.size})",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onBackground
        )
      }

      items(chapters) { ch ->
        val isDownloaded = downloadedBooks.contains(ch.id)
        val subject = ChseDataProvider.subjects.find { it.id == ch.subjectId }

        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("book_card_${ch.id}")
            .clickable { onOpenChapter(ch.id) }
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Book Cover thumbnail simulation
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = Color(subject?.colorHex ?: 0xFF5B21B6),
              modifier = Modifier
                .width(64.dp)
                .height(84.dp)
            ) {
              Column(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(6.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Text(
                  text = "CHSE",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                  )
                )
                Icon(
                  imageVector = Icons.Default.MenuBook,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier.size(24.dp)
                )
                Text(
                  text = "CH ${ch.chapterNumber}",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.9f)
                  )
                )
              }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = subject?.name ?: "Science",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(subject?.colorHex ?: 0xFF5B21B6)
                  )
                )

                if (isDownloaded) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = Icons.Default.DownloadDone,
                      contentDescription = "Downloaded",
                      tint = BrandGreen,
                      modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = "Offline Ready",
                      style = MaterialTheme.typography.labelSmall.copy(
                        color = BrandGreen,
                        fontWeight = FontWeight.SemiBold
                      )
                    )
                  }
                }
              }

              Spacer(modifier = Modifier.height(4.dp))

              Text(
                text = ch.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )

              Text(
                text = ch.odiaTitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
              )

              Spacer(modifier = Modifier.height(8.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Weightage: ${ch.weightageMarks} Marks",
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row {
                  IconButton(
                    onClick = {
                      downloadedBooks = if (isDownloaded) {
                        downloadedBooks - ch.id
                      } else {
                        downloadedBooks + ch.id
                      }
                    },
                    modifier = Modifier.size(32.dp)
                  ) {
                    Icon(
                      imageVector = if (isDownloaded) Icons.Default.DeleteOutline else Icons.Default.FileDownload,
                      contentDescription = "Download for offline reading",
                      tint = MaterialTheme.colorScheme.primary,
                      modifier = Modifier.size(18.dp)
                    )
                  }

                  Spacer(modifier = Modifier.width(6.dp))

                  FilledTonalButton(
                    onClick = { onOpenChapter(ch.id) },
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                  ) {
                    Text("Read Book")
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
