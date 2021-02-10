package au.edu.swin.sdmd.lifecyclekotln

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

/**
 * This app will display messages on screen, and in the Log
 * indicating the life cycle states
 *
 * @author nronald
 * Based on code developed by rvasa
 */
class MainActivity : AppCompatActivity() {

    /**
     * Called when the activity is first created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appendState("onCreate")
    }

    /**
     * Appends the state to the on-screen text.
     * @param s description of the state
     */
    private fun appendState(s: String) {
        val stateTextView: TextView = findViewById(R.id.stateTextView)
        val prevStates = stateTextView.getText().toString()
        stateTextView.setText(prevStates + "\n" + s)
        Log.i("ACTIVITY-CURRENT-STATE", s)
    }

    override fun onStart() {
        super.onStart()
        appendState("onStart")
    }

    override fun onResume() {
        super.onResume()
        appendState("onResume")
    }

    override fun onRestart() {
        super.onRestart()
        appendState("onRestart")
    }

    override fun onPause() {
        super.onPause()
        appendState("onPause")
    }

    override fun onStop() {
        super.onStop()
        appendState("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        appendState("onDestroy")
    }
}