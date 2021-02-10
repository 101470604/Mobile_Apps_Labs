package android.pass.not_breaking_the_rules

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.UiThread

public class MainActivity : AppCompatActivity() {

    lateinit var timer_text : TextView
    lateinit var button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timer_text = findViewById(R.id.timer_text)
        button = findViewById(R.id.button)
        button.setOnClickListener {
            try {
                var countdown = asyncTime()
                countdown.execute()
            }
            catch (ie : InterruptedException) {
                ie.printStackTrace()
            }
        }
    }


    inner class asyncTime : AsyncTask<Int, Int, Int>() {
        override fun doInBackground(vararg p0: Int?): Int {
            for (i in 3 downTo 0)
            {
                publishProgress(i)
                Thread.sleep(1000)
            }
            return 0
        }

        override fun onProgressUpdate(vararg num: Int?) {
            super.onProgressUpdate(*num)
            timer_text.text = num[0].toString()
            Log.d("Asyc" ,"Progress Update")
        }
    }

    }
