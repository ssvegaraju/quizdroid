package com.dichotomyllc.quizdroid

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topics = QuizApp.instance.topicRepo.getTopicList()

        for (i in 0 until topics.size) {
            Log.v(TAG, topics[i].title)
            val constraintLayout = findViewById<LinearLayout>(R.id.linearLayout)
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            button.text = topics[i].title
            button.setOnClickListener {
                switchToTopicOverview(topics[i].title)
            }
            constraintLayout.addView(button)
        }
    }

    private fun switchToTopicOverview(quizType: String) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("quizType", quizType)
        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "Restarting...")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "Resuming...")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "Pausing...")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "Starting...")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destroying...")
    }
}

enum class QuizType() {
    Physics,
    Math,
    Marvel
}