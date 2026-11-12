package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.StudentRepository
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

sealed class Screen {
  object Home : Screen()
  data class Courses(val subjectId: String? = null) : Screen()
  object Books : Screen()
  data class Tests(val testId: String? = null) : Screen()
  object Profile : Screen()

  // Sub-screens
  data class ChapterDetail(val chapterId: String) : Screen()
  object Videos : Screen()
  object Pyqs : Screen()
  object Formulas : Screen()
  object Planner : Screen()
  object DoubtSolver : Screen()
}

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val profile by StudentRepository.profile.collectAsState()

      MyApplicationTheme(darkTheme = profile.isDarkMode) {
        MainAppContent()
      }
    }
  }
}

@Composable
fun MainAppContent() {
  var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

  // Handle back stack for sub screens
  val isSubScreen = when (currentScreen) {
    is Screen.ChapterDetail,
    is Screen.Videos,
    is Screen.Pyqs,
    is Screen.Formulas,
    is Screen.Planner,
    is Screen.DoubtSolver -> true
    else -> false
  }

  if (isSubScreen) {
    BackHandler {
      currentScreen = Screen.Home
    }
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    bottomBar = {
      // Show bottom navigation on primary 5 tabs
      if (!isSubScreen) {
        BottomNavigationBar(
          currentScreen = currentScreen,
          onSelectScreen = { currentScreen = it }
        )
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (val screen = currentScreen) {
        is Screen.Home -> {
          HomeScreen(
            onNavigateToSubject = { subjectId ->
              currentScreen = Screen.Courses(subjectId)
            },
            onNavigateToChapter = { chapterId ->
              currentScreen = Screen.ChapterDetail(chapterId)
            },
            onOpenBooks = { currentScreen = Screen.Books },
            onOpenVideos = { currentScreen = Screen.Videos },
            onOpenTests = { currentScreen = Screen.Tests() },
            onOpenPyq = { currentScreen = Screen.Pyqs },
            onOpenFormulas = { currentScreen = Screen.Formulas },
            onOpenPlanner = { currentScreen = Screen.Planner },
            onOpenDoubtSolver = { currentScreen = Screen.DoubtSolver }
          )
        }

        is Screen.Courses -> {
          CoursesScreen(
            selectedSubjectId = screen.subjectId,
            onNavigateToChapter = { chapterId ->
              currentScreen = Screen.ChapterDetail(chapterId)
            },
            onBack = { currentScreen = Screen.Home }
          )
        }

        is Screen.Books -> {
          BooksScreen(
            onOpenChapter = { chapterId ->
              currentScreen = Screen.ChapterDetail(chapterId)
            }
          )
        }

        is Screen.Tests -> {
          OnlineTestScreen(
            activeTestId = screen.testId,
            onBack = { currentScreen = Screen.Home }
          )
        }

        is Screen.Profile -> {
          ProfileScreen(
            onOpenPlanner = { currentScreen = Screen.Planner },
            onOpenTests = { currentScreen = Screen.Tests() }
          )
        }

        is Screen.ChapterDetail -> {
          ChapterLearningScreen(
            chapterId = screen.chapterId,
            onBack = { currentScreen = Screen.Courses() },
            onStartChapterTest = { testId ->
              currentScreen = Screen.Tests(testId)
            }
          )
        }

        is Screen.Videos -> {
          VideoLecturesScreen(
            onOpenChapter = { chapterId ->
              currentScreen = Screen.ChapterDetail(chapterId)
            },
            onBack = { currentScreen = Screen.Home }
          )
        }

        is Screen.Pyqs -> {
          PyqScreen(
            onBack = { currentScreen = Screen.Home }
          )
        }

        is Screen.Formulas -> {
          FormulasScreen(
            onBack = { currentScreen = Screen.Home }
          )
        }

        is Screen.Planner -> {
          StudyPlannerScreen(
            onBack = { currentScreen = Screen.Home }
          )
        }

        is Screen.DoubtSolver -> {
          DoubtSolverScreen(
            onBack = { currentScreen = Screen.Home }
          )
        }
      }
    }
  }
}

data class NavItem(
  val label: String,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val targetScreen: Screen,
  val testTag: String
)

@Composable
fun BottomNavigationBar(
  currentScreen: Screen,
  onSelectScreen: (Screen) -> Unit
) {
  val items = listOf(
    NavItem("Home", Icons.Filled.Home, Icons.Outlined.Home, Screen.Home, "nav_home"),
    NavItem("Courses", Icons.Filled.School, Icons.Outlined.School, Screen.Courses(), "nav_courses"),
    NavItem("Books", Icons.Filled.MenuBook, Icons.Outlined.MenuBook, Screen.Books, "nav_books"),
    NavItem("Tests", Icons.Filled.Quiz, Icons.Outlined.Quiz, Screen.Tests(), "nav_tests"),
    NavItem("Profile", Icons.Filled.Person, Icons.Outlined.Person, Screen.Profile, "nav_profile")
  )

  NavigationBar(
    containerColor = MaterialTheme.colorScheme.surface,
    tonalElevation = 8.dp
  ) {
    items.forEach { item ->
      val isSelected = when (item.targetScreen) {
        is Screen.Home -> currentScreen is Screen.Home
        is Screen.Courses -> currentScreen is Screen.Courses
        is Screen.Books -> currentScreen is Screen.Books
        is Screen.Tests -> currentScreen is Screen.Tests
        is Screen.Profile -> currentScreen is Screen.Profile
        else -> false
      }

      NavigationBarItem(
        selected = isSelected,
        onClick = { onSelectScreen(item.targetScreen) },
        icon = {
          Icon(
            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
            contentDescription = item.label
          )
        },
        label = { Text(item.label) },
        modifier = Modifier.testTag(item.testTag)
      )
    }
  }
}
