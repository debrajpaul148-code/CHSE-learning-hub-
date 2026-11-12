package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Subject
import com.example.ui.theme.*

@Composable
fun SubjectIconBadge(iconType: String, colorHex: Long, modifier: Modifier = Modifier) {
  val icon: ImageVector = when (iconType) {
    "bolt" -> Icons.Default.Bolt
    "science" -> Icons.Default.Science
    "calculate" -> Icons.Default.Calculate
    "eco" -> Icons.Default.Eco
    "menu_book" -> Icons.Default.MenuBook
    "auto_stories" -> Icons.Default.AutoStories
    else -> Icons.Default.School
  }

  Box(
    modifier = modifier
      .size(46.dp)
      .clip(RoundedCornerShape(12.dp))
      .background(Color(colorHex).copy(alpha = 0.15f)),
    contentAlignment = Alignment.Center
  ) {
    Icon(
      imageVector = icon,
      contentDescription = iconType,
      tint = Color(colorHex),
      modifier = Modifier.size(26.dp)
    )
  }
}

@Composable
fun ClassLevelSelector(
  selectedLevel: Int,
  onLevelSelected: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(24.dp),
    color = MaterialTheme.colorScheme.surfaceVariant,
    modifier = modifier
  ) {
    Row(
      modifier = Modifier.padding(4.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(20.dp))
          .background(
            if (selectedLevel == 11) MaterialTheme.colorScheme.primary else Color.Transparent
          )
          .clickable { onLevelSelected(11) }
          .padding(horizontal = 14.dp, vertical = 6.dp)
      ) {
        Text(
          text = "+2 1st Year (Class 11)",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = if (selectedLevel == 11) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(20.dp))
          .background(
            if (selectedLevel == 12) MaterialTheme.colorScheme.primary else Color.Transparent
          )
          .clickable { onLevelSelected(12) }
          .padding(horizontal = 14.dp, vertical = 6.dp)
      ) {
        Text(
          text = "+2 2nd Year (Class 12)",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = if (selectedLevel == 12) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
      }
    }
  }
}

@Composable
fun SubjectCard(
  subject: Subject,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val progress = if (subject.chaptersCount > 0) {
    subject.completedChaptersCount.toFloat() / subject.chaptersCount.toFloat()
  } else 0f

  Card(
    onClick = onClick,
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      SubjectIconBadge(iconType = subject.iconType, colorHex = subject.colorHex)

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = subject.name,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = subject.odiaName,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = subject.description,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
              .weight(1f)
              .height(6.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = Color(subject.colorHex),
            trackColor = Color(subject.colorHex).copy(alpha = 0.15f),
          )

          Spacer(modifier = Modifier.width(10.dp))

          Text(
            text = "${subject.completedChaptersCount}/${subject.chaptersCount} Chapters",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      Spacer(modifier = Modifier.width(8.dp))

      Icon(
        imageVector = Icons.Default.ChevronRight,
        contentDescription = "Open subject",
        tint = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}
