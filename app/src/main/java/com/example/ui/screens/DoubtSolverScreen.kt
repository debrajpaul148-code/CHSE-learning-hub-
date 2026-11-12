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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ai.ChseDoubtSolver
import com.example.ai.DoubtMessage
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoubtSolverScreen(
  onBack: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  var userQuestion by remember { mutableStateOf("") }
  var selectedLanguage by remember { mutableStateOf("English") }
  var hasAttachedImage by remember { mutableStateOf(false) }
  var isLoading by remember { mutableStateOf(false) }

  val coroutineScope = rememberCoroutineScope()

  var messages by remember {
    mutableStateOf(
      listOf(
        DoubtMessage(
          id = "welcome",
          text = "Namaskar! I am your official CHSE Odisha +2 Science AI Doubt Solver. You can ask me any numerical question, derivation, reaction mechanism, or biology concept in English, Odia (ଓଡ଼ିଆରେ), or Hindi.",
          isUser = false,
          timestamp = "Just now",
          stepByStepPoints = listOf(
            "Prioritizes Council of Higher Secondary Education (CHSE) Odisha syllabus",
            "Explains physics numericals with SI units & formulas",
            "Writes complete chemical reaction mechanisms",
            "Full Odia language support (ଓଡ଼ିଆ ଭାଷାରେ ବୁଝାନ୍ତୁ)"
          )
        )
      )
    )
  }

  val sampleQuestions = listOf(
    "Explain Gauss's law with Odia explanation (ଓଡ଼ିଆରେ ବୁଝାନ୍ତୁ)",
    "Derive Lens Maker's Formula for thin convex lens",
    "Explain Aldol Condensation with chemical mechanism",
    "How to solve 3x3 linear equations using Matrix Inversion method?",
    "Explain Double Fertilization in flowering plants (Angiosperms)",
    "State Einstein's photoelectric equation and stopping potential"
  )

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
        Row(verticalAlignment = Alignment.CenterVertically) {
          if (onBack != null) {
            IconButton(onClick = onBack) {
              Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
          }
          Column {
            Text(
              text = "CHSE AI Doubt Solver",
              style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Multi-lingual step-by-step problem solver (+2 Science)",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Language Pills
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Language:",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
          )

          Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("English", "Odia", "Hindi").forEach { lang ->
              FilterChip(
                selected = selectedLanguage == lang,
                onClick = { selectedLanguage = lang },
                label = {
                  Text(
                    text = if (lang == "Odia") "ଓଡ଼ିଆ (Odia)" else if (lang == "Hindi") "हिन्दी (Hindi)" else "English",
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
      }
    }

    // Chat History
    LazyColumn(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      contentPadding = PaddingValues(vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Common Doubt Pills
      item {
        Text(
          text = "Tap a suggested question to solve:",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(sampleQuestions) { q ->
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier.clickable { userQuestion = q }
            ) {
              Text(
                text = q,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium)
              )
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
              shape = RoundedCornerShape(16.dp),
              color = MaterialTheme.colorScheme.primary,
              modifier = Modifier.widthIn(max = 280.dp)
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                if (msg.imageUri != null) {
                  Row(
                    modifier = Modifier.padding(bottom = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Icon(imageVector = Icons.Default.Image, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Photo question attached", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.9f))
                  }
                }
                Text(
                  text = msg.text,
                  style = MaterialTheme.typography.bodyMedium,
                  color = Color.White
                )
              }
            }
          }
        } else {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
          ) {
            Card(
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
              elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
              modifier = Modifier.widthIn(max = 320.dp)
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Psychology,
                    contentDescription = null,
                    tint = BrandIndigo,
                    modifier = Modifier.size(20.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "CHSE AI Tutor • ${msg.language}",
                    style = MaterialTheme.typography.labelMedium.copy(
                      fontWeight = FontWeight.Bold,
                      color = BrandIndigo
                    )
                  )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                  text = msg.text,
                  style = MaterialTheme.typography.bodyMedium
                )

                if (msg.stepByStepPoints.isNotEmpty()) {
                  Spacer(modifier = Modifier.height(10.dp))
                  msg.stepByStepPoints.forEach { pt ->
                    Row(
                      modifier = Modifier.padding(vertical = 3.dp),
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
            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
              text = "Generating step-by-step CHSE board exam solution...",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }

    // Input Bar
    Surface(
      color = MaterialTheme.colorScheme.surface,
      shadowElevation = 6.dp
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        if (hasAttachedImage) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = BrandGreen.copy(alpha = 0.15f)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(imageVector = Icons.Default.Image, contentDescription = null, tint = BrandGreen, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Diagram/Numerical photo attached", style = MaterialTheme.typography.labelSmall.copy(color = BrandGreen, fontWeight = FontWeight.Bold))
              }
            }

            IconButton(onClick = { hasAttachedImage = false }, modifier = Modifier.size(24.dp)) {
              Icon(imageVector = Icons.Default.Close, contentDescription = "Remove")
            }
          }
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(onClick = { hasAttachedImage = !hasAttachedImage }) {
            Icon(
              imageVector = Icons.Default.PhotoCamera,
              contentDescription = "Attach Question Photo",
              tint = if (hasAttachedImage) BrandGreen else MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          OutlinedTextField(
            value = userQuestion,
            onValueChange = { userQuestion = it },
            placeholder = { Text("Type any question or doubt...") },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
              .weight(1f)
              .testTag("doubt_screen_input"),
            maxLines = 3
          )

          Spacer(modifier = Modifier.width(8.dp))

          IconButton(
            onClick = {
              if (userQuestion.isNotBlank() && !isLoading) {
                val q = userQuestion
                val attached = hasAttachedImage
                userQuestion = ""
                hasAttachedImage = false

                val userMsg = DoubtMessage(
                  id = "u_${System.currentTimeMillis()}",
                  text = q,
                  isUser = true,
                  timestamp = "Just now",
                  language = selectedLanguage,
                  imageUri = if (attached) "mock://photo" else null
                )
                messages = messages + userMsg
                isLoading = true

                coroutineScope.launch {
                  val ans = ChseDoubtSolver.answerQuestion(q, selectedLanguage, attached)
                  messages = messages + ans
                  isLoading = false
                }
              }
            },
            modifier = Modifier
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.primary)
          ) {
            Icon(imageVector = Icons.Default.Send, contentDescription = "Send", tint = Color.White)
          }
        }
      }
    }
  }
}
