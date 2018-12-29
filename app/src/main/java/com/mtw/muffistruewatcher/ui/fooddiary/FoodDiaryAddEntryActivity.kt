package com.mtw.muffistruewatcher.ui.fooddiary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.mtw.muffistruewatcher.R
import com.mtw.muffistruewatcher.persistence.FoodDiaryEntry
import java.time.LocalDateTime

class FoodDiaryAddEntryActivity : AppCompatActivity() {

    private var editEntryView: EditText? = null
    private var editCommentaryView: EditText? = null
    private var editPointsView: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_diary_add_entry)
        editEntryView = findViewById(R.id.text_food_diary_add_entry_name)
        editCommentaryView = findViewById(R.id.text_food_diary_add_entry_commentary)
        editPointsView = findViewById(R.id.text_food_diary_add_entry_points)

        // If the intent isn't null,
        val entry: FoodDiaryEntry? = intent.getSerializableExtra(FoodDiaryActivity.EXTRA_DIARY_ENTRY) as FoodDiaryEntry?
        entry?.let {
            editEntryView?.setText(it.name)
            editCommentaryView?.setText(it.commentary)
            editPointsView?.setText(it.points.toString())
        }

        // Set the button listener
        val button = findViewById<Button>(R.id.food_diary_add_entry_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editEntryView!!.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val returnEntry = entry?.copy(
                    name = editEntryView!!.text.toString(),
                    commentary = editCommentaryView!!.text.toString(),
                    points = Integer.parseInt(editPointsView!!.text.toString())
                ) ?: FoodDiaryEntry(
                    name = editEntryView!!.text.toString(),
                    commentary = editCommentaryView!!.text.toString(),
                    points = Integer.parseInt(editPointsView!!.text.toString()),
                    eatenDate = LocalDateTime.now()
                )
                replyIntent.putExtra(EXTRA_ENTRY, returnEntry)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        val EXTRA_ENTRY = "com.mtw.muffistruewatcher.ui.fooddiary.FoodDiaryAddEntryActivity.ENTRY"
    }
}
