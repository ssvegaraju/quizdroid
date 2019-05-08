package com.dichotomyllc.quizdroid


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.ContentFrameLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 *
 */
class QuizOverviewFragment : Fragment() {

    val TAG = "QuizOverviewFragment"
    private var fm: FragmentActivity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_quiz_overview, container, false)

        Log.v(TAG, "Created View ")
        arguments?.let {
            val quizType = it.getString(QUIZ_TYPE_KEY)
            if (quizType != null) {

            }
        }


        return rootView
    }

    companion object {
        const val QUIZ_TYPE_KEY = "quiz type"

        fun newInstance(quizType: String) : QuizOverviewFragment {
            val args = Bundle()
            args.putString(QUIZ_TYPE_KEY, quizType)
            val theFragment = QuizOverviewFragment()
            theFragment.arguments = args
            return theFragment
        }
    }
}
