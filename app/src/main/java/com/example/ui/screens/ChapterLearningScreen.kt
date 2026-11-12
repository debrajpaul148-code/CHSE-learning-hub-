package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai.ChseDoubtSolver
import com.example.ai.DoubtMessage
import com.example.data.ChseDataProvider
import com.example.data.StudentRepository
import com.example.model.*
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterLearningScreen(
  chapterId: String,
  onBack: () -> Unit,
  onStartChapterTest: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBack() }

  val chapter = remember(chapterId) {
    ChseDataProvider.chapters.find { it.id == chapterId } ?: ChseDataProvider.chapters.first()
  }
  val content = remember(chapterId) {
    // For demo/prototype use flagship rich content, adjusted for chapter id
    ChseDataProvider.flagshipChapterContent.copy(chapterId = chapterId)
  }

  // Update repository's active chapter
  LaunchedEffect(chapterId) {
    StudentRepository.setActiveChapter(chapterId)
  }

  var selectedSectionIndex by remember { mutableIntStateOf(0) }
  val sections = listOf(
    "1. Overview",
    "2. Notes",
    "3. Textbook",
    "4. Definitions",
    "5. Formulas",
    "6. Diagrams",
    "7. PYQs",
    "8. MCQ Practice",
    "9. Short Q&A",
    "10. Long Q&A",
    "11. Test",
    "12. AI Doubt Solver"
  )

  var isBookmarked by remember {
    mutableStateOf(StudentRepository.isBookmarked("chapter_$chapterId"))
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = "CH ${chapter.chapterNumber}: ${chapter.title}",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              maxLines = 1
            )
            Text(
              text = "CHSE Weightage: ${chapter.weightageMarks} Marks • ${sections[selectedSectionIndex]}",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
          }
        },
        actions = {
          IconButton(onClick = {
            isBookmarked = StudentRepository.toggleBookmark("chapter_$chapterId")
          }) {
            Icon(
              imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
              contentDescription = "Bookmark",
              tint = if (isBookmarked) BrandGold else MaterialTheme.colorScheme.onSurface
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface
        )
      )
    }
  ) { padding ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(padding)
        .background(MaterialTheme.colorScheme.background)
    ) {
      // 12-Section Scrollable Tab Row
      ScrollableTabRow(
        selectedTabIndex = selectedSectionIndex,
        edgePadding = 12.dp,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        divider = { HorizontalDivider() }
      ) {
        sections.forEachIndexed { index, title ->
          Tab(
            selected = selectedSectionIndex == index,
            onClick = { selectedSectionIndex = index },
            text = {
              Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = if (selectedSectionIndex == index) FontWeight.Bold else FontWeight.Normal
                )
              )
            }
          )
        }
      }

      // Section Content Body
      Box(modifier = Modifier.fillMaxSize()) {
        when (selectedSectionIndex) {
          0 -> SectionOverview(overview = content.overview, chapter = chapter)
          1 -> SectionCompleteNotes(notes = content.notes)
          2 -> SectionTextbookReader(chapterId = chapterId, pages = content.textbookPages)
          3 -> SectionDefinitions(definitions = content.definitions)
          4 -> SectionFormulas(formulas = content.formulas)
          5 -> SectionDiagrams(diagrams = content.diagrams)
          6 -> SectionPyqs(pyqs = content.pyqs)
          7 -> SectionMcqPractice(mcqs = content.mcqs)
          8 -> SectionShortAnswers(items = content.shortAnswers)
          9 -> SectionLongAnswers(items = content.longAnswers)
          10 -> SectionChapterTestLauncher(
            chapter = chapter,
            onStartTest = { onStartChapterTest("test_1") }
          )
          11 -> SectionAiDoubtSolver(chapterTitle = chapter.title, doubtPrompts = content.doubtPrompts)
        }
      }
    }
  }
}

// 1. Overview Section
@Composable
private fun SectionOverview(overview: ChapterOverview, chapter: Chapter) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "CHSE Odisha Exam Blueprint",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onPrimaryContainer
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = overview.chseWeightageNotice,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
          )
        }
      }
    }

    item {
      Text(
        text = "Key Syllabus Concepts",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
      )
    }

    items(overview.keyConcepts) { concept ->
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
          Icon(
            imageVector = Icons.Default.CheckCircleOutline,
            contentDescription = null,
            tint = BrandGreen,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(12.dp))
          Text(
            text = concept,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
          )
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "Examiner Tips for Higher Marks",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
      )
    }

    items(overview.examTips) { tip ->
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BrandAmberLight.copy(alpha = 0.5f))
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          verticalAlignment = Alignment.Top
        ) {
          Icon(
            imageVector = Icons.Default.Lightbulb,
            contentDescription = null,
            tint = BrandGold,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = tip,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF78350F)
          )
        }
      }
    }
  }
}

// 2. Complete Notes Section
@Composable
private fun SectionCompleteNotes(notes: List<NoteSection>) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    items(notes) { note ->
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = note.heading,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = note.content,
            style = MaterialTheme.typography.bodyMedium
          )

          if (note.bulletPoints.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))
            note.bulletPoints.forEach { point ->
              Row(
                modifier = Modifier.padding(vertical = 3.dp),
                verticalAlignment = Alignment.Top
              ) {
                Text(text = "• ", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.primary)
                Text(text = point, style = MaterialTheme.typography.bodyMedium)
              }
            }
          }

          if (note.examTakeaway.isNotBlank()) {
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = MaterialTheme.colorScheme.surfaceVariant
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = BrandGold, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = note.examTakeaway,
                  style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }
      }
    }
  }
}

// 3. Digital Textbook Reader (PW Books style)
@Composable
private fun SectionTextbookReader(chapterId: String, pages: List<TextbookPage>) {
  var currentPageIndex by remember { mutableIntStateOf(0) }
  var fontSizeSp by remember { mutableFloatStateOf(16f) }
  var personalNote by remember { mutableStateOf(StudentRepository.getPersonalNote(chapterId)) }
  var isEditingNote by remember { mutableStateOf(false) }

  val currentPage = pages.getOrNull(currentPageIndex) ?: pages.first()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Textbook Bureau Reader",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
          IconButton(onClick = { if (fontSizeSp > 13f) fontSizeSp -= 2f }) {
            Icon(imageVector = Icons.Default.FormatSize, contentDescription = "Decrease size", modifier = Modifier.size(18.dp))
          }
          Text(text = "${fontSizeSp.toInt()}pt", style = MaterialTheme.typography.labelSmall)
          IconButton(onClick = { if (fontSizeSp < 24f) fontSizeSp += 2f }) {
            Icon(imageVector = Icons.Default.FormatSize, contentDescription = "Increase size", modifier = Modifier.size(24.dp))
          }
        }
      }
    }

    // Page Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Page ${currentPage.pageNumber} of ${pages.size}",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.primary
            )
            Text(
              text = "CHSE Bureau Curriculum",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = currentPage.pageTitle,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
          )

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = currentPage.content,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontSize = fontSizeSp.sp,
              lineHeight = (fontSizeSp * 1.5).sp
            ),
            color = MaterialTheme.colorScheme.onSurface
          )

          if (currentPage.callout.isNotBlank()) {
            Spacer(modifier = Modifier.height(14.dp))
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = BrandIndigo.copy(alpha = 0.1f),
              border = androidx.compose.foundation.BorderStroke(1.dp, BrandIndigo.copy(alpha = 0.3f))
            ) {
              Text(
                text = currentPage.callout,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = BrandIndigo
              )
            }
          }
        }
      }
    }

    // Page navigation buttons
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        OutlinedButton(
          onClick = { if (currentPageIndex > 0) currentPageIndex-- },
          enabled = currentPageIndex > 0
        ) {
          Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Prev Page")
        }

        Button(
          onClick = { if (currentPageIndex < pages.size - 1) currentPageIndex++ },
          enabled = currentPageIndex < pages.size - 1
        ) {
          Text("Next Page")
          Spacer(modifier = Modifier.width(6.dp))
          Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
        }
      }
    }

    // Personal Student Notes
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.NoteAlt, contentDescription = null, tint = BrandPurple)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "My Personal Chapter Notes",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
              )
            }

            TextButton(onClick = {
              if (isEditingNote) {
                StudentRepository.savePersonalNote(chapterId, personalNote)
              }
              isEditingNote = !isEditingNote
            }) {
              Text(if (isEditingNote) "Save" else "Edit Note")
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          if (isEditingNote) {
            OutlinedTextField(
              value = personalNote,
              onValueChange = { personalNote = it },
              placeholder = { Text("Write personal formulas, shortcuts, or teacher tips...") },
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(12.dp)
            )
          } else {
            Text(
              text = if (personalNote.isBlank()) "No notes added yet. Tap 'Edit Note' to write your personal summary!" else personalNote,
              style = MaterialTheme.typography.bodyMedium,
              color = if (personalNote.isBlank()) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
            )
          }
        }
      }
    }
  }
}

// 4. Definitions Section
@Composable
private fun SectionDefinitions(definitions: List<DefinitionItem>) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    items(definitions) { def ->
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = def.term,
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.primary
            )
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = MaterialTheme.colorScheme.primaryContainer
            ) {
              Text(
                text = "${def.marks} Mark Question",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(text = def.definition, style = MaterialTheme.typography.bodyMedium)

          if (def.example.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Example / SI Unit: ${def.example}",
              style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }
  }
}

// 5. Formulas Section
@Composable
private fun SectionFormulas(formulas: List<FormulaItem>) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    items(formulas) { form ->
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = form.name,
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = BrandIndigo.copy(alpha = 0.15f)
            ) {
              Text(
                text = form.siUnit,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandIndigo)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Formula Display Box
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxWidth()
          ) {
            Box(
              modifier = Modifier.padding(14.dp),
              contentAlignment = Alignment.CenterStart
            ) {
              Text(
                text = form.formula,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  fontFamily = FontFamily.Monospace,
                  color = MaterialTheme.colorScheme.primary
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(text = "Variables: ${form.variables}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "Application: ${form.application}", style = MaterialTheme.typography.bodySmall)
        }
      }
    }
  }
}

// 6. Diagrams Section
@Composable
private fun SectionDiagrams(diagrams: List<DiagramItem>) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    items(diagrams) { diag ->
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = diag.title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
          )

          Spacer(modifier = Modifier.height(6.dp))
          Text(text = diag.description, style = MaterialTheme.typography.bodyMedium)

          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Crucial Examination Labels:",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
          )

          diag.keyLabels.forEach { label ->
            Row(
              modifier = Modifier.padding(vertical = 2.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(imageVector = Icons.Default.Label, contentDescription = null, tint = BrandCyan, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = label, style = MaterialTheme.typography.bodySmall)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = BrandAmberLight.copy(alpha = 0.5f)
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text(text = "Drawing & Presentation Tip:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF78350F)))
              Text(text = diag.drawingTip, style = MaterialTheme.typography.bodySmall, color = Color(0xFF78350F))
            }
          }
        }
      }
    }
  }
}

// 7. PYQs Section
@Composable
private fun SectionPyqs(pyqs: List<PyqItem>) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    items(pyqs) { pyq ->
      var isExpanded by remember { mutableStateOf(false) }

      Card(
        shape = RoundedCornerShape(14.dp),
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
            Surface(
              shape = RoundedCornerShape(6.dp),
              color = BrandGold.copy(alpha = 0.2f)
            ) {
              Text(
                text = "CHSE ${pyq.year} • ${pyq.marks} Marks",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFFB45309))
              )
            }

            Text(
              text = pyq.groupName,
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = pyq.questionText,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
          )

          Spacer(modifier = Modifier.height(10.dp))
          TextButton(
            onClick = { isExpanded = !isExpanded },
            contentPadding = PaddingValues(0.dp)
          ) {
            Text(if (isExpanded) "Hide Model Solution" else "View Step-by-Step Model Solution")
            Icon(
              imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
              contentDescription = null
            )
          }

          if (isExpanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Text(
                  text = "Official CHSE Marking Scheme Breakdown:",
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.height(6.dp))
                pyq.solutionSteps.forEach { step ->
                  Row(
                    modifier = Modifier.padding(vertical = 3.dp),
                    verticalAlignment = Alignment.Top
                  ) {
                    Text(text = "✓ ", color = BrandGreen, fontWeight = FontWeight.Bold)
                    Text(text = step, style = MaterialTheme.typography.bodySmall)
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

// 8. MCQ Practice Section
@Composable
private fun SectionMcqPractice(mcqs: List<McqItem>) {
  var selectedOptions by remember { mutableStateOf(mutableMapOf<String, Int>()) }
  var showExplanations by remember { mutableStateOf(mutableMapOf<String, Boolean>()) }

  val answeredCount = selectedOptions.size
  val correctCount = selectedOptions.count { (id, optIndex) ->
    mcqs.find { it.id == id }?.correctIndex == optIndex
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Practice Score",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
              color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
              text = "$correctCount of $answeredCount correct (Total ${mcqs.size} MCQs)",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onPrimaryContainer
            )
          }

          if (answeredCount > 0) {
            val accuracy = (correctCount * 100) / answeredCount
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = MaterialTheme.colorScheme.primary
            ) {
              Text(
                text = "$accuracy% Accuracy",
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = Color.White)
              )
            }
          }
        }
      }
    }

    items(mcqs) { mcq ->
      val userSelected = selectedOptions[mcq.id]
      val isAnswered = userSelected != null

      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = mcq.question,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
          )

          Spacer(modifier = Modifier.height(12.dp))

          mcq.options.forEachIndexed { index, option ->
            val isOptionSelected = userSelected == index
            val isCorrectOption = mcq.correctIndex == index

            val backgroundColor = when {
              !isAnswered -> if (isOptionSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
              isCorrectOption -> BrandGreen.copy(alpha = 0.15f)
              isOptionSelected -> BrandRed.copy(alpha = 0.15f)
              else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            }

            val borderColor = when {
              !isAnswered -> Color.Transparent
              isCorrectOption -> BrandGreen
              isOptionSelected -> BrandRed
              else -> Color.Transparent
            }

            Surface(
              shape = RoundedCornerShape(10.dp),
              color = backgroundColor,
              border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable {
                  if (!isAnswered) {
                    val updated = selectedOptions.toMutableMap()
                    updated[mcq.id] = index
                    selectedOptions = updated
                  }
                }
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "${('A' + index)}. ",
                  style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                  color = if (isAnswered && isCorrectOption) BrandGreen else MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = option,
                  style = MaterialTheme.typography.bodyMedium,
                  modifier = Modifier.weight(1f)
                )

                if (isAnswered) {
                  if (isCorrectOption) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Correct", tint = BrandGreen)
                  } else if (isOptionSelected) {
                    Icon(imageVector = Icons.Default.Cancel, contentDescription = "Incorrect", tint = BrandRed)
                  }
                }
              }
            }
          }

          if (isAnswered) {
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = MaterialTheme.colorScheme.surfaceVariant
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Text(
                  text = "Explanation:",
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                )
                Text(
                  text = mcq.explanation,
                  style = MaterialTheme.typography.bodySmall
                )
              }
            }
          }
        }
      }
    }
  }
}

// 9. Short Answers Section
@Composable
private fun SectionShortAnswers(items: List<ShortQaItem>) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    items(items) { item ->
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Question (${item.marks} Marks)",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            )
            Text(text = "Group B", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }

          Spacer(modifier = Modifier.height(6.dp))
          Text(text = item.question, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))

          Spacer(modifier = Modifier.height(10.dp))
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text(text = "Model Answer:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
              Text(text = item.modelAnswer, style = MaterialTheme.typography.bodyMedium)
              Spacer(modifier = Modifier.height(6.dp))
              item.keyPoints.forEach { point ->
                Text(text = "• $point", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }
            }
          }
        }
      }
    }
  }
}

// 10. Long Answers Section (CHSE 7-Mark Long Questions)
@Composable
private fun SectionLongAnswers(items: List<LongQaItem>) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    items(items) { item ->
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = BrandPurple.copy(alpha = 0.15f)
          ) {
            Text(
              text = "CHSE Group C • ${item.marks} Marks Long Question",
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandPurple)
            )
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(text = item.question, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))

          Spacer(modifier = Modifier.height(10.dp))
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text(text = "Structured Complete Answer:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary))
              Spacer(modifier = Modifier.height(6.dp))
              Text(text = item.structuredAnswer, style = MaterialTheme.typography.bodySmall)

              Spacer(modifier = Modifier.height(10.dp))
              Text(text = "Marking Scheme Breakdown:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
              item.stepBreakdown.forEach { step ->
                Text(text = "✓ $step", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
              }
            }
          }
        }
      }
    }
  }
}

// 11. Chapter Test Launcher
@Composable
private fun SectionChapterTestLauncher(
  chapter: Chapter,
  onStartTest: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    contentAlignment = Alignment.Center
  ) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(BrandGreen.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(imageVector = Icons.Default.Quiz, contentDescription = null, tint = BrandGreen, modifier = Modifier.size(36.dp))
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "Chapter Assessment Test",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Text(
          text = "Test your mastery on ${chapter.title}",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Questions", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("10 MCQs", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
          }
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Duration", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("20 Mins", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
          }
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Total Marks", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("20 Marks", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
          }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
          onClick = onStartTest,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("start_chapter_test_button")
        ) {
          Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Start Timed Chapter Test")
        }
      }
    }
  }
}

// 12. AI Doubt Solver Section
@Composable
private fun SectionAiDoubtSolver(
  chapterTitle: String,
  doubtPrompts: List<String>
) {
  var userQuestion by remember { mutableStateOf("") }
  var selectedLanguage by remember { mutableStateOf("English") }
  var messages by remember {
    mutableStateOf(
      listOf(
        DoubtMessage(
          id = "welcome",
          text = "Namaskar! I am your CHSE Odisha +2 Science AI Doubt Solver. Ask me any doubt on $chapterTitle in English, Odia (ଓଡ଼ିଆ), or Hindi.",
          isUser = false,
          timestamp = "Now",
          stepByStepPoints = listOf(
            "Supports step-by-step math derivations",
            "Explains physics numericals with SI units",
            "Odia language answers available (ଓଡ଼ିଆରେ ଉତ୍ତର)"
          )
        )
      )
    )
  }
  var isLoading by remember { mutableStateOf(false) }
  val coroutineScope = rememberCoroutineScope()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    // Language Selector Row
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Response Language:",
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
      )

      Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        listOf("English", "Odia", "Hindi").forEach { lang ->
          FilterChip(
            selected = selectedLanguage == lang,
            onClick = { selectedLanguage = lang },
            label = {
              Text(
                text = if (lang == "Odia") "ଓଡ଼ିଆ" else if (lang == "Hindi") "हिन्दी" else "English",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = if (selectedLanguage == lang) FontWeight.Bold else FontWeight.Normal
                )
              )
            },
            shape = RoundedCornerShape(10.dp)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Messages List
    LazyColumn(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // Suggested Doubt Prompts
      item {
        Text(
          text = "Frequently Asked CHSE Doubts:",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
        Spacer(modifier = Modifier.height(4.dp))
        doubtPrompts.forEach { prompt ->
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 2.dp)
              .clickable {
                userQuestion = prompt
              }
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(imageVector = Icons.Default.HelpOutline, contentDescription = null, tint = BrandPurple, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = prompt, style = MaterialTheme.typography.bodySmall)
            }
          }
        }
      }

      items(messages) { msg ->
        if (msg.isUser) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
          ) {
            Surface(
              shape = RoundedCornerShape(14.dp),
              color = MaterialTheme.colorScheme.primary,
              modifier = Modifier.widthIn(max = 280.dp)
            ) {
              Text(
                text = msg.text,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
              )
            }
          }
        } else {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
          ) {
            Card(
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
              elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
              modifier = Modifier.widthIn(max = 320.dp)
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = BrandIndigo, modifier = Modifier.size(18.dp))
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "AI CHSE Tutor (${msg.language})",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandIndigo)
                  )
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(text = msg.text, style = MaterialTheme.typography.bodyMedium)

                if (msg.stepByStepPoints.isNotEmpty()) {
                  Spacer(modifier = Modifier.height(8.dp))
                  msg.stepByStepPoints.forEach { pt ->
                    Row(
                      modifier = Modifier.padding(vertical = 2.dp),
                      verticalAlignment = Alignment.Top
                    ) {
                      Text(text = "• ", color = BrandIndigo, fontWeight = FontWeight.Bold)
                      Text(text = pt, style = MaterialTheme.typography.bodySmall)
                    }
                  }
                }
              }
            }
          }
        }
      }

      if (isLoading) {
        item {
          Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = "Analyzing CHSE Odisha textbook & derivation steps...",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Question Input Box
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      OutlinedTextField(
        value = userQuestion,
        onValueChange = { userQuestion = it },
        placeholder = { Text("Ask any doubt (English, Odia, Hindi)...") },
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
          .weight(1f)
          .testTag("doubt_input_field"),
        maxLines = 3
      )

      Spacer(modifier = Modifier.width(8.dp))

      IconButton(
        onClick = {
          if (userQuestion.isNotBlank() && !isLoading) {
            val q = userQuestion
            userQuestion = ""
            val userMsg = DoubtMessage(
              id = "u_${System.currentTimeMillis()}",
              text = q,
              isUser = true,
              timestamp = "Just now",
              language = selectedLanguage
            )
            messages = messages + userMsg
            isLoading = true

            coroutineScope.launch {
              val ans = ChseDoubtSolver.answerQuestion(q, selectedLanguage)
              messages = messages + ans
              isLoading = false
            }
          }
        },
        modifier = Modifier
          .clip(CircleShape)
          .background(MaterialTheme.colorScheme.primary)
      ) {
        Icon(imageVector = Icons.Default.Send, contentDescription = "Send doubt", tint = Color.White)
      }
    }
  }
}
