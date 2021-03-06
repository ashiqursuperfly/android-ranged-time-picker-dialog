package com.ashiqurrahman.rangedtimepickerdialog.library

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ashiqurrahman.rangedtimepickerdialog.R
import com.google.android.material.textview.MaterialTextView


/*
 * Created by : 
 * <a href="https://www.github.com/ashiqursuperfly">Ashiqur Rahman</a> on 7/1/20.
*/

class PagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    interface FragmentViewPagerLifecycle {
        fun onResumeFragment()
        fun onPauseFragment()
    }

    private val pageChangeListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {
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
        when (position) {
            0 -> return PickStartTimeFragment()
            1 -> return PickEndTimeFragment()
        }

        Log.d("RangeTimePicker", "Error! ViewPager Adapter with unknown position:$position")
        return PickStartTimeFragment()

    }

    override fun getCount(): Int {
        return 2
    }

    fun createTabCustomView(context: Context, header: String, text: String, idx: Int): View {
        val v: View = LayoutInflater.from(context).inflate(R.layout.custom_tab, null)
        val title = v.findViewById(R.id.tv_title) as MaterialTextView
        title.text = header

        val time = v.findViewById(R.id.tv_time) as MaterialTextView
        time.text = text
        if (idx == 0) {
            title.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            time.setTextColor(ContextCompat.getColor(context, android.R.color.black))
        }
        return v
    }
}