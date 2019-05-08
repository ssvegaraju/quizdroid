package com.dichotomyllc.quizdroid

interface TopicRepository {
    var quiz: Quiz
    var topic: Topic
}

class Topic(_title: String, _shortDesc: String, _longDesc: String) {
    val title = _title
    val shortDesc = _shortDesc
    val longDesc = _longDesc
}

class TopicQuiz(quizType: QuizType) : TopicRepository {
    override lateinit var topic: Topic
    override lateinit var quiz: Quiz

    init {
        when (quizType) {
            QuizType.Physics ->{
                topic = Topic(quizType.toString(), "Questions about how the world works.",
                    QuizApp.instance.getString(R.string.physicsDesx))
                quiz = createPhysQuiz()
            }
            QuizType.Math ->{
                topic = Topic(quizType.toString(), "Questions about how numbers work.",
                    QuizApp.instance.getString(R.string.physicsDesx))
                quiz = createMathQuiz()
            }
            QuizType.Marvel ->{
                topic = Topic(quizType.toString(), "Questions about comic superheroes.",
                    QuizApp.instance.getString(R.string.physicsDesx))
                quiz = createMarvelQuiz()
            }
        }
    }


    fun createPhysQuiz(): Quiz {
        val questions: MutableList<Question> = mutableListOf<Question>()
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
        val questions: MutableList<Question> = mutableListOf<Question>()
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
        val questions: MutableList<Question> = mutableListOf<Question>()
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
}