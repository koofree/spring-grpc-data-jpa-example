package com.example.demo

import com.example.demo.entity.Question
import com.example.demo.entity.QuestionRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@Component
class ApplicationTestDataLoader(
    private val questionRepository: QuestionRepository
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        questionRepository.saveAll(listOf(
            Question("test 1", "1", "2"),
            Question("test 2", "a", "b"),
            Question("테스트 3", "가", "나")
        ))
    }
}
