package au.edu.swin.movieratingskotlin

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Utils {
    /** Creates a unique movie icon based on name and rating  */
    fun getMovieIcon(movieName: String, movieRating: String): Bitmap {
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
    fun getColor(name: String): Int {
        val hex = toHexString(name)
        val red = "#" + hex.substring(0, 2)
        val green = "#" + hex.substring(2, 4)
        val blue = "#" + hex.substring(4, 6)
        val alpha = "#" + hex.substring(6, 8)
        return Color.argb(Integer.decode(alpha)!!, Integer.decode(red)!!,
                Integer.decode(green)!!, Integer.decode(blue)!!)
    }

    /** Given a movie name -- generate a hex value from its hashcode  */
    fun toHexString(name: String): String {
        val hc = name.hashCode()
        var hex = Integer.toHexString(hc)
        if (hex.length < 8) {
            hex = hex + hex + hex
            hex = hex.substring(0, 8) // use default color value
        }
        return hex
    }

    /** Crude optimization to obtain a contrasting color -- does not work well yet  */
    fun getTextColor(bg: Int): Int {

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