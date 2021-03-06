package edu.rosehulman.clubhub.overview.events

import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import edu.rosehulman.clubhub.CurrentState
import edu.rosehulman.clubhub.R
import edu.rosehulman.clubhub.event.EventActivity
import edu.rosehulman.clubhub.model.Club
import edu.rosehulman.clubhub.model.Event
import edu.rosehulman.clubhub.model.Official
import edu.rosehulman.clubhub.util.Constants

class EventsAdapter(val context: Context?, val view: View) : RecyclerView.Adapter<EventsViewHolder>() {

    val eventsRef = FirebaseFirestore.getInstance().collection(Event.KEY_COLLECTION)
    val officialRef = FirebaseFirestore.getInstance().collection(Official.KEY_COLLECTION)
    val clubsRef = FirebaseFirestore.getInstance().collection(Club.KEY_COLLECTION)

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
        clubsRef
            .whereArrayContains(Club.KEY_MEMBERS, CurrentState.user.id)
            .addSnapshotListener { snapshot, firestoreException ->
                if (firestoreException != null) {
                    Log.d(Constants.TAG, "Error: $firestoreException")
                    return@addSnapshotListener
                }
                getAllEvents(snapshot)
            }
    }

    private fun getAllEvents(snapshot: QuerySnapshot?) {
        eventsRef
            .whereGreaterThanOrEqualTo(Event.KEY_DATE_TIME, Timestamp.now())
            .orderBy(Event.KEY_DATE_TIME, Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot2, firestoreException ->
                if (firestoreException != null) {
                    Log.d(Constants.TAG, "Error: $firestoreException")
                    return@addSnapshotListener
                }
                populateLocalEvents(snapshot, snapshot2)
            }
    }

    private fun populateLocalEvents(clubSnapshot: QuerySnapshot?, eventSnapshot: QuerySnapshot?) {
        Log.d(Constants.TAG, "Populating Events")
        events.clear()
        clubSnapshot?.let { clubSnap ->
            clubSnap.map { doc -> Club.fromSnapshot(doc) }.forEach { club ->
                Log.d(Constants.TAG, club.toString())
                eventSnapshot?.let { eventSnap ->
                    events.addAll(eventSnap.map { doc -> Event.fromSnapshot(doc) }.filter { event -> event.clubId == club.id })
                    Log.d(Constants.TAG, events.toString())
                }
            }
            events.sortBy { it.dateTime }
            notifyDataSetChanged()
        }
    }

    fun selectEvent(position: Int) {
        val intent = Intent(context, EventActivity::class.java).apply {
            putExtra(Constants.ARG_INTENT_EVENT, events[position])
        }
        context?.startActivity(intent)
    }

    fun remove(position: Int) {
        officialRef
            .whereEqualTo(Official.KEY_USER_ID, CurrentState.user.id)
            .addSnapshotListener { snapshot, firestoreException ->
                if (firestoreException != null) {
                    Log.d(Constants.TAG, "Error: $firestoreException")
                    return@addSnapshotListener
                } else {
                    snapshot?.let {
                        val officials = it.map { doc -> Official.fromSnapshot(doc) }
                        if (officials.any { official ->  official.clubId == events[position].clubId }) {
                            val event = events[position]
                            eventsRef.document(events[position].id).delete()
                            Snackbar.make(view, "Are you sure you want to delete?", Snackbar.LENGTH_INDEFINITE)
                                .setAction("undo") {
                                    eventsRef.add(event)
                                }.show()
                        } else {
                            Toast.makeText(context, "Missing authorization", Toast.LENGTH_LONG).show()
                        }
                    }

                }
            }
        notifyDataSetChanged()
    }
}