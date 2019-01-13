package edu.rosehulman.attendancecheckoff.overview.events

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.event.EventActivity
import edu.rosehulman.attendancecheckoff.util.Utils

class EventsAdapter(val context: Context?) : RecyclerView.Adapter<EventsViewHolder>() {

    val events = Utils.testUser.events

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.events_item, parent, false)
        return EventsViewHolder(view, this)
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(events[position])
    }

    fun selectEvent(position: Int) {
        val intent = Intent(context, EventActivity::class.java).apply {
            putExtra("CLUB", events[position])
        }
        context?.startActivity(intent)
    }
}