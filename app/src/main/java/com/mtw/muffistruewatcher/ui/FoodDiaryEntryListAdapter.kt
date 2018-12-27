package com.mtw.muffistruewatcher.ui

import android.content.Context
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
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var foodDiaryEntries: List<FoodDiaryEntry>? = null

    inner class FoodDiaryEntryHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val nameView: TextView = itemView.findViewById(R.id.label_rv_itm_fd_name)
        internal val dateView: TextView = itemView.findViewById(R.id.label_rv_itm_fd_date)
        internal val commentaryLabel: TextView = itemView.findViewById(R.id.label_rv_itm_fd_commentary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodDiaryEntryHolder {
        val itemView = inflater.inflate(R.layout.recycler_view_item_food_diary, parent, false)
        return FoodDiaryEntryHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodDiaryEntryHolder, position: Int) {
        if (foodDiaryEntries != null) {
            val current = foodDiaryEntries!![position]
            holder.nameView.text = current.name
            holder.dateView.text = current.eatenDate.format(dateTimeFormatter)
            holder.commentaryLabel.text = current.commentary
        } else {
            // Covers the case of data not being ready yet.
            holder.nameView.text = "No Entries Yet"
        }
    }

    fun setEntries(entries: List<FoodDiaryEntry>) {
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