package com.dichotomyllc.quizdroid


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A simple [Fragment] subclass.
 *
 */
class QuizFragment : Fragment() {
    val TAG = "QuizFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_quiz, container, false)

        Log.v(TAG, "created view")

        return rootView
    }

    companion object {
        val QUIZ_TYPE_KEY = "quizType"
        fun newInstance(quizType: String) : QuizFragment{
            val args = Bundle()
            args.putString(QUIZ_TYPE_KEY, quizType)
            val frag = QuizFragment()
            frag.arguments = args
            return frag
        }
    }
}
