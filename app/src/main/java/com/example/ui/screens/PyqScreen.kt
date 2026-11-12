package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.ChseDataProvider
import com.example.model.PyqPaper
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PyqScreen(
  onBack: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  val papers = ChseDataProvider.pyqPapers
  var selectedYearFilter by remember { mutableStateOf<Int?>(null) }
  var selectedSubjectFilter by remember { mutableStateOf<String?>(null) }
  var expandedPaperId by remember { mutableStateOf<String?>(papers.firstOrNull()?.id) }

  val filteredPapers = remember(selectedYearFilter, selectedSubjectFilter) {
    papers.filter { paper ->
      (selectedYearFilter == null || paper.year == selectedYearFilter) &&
          (selectedSubjectFilter == null || paper.subjectName == selectedSubjectFilter)
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
      Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          if (onBack != null) {
            IconButton(onClick = onBack) {
              Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
          }
          Column {
            Text(
              text = "CHSE Previous Year Questions (PYQs)",
              style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Official Board Papers (2019-2024) with Step-by-Step Solutions",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Year Filter
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          item {
            FilterChip(
              selected = selectedYearFilter == null,
              onClick = { selectedYearFilter = null },
              label = { Text("All Years") },
              shape = RoundedCornerShape(10.dp)
            )
          }
          listOf(2024, 2023, 2022, 2021, 2020).forEach { year ->
            item {
              FilterChip(
                selected = selectedYearFilter == year,
                onClick = { selectedYearFilter = year },
                label = { Text("$year") },
                shape = RoundedCornerShape(10.dp)
              )
            }
          }
        }
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      items(filteredPapers) { paper ->
        val isExpanded = expandedPaperId == paper.id

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
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = BrandGold.copy(alpha = 0.2f)
              ) {
                Text(
                  text = "CHSE ${paper.year} Examination",
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFFB45309))
                )
              }

              Text(
                text = "${paper.totalMarks} Marks • ${paper.duration}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = paper.title,
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
              onClick = {
                expandedPaperId = if (isExpanded) null else paper.id
              },
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(if (isExpanded) "Collapse Paper" else "View Solved Question Paper")
              Spacer(modifier = Modifier.width(6.dp))
              Icon(imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore, contentDescription = null)
            }

            if (isExpanded) {
              Spacer(modifier = Modifier.height(14.dp))
              HorizontalDivider()
              Spacer(modifier = Modifier.height(14.dp))

              Text(
                text = "Questions & Official Marking Solutions:",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
              )

              paper.questions.forEachIndexed { qIdx, pyq ->
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                  shape = RoundedCornerShape(12.dp),
                  color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                      Text(
                        text = "Q${qIdx + 1} (${pyq.marks} Marks)",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandPurple)
                      )
                      Text(text = pyq.groupName, style = MaterialTheme.typography.labelSmall)
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = pyq.questionText, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Step-by-Step Marking Solution:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    pyq.solutionSteps.forEach { step ->
                      Row(
                        modifier = Modifier.padding(vertical = 2.dp),
                        verticalAlignment = Alignment.Top
                      ) {
                        Text(text = "• ", color = BrandGreen, fontWeight = FontWeight.Bold)
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
  }
}
