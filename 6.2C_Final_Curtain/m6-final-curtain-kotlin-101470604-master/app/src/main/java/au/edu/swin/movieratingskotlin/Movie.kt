package au.edu.swin.movieratingskotlin

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ArrayList

data class Movie(val name: String, val rating: String, val votes: String)
