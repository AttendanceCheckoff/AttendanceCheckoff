package edu.rosehulman.attendancecheckoff.event

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.model.User
import edu.rosehulman.attendancecheckoff.util.Constants

class EventAdapter(val context: Context, event: Event): RecyclerView.Adapter<EventHolder>() {

    val usersRef = FirebaseFirestore.getInstance().collection("users")

    init{

    }

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

    }


    fun populateLocalMembers(snapshot: QuerySnapshot) {
        Log.d(Constants.TAG, "Populating Members")
        members.clear()
        snapshot?.let {
            members.addAll(it.map { doc -> User.fromSnapshot(doc) })
            notifyDataSetChanged()
        }
    }



}