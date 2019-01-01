package com.mtw.muffistruewatcher.ui.todo

import android.net.Uri
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mtw.muffistruewatcher.R
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.app_bar_todo.*

class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val pages = listOf(
        Pair("Overdue", TodoOverdueFragment.newInstance("a", "b")),
        Pair("Today", TodoTodayFragment.newInstance("unnecessary", "params")),
        Pair("Queue", TodoQueueFragment.newInstance("c", "d"))
    )

    override fun getPageTitle(position: Int): CharSequence? {
        return pages[position].first
    }

    override fun getItem(position: Int): Fragment {
        return pages[position].second
    }

    override fun getCount(): Int {
        return pages.size
    }
}

class TodoActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    TodoOverdueFragment.OnFragmentInteractionListener, TodoTodayFragment.OnFragmentInteractionListener,
    TodoQueueFragment.OnFragmentInteractionListener {

    // I assume that I'll need to split this, to handle both? I need to read that "How 2 Fragment" tutorial.
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // Tabs & Swipes
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        viewPager.adapter = TabAdapter(supportFragmentManager)
        viewPager.currentItem = 1
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.todo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return com.mtw.muffistruewatcher.ui.onNavigationItemSelected(this, drawer_layout, item)
    }
}
