package edu.rosehulman.attendancecheckoff.overview.events

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import edu.rosehulman.attendancecheckoff.model.Event

class EventsAdapter: RecyclerView.Adapter<EventsViewHolder>() {

    val events = ArrayList<Event>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): EventsViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(p0: EventsViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}