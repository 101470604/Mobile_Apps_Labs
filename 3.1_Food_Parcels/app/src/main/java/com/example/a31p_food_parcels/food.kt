package com.example.a31p_food_parcels

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Food (val arrayId : Int, val imageId : Int) : Parcelable

@Parcelize
data class FoodMetaData (
    var name: String,
    var url: String,
    var keywords : String,
    var dateObtained : String,
    var whoObtained : String,
    var share : Boolean,
    var rating : Int
) : Parcelable