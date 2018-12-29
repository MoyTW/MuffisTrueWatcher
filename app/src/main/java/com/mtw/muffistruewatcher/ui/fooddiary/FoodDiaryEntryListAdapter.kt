package com.mtw.muffistruewatcher.ui.fooddiary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mtw.muffistruewatcher.R
import com.mtw.muffistruewatcher.persistence.FoodDiaryEntry
import java.time.format.DateTimeFormatter


class FoodDiaryEntryListAdapter(
    context: Context,
    private val onEditClickListener: View.OnClickListener,
    private val onCopyClickListener: View.OnClickListener,
    private val onDeleteClickListener: View.OnClickListener
) : RecyclerView.Adapter<FoodDiaryEntryListAdapter.FoodDiaryEntryHolder>() {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var foodDiaryEntries: List<FoodDiaryEntry>? = null

    inner class FoodDiaryEntryHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val nameView: TextView = itemView.findViewById(R.id.label_rv_itm_fd_name)
        internal val pointsView: TextView = itemView.findViewById(R.id.label_rv_itm_fd_points)
        internal val dateView: TextView = itemView.findViewById(R.id.label_rv_itm_fd_date)
        internal val commentaryLabel: TextView = itemView.findViewById(R.id.label_rv_itm_fd_commentary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodDiaryEntryHolder {
        val itemView = inflater.inflate(R.layout.recycler_view_item_food_diary, parent, false)

        itemView.findViewById<ConstraintLayout>(R.id.layout_rv_itm_fd_details).setOnClickListener(onEditClickListener)
        itemView.findViewById<ImageButton>(R.id.button_rv_itm_fd_copy).setOnClickListener(onCopyClickListener)
        itemView.findViewById<ImageButton>(R.id.button_rv_itm_fd_delete).setOnClickListener(onDeleteClickListener)
        return FoodDiaryEntryHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodDiaryEntryHolder, position: Int) {
        if (foodDiaryEntries != null) {
            val current = foodDiaryEntries!![position]
            holder.nameView.text = current.name
            holder.pointsView.text = current.points.toString()
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