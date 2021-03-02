package com.example.conversion_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView



class MainActivity : AppCompatActivity() {

    val result : Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        var result = findViewById<TextView>(R.id.display_conversion_result)

        outState.putString("result", result.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        var result = findViewById<TextView>(R.id.display_conversion_result)
        result.text = savedInstanceState.getString("result")
    }

    fun convert(view: View)
    {
        var result = 0.0
        var miles = findViewById<EditText>(R.id.input_miles)
        var feet = findViewById<EditText>(R.id.input_feet)
        var inches = findViewById<EditText>(R.id.input_inches)
        var displayResult = findViewById<TextView>(R.id.display_conversion_result)
        var checkbox = findViewById<CheckBox>(R.id.conversion_switch)
        var unit = ""

        if (miles.text.toString() != "")
        {
            result = (java.lang.Double.parseDouble(miles.text.toString()) * 160934.4)
        }
        else if (feet.text.toString() != "")
        {
            result = (java.lang.Double.parseDouble(feet.text.toString()) * 30.48)
        }
        else if (inches.text.toString() != "")
        {
            result = (java.lang.Double.parseDouble(inches.text.toString()) * 2.54)
        }


        if (checkbox.isChecked())
        {
            unit = getString(R.string.label_m)
            displayResult.text = (result / 1000).toString() + unit
        }
        else
        {
            unit = getString(R.string.label_cm)
            displayResult.text = (result).toString() + unit
        }
    }

}
