package com.mtw.muffistruewatcher.ui

import android.content.Context
import android.content.Intent
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import android.view.MenuItem
import com.mtw.muffistruewatcher.R
import com.mtw.muffistruewatcher.ui.fooddiary.FoodDiaryActivity
import com.mtw.muffistruewatcher.ui.todo.TodoActivity

fun onNavigationItemSelected(packageContext: Context, drawerLayout: DrawerLayout, item: MenuItem): Boolean {
    // Handle navigation view item clicks here.
    when (item.itemId) {
        R.id.nav_food_diary -> {
            val intent = Intent(packageContext, FoodDiaryActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            packageContext.startActivity(intent)
        }
        R.id.nav_checklist_builder -> {
            val intent = Intent(packageContext, ChecklistBuilder::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            packageContext.startActivity(intent)
        }
        R.id.nav_todo -> {
            val intent = Intent(packageContext, TodoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            packageContext.startActivity(intent)
        }
        R.id.nav_settings -> {
            val intent = Intent(packageContext, SettingsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            packageContext.startActivity(intent)
        }
    }

    drawerLayout.closeDrawer(GravityCompat.START)
    return true
}