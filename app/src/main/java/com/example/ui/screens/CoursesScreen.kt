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
import com.example.model.Chapter
import com.example.model.Subject
import com.example.ui.components.ClassLevelSelector
import com.example.ui.components.SubjectIconBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(
  selectedSubjectId: String? = null,
  onNavigateToChapter: (String) -> Unit,
  onBack: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  val selectedLevel by StudentRepository.selectedClassLevel.collectAsState()
  val chapters by StudentRepository.chapters.collectAsState()

  val subjectsForLevel = remember(selectedLevel) {
    ChseDataProvider.subjects.filter { it.classLevel == selectedLevel }
  }

  var activeSubjectId by remember(selectedSubjectId, selectedLevel) {
    mutableStateOf(selectedSubjectId ?: subjectsForLevel.firstOrNull()?.id ?: "phy_12")
  }

  val currentSubject = remember(activeSubjectId) {
    ChseDataProvider.subjects.find { it.id == activeSubjectId } ?: subjectsForLevel.first()
  }

  val chaptersForSubject = remember(activeSubjectId, chapters) {
    chapters.filter { it.subjectId == activeSubjectId }
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
          verticalAlignment = Alignment.CenterVertically
        ) {
          if (onBack != null) {
            IconButton(onClick = onBack) {
              Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
          }
          Column {
            Text(
              text = "CHSE Science Courses",
              style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "Official Bureau Syllabus & Chapter Modules",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
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

        Spacer(modifier = Modifier.height(12.dp))

        // Subject Horizontal Tabs
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
          items(subjectsForLevel) { subj ->
            val isSelected = subj.id == activeSubjectId
            FilterChip(
              selected = isSelected,
              onClick = { activeSubjectId = subj.id },
              label = {
                Text(
                  text = subj.name,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
              },
              leadingIcon = {
                Icon(
                  imageVector = when (subj.iconType) {
                    "bolt" -> Icons.Default.Bolt
                    "science" -> Icons.Default.Science
                    "calculate" -> Icons.Default.Calculate
                    "eco" -> Icons.Default.Eco
                    "menu_book" -> Icons.Default.MenuBook
                    else -> Icons.Default.AutoStories
                  },
                  contentDescription = null,
                  modifier = Modifier.size(16.dp),
                  tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
              },
              shape = RoundedCornerShape(12.dp)
            )
          }
        }
      }
    }

    // Chapters List
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      contentPadding = PaddingValues(vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      item {
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color(currentSubject.colorHex).copy(alpha = 0.08f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            SubjectIconBadge(iconType = currentSubject.iconType, colorHex = currentSubject.colorHex)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "${currentSubject.name} (${currentSubject.odiaName})",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "${currentSubject.completedChaptersCount}/${currentSubject.chaptersCount} Chapters Mastered • Class ${currentSubject.classLevel}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      }

      if (chaptersForSubject.isEmpty()) {
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 40.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Icon(
                imageVector = Icons.Default.MenuBook,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(48.dp)
              )
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = "Chapters being prepared according to CHSE Odisha Syllabus",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      } else {
        items(chaptersForSubject) { chapter ->
          ChapterItemCard(
            chapter = chapter,
            onOpenChapter = { onNavigateToChapter(chapter.id) }
          )
        }
      }
    }
  }
}

@Composable
fun ChapterItemCard(
  chapter: Chapter,
  onOpenChapter: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    onClick = onOpenChapter,
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .testTag("chapter_card_${chapter.id}")
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = MaterialTheme.colorScheme.primaryContainer
        ) {
          Text(
            text = "CH ${chapter.chapterNumber} • ${chapter.weightageMarks} Marks Weightage",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onPrimaryContainer
            )
          )
        }

        if (chapter.isCompleted) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = BrandGreen.copy(alpha = 0.15f)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Completed",
                tint = BrandGreen,
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Completed",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = BrandGreen
                )
              )
            }
          }
        } else if (chapter.progressPercent > 0) {
          Text(
            text = "${chapter.progressPercent}% in progress",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.primary
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = chapter.title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface
      )

      Text(
        text = chapter.odiaTitle,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = chapter.summary,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.AutoStories,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "12 Study Modules",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
          )
        }

        FilledTonalButton(
          onClick = onOpenChapter,
          shape = RoundedCornerShape(10.dp),
          contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
        ) {
          Text("Open Chapter")
          Spacer(modifier = Modifier.width(4.dp))
          Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(16.dp))
        }
      }
    }
  }
}
