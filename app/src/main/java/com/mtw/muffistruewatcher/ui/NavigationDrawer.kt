package com.mtw.muffistruewatcher.ui

import android.content.Context
import android.content.Intent
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.MenuItem
import com.mtw.muffistruewatcher.R

fun onNavigationItemSelected(packageContext: Context, drawerLayout: DrawerLayout, item: MenuItem): Boolean {
    // Handle navigation view item clicks here.
    when (item.itemId) {
        R.id.nav_food_diary -> {
            val intent = Intent(packageContext, FoodDiary::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            packageContext.startActivity(intent)
        }
        R.id.nav_checklist_builder -> {
            val intent = Intent(packageContext, ChecklistBuilder::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            packageContext.startActivity(intent)

        }
        R.id.nav_send -> {

        }
    }

    drawerLayout.closeDrawer(GravityCompat.START)
    return true
}