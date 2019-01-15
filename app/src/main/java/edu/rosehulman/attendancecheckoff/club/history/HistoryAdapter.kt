package edu.rosehulman.attendancecheckoff.club.history

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.event.EventActivity
import edu.rosehulman.attendancecheckoff.model.Event

class HistoryAdapter(val context: Context?) : RecyclerView.Adapter<HistoryViewHolder>() {

    val events = ArrayList<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.events_item, parent, false)
        return HistoryViewHolder(view, this)
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(events[position])
    }

    fun selectEvent(position: Int) {
        val intent = Intent(context, EventActivity::class.java).apply {
            putExtra("CLUB", events[position])
        }
        context?.startActivity(intent)
    }
}
