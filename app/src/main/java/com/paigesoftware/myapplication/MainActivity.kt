package com.paigesoftware.myapplication

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentPagerAdapter
import androidx.palette.graphics.Palette
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class MainActivity : AppCompatActivity() {

    private val TAG: String = "ab"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.htab_toolbar)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) supportActionBar!!.setTitle("Parallax Tabs")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val viewPager = findViewById<ViewPager>(R.id.htab_viewpager)
        setupViewPager(viewPager)


        val tabLayout = findViewById<TabLayout>(R.id.htab_tabs)
        tabLayout.setupWithViewPager(viewPager)

        val collapsingToolbarLayout =
            findViewById<View>(R.id.htab_collapse_toolbar) as CollapsingToolbarLayout

        //Using the Palette API, we’ll set colours for the Toolbar, TabLayout and Status Bar. This is easily done by the following code snippet:
        //Vibrant Color, animation 효과다
        try {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.sexy)
            Palette.from(bitmap).generate { palette ->
                val vibrantColor: Int =
                    palette?.getVibrantColor(Color.parseColor("#3F51B5")) ?: Color.WHITE
                val vibrantDarkColor: Int =
                    palette?.getDarkVibrantColor(Color.parseColor("#303F9F")) ?: Color.WHITE
                collapsingToolbarLayout.setContentScrimColor(vibrantColor)
                collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor)
            }
        } catch (e: Exception) {
            // if Bitmap fetch fails, fallback to primary colors
            Log.e(TAG, "onCreate: failed to create bitmap from background", e.fillInStackTrace())
            collapsingToolbarLayout.setContentScrimColor(
                Color.parseColor("#3F51B5")
            )
            collapsingToolbarLayout.setStatusBarScrimColor(
                Color.parseColor("#303F9F")
            )
        }

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                Log.d(TAG, "onTabSelected: pos: " + tab.position)
                when (tab.position) {
                    0 -> showToast("One")
                    1 -> showToast("Two")
                    2 -> showToast("Three")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(
            supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        adapter.addFragment(
            DummyFragment(
                Color.parseColor("#00BCD4")
            ), "Cyan"
        )
        adapter.addFragment(
            DummyFragment(
                Color.parseColor("#FFC107")
            ), "Amber"
        )
        adapter.addFragment(
            DummyFragment(
                Color.parseColor("#F3E5F5")
            ), "Purple"
        )
        viewPager.adapter = adapter
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }


}