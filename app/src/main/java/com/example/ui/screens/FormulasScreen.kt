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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.ChseDataProvider
import com.example.model.FormulaItem
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulasScreen(
  onBack: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf("Physics Formulas") }
  val tabs = listOf("Physics Formulas", "Chemistry Reactions", "Maths Integrals", "Biology Diagrams")

  val physicsFormulas = ChseDataProvider.flagshipChapterContent.formulas

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
              text = "CHSE Formula & Revision Sheets",
              style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "Exam-oriented quick formulas, named reactions & derivations",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(tabs) { tab ->
            FilterChip(
              selected = selectedTab == tab,
              onClick = { selectedTab = tab },
              label = { Text(tab) },
              shape = RoundedCornerShape(10.dp)
            )
          }
        }
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      when (selectedTab) {
        "Physics Formulas" -> {
          items(physicsFormulas) { form ->
            FormulaCard(form)
          }
        }

        "Chemistry Reactions" -> {
          item {
            ReactionCard(
              title = "Aldol Condensation",
              conditions = "Aldehydes with α-H + dil. NaOH (10%)",
              equation = "2 CH₃CHO  --(dil. NaOH)-->  CH₃-CH(OH)-CH₂-CHO  --(Δ)-->  CH₃-CH=CH-CHO + H₂O",
              importance = "CHSE 3-Mark Named Reaction in Group B"
            )
          }
          item {
            ReactionCard(
              title = "Cannizzaro Reaction",
              conditions = "Aldehydes with NO α-H (HCHO, C₆H₅CHO) + conc. NaOH (50%)",
              equation = "2 HCHO + 50% NaOH  -->  CH₃OH (Methanol) + HCOONa (Sodium Formate)",
              importance = "Guaranteed in Organic Conversions"
            )
          }
          item {
            ReactionCard(
              title = "Kohlrausch's Law of Independent Migration",
              conditions = "Weak electrolytes at infinite dilution",
              equation = "Λ°ₘ = ν₊ λ°₊ + ν₋ λ°₋  =>  Λ°(CH₃COOH) = Λ°(CH₃COONa) + Λ°(HCl) - Λ°(NaCl)",
              importance = "Electrochemistry Group C 7-Mark question"
            )
          }
        }

        "Maths Integrals" -> {
          item {
            IntegralCard(
              name = "Integration by Parts Formula",
              formula = "∫ u · v dx = u ∫ v dx - ∫ [ (du/dx) · ∫ v dx ] dx",
              rule = "ILATE Rule: Inverse, Logarithmic, Algebraic, Trigonometric, Exponential"
            )
          }
          item {
            IntegralCard(
              name = "Standard Integral 1 / (x² + a²)",
              formula = "∫ dx / (x² + a²) = (1/a) tan⁻¹(x/a) + C",
              rule = "Essential substitution: x = a tan θ"
            )
          }
          item {
            IntegralCard(
              name = "Definite Integral Property (King's Property)",
              formula = "∫₀ᵃ f(x) dx = ∫₀ᵃ f(a - x) dx",
              rule = "Most repeated CHSE 4-mark and 6-mark integration technique"
            )
          }
        }

        "Biology Diagrams" -> {
          items(ChseDataProvider.flagshipChapterContent.diagrams) { diag ->
            Card(
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
              elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Text(text = diag.title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = diag.description, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Key Labels: ${diag.keyLabels.joinToString(", ")}", style = MaterialTheme.typography.labelSmall, color = BrandIndigo)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun FormulaCard(form: FormulaItem) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(text = form.name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
        Text(text = form.siUnit, style = MaterialTheme.typography.labelSmall, color = BrandIndigo)
      }

      Spacer(modifier = Modifier.height(8.dp))
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = form.formula,
          modifier = Modifier.padding(12.dp),
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.primary
          )
        )
      }

      Spacer(modifier = Modifier.height(6.dp))
      Text(text = "Variables: ${form.variables}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
  }
}

@Composable
fun ReactionCard(title: String, conditions: String, equation: String, importance: String) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Text(text = title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = BrandPurple))
      Text(text = conditions, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
      Spacer(modifier = Modifier.height(8.dp))
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = equation,
          modifier = Modifier.padding(12.dp),
          style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.SemiBold)
        )
      }
      Spacer(modifier = Modifier.height(6.dp))
      Text(text = "★ $importance", style = MaterialTheme.typography.labelSmall.copy(color = BrandGold, fontWeight = FontWeight.Bold))
    }
  }
}

@Composable
fun IntegralCard(name: String, formula: String, rule: String) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Text(text = name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
      Spacer(modifier = Modifier.height(8.dp))
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = formula,
          modifier = Modifier.padding(12.dp),
          style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        )
      }
      Spacer(modifier = Modifier.height(6.dp))
      Text(text = "Tip: $rule", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
  }
}
