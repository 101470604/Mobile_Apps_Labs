package au.edu.swin.staticclock

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.text.SimpleDateFormat

/**
 *
 * App to show a static clock. Try rotating the device to see what happens!
 *
 * @author nronald
 * Based on code by rvasa
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUI()
    }

    private fun initializeUI() {
        val sysTime = System.currentTimeMillis()
        val format = SimpleDateFormat("kk:mm:ss")
        val timeStr = format.format(sysTime)
        val ctv: TextView = findViewById(R.id.timeTextView)
        ctv.text = timeStr
    }

}
