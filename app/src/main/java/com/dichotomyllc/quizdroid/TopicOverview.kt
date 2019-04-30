package com.dichotomyllc.quizdroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.content.Intent


class TopicOverview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.topic_overview)
        findViewById<TextView>(R.id.tvDesc).text = intent.extras.get("quizType").toString()
    }
}