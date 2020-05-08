package ru.itis.sing_english.data.model

data class QuizOption (
    val word: Word,
    var isEnglish: Boolean,
    var state: State? = State.DEFAULT
)

enum class State {
    DEFAULT, WRONG, RIGHT
}
