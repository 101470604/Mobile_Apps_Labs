package au.edu.swin.movieratingskotlin

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ArrayList

data class Movie(val name: String, val rating: String, val votes: String) {
    companion object {
        /** Loads movie information from a raw resource file  */
        fun loadFromFile(inputStream: InputStream): ArrayList<Movie> {
            val movies = ArrayList<Movie>(20000)
            try {
                val br = BufferedReader(InputStreamReader(inputStream))
                while (true) {
                    val line = br.readLine() ?: break
                    val lRatings = line.substring(0, 3).trim { it <= ' ' }
                    val lVotes = line.substring(4, 12).trim { it <= ' ' }
                    val lName = line.substring(13).trim { it <= ' ' }
                    movies.add(Movie(lName, lRatings, lVotes))
                }
            } catch (iox: IOException) {
            }
            // pure evil at work
            return movies
        }
    }
}
