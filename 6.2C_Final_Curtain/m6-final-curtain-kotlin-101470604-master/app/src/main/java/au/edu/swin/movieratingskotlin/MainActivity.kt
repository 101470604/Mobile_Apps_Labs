package au.edu.swin.movieratingskotlin

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ArrayList

/**
 * Displays movie name, rating and votes. A custom icon is generated based on movie name and rating.
 * @author nronald
 * Based on code written by rvasa
 *
 */

class MainActivity : AppCompatActivity(){

    lateinit var global_util : Utils
    lateinit var recyclerView : RecyclerView
    lateinit var recycleAdapter: recyleAdapter
    lateinit var loadingBar : ProgressBar
    /* lazy initialise will only initialise movie list when its requested */
    lateinit var movies: MutableList<Movie>
    lateinit var loadingText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movies = ArrayList()
        initializeUI()
    }

    private fun initializeUI()
    {
        val br = BufferedReader(InputStreamReader(resources.openRawResource(R.raw.ratings)))
        var asynchLoader = AsynchLoad(this)
        asynchLoader.execute(br)
        global_util = Utils()
        recyclerView = findViewById(R.id.recycleView)
        loadingText = findViewById(R.id.label_loading)
        loadingBar = findViewById(R.id.indeterminateBar)
    }

    // Load movies one at a time
    inner class AsynchLoad(private var context: Context) : AsyncTask<BufferedReader, Boolean, Int>()
    {

        override fun doInBackground(vararg br: BufferedReader?): Int {
            try {
                while (true) {
                    val line = br[0]?.readLine() ?: break
                    val lRatings = line.substring(0, 3).trim { it <= ' ' }
                    val lVotes = line.substring(4, 12).trim { it <= ' ' }
                    val lName = line.substring(13).trim { it <= ' ' }
                    movies.add(Movie(lName, lRatings, lVotes))
                    publishProgress(true)
                }
            }
            catch (iox: IOException) {
            }
            publishProgress(false)
            return 0
        }

        override fun onProgressUpdate(vararg loading: Boolean?) {
            super.onProgressUpdate(*loading)

            if (loading[0] == false)
            {
                recyclerView.adapter?.notifyDataSetChanged()
                loadingText.visibility = View.GONE
                loadingBar.visibility = View.GONE
            }

        }

        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)

            recyclerView.layoutManager = LinearLayoutManager(context)
            recycleAdapter = recyleAdapter(movies, global_util)
            recyclerView.adapter = recycleAdapter
        }
    }
}

