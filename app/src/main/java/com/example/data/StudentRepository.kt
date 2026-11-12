package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object StudentRepository {

  private val _profile = MutableStateFlow(StudentProfile())
  val profile: StateFlow<StudentProfile> = _profile.asStateFlow()

  private val _goals = MutableStateFlow(ChseDataProvider.defaultGoals)
  val goals: StateFlow<List<StudyGoal>> = _goals.asStateFlow()

  private val _mockTests = MutableStateFlow(ChseDataProvider.mockTests)
  val mockTests: StateFlow<List<MockTest>> = _mockTests.asStateFlow()

  private val _announcements = MutableStateFlow(ChseDataProvider.announcements)
  val announcements: StateFlow<List<ChseAnnouncement>> = _announcements.asStateFlow()

  private val _customChapters = MutableStateFlow(ChseDataProvider.chapters)
  val chapters: StateFlow<List<Chapter>> = _customChapters.asStateFlow()

  // Selected Class Level (11 or 12)
  private val _selectedClassLevel = MutableStateFlow(12)
  val selectedClassLevel: StateFlow<Int> = _selectedClassLevel.asStateFlow()

  // Currently opened chapter for Continue Learning
  private val _currentActiveChapterId = MutableStateFlow("ch_phy12_1")
  val currentActiveChapterId: StateFlow<String> = _currentActiveChapterId.asStateFlow()

  // Admin Mode state
  private val _isAdminMode = MutableStateFlow(false)
  val isAdminMode: StateFlow<Boolean> = _isAdminMode.asStateFlow()

  fun setClassLevel(classLevel: Int) {
    _selectedClassLevel.value = classLevel
    _profile.update { it.copy(classLevel = classLevel) }
  }

  fun setActiveChapter(chapterId: String) {
    _currentActiveChapterId.value = chapterId
  }

  fun toggleGoal(goalId: String) {
    _goals.update { list ->
      list.map {
        if (it.id == goalId) it.copy(isDone = !it.isDone) else it
      }
    }
  }

  fun addGoal(title: String, subject: String, targetMinutes: Int) {
    val newGoal = StudyGoal(
      id = "goal_${System.currentTimeMillis()}",
      title = title,
      subject = subject,
      targetMinutes = targetMinutes,
      isDone = false
    )
    _goals.update { listOf(newGoal) + it }
  }

  fun toggleBookmark(bookmarkKey: String): Boolean {
    val current = _profile.value.bookmarks.toMutableList()
    val isBookmarked = if (current.contains(bookmarkKey)) {
      current.remove(bookmarkKey)
      false
    } else {
      current.add(bookmarkKey)
      true
    }
    _profile.update { it.copy(bookmarks = current) }
    return isBookmarked
  }

  fun isBookmarked(bookmarkKey: String): Boolean {
    return _profile.value.bookmarks.contains(bookmarkKey)
  }

  fun savePersonalNote(chapterId: String, noteText: String) {
    val current = _profile.value.personalNotes.toMutableMap()
    current[chapterId] = noteText
    _profile.update { it.copy(personalNotes = current) }
  }

  fun getPersonalNote(chapterId: String): String {
    return _profile.value.personalNotes[chapterId] ?: ""
  }

  fun updateChapterProgress(chapterId: String, progress: Int) {
    _customChapters.update { list ->
      list.map { ch ->
        if (ch.id == chapterId) {
          ch.copy(progressPercent = progress, isCompleted = progress >= 100)
        } else ch
      }
    }
  }

  fun recordTestResult(testId: String, score: Int) {
    _mockTests.update { list ->
      list.map { test ->
        if (test.id == testId) {
          test.copy(isCompleted = true, lastScore = score, lastTakenTime = "Just now")
        } else test
      }
    }
    // Update profile streak and test stats
    _profile.update {
      it.copy(
        todayMinutes = it.todayMinutes + 25,
        totalStudyHours = it.totalStudyHours + 0.4
      )
    }
  }

  fun setLanguagePreference(language: String) {
    _profile.update { it.copy(languagePref = language) }
  }

  fun setDarkMode(isDark: Boolean) {
    _profile.update { it.copy(isDarkMode = isDark) }
  }

  fun toggleAdminMode() {
    _isAdminMode.value = !_isAdminMode.value
  }

  fun addAnnouncement(title: String, category: String, description: String) {
    val newAnn = ChseAnnouncement(
      id = "ann_${System.currentTimeMillis()}",
      title = title,
      date = "Today",
      category = category,
      description = description,
      isImportant = true
    )
    _announcements.update { listOf(newAnn) + it }
  }
}
