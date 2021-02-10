package au.edu.swin.sdmd.sunrisekotlin

import android.content.Context
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.DatePicker
import au.edu.swin.sdmd.sunrisekotlin.calc.AstronomicalCalendar
import au.edu.swin.sdmd.sunrisekotlin.calc.GeoLocation
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

open class SuntimeCore : Fragment() {

    protected fun copyOverData()
    {
        val inputStream:InputStream = resources.openRawResource(R.raw.au_locations)
        val inputStreamReader = InputStreamReader(inputStream)
        val file = File(context?.filesDir,"locations.txt")

        if (!file.exists()) {
            val fos = FileOutputStream(file, true)
            var str = ""
            var line: String?
            val br = BufferedReader(inputStreamReader)
            line = br.readLine()

            while (br.readLine() != null) {
                if (line != null) {
                    str += line + "\n"
                }
                line = br.readLine()
            }
            br.close()

            fos.write(str.toByteArray())
            fos.close()
        }
    }

    protected fun readInLocations() : List<GeoLocation>
    {
        copyOverData()
        var result = mutableListOf<GeoLocation>()
        val fis = context?.openFileInput("locations.txt")
        val reader = fis?.bufferedReader()
        var str = reader?.readLine()

        while (str != null)
        {
            val line : List<String> = str.split(',')
            var newLocation = GeoLocation(line[0],line[1].toDouble(),line[2].toDouble(),TimeZone.getTimeZone(line[3]))
            result.add(newLocation)
            str = reader?.readLine()
        }

        fis?.close()
        return result
    }

    protected fun getRiseSetTimes(year : Int, month : Int, day : Int, geolocation : GeoLocation) : RiseAndSetTimes {
        val ac = AstronomicalCalendar(geolocation)
        ac.getCalendar().set(year, month, day)
        val srise = ac.getSunrise()
        val sset = ac.getSunset()
        val sdf = SimpleDateFormat("HH:mm")
        Log.d("SUNRISE Unformatted", srise.toString())
        return RiseAndSetTimes(sdf.format(srise), sdf.format(sset), "$year/$month/$day", geolocation.locationName)
    }
}