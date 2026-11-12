package com.example

import com.example.data.ChseDataProvider
import com.example.data.StudentRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun `verify CHSE Odisha subjects and chapters exist`() {
    val subjects = ChseDataProvider.subjects
    assertTrue(subjects.isNotEmpty())
    val class12Physics = subjects.find { it.id == "phy_12" }
    assertTrue(class12Physics != null)
    assertEquals(12, class12Physics?.classLevel)

    val chapters = ChseDataProvider.chapters
    assertTrue(chapters.isNotEmpty())
    val electrostatics = chapters.find { it.id == "ch_phy12_1" }
    assertTrue(electrostatics != null)
    assertEquals("Electric Charges and Fields", electrostatics?.title)
  }

  @Test
  fun `verify student repository goal toggling`() {
    val initialGoals = StudentRepository.goals.value
    assertTrue(initialGoals.isNotEmpty())
    val targetGoal = initialGoals.first()
    val initialDone = targetGoal.isDone

    StudentRepository.toggleGoal(targetGoal.id)
    val updatedGoal = StudentRepository.goals.value.find { it.id == targetGoal.id }
    assertEquals(!initialDone, updatedGoal?.isDone)
  }
}
