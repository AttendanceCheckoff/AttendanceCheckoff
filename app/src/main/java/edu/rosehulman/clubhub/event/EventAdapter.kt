package edu.rosehulman.clubhub.event

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import edu.rosehulman.clubhub.R
import edu.rosehulman.clubhub.model.Event
import edu.rosehulman.clubhub.model.User
import edu.rosehulman.clubhub.util.Constants

class EventAdapter(val context: Context, val currentEvent: Event): RecyclerView.Adapter<EventHolder>() {

    val usersRef = FirebaseFirestore.getInstance().collection("users")
    val members = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.officials_member_item, parent, false)
        return EventHolder(view, this)
    }

    override fun getItemCount() = members.size

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(members[position])
    }

    fun addSnapshotListener(){
        Log.d(Constants.TAG, "Adding Event Snapshot Listener")
        Log.d(Constants.TAG, currentEvent.id)
        usersRef
            .whereArrayContains(User.KEY_EVENTS, currentEvent.id)
            .addSnapshotListener { snapshot, firestoreException ->
                if (firestoreException != null) {
                    Log.d(Constants.TAG, "Error: $firestoreException")
                    return@addSnapshotListener
                }
                populateLocalMembers(snapshot)
            }
    }

    fun populateLocalMembers(snapshot: QuerySnapshot?) {
        Log.d(Constants.TAG, "Populating Members")
        members.clear()
        snapshot?.let {
            members.addAll(it.map { doc -> User.fromSnapshot(doc) })
            notifyDataSetChanged()
        }
    }

    fun sendEmail(position: Int) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.email_domain, members[position].username)))
        }
        context.startActivity(intent)
    }
}