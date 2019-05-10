package com.dichotomyllc.quizdroid

import android.app.Application
import android.util.Log

class QuizApp : Application() {

    private val TAG = "QuizApp"
    var topicRepo: TopicRepository = TopicQuiz()

    override fun onCreate() {
        super.onCreate()
        Log.v(TAG, "Created quiz app...")
        topicRepo = TopicQuiz()
        Log.v(TAG, topicRepo.topics.size.toString())
        instance = this
    }

    companion object {
        lateinit var instance: QuizApp
            private set
    }
}