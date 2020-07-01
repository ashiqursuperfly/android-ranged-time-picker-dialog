package com.ashiqurrahman.daterangepickerdialog.base

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager


/*
 * Created by : 
 * <a href="https://www.github.com/ashiqursuperfly">Ashiqur Rahman</a> on 7/1/20.
*/

class PagerAdapter(fm: FragmentManager)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val TAB_TITLES = arrayOf("Start\n12:00AM", "End\n16:45")

    interface FragmentViewPagerLifecycle {
        fun onResumeFragment()
        fun onPauseFragment()
    }

    private val pageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        var currentPosition = 0
        override fun onPageSelected(newPosition: Int) {
            val fragmentToShow = getItem(newPosition) as FragmentViewPagerLifecycle
            fragmentToShow.onResumeFragment()
            val fragmentToHide = getItem(currentPosition) as FragmentViewPagerLifecycle
            fragmentToHide.onPauseFragment()
            currentPosition = newPosition
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    fun getPageChangeListener(): ViewPager.OnPageChangeListener {
        return pageChangeListener
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> return PickStartTimeFragment()
            1 -> return PickEndTimeFragment()
        }

        Log.d(this.javaClass.simpleName,"Error! ViewPager Adapter with unknown position:$position")
        return PickStartTimeFragment()

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        return 2
    }
}