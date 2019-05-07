package com.dichotomyllc.quizdroid

import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Layout
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz)

        val quizType: QuizType = intent.extras.get("quizType") as QuizType

        findViewById<TextView>(R.id.tvDesc).text = when(quizType) {
            QuizType.Physics -> "This quiz asks about many different quirks and oddities pertaining to the field of Physics."
            QuizType.Math -> "This quiz asks about many different tidbits and phenomena pertaining to the field of mathematics."
            QuizType.Marvel -> "This quiz asks about some trivia tidbits pertaining to the Marvel Universe of comics."
        } + " This quiz has 5 questions."
        findViewById<TextView>(R.id.tvTitle).text = """$quizType ${getString(R.string.quizOverview)}"""

        /*findViewById<Button>(R.id.btnBegin).setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("quiz", quiz.toString())
            startActivity(intent)
        }*/

        val questionText: TextView = findViewById<TextView>(R.id.tvQuestion)
        var radioGroup: RadioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val answer1: RadioButton = findViewById<RadioButton>(R.id.rbtn1)
        val answer2: RadioButton = findViewById<RadioButton>(R.id.rbtn2)
        val answer3: RadioButton = findViewById<RadioButton>(R.id.rbtn3)
        val answer4: RadioButton = findViewById<RadioButton>(R.id.rbtn4)
        val submit: Button = findViewById<Button>(R.id.btnSubmit)
        val proceed: Button = findViewById<Button>(R.id.btnProceed)
        val correctText: TextView = findViewById<TextView>(R.id.tvAnswerDisp)
        fun onAnswerSelected() {
            submit.visibility = View.VISIBLE
        }
        answer1.setOnClickListener {onAnswerSelected()}
        answer2.setOnClickListener {onAnswerSelected()}
        answer3.setOnClickListener {onAnswerSelected()}
        answer4.setOnClickListener {onAnswerSelected()}

        var quiz: Quiz = when(quizType) {
            QuizType.Physics -> createPhysQuiz()
            QuizType.Math -> createMathQuiz()
            QuizType.Marvel -> createMarvelQuiz()
            else -> Quiz(mutableListOf<Question>())
        }
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
            if (questionNumber == 5) {
                proceed.text = "Finish"
            } else {
                proceed.text = "Next"
            }
            proceed.visibility = View.VISIBLE
            correctText.visibility = View.VISIBLE
        }

        proceed.setOnClickListener {
            radioGroup.clearCheck()
            findViewById<RadioButton>(correctAnswer).setBackgroundColor(Color.TRANSPARENT)
            findViewById<RadioButton>(chosenAnswer).setBackgroundColor(Color.TRANSPARENT)
            correctText.visibility = View.INVISIBLE
            proceed.visibility = View.INVISIBLE
            submit.visibility = View.INVISIBLE
            if (questionNumber == 5) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                setQuestionView(quiz.questions[questionNumber])
            }
            questionNumber++
        }

    }


    fun createPhysQuiz(): Quiz {
        var questions: MutableList<Question> = mutableListOf<Question>()
        questions.add(Question("What does the constant 'g' refer to in Physics?",
            mutableListOf<Answer>(
                Answer("Friction", false),
                Answer("Gravity", true),
                Answer("Gimbal Lock", false),
                Answer("Torque", false)
                )))
        questions.add(Question("What does 'F=ma' stand for?",
            mutableListOf<Answer>(
                Answer("Friction = momentum * acceleration", false),
                Answer("Force = mass * acceleration", true),
                Answer("Force = momentum * acceleration", false),
                Answer("Friction = mass * acceleration", false)
            )))
        questions.add(Question("Which would fall faster: a bowling ball or a feather?",
            mutableListOf<Answer>(
                Answer("Feather", false),
                Answer("Bowling ball", true),
                Answer("Both would fall at the same rate", false),
                Answer("None of these", false)
            )))
        questions.add(Question("Who came up with the Theory of Relativity?",
            mutableListOf<Answer>(
                Answer("Isaac Newton", false),
                Answer("Albert Einstein", true),
                Answer("Thomas Edison", false),
                Answer("Shaq", false)
            )))
        questions.add(Question("What is the SI unit of weight?",
            mutableListOf<Answer>(
                Answer("Kilograms", false),
                Answer("Newtons", true),
                Answer("Pounds", false),
                Answer("Absolute Unit", false)
            )))
        return Quiz(questions)
    }

    fun createMathQuiz(): Quiz {
        var questions: MutableList<Question> = mutableListOf<Question>()
        questions.add(Question("Which is larger, the set of all even integers or the set of all integers?",
            mutableListOf<Answer>(
                Answer("All Integers", false),
                Answer("They are the same size", true),
                Answer("Even Integers", false),
                Answer("None of these", false)
            )))
        questions.add(Question("How many solutions does a first order Differential Equation have?",
            mutableListOf<Answer>(
                Answer("1", false),
                Answer("Infinitely Many", true),
                Answer("2", false),
                Answer("Not enough information", false)
            )))
        questions.add(Question("What does the series 1 + 0.5 + 0.25 + ... converge to?",
            mutableListOf<Answer>(
                Answer("Infinity", false),
                Answer("2", true),
                Answer("3", false),
                Answer("2.5", false)
            )))
        questions.add(Question("Who invented Calculus?",
            mutableListOf<Answer>(
                Answer("Gottfried Leibniz", false),
                Answer("Both of these dudes", true),
                Answer("Isaac Newton", false),
                Answer("None of these dudes", false)
            )))
        questions.add(Question("Which is larger, the set of all rational numbers or the set of all irrational numbers?",
            mutableListOf<Answer>(
                Answer("All rational numbers", false),
                Answer("All irrational numbers", true),
                Answer("None of these", false),
                Answer("They are the same size", false)
            )))
        return Quiz(questions)
    }

    fun createMarvelQuiz(): Quiz {
        var questions: MutableList<Question> = mutableListOf<Question>()
        questions.add(Question("How many siblings does Thor have?",
            mutableListOf<Answer>(
                Answer("1", false),
                Answer("2", true),
                Answer("3", false),
                Answer("4", false)
            )))
        questions.add(Question("What is Spiderman's real name?",
            mutableListOf<Answer>(
                Answer("Pepper Potts", false),
                Answer("Peter Parker", true),
                Answer("Peter Stark", false),
                Answer("Tony Stark", false)
            )))
        questions.add(Question("Who does Doctor Strange fight against countless times?",
            mutableListOf<Answer>(
                Answer("The Ancient One", false),
                Answer("Dormamu", true),
                Answer("Thanos", false),
                Answer("Crippling depression at the loss of his hands", false)
            )))
        questions.add(Question("Which stone does Vision have as his core?",
            mutableListOf<Answer>(
                Answer("The Soul Stone", false),
                Answer("The Mind Stone", true),
                Answer("The Vision Stone", false),
                Answer("The Reality Stone", false)
            )))
        questions.add(Question("How long was Captain America frozen in ice?",
            mutableListOf<Answer>(
                Answer("45 years", false),
                Answer("70 years", true),
                Answer("75 years", false),
                Answer("100 years", false)
            )))
        return Quiz(questions)
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