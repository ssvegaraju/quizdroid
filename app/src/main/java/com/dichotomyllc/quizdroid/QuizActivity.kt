package com.dichotomyllc.quizdroid

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class QuizActivity : AppCompatActivity() {

    private val TAG = "QuizActivity"

    private var questionNumber: Int = 0
    private var chosenAnswer:Int = 0
    private var numCorrect: Int = 0
    private var correctAnswer: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz)
        val quizType: String = intent.extras!!.get("quizType") as String

        val topicQuiz: Topic = QuizApp.instance.topicRepo.getTopic((quizType))

        val fragment = QuizOverviewFragment.newInstance(quizType)
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.add(R.id.container, fragment, "QUIZ_OVERVIEW_FRAGMENT")
        ft.commit()
        ft.runOnCommit {
            var desc = topicQuiz.longDesc
            findViewById<TextView>(R.id.tvDesc).text = desc
            findViewById<TextView>(R.id.tvTitle).text = """${topicQuiz.title} ${getString(R.string.quizOverview)}"""

            findViewById<Button>(R.id.btnBegin).setOnClickListener {
                val frag = QuizFragment.newInstance(quizType.toString())
                val fragt: FragmentTransaction = supportFragmentManager.beginTransaction()
                fragt.replace(R.id.container, frag, "QUIZ_FRAGMENT")
                fragt.commit()
                fragt.runOnCommit {InitializeQuiz(QuizApp.instance.topicRepo.loadQuiz(topicQuiz))}
            }
        }

    }

    @SuppressLint("SetTextI18n")
    fun InitializeQuiz(quiz: Quiz) {
        val questionText: TextView = findViewById(R.id.tvQuestion)
        var radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        val answer1: RadioButton = findViewById(R.id.rbtn1)
        val answer2: RadioButton = findViewById(R.id.rbtn2)
        val answer3: RadioButton = findViewById(R.id.rbtn3)
        val answer4: RadioButton = findViewById(R.id.rbtn4)
        val submit: Button = findViewById(R.id.btnSubmit)
        val proceed: Button = findViewById(R.id.btnProceed)
        val correctText: TextView = findViewById(R.id.tvAnswerDisp)
        fun onAnswerSelected() {
            submit.visibility = View.VISIBLE
        }
        answer1.setOnClickListener {onAnswerSelected()}
        answer2.setOnClickListener {onAnswerSelected()}
        answer3.setOnClickListener {onAnswerSelected()}
        answer4.setOnClickListener {onAnswerSelected()}


        quiz.questions = quiz.questions.shuffled()

        fun setQuestionView(question: Question) {
            question.answers = question.answers.shuffled().toMutableList()
            questionText.text = question.question
            answer1.text = question.answers[0].ans
            answer2.text = question.answers[1].ans
            answer3.text = question.answers[2].ans
            answer4.text = question.answers[3].ans
            correctAnswer = when(question.getCorrectAnswerIndex()) {
                0 -> answer1.id
                1 -> answer2.id
                2 -> answer3.id
                3 -> answer4.id
                else -> answer1.id
            }
        }

        setQuestionView(quiz.questions[questionNumber])
        questionNumber++;

        submit.setOnClickListener {
            answer1.isClickable = false
            answer2.isClickable = false
            answer3.isClickable = false
            answer4.isClickable = false
            submit.visibility = View.INVISIBLE
            chosenAnswer = radioGroup.checkedRadioButtonId
            if (chosenAnswer == correctAnswer) {
                numCorrect++
            } else {
                findViewById<RadioButton>(chosenAnswer).setBackgroundColor(Color.RED)
            }
            findViewById<RadioButton>(correctAnswer).setBackgroundColor(Color.GREEN)
            correctText.text =
                """${getString(R.string.answeredTextDisplay)} $numCorrect ${getString(R.string.outof)} $questionNumber ${getString(
                    R.string.questionscorrectly
                )}""".trim()
            if (questionNumber == quiz.questions.size) {
                proceed.text = "Finish"
            } else {
                proceed.text = "Next"
            }
            proceed.visibility = View.VISIBLE
            correctText.visibility = View.VISIBLE
        }

        proceed.setOnClickListener {
            answer1.isClickable = true
            answer2.isClickable = true
            answer3.isClickable = true
            answer4.isClickable = true
            radioGroup.clearCheck()
            findViewById<RadioButton>(correctAnswer).setBackgroundColor(Color.TRANSPARENT)
            findViewById<RadioButton>(chosenAnswer).setBackgroundColor(Color.TRANSPARENT)
            correctText.visibility = View.INVISIBLE
            proceed.visibility = View.INVISIBLE
            submit.visibility = View.INVISIBLE
            if (questionNumber == quiz.questions.size) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                setQuestionView(quiz.questions[questionNumber])
            }
            questionNumber++
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