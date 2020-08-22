package com.example.demo.entity

import com.vladmihalcea.hibernate.type.json.JsonStringType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "question")
@TypeDef(name = "text", typeClass = JsonStringType::class)
class Question(
    val question: String,
    vararg choices: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Type(type = "text")
    @Column(columnDefinition = "text", name = "choices")
    val choices: List<String> = choices.toList()
}
