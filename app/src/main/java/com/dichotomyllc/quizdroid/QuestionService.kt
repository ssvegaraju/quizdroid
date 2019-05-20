package com.dichotomyllc.quizdroid

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import android.os.Handler
import android.preference.PreferenceManager
import android.app.AlarmManager
import android.R.string.cancel
import android.app.PendingIntent
import android.content.BroadcastReceiver
import java.util.*


class QuestionService : IntentService("QuestionService") {

    companion object {
        private var instance: QuestionService? = null
        fun newInstance(): QuestionService {
            if (instance == null) {
                instance = QuestionService()
            }
            return instance!!
        }
    }

    val mHandler = Handler()
    private var running: Boolean = true

    var downloadURL: String = ""
    var minutes: String = ""
    private lateinit var alarmManager: AlarmManager
    override fun onCreate() {
        super.onCreate()
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        downloadURL = sharedPrefs.getString("downloadURL", "CRITICAL FAILURE")!!
        minutes = sharedPrefs.getString("minutes", "1")!!
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    override fun onHandleIntent(intent: Intent?) {
        // begin repeating task
        val waitTime = (minutes.toInt() * 60 * 1000).toLong()
        val runnable = object : Runnable {
            override fun run() {
                mHandler.post{
                    // Download .json, make sure file is uncorrupted.
                }
                if (running)
                    mHandler.postDelayed(this, waitTime)
            }
        }
        mHandler.postDelayed(runnable, 0)
    }
}
