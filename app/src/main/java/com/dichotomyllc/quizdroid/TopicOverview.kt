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