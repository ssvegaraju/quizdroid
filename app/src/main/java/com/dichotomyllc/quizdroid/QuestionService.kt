package com.dichotomyllc.quizdroid

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import android.os.Handler
import android.preference.PreferenceManager
import android.app.AlarmManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import android.net.ConnectivityManager
import android.provider.Settings
import android.os.Build
import android.annotation.TargetApi
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.view.WindowManager
import java.lang.Exception


class QuestionService : IntentService("QuestionService") {

    private val TAG: String = "QuestionService"
    private val fileName: String = "questions.json"

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
                    // Check if internet access else take to settings.
                    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val activeNetworkInfo = connectivityManager.activeNetworkInfo
                    val connected = activeNetworkInfo != null && activeNetworkInfo.isConnected
                    if (!connected) {
                        Toast.makeText(applicationContext, "Not connected to internet", Toast.LENGTH_LONG).show()
                        if (isAirplaneModeOn(applicationContext)) run {
                            val builder: AlertDialog.Builder = AlertDialog.Builder(applicationContext)
                            builder.setTitle("Disable Airplane Mode?")
                                .setMessage("You are currently not connected to the internet. Would you like to disable airplane mode?")
                                .setPositiveButton("Yes", DialogInterface.OnClickListener { _: DialogInterface, _: Int ->
                                    try {
                                        val intent1 = Intent("android.settings.WIRELESS_SETTINGS")
                                        intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent1)
                                    } catch (e: Exception) {
                                        startActivity(Intent(Settings.ACTION_SETTINGS))
                                    }
                                })
                                .setNegativeButton("No", DialogInterface.OnClickListener{ _: DialogInterface, _: Int ->
                                    // do nothing

                                })
                            val dialog = builder.create()

                            dialog.window!!.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)
                            //dialog.show()
                            // so this line currently breaks everything due to an interference between
                            // the action bar requiring the PreferenceTheme, and the dialog requiring a child of Theme.AppCompat.
                            // I have spent a few hours without finding a fix but the code is all there and the intents start correctly
                            // to open the settings for airplane mode if airplane mode is detected.
                        }
                    } else {
                        doNetworkRequest()
                    }
                }
                if (running)
                    mHandler.postDelayed(this, waitTime)
            }
        }
        mHandler.postDelayed(runnable, 0)
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun isAirplaneModeOn(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Settings.System.getInt(
                context.contentResolver,
                Settings.System.AIRPLANE_MODE_ON, 0
            ) != 0
        } else {
            Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON, 0
            ) != 0
        }
    }

    fun doNetworkRequest() {
        // Download .json, make sure file is uncorrupted.
        doAsync {
            var succeeded = false
            try {
                mHandler.post {Toast.makeText(applicationContext, "Attempting to download file from URL...", Toast.LENGTH_SHORT).show()}
                val request = URL("http://tednewardsandbox.site44.com/questions.json").readText()
                // Log.v(TAG, request)
                // make changes to questions.json
                var fos: FileOutputStream? = null
                try {
                    fos = openFileOutput(fileName, Context.MODE_PRIVATE)
                    fos.write(request.toByteArray())
                    succeeded = true
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    if (fos != null) {
                        try {
                            fos.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
                mHandler.post{
                    if (succeeded) {
                        Toast.makeText(applicationContext, "Successfully downloaded file!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "Failed to downloaded file!", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: FileNotFoundException) {
                Log.e(TAG, "File not found at specified URL!!!")
                e.printStackTrace()
                // no changes to questions.json
            }

            uiThread {

            }
        }
    }
}
