package com.dichotomyllc.quizdroid


import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.EditTextPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceManager
import android.util.Patterns
import android.widget.Toast

class Preferences : PreferenceFragmentCompat() {

    lateinit var sharedPrefs: SharedPreferences
    lateinit var url: EditTextPreference
    lateinit var minutes: EditTextPreference

    private val defaultURL = "http://tednewardsandbox.site44.com/questions.json"
    private val defaultMin = "1"

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity)

        // Assign preferences
        url = preferenceScreen.findPreference("downloadURL") as EditTextPreference
        minutes = preferenceScreen.findPreference("minutes") as EditTextPreference

        url.summary =  if (url.text.isEmpty()) defaultURL else url.text
        minutes.summary = if (minutes.text.isEmpty()) defaultMin else minutes.text

        url.onPreferenceChangeListener = object: Preference.OnPreferenceChangeListener{
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                var inputURL: String = newValue.toString()

                if (!Patterns.WEB_URL.matcher(inputURL).matches()) {
                    Toast.makeText(context, "Invalid URL provided, resetting to default!", Toast.LENGTH_LONG).show()
                    inputURL = defaultURL
                }
                url.text = inputURL
                url.summary = inputURL
                updateDownloadTask(inputURL, minutes.text)
                return true
            }
        }
        minutes.onPreferenceChangeListener = object: Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                var inputMin = newValue.toString()
                try {
                    inputMin.toInt()
                } catch (e: Exception) {
                    Toast.makeText(context, "Invalid time given, resetting to default!", Toast.LENGTH_LONG).show()
                    inputMin = defaultMin
                }
                minutes.text = inputMin
                minutes.summary = inputMin
                updateDownloadTask(url.text, inputMin)
                return true
            }
        }
    }

    fun updateDownloadTask(dlurl: String, minutes: String) {
        QuestionService.newInstance().downloadURL = dlurl
        QuestionService.newInstance().minutes = minutes
    }
}
