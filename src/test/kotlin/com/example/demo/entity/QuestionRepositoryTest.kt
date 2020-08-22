package com.example.demo.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class QuestionRepositoryTest {

    @Autowired
    lateinit var questionRepository: QuestionRepository

    @Test
    fun `when save a item should be able to find a item`() {
        // given
        val testQuestion = Question("test question", "hello", "hi")

        // when
        val entity = questionRepository.save(testQuestion)

        // then
        val result = questionRepository.findById(entity.id)

        assertTrue(result.isPresent)
        assertTrue(result.get().id > 0)
        assertEquals(result.get().question, "test question")
    }
}
