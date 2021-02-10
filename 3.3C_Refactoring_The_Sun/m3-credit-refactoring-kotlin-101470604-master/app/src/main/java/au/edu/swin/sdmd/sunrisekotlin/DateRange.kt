package au.edu.swin.sdmd.sunrisekotlin


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import au.edu.swin.sdmd.sunrisekotlin.calc.GeoLocation
import java.util.*


class DateRange : SuntimeCore(), shareable, OnItemClickedListener {

    lateinit var cal : Calendar
    lateinit var geoLocation : GeoLocation
    lateinit var startDateText: TextView
    lateinit var endDateText: TextView
    lateinit var generateButton : Button
    lateinit var startDate: Date
    lateinit var endDate: Date
    lateinit var locationSpinner : Spinner
    lateinit var recyclerView : RecyclerView
    lateinit var recycleAdapter: RecyclerAdapter
    lateinit var generatedTimes : MutableList<RiseAndSetTimes>
    lateinit var shareableInfo: RiseAndSetTimes
    lateinit var cityList : List<GeoLocation>
    lateinit var addLocationButton : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val thisView = inflater.inflate(R.layout.fragment_date_range, container, false)
        // Initialize view 
        locationSpinner = thisView.findViewById(R.id.locationSpinner)
        startDateText = thisView.findViewById(R.id.fromDate)
        endDateText = thisView.findViewById(R.id.toDate)
        generateButton = thisView.findViewById(R.id.generate_button)
        recyclerView = thisView.findViewById(R.id.recycleView)
        locationSpinner = thisView.findViewById(R.id.locationSpinner)
        addLocationButton = thisView.findViewById(R.id.new_location_button)
        cal = Calendar.getInstance()
        startDate = Date()
        endDate = Date()

        cityList = readInLocations()
        geoLocation =  cityList[0]

        addLocationButton.setOnClickListener {
            var intent = Intent(this.context, add_location::class.java)
            startActivity(intent)
        }
        setEventHandlers()

        return thisView
    }

    override fun onResume() {
        cityList = readInLocations()
        super.onResume()
    }

    override fun share() : RiseAndSetTimes
    {
        return shareableInfo
    }

    private fun setEventHandlers()
    {
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        startDateText.setOnClickListener {
            val dpd = DatePickerDialog(
                this.context,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    startDateText.setText("" + mDay + "/" + mMonth + "/" + mYear + "")
                    cal.set(mYear, mMonth, mDay)
                    startDate.time = cal.timeInMillis
                }, year, month, day)
            dpd.show()
        }

        endDateText.setOnClickListener {
            val dpd = DatePickerDialog(
                this.context,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    endDateText.setText("" + mDay + "/" + mMonth + "/" + mYear + "")
                    cal.set(mYear, mMonth, mDay)
                    endDate.time = cal.timeInMillis
                }, year, month, day)
            dpd.show()
        }

        generateButton.setOnClickListener {
            if (startDate.time > endDate.time)
            {
                startDateText.setError("Start date must be before the end date")
                endDateText.setError("End date must be before the start date")
            }
            else
            {
                startDateText.setError(null)
                endDateText.setError(null)
                generateList()
            }
        }
        var locationNames = mutableListOf<String>()
        for (i in cityList)
        {
            locationNames.add(i.locationName)
        }
        locationSpinner.adapter = ArrayAdapter(this.context, R.layout.location_spinner, locationNames)
        locationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                geoLocation = cityList[position]
                generateList()
            }
        }
    }

    private fun generateList()
    {
        // Refresh our list
        generatedTimes = mutableListOf<RiseAndSetTimes>()
        var newTimes : RiseAndSetTimes

        var myCal = Calendar.getInstance()

        var riseSetTime : RiseAndSetTimes
        for (i in startDate.time .. endDate.time step 86400000)
        {
            myCal.timeInMillis = i
            newTimes = getRiseSetTimes(myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),myCal.get(Calendar.DAY_OF_MONTH), geoLocation)
            generatedTimes.add(newTimes)
        }

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recycleAdapter = RecyclerAdapter(generatedTimes,this)
        recyclerView.adapter = recycleAdapter
    }

    override fun onItemClick(position: Int) {
        if (recycleAdapter.selectedPosition == position){
            recycleAdapter.selectedPosition = -1
        }
        else {
            recycleAdapter.selectedPosition = position
        }

        shareableInfo = generatedTimes[position]
    }
}
