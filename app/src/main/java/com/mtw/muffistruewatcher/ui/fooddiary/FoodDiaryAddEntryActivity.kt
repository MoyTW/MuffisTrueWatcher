package com.mtw.muffistruewatcher.ui.fooddiary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.mtw.muffistruewatcher.R

class FoodDiaryAddEntryActivity : AppCompatActivity() {

    private var editEntryView: EditText? = null
    private var editCommentaryView: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_diary_add_entry)
        editEntryView = findViewById(R.id.food_diary_add_entry_edit)
        editCommentaryView = findViewById(R.id.text_food_diary_add_entry_commentary)

        val button = findViewById<Button>(R.id.food_diary_add_entry_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editEntryView!!.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(EXTRA_NAME, editEntryView!!.text.toString())
                replyIntent.putExtra(EXTRA_COMMENTARY, editCommentaryView!!.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        val EXTRA_NAME = "com.mtw.muffistruewatcher.ui.fooddiary.FoodDiaryAddEntryActivity.NAME"
        val EXTRA_COMMENTARY = "com.mtw.muffistruewatcher.ui.fooddiary.FoodDiaryAddEntryActivity.COMMENTARY"
    }
}
