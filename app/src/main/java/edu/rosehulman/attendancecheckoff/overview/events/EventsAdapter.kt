package edu.rosehulman.attendancecheckoff.overview.events

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import edu.rosehulman.attendancecheckoff.CurrentState
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.event.EventActivity
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.model.Official
import edu.rosehulman.attendancecheckoff.util.Constants

class EventsAdapter(val context: Context?) : RecyclerView.Adapter<EventsViewHolder>() {

    val eventsRef = FirebaseFirestore.getInstance().collection("events")
    val officialRef = FirebaseFirestore.getInstance().collection("officials")

    val events = ArrayList<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.events_item, parent, false)
        return EventsViewHolder(view, this)
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(events[position])
    }

    fun addSnapshotListener() {
        eventsRef
            .whereGreaterThanOrEqualTo(Event.KEY_DATE_TIME, Timestamp.now())
            .whereArrayContains(Event.KEY_ATTENDED_MEMBERS, CurrentState.user.id)
            .addSnapshotListener { snapshot, firestoreException ->
                if (firestoreException != null) {
                    Log.d(Constants.TAG, "Error: $firestoreException")
                    return@addSnapshotListener
                }
                populateLocalEvents(snapshot)
            }
    }

    private fun populateLocalEvents(snapshot: QuerySnapshot?) {
        Log.d(Constants.TAG, "Populating Clubs")
        events.clear()
        snapshot?.let {
            events.addAll(it.map { doc -> Event.fromSnapshot(doc) })
            notifyDataSetChanged()
        }
    }

    fun selectEvent(position: Int) {
        val intent = Intent(context, EventActivity::class.java).apply {
            putExtra(Constants.ARG_INTENT_EVENT, events[position])
        }
        context?.startActivity(intent)
    }

    fun remove(position: Int){
        officialRef
            .whereEqualTo(Official.KEY_USER_ID, CurrentState.user.id)
            .addSnapshotListener { snapshot, firestoreException ->
                if (firestoreException != null) {
                    Log.d(Constants.TAG, "Error: $firestoreException")
                    return@addSnapshotListener
                }
                else{
                    for (document in snapshot!!){
                        val doc = Official.fromSnapshot(document)

                        Log.d("IDs", doc.clubId + ": "+ events[position].clubId)
                        if (doc.clubId.equals(events[position].clubId)){
                            eventsRef.document(events[position].id).delete()
                        }
                    }
                }
            }
    }
}