package au.edu.swin.sdmd.sunrisekotlin

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import kotlinx.android.parcel.Parcelize
import au.edu.swin.sdmd.sunrisekotlin.calc.GeoLocation
import java.util.*

data class DateData(val year : Int, val month : Int, val day : Int)

    @Parcelize
data class RiseAndSetTimes(val sunRise : String, val sunSet : String, val date : String, val location: String) : Parcelable

interface shareable
{
    fun share(): RiseAndSetTimes
}

interface OnItemClickedListener
{
    fun onItemClick(position : Int)
}