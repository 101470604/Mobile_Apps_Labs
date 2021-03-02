package com.example.a31p_food_parcels

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import java.util.*


class MainActivity : AppCompatActivity() {

    var myBundle : Bundle? = null
    var item_1_name : TextView? = null
    var item_2_name : TextView? = null
    var item_3_name : TextView? = null
    var item_4_name : TextView? = null
    var item_1_date_obtained : TextView? = null
    var item_2_date_obtained : TextView? = null
    var item_3_date_obtained : TextView? = null
    var item_4_date_obtained : TextView? = null

    var hamburger : ImageView? = null
    var icecream : ImageView? = null
    var bahnmi : ImageView? = null
    var dumpling : ImageView? = null

    var hamburgerData : FoodMetaData? = null
    var bahnmiData : FoodMetaData? = null
    var icecreamData: FoodMetaData? = null
    var dumplingData : FoodMetaData? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



         // Part 1 implementation

        // For each image, when clicked open a new activity, & pass the ID a of string-arrray resource
         //so that it can be loaded in the new activity.

        hamburger?.setOnClickListener {
            var arrayId : Int = R.array.hamburger
            var imgId : Int = R.drawable.cheeseburger
            viewImage(arrayId, imgId)
        }

        bahnmi?.setOnClickListener {
            var arrayId : Int = R.array.bahnmi
            var imgId : Int = R.drawable.bahn_mi
            viewImage(arrayId, imgId)
        }

        icecream?.setOnClickListener {
            var arrayId : Int = R.array.icecream
            var imgId : Int = R.drawable.ben_and_jerrys
            viewImage(arrayId, imgId)
        }

        dumpling?.setOnClickListener {
            var arrayId : Int = R.array.dumplings
            var imgId : Int = R.drawable.dumplings
            viewImage(arrayId, imgId)
        }

        //******** Part 2 Implementation ********//

        /* Retrieve the objects values & display them to the user
        initObjects()
        initUi()
        setEventListeners() */
    }

    // Ensure all our objects are in the new intent & the new activity knows which one to access
    private fun editMetaData(myBundle : Bundle, name : String)
    {
        val myIntent = Intent(this@MainActivity, MetaDataForm::class.java)
        myIntent.putExtras(myBundle)
        myIntent.putExtra("current_food_meta_data", name)
        startActivity(myIntent)
    }

    private fun viewImage(arrayId : Int, imageId : Int)
    {
        val myIntent = Intent(this@MainActivity, FoodView::class.java)
        val foodData = Food(arrayId, imageId)
        myIntent.putExtra("food_data", foodData)
        startActivity(myIntent)
    }

    private fun setText()
    {
        item_1_name?.setText(hamburgerData?.name)
        item_2_name?.setText(bahnmiData?.name)
        item_3_name?.setText(dumplingData?.name)
        item_4_name?.setText(icecreamData?.name)
        item_1_date_obtained?.setText(hamburgerData?.dateObtained)
        item_2_date_obtained?.setText(bahnmiData?.dateObtained)
        item_3_date_obtained?.setText(dumplingData?.dateObtained)
        item_4_date_obtained?.setText(icecreamData?.dateObtained)
    }

    private fun initObjects()
    {        // Make sure the required objects exist
        myBundle = intent.extras

        if (myBundle == null)
        {
            myBundle = Bundle()
        }

        if (!intent.hasExtra("hamburger_data")) {
            var hamburgerData = FoodMetaData("hamburger", "", "", "", "",false, 0)
            myBundle?.putParcelable("hamburger_data", hamburgerData)
        }

        if (!intent.hasExtra("bahnmi_data"))
        {
            var bahnmiData = FoodMetaData("bahnmi","", "", "", "",false, 0 )
            myBundle?.putParcelable("bahnmi_data", bahnmiData)
        }

        if (!intent.hasExtra("icecream_data")) {
            var iceCreamData = FoodMetaData("icecream", "", "", "", "",false, 0)
            myBundle?.putParcelable("icecream_data", iceCreamData)
        }

        if (!intent.hasExtra("dumpling_data"))
        {
            var dumplingData  = FoodMetaData("dumplings","",  "", "", "", false,0 )
            myBundle?.putParcelable("dumpling_data", dumplingData)
        }

        hamburgerData = myBundle?.getParcelable("hamburger_data") as FoodMetaData?
        icecreamData = myBundle?.getParcelable("icecream_data") as FoodMetaData?
        bahnmiData = myBundle?.getParcelable("bahnmi_data") as FoodMetaData?
        dumplingData = myBundle?.getParcelable("dumpling_data") as FoodMetaData?
    }

    private fun initUi ()
    {

        hamburger = findViewById<ImageView>(R.id.hamburger)
        bahnmi = findViewById<ImageView>(R.id.bahnmi)
        icecream = findViewById<ImageView>(R.id.icecream)
        dumpling = findViewById<ImageView>(R.id.dumplings)

        item_1_name = findViewById(R.id.item_1_name)
        item_2_name = findViewById(R.id.item_2_name)
        item_3_name = findViewById(R.id.item_3_name)
        item_4_name = findViewById(R.id.item_4_name)
        item_1_date_obtained = findViewById(R.id.item_1_date_obtained)
        item_2_date_obtained = findViewById(R.id.item_2_date_obtained)
        item_3_date_obtained = findViewById(R.id.item_3_date_obtained)
        item_4_date_obtained = findViewById(R.id.item_4_date_obtained)

        setText()
    }

    private fun setEventListeners()
    {

        // Make sure the required objects exist & are in the bundle before we start a new activity
        hamburger?.setOnClickListener {
            editMetaData(myBundle!!, "hamburger_data")
        }

        bahnmi?.setOnClickListener {
            editMetaData(myBundle!!, "bahnmi_data")
        }

        icecream?.setOnClickListener {
            editMetaData(myBundle!!, "icecream_data")
        }

        dumpling?.setOnClickListener {
            editMetaData(myBundle!!, "dumpling_data")
        }
    }
}
