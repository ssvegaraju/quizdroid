package com.dichotomyllc.quizdroid

class Quiz(_questions: List<Question>) {
    var questions: List<Question> = _questions
}

class Question(_question: String, _answers: MutableList<Answer>) {
    val question: String = _question
    var answers: MutableList<Answer> = _answers
    init {
        if (answers.size < 4) {
            while (answers.size < 4) {
                answers.add(Answer("", false))
            }
        } else if (answers.size > 4) {
            answers = answers.slice(0..3).toMutableList()
        }
    }

    fun getCorrectAnswerIndex(): Int {
        var index: Int = 0
        for (ans in answers) {
            if (ans.correct) {
                break
            } else {
                index++
            }
        }
        return index
    }
}

class Answer(_ans: String, _correct: Boolean) {
    val ans: String = _ans
    val correct: Boolean = _correct
}
