package com.mtw.muffistruewatcher.ui.fooddiary

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mtw.muffistruewatcher.HandInjection
import com.mtw.muffistruewatcher.R
import com.mtw.muffistruewatcher.persistence.FoodDiaryEntry
import com.mtw.muffistruewatcher.ui.ViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_food_diary.*
import kotlinx.android.synthetic.main.app_bar_food_diary.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_food_diary.*
import java.time.LocalDateTime
import java.util.*


class FoodDiaryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val FOOD_DIARY_ADD_ENTRY_REQUEST_CODE = 1
    private val FOOD_DIARY_EDIT_ENTRY_REQUEST_CODE = 2

    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: FoodDiaryEntryViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_diary)
        setSupportActionBar(toolbar)

        button_food_diary_add_entry.setOnClickListener { _ ->
            val intent = Intent(this, FoodDiaryAddEntryActivity::class.java)
            startActivityForResult(intent, FOOD_DIARY_ADD_ENTRY_REQUEST_CODE)
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // This is awkward to just all splat here, frankly.
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = FoodDiaryEntryListAdapter(
            this,
            onEditClickListener = {
                val intent = Intent(this, FoodDiaryAddEntryActivity::class.java)
                intent.putExtra(EXTRA_DIARY_ENTRY, it)
                startActivityForResult(intent, FOOD_DIARY_EDIT_ENTRY_REQUEST_CODE)
            },
            onCopyClickListener = {
                val now = LocalDateTime.now()
                viewModel.insert(
                    it.copy(
                        id = UUID.randomUUID().toString(),
                        eatenDate = now,
                        createdDate = now,
                        updatedDate = now
                    )
                )
            },
            onDeleteClickListener = {
                AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Really delete this entry?")
                    .setMessage("Deletion is final! Once you delete this entry there's no way to get it back!")
                    .setPositiveButton("DELETE") { _, _ -> viewModel.delete(it) }
                    .setNegativeButton("CANCEL", null)
                    .create()
                    .show()
            })
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)

        viewModelFactory = HandInjection.provideViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FoodDiaryEntryViewModel::class.java)
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
        menuInflater.inflate(R.menu.food_diary, menu)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FOOD_DIARY_ADD_ENTRY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            viewModel.insert(data!!.getSerializableExtra(FoodDiaryAddEntryActivity.EXTRA_ENTRY) as FoodDiaryEntry)
        } else if (requestCode == FOOD_DIARY_EDIT_ENTRY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // TODO: Systemize the updatedDate?
            val entry = (data!!.getSerializableExtra(FoodDiaryAddEntryActivity.EXTRA_ENTRY) as FoodDiaryEntry)
                .copy(updatedDate = LocalDateTime.now())
            viewModel.update(entry)
        }
    }

    override fun onStart() {
        super.onStart()

        compositeDisposable.add(viewModel.foodDiaryEntries()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { if (it != null) {
                    (recyclerview.adapter as FoodDiaryEntryListAdapter).setEntries(it)
                } },
                { error -> Log.e(TAG, "Unable to get username", error) })
        )
    }

    override fun onStop() {
        super.onStop()

        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    companion object {
        private val TAG = FoodDiaryActivity::class.java.simpleName
        val EXTRA_DIARY_ENTRY = "com.mtw.muffistruewatcher.ui.fooddiary.FoodDiaryActivity.ENTRY"
    }
}
