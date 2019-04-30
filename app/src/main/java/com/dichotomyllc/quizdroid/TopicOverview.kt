package com.dichotomyllc.quizdroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.content.Intent
import android.util.Log
import android.widget.Button


class TopicOverview : AppCompatActivity() {

    val TAG: String = "TopicOverview"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.topic_overview)
        val quiz: QuizType = intent.extras.get("quizType") as QuizType

        findViewById<TextView>(R.id.tvDesc).text = when(quiz) {
            QuizType.Physics -> "This quiz asks about many different quirks and oddities pertaining to the field of Physics."
            QuizType.Math -> "This quiz asks about many different tidbits and phenomena pertaining to the field of mathematics."
            QuizType.Marvel -> "This quiz asks about some trivia tidbits pertaining to the Marvel Universe of comics."
        }
        findViewById<TextView>(R.id.tvTitle).text = """$quiz ${getString(R.string.quizOverview)}"""

        findViewById<Button>(R.id.btnBegin).setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("quiz", quiz.toString())
            startActivity(intent)
        }
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