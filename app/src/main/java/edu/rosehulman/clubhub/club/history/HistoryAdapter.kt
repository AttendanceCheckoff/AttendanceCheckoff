package edu.rosehulman.attendancecheckoff.club.history

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.event.EventActivity
import edu.rosehulman.attendancecheckoff.model.Club
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.util.Constants

class HistoryAdapter(val context: Context?, val club: Club) : RecyclerView.Adapter<HistoryViewHolder>() {

    val events = ArrayList<Event>()
    val eventsRef = FirebaseFirestore.getInstance().collection("events")


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
            putExtra(Constants.ARG_INTENT_EVENT, events[position])
        }
        context?.startActivity(intent)
    }

    fun addSnapshotListener() {
        eventsRef
            .whereLessThanOrEqualTo(Event.KEY_DATE_TIME, Timestamp.now())
            .whereEqualTo(Event.KEY_CLUB_ID, club.id)
            .addSnapshotListener { snapshot, firestoreException ->
                if (firestoreException != null) {
                    Log.d(Constants.TAG, "Error: $firestoreException")
                    return@addSnapshotListener
                }
                populateLocalEvents(snapshot)
            }

    }

    fun populateLocalEvents(snapshot: QuerySnapshot?) {
        events.clear()
        for (document in snapshot!!) {
            val doc = Event.fromSnapshot(document)
            events.add(doc)
            notifyDataSetChanged()
        }
    }
}
