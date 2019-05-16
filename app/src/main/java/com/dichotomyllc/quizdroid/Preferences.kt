package com.dichotomyllc.quizdroid


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class Preferences : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
