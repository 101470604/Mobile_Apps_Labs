package au.edu.swin.movieratingskotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import java.util.ArrayList

/**
 * Displays movie name, rating and votes. A custom icon is generated based on movie name and rating.
 * @author nronald
 * Based on code written by rvasa
 *
 */

class MainActivity : AppCompatActivity() {

    /* lazy initialise will only initialise movie list when its requested */
    private val movies: ArrayList<Movie> by lazy {
        Movie.loadFromFile(resources.openRawResource(R.raw.ratings))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeUI()
    }

    private fun initializeUI()
    {
        val listView = findViewById<ListView>(R.id.list)
        listView.adapter = RowIconAdapter(this, R.layout.listrow, R.id.row_label, movies)
    }

    internal inner class RowIconAdapter(c: Context, rowResourceId: Int, textViewResourceId: Int,
                                        movies: ArrayList<Movie>) : ArrayAdapter<Movie>(c, rowResourceId, textViewResourceId, movies) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


            val inflatter = LayoutInflater.from(parent?.context)
            val row = inflatter.inflate(R.layout.listrow, parent, false)
            val currMovie = getItem(position)
            val icon = row.findViewById(R.id.row_icon) as ImageView
            val movieText = row.findViewById(R.id.row_label) as TextView
            val votesText = row.findViewById(R.id.row_subtext) as TextView
            movieText.text = currMovie.name
            val votesStr = currMovie.votes + " votes"
            votesText.text = votesStr
            val movieIcon = getMovieIcon(currMovie.name, currMovie.rating)
            icon.setImageBitmap(movieIcon)
            Log.w("MVMVMVMVMVMV", "Creating row view at position " + position + " movie " + currMovie.name)
            return row
        }
    }

    /** Creates a unique movie icon based on name and rating  */
    private fun getMovieIcon(movieName: String, movieRating: String): Bitmap {
        val bgColor = getColor(movieName)
        val b = Bitmap.createBitmap(192, 192, Bitmap.Config.ARGB_8888)
        b.eraseColor(bgColor) // fill bitmap with the color
        val c = Canvas(b)
        val p = Paint()
        p.isAntiAlias = true
        p.color = getTextColor(bgColor)
        p.textSize = 96.0f
        c.drawText(movieRating, 32f, 96f, p)
        return b
    }

    /** Construct a color from a movie name  */
    private fun getColor(name: String): Int {
        val hex = toHexString(name)
        val red = "#" + hex.substring(0, 2)
        val green = "#" + hex.substring(2, 4)
        val blue = "#" + hex.substring(4, 6)
        val alpha = "#" + hex.substring(6, 8)
        return Color.argb(Integer.decode(alpha)!!, Integer.decode(red)!!,
                Integer.decode(green)!!, Integer.decode(blue)!!)
    }

    /** Given a movie name -- generate a hex value from its hashcode  */
    private fun toHexString(name: String): String {
        val hc = name.hashCode()
        var hex = Integer.toHexString(hc)
        if (hex.length < 8) {
            hex = hex + hex + hex
            hex = hex.substring(0, 8) // use default color value
        }
        return hex
    }

    /** Crude optimization to obtain a contrasting color -- does not work well yet  */
    private fun getTextColor(bg: Int): Int {

        var r = Color.red(bg)
        var g = Color.green(bg)
        var b = Color.blue(bg)
        var hex = Integer.toHexString(r) + Integer.toHexString(g)
        hex += Integer.toHexString(b)

        val cDec = Integer.decode("#$hex")!!
        if (cDec > 0xFFFFFF / 2)
        // go dark for lighter shades
            return Color.rgb(0, 0, 0)
        else {
            r = (r + 128) % 256
            g = (g + 128) % 256
            b = (b + 128) % 256
            return Color.rgb(r, g, b)
        }
    }
}

