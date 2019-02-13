package edu.rosehulman.clubhub.club.personal

import android.support.v7.widget.RecyclerView
import android.view.View
import edu.rosehulman.clubhub.model.Event
import edu.rosehulman.clubhub.util.toReadableString
import kotlinx.android.synthetic.main.events_item.view.*

class PersonalHolder(itemView: View, val adapter: PersonalAdapter): RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            adapter.selectEvent(adapterPosition)
        }
    }

    fun bind(event: Event){
        itemView.events_item_name.text = event.name
        itemView.events_item_date.text = event.dateTime.toReadableString()
    }
}