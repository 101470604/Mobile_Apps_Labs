package au.edu.swin.sdmd.sunrisekotlin

import android.content.Intent
import android.net.sip.SipSession
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import au.edu.swin.sdmd.sunrisekotlin.calc.GeoLocation
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import au.edu.swin.sdmd.sunrisekotlin.R
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlin.text.Typography.times


class Home : SuntimeCore(), shareable {

    lateinit var dp : DatePicker
    lateinit var geoLocation : GeoLocation
    lateinit var sunriseTV : TextView
    lateinit var sunsetTV : TextView
    lateinit var spinner : Spinner
    lateinit var cal : Calendar
    lateinit var cityList : List<GeoLocation>
    lateinit var addLocationButton : Button

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View?
    {
        var  thisView: View = inflater.inflate(R.layout.fragment_home, container, false)
        dp = thisView.findViewById(R.id.datePicker)
        spinner = thisView.findViewById(R.id.locationSpinner)
        sunriseTV = thisView.findViewById(R.id.sunriseTimeTV)
        sunsetTV = thisView.findViewById(R.id.sunsetTimeTV)
        addLocationButton = thisView.findViewById(R.id.new_location_button)
        cal = Calendar.getInstance()
        cityList = readInLocations()
        geoLocation = cityList[0]

        addLocationButton.setOnClickListener {
            var intent = Intent(this.context, add_location::class.java)
            startActivity(intent)
        }

        initializeUI(dp, spinner)
        return thisView
    }

    override fun onResume() {
        cityList = readInLocations()
        super.onResume()
    }

    override fun share() : RiseAndSetTimes {
        var year = datePicker.year
        var month = datePicker.month
        var day = datePicker.dayOfMonth

        return RiseAndSetTimes(
            sunriseTV.text.toString(), sunsetTV.text.toString(), "$year/$month/$day", geoLocation.locationName
        )
    }

    private fun initializeUI(datePicker : DatePicker, spinner : Spinner) {
        // Setup datepicker and initial sunrise/set values
        val date = DateData(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.init(date.year, date.month, date.day, dateChangeHandler) // setup initial values and reg. handler

        val times = getRiseSetTimes(date.year, date.month, date.day, geoLocation)
        sunriseTV.setText(times.sunRise)
        sunsetTV.setText(times.sunSet)

        var locationNames = mutableListOf<String>()
        for (i in cityList)
        {
            locationNames.add(i.locationName)
        }

        // Setup Spinner
        spinner.adapter = ArrayAdapter(this.context,R.layout.location_spinner,locationNames)
        spinner.onItemSelectedListener = spinnerHandler
    }

    internal var spinnerHandler = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
        }

        override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
            geoLocation = cityList[position]
            val newTimes = getRiseSetTimes(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH), geoLocation)
            sunriseTV.setText(newTimes.sunRise)
            sunsetTV.setText(newTimes.sunSet)
        }
    }

    internal var dateChangeHandler: DatePicker.OnDateChangedListener =
        DatePicker.OnDateChangedListener { dp, year, monthOfYear, dayOfMonth ->
            val times = getRiseSetTimes(year,monthOfYear, dayOfMonth, geoLocation)
            cal.set(year, monthOfYear, dayOfMonth)
            sunriseTV.setText(times.sunRise)
            sunsetTV.setText(times.sunSet)
        }

}

