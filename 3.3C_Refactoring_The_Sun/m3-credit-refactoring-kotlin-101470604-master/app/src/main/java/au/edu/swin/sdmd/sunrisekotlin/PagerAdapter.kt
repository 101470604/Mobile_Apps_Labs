package au.edu.swin.sdmd.sunrisekotlin

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PagerAdapter : FragmentPagerAdapter {

    private var home : Fragment = Home()
    private var dateRange : Fragment = DateRange()

    constructor (fManager : FragmentManager) : super(fManager)

    override fun getItem(fIndex: Int): Fragment {
        return when (fIndex)
        {
            0 -> {
                home
            }
            else -> {
                dateRange
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position)
        {
            0 -> "Home"
            else -> "Table"
        }
    }
}