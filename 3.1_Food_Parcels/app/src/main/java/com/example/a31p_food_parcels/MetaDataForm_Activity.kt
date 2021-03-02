package com.example.a31p_food_parcels

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validUrl
import kotlinx.android.synthetic.main.meta_data_form.*
import java.lang.Integer.parseInt
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class MetaDataForm : AppCompatActivity() {
    // Bundle stored to keep all objects persistent across views
    var myBundle : Bundle? = null
    // String Key for the bundle object we're accessing
    var foodName : String? = null
    // View objects to manipulate
    var nameInput : EditText? =  null
    var urlInput : EditText? = null
    var keywordsInput : EditText? = null
    var dateObtainedInput : EditText? = null
    var whoObtainedInput : EditText? =  null
    var ratingInput : EditText? = null
    var submitButton : Button? =null
    var foodMetaData : FoodMetaData? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.meta_data_form)

        // Retrieve all objects from the bundle
        myBundle = intent.extras
        // This is safe because we check for the existence of this extra in main activity
        foodName = this.intent.extras?.get("current_food_meta_data") as String
        foodMetaData = this.intent.extras?.get(foodName) as FoodMetaData


        nameInput =  findViewById<EditText>(R.id.name_value)
        urlInput = findViewById<EditText>(R.id.url_value)
        keywordsInput = findViewById<EditText>(R.id.keywords_value)
        dateObtainedInput  = findViewById<EditText>(R.id.date_obtained_value)
        whoObtainedInput =  findViewById<EditText>(R.id.who_obtained_value)
        ratingInput= findViewById<EditText>(R.id.rating_value)
        submitButton = findViewById(R.id.submit_button)
        submitButton?.setText("Submit")


        // Set the textfields to contain the saved values
        nameInput?.setText(foodMetaData?.name)
        urlInput?.setText(foodMetaData?.url)
        keywordsInput?.setText(foodMetaData?.keywords)
        dateObtainedInput?.setText(foodMetaData?.dateObtained)
        whoObtainedInput?.setText(foodMetaData?.whoObtained)
        ratingInput?.setText((foodMetaData?.rating).toString())

        share_checkbox.setChecked(foodMetaData?.share!!)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        dateObtainedInput?.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    dateObtainedInput?.setText("" + mDay + "/" + mMonth + "/" + mYear + "")
                }, year, month, day)

            dpd.show()
        }

        submitButton?.setOnClickListener {
            if (validateInput())
            {
                // Retrieve the new values & save to the parcelable object
                foodMetaData?.name = nameInput?.text.toString()
                foodMetaData?.url = urlInput?.text.toString()
                foodMetaData?.keywords = keywordsInput?.text.toString()
                foodMetaData?.dateObtained = dateObtainedInput?.text.toString()
                foodMetaData?.whoObtained = whoObtainedInput?.text.toString()
                foodMetaData?.rating = parseInt(ratingInput?.text.toString())
                foodMetaData?.share = false

                if (share_checkbox.isChecked)
                {
                    foodMetaData?.share = true
                }

                var myIntent = Intent(this@MetaDataForm, MainActivity::class.java)
                myBundle?.putParcelable(foodName, foodMetaData)
                myIntent.putExtras(myBundle!!)
                startActivity(myIntent)
            }
        }
    }

    private fun validateInput() : Boolean
    {
        // Check if the inputs are empty
        var result : Boolean = true

        if (nameInput?.text.toString() == "") {
            nameInput?.setError("Cannot be empty")
            result = false
        }

        if (whoObtainedInput?.text.toString() == "") {
            whoObtainedInput?.setError("Cannot be empty")
            result = false
        }

        if (!whoObtainedInput?.text.toString().validEmail()) {
            whoObtainedInput?.setError("Must be a valid email address")
            result = false
        }

        if (ratingInput?.text.toString().toInt() > 5 || ratingInput?.text.toString().toInt() < 0) {
            ratingInput?.setError("Must be a number from 0-5")
            result = false
        }

        return result
    }

}