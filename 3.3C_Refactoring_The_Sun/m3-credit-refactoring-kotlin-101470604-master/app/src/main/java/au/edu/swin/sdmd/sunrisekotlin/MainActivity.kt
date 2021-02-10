package au.edu.swin.sdmd.sunrisekotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.util.Log
import android.widget.*
import au.edu.swin.sdmd.sunrisekotlin.calc.AstronomicalCalendar
import au.edu.swin.sdmd.sunrisekotlin.calc.GeoLocation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.text.SimpleDateFormat
import java.util.*
import android.support.v4.view.ViewPager
import android.view.View

class MainActivity : AppCompatActivity() {


    private lateinit var viewPager : ViewPager
    private lateinit var pagerAdapter : PagerAdapter
    private lateinit var shareButton : Button
    private lateinit var fragmentManager : FragmentManager
    private lateinit var tabManager : TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabManager = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewpager)
        fragmentManager = supportFragmentManager
        pagerAdapter = PagerAdapter(fragmentManager)
        viewPager.adapter = pagerAdapter
        tabManager.setupWithViewPager(viewPager)
        shareButton = findViewById(R.id.share_button)

        shareButton.setOnClickListener {
            share()
        }
    }



    private fun share()
    {
        val currentFragment : shareable = pagerAdapter.getItem(tabManager.selectedTabPosition) as shareable
        val info = currentFragment.share()

        var share = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.setType("text/plain")
            this.putExtra(Intent.EXTRA_SUBJECT, "Sun rise and set times")
            this.putExtra(Intent.EXTRA_TEXT, "${info.location} ${info.date} - Sunrise: ${info.sunRise}, Sunset ${info.sunSet}")
        }
        startActivity(Intent.createChooser(share, "Share!"))
    }
}
