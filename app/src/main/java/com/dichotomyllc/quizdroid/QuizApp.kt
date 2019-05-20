package com.dichotomyllc.quizdroid

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import java.io.*
import java.lang.StringBuilder

class QuizApp : Application() {

    private val TAG = "QuizApp"

    lateinit var topicRepo: TopicRepository

    override fun onCreate() {
        super.onCreate()
        topicRepo = TopicQuiz()
    }

    init {
        instance = this
    }


    companion object {
        private val fileName = "questions.json"
        lateinit var instance: QuizApp

        fun getQuestionsFromFile(): String {
            return getStringFromFile("${instance.applicationContext.filesDir}/$fileName")
        }

        @Throws(IOException::class)
        private fun convertStreamToString(`is`: InputStream): String {
            // http://www.java2s.com/Code/Java/File-Input-Output/ConvertInputStreamtoString.htm
            val reader = BufferedReader(InputStreamReader(`is`))
            val sb = StringBuilder()
            var line: String? = reader.readLine()
            var firstLine: Boolean? = true
            while ((line) != null) {
                if (firstLine!!) {
                    sb.append(line)
                    firstLine = false
                } else {
                    sb.append("\n").append(line)
                }
                line = reader.readLine()
            }
            reader.close()
            return sb.toString()
        }

        @Throws(IOException::class)
        private fun getStringFromFile(filePath: String): String {
            val fl = File(filePath)
            val fin = FileInputStream(fl)
            val ret = convertStreamToString(fin)
            //Make sure you close all streams.
            fin.close()
            return ret
        }

    }
}