package com.example.model

data class Subject(
  val id: String,
  val name: String,
  val odiaName: String,
  val classLevel: Int, // 11 or 12
  val iconType: String,
  val colorHex: Long,
  val description: String,
  val chaptersCount: Int,
  val completedChaptersCount: Int,
)

data class Chapter(
  val id: String,
  val subjectId: String,
  val chapterNumber: Int,
  val title: String,
  val odiaTitle: String,
  val classLevel: Int,
  val weightageMarks: Int,
  val summary: String,
  var isCompleted: Boolean = false,
  var progressPercent: Int = 0,
)

// 12 Section content for each chapter
data class ChapterOverview(
  val keyConcepts: List<String>,
  val chseWeightageNotice: String,
  val examTips: List<String>,
  val learningObjectives: List<String>,
)

data class NoteSection(
  val heading: String,
  val content: String,
  val bulletPoints: List<String> = emptyList(),
  val examTakeaway: String = "",
)

data class TextbookPage(
  val pageNumber: Int,
  val pageTitle: String,
  val content: String,
  val callout: String = "",
)

data class DefinitionItem(
  val term: String,
  val definition: String,
  val marks: Int, // 1 or 2 marks
  val example: String = "",
)

data class FormulaItem(
  val name: String,
  val formula: String,
  val siUnit: String,
  val variables: String,
  val application: String,
)

data class DiagramItem(
  val title: String,
  val description: String,
  val keyLabels: List<String>,
  val examImportance: String,
  val drawingTip: String,
)

data class PyqItem(
  val year: Int,
  val questionText: String,
  val marks: Int,
  val groupName: String, // Group A (MCQ/1M), Group B (2/3M), Group C (7M)
  val solutionSteps: List<String>,
)

data class McqItem(
  val id: String,
  val question: String,
  val options: List<String>,
  val correctIndex: Int,
  val explanation: String,
  val topic: String = "",
)

data class ShortQaItem(
  val question: String,
  val marks: Int, // 2 or 3 marks
  val modelAnswer: String,
  val keyPoints: List<String>,
)

data class LongQaItem(
  val question: String,
  val marks: Int, // 7 marks (CHSE Long Question pattern)
  val structuredAnswer: String,
  val stepBreakdown: List<String>,
  val diagramRequired: Boolean = true,
  val markingScheme: String = "",
)

data class ChapterContent(
  val chapterId: String,
  val overview: ChapterOverview,
  val notes: List<NoteSection>,
  val textbookPages: List<TextbookPage>,
  val definitions: List<DefinitionItem>,
  val formulas: List<FormulaItem>,
  val diagrams: List<DiagramItem>,
  val pyqs: List<PyqItem>,
  val mcqs: List<McqItem>,
  val shortAnswers: List<ShortQaItem>,
  val longAnswers: List<LongQaItem>,
  val doubtPrompts: List<String>,
)

data class VideoLecture(
  val id: String,
  val chapterId: String,
  val subjectId: String,
  val title: String,
  val tutorName: String,
  val duration: String,
  val videoUrl: String,
  val timestamps: List<Pair<String, String>>,
  val keySummary: String,
)

data class MockTest(
  val id: String,
  val title: String,
  val subjectName: String,
  val classLevel: Int,
  val testType: String, // "Chapter Test", "Unit Test", "Half-Yearly Mock", "Annual Mock", "PYQ Practice"
  val durationMinutes: Int,
  val totalMarks: Int,
  val questions: List<McqItem>,
  var isCompleted: Boolean = false,
  var lastScore: Int? = null,
  var lastTakenTime: String? = null,
)

data class PyqPaper(
  val id: String,
  val title: String,
  val subjectId: String,
  val subjectName: String,
  val year: Int,
  val duration: String,
  val totalMarks: Int,
  val questions: List<PyqItem>,
)

data class StudyGoal(
  val id: String,
  val title: String,
  val subject: String,
  val targetMinutes: Int,
  var isDone: Boolean = false,
)

data class TimetableItem(
  val day: String,
  val timeSlot: String,
  val subject: String,
  val chapterTitle: String,
)

data class StudentProfile(
  var name: String = "Odisha Science Aspirant",
  var classLevel: Int = 12,
  var targetPercentage: Int = 92,
  var streakDays: Int = 14,
  var totalStudyHours: Double = 86.5,
  var todayMinutes: Int = 110,
  var dailyGoalMinutes: Int = 180,
  var languagePref: String = "English", // English, Odia, Hindi
  var isDarkMode: Boolean = false,
  var bookmarks: MutableList<String> = mutableListOf(),
  var personalNotes: MutableMap<String, String> = mutableMapOf(),
)

data class ChseAnnouncement(
  val id: String,
  val title: String,
  val date: String,
  val category: String, // "Exam Schedule", "Syllabus Update", "Admit Card", "Practical Exam"
  val description: String,
  val isImportant: Boolean = true,
)
