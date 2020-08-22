package com.example.demo.rpc

import com.example.demo.entity.QuestionRepository
import com.example.demo.rpc.ReactorDiagnosisServiceGrpc.DiagnosisServiceImplBase
import org.lognet.springboot.grpc.GRpcService
import org.springframework.data.domain.PageRequest
import reactor.core.publisher.Mono

@GRpcService
class DiagnosisService(
    private val questionRepository: QuestionRepository
) : DiagnosisServiceImplBase() {
    override fun getDiagnosis(request: Mono<GetDiagnosisRequest>): Mono<GetDiagnosisResponse> = request
        .map {
            questionRepository.findAll(PageRequest.of(0, it.questionCount))
        }
        .map { page ->
            page.content.map { entity ->
                Question.newBuilder()
                    .setQuestion(entity.question)
                    .addAllChoices(entity.choices)
                    .build()
            }
        }
        .map { questions ->
            GetDiagnosisResponse.newBuilder()
                .addAllQuestions(questions)
                .build()
        }
}
