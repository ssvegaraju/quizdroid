package com.dichotomyllc.quizdroid

import android.app.Application
import android.util.Log

class QuizApp : Application() {

    private val TAG = "QuizApp"
    var topics: List<TopicRepository> = listOf()

    override fun onCreate() {
        super.onCreate()
        instance = this
        // Hardcoding in topics for now, these will eventually be drawn from some other source.
        topics = listOf(TopicQuiz(QuizType.Physics), TopicQuiz(QuizType.Math), TopicQuiz(QuizType.Marvel))
        Log.v(TAG, "Created quiz app...")
    }

    fun getTopic(quizType: QuizType) : TopicRepository {
        return topics.single {it.topic.title == quizType.toString()}
    }

    companion object {
        lateinit var instance: QuizApp
            private set
    }
}