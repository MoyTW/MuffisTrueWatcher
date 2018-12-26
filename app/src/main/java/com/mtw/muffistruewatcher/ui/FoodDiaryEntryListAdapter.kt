package com.mtw.muffistruewatcher.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mtw.muffistruewatcher.R
import com.mtw.muffistruewatcher.persistence.FoodDiaryEntry
import java.time.format.DateTimeFormatter


class FoodDiaryEntryListAdapter(context: Context): RecyclerView.Adapter<FoodDiaryEntryListAdapter.FoodDiaryEntryHolder>() {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    private val inflater: LayoutInflater
    private var foodDiaryEntries: List<FoodDiaryEntry>? = null

    inner class FoodDiaryEntryHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val entryView: TextView = itemView.findViewById(R.id.text_description)
        internal val dateView: TextView = itemView.findViewById(R.id.text_date)

    }

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodDiaryEntryHolder {
        val itemView = inflater.inflate(R.layout.recycler_view_item_food_diary, parent, false)
        return FoodDiaryEntryHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodDiaryEntryHolder, position: Int) {
        if (foodDiaryEntries != null) {
            val current = foodDiaryEntries!![position]
            holder.entryView.text = current.description
            holder.dateView.text = current.eatenDate.format(dateTimeFormatter)
        } else {
            // Covers the case of data not being ready yet.
            holder.entryView.text = "No Entries Yet"
        }
    }

    fun setEntries(entries: List<FoodDiaryEntry>) {
        Log.i("ASDF", "lkjalkdsjlfkjsldkfjlaskdjflskxmcnv,mxncv,mxncv,mxcnv,mxncvdjf entries: " + entries.size)
        foodDiaryEntries = entries
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (foodDiaryEntries != null)
            foodDiaryEntries!!.size
        else
            0
    }
}