package au.edu.swin.sdmd.sunrisekotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import java.io.File
import java.io.FileOutputStream
import java.util.*

class add_location : AppCompatActivity() {

    lateinit var addLocationButton : Button
    lateinit var name : EditText
    lateinit var latitude : EditText
    lateinit var longitude : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_location)

        addLocationButton = findViewById(R.id.new_location_button)
        name = findViewById(R.id.input_location_name)
        latitude= findViewById(R.id.input_latitude)
        longitude = findViewById(R.id.input_longitude)
        addLocationButton.setOnClickListener {
            var error = false

            if ( latitude.text.isEmpty() || longitude.text.isEmpty() || name.text.isEmpty())
            {
                name.setError("Cannot be blank")
                error = true
            }
            else {
                if (latitude.text.toString().toDouble() > 180 || latitude.text.toString().toDouble() < -180)
                {
                    latitude.setError("Latitude must be within -180 to 180")
                    error = true
                }

                if (longitude.text.toString().toDouble() > 90 || longitude.text.toString().toDouble() < -90)
                {
                    longitude.setError("Longitude must be within -90 to 90")
                    error = true
                }
            }


            if (name.text.toString() == "")
            {
                name.setError("Cannot be blank")
                error = true
            }

            if (!error)
            {
                var intent = Intent(this, MainActivity::class.java)
                val file = File(filesDir,"locations.txt")
                val fos = FileOutputStream(file,true)
                var data : String = name.text.toString() + "," + latitude.text.toString() + "," + longitude.text.toString() + "," + TimeZone.getDefault().toString() + "\n"
                Log.d("File", data)
                fos.write(data.toByteArray())
                fos.close()
                startActivity(intent)
            }
        }
    }
}