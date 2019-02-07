package edu.rosehulman.attendancecheckoff.club.personal

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import edu.rosehulman.attendancecheckoff.CurrentState
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.event.EventActivity
import edu.rosehulman.attendancecheckoff.model.Club
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.util.Constants

class PersonalAdapter(val context: Context?, val club: Club): RecyclerView.Adapter<PersonalHolder>() {

    val eventsRef = FirebaseFirestore.getInstance().collection("events")
    val events = ArrayList<Event>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalHolder {
         val view = LayoutInflater.from(context).inflate(R.layout.events_item, parent, false)
        return PersonalHolder(view, this)
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: PersonalHolder, position: Int) {
        holder.bind(events[position])
    }

    fun selectEvent(position: Int){
        val intent = Intent(context, EventActivity::class.java).apply {
            putExtra(Constants.ARG_INTENT_EVENT, events[position])
        }
        context?.startActivity(intent)
    }

    fun addSnapshotListener(){
        eventsRef
            .whereArrayContains(Event.KEY_ATTENDED_MEMBERS, CurrentState.user.id)
            .addSnapshotListener { snapshot, firestoreException ->
                if (firestoreException != null) {
                    Log.d(Constants.TAG, "Error: $firestoreException")
                    return@addSnapshotListener
                }
                populateLocalEvents(snapshot)
            }

    }

    fun populateLocalEvents(snapshot: QuerySnapshot?){
        events.clear()
        for (document in snapshot!!){
            val doc = Event.fromSnapshot(document)
            if (doc.clubId == club.id){
                events.add(doc)
            }
            notifyDataSetChanged()
        }
    }
}