package com.dichotomyllc.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnPhys).setOnClickListener {
            switchToTopicOverview(QuizType.Physics)
        }
        findViewById<Button>(R.id.btnMath).setOnClickListener {
            switchToTopicOverview(QuizType.Math)
        }
        findViewById<Button>(R.id.btnMarvel).setOnClickListener {
            switchToTopicOverview(QuizType.Marvel)
        }
    }

    private fun switchToTopicOverview(quizType: QuizType) {
        val intent = Intent(this, TopicOverview::class.java)
        intent.putExtra("quizType", quizType)
        startActivity(intent)
    }
}

enum class QuizType() {
    Physics,
    Math,
    Marvel
}