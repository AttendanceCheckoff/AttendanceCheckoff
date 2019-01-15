package edu.rosehulman.attendancecheckoff.club.history

import android.support.v7.widget.RecyclerView
import android.view.View
import edu.rosehulman.attendancecheckoff.model.Event
import kotlinx.android.synthetic.main.events_item.view.*

class HistoryViewHolder(itemView: View, adapter: HistoryAdapter) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            adapter.selectEvent(adapterPosition)
        }
    }

    fun bind(event: Event) {
        itemView.events_item_name.text = event.name
        itemView.events_item_date.text = event.dateTime.date.toString()
    }
}
