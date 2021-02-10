package com.example.a31p_food_parcels

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class FoodView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_view)

        // Using the stored ID, retrieve our string array resource
        val myFood : Food = (intent.extras?.get("food_data") as Food)
        val foodData = resources.getStringArray(myFood.arrayId)

        // Retrieve the elements we wish to manipulate
        var displayImage : ImageView = findViewById(R.id.display_image)
        var title : TextView = findViewById(R.id.image_view_title)
        var name : TextView = findViewById(R.id.name_value)
        var description : TextView = findViewById(R.id.description_value)

        // Set saved values
        displayImage.setImageResource(myFood.imageId)
        title.text = foodData[0]
        name.text = foodData[0]
        description.text = foodData[1]

    }

}