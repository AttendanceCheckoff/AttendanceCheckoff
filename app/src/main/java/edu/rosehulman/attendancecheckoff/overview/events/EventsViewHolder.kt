package edu.rosehulman.attendancecheckoff.overview.events

import android.support.v7.widget.RecyclerView
import android.view.View
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.util.toReadableString
import kotlinx.android.synthetic.main.events_item.view.*

class EventsViewHolder(itemView: View, adapter: EventsAdapter) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            adapter.selectEvent(adapterPosition)
        }
    }

    fun bind(event: Event) {
        itemView.events_item_name.text = event.name
        itemView.events_item_date.text = event.dateTime.toReadableString()
    }
}
