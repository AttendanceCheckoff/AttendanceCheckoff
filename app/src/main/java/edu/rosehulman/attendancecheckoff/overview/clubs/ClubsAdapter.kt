package edu.rosehulman.attendancecheckoff.overview.clubs

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
import edu.rosehulman.attendancecheckoff.club.ClubActivity
import edu.rosehulman.attendancecheckoff.model.Club
import edu.rosehulman.attendancecheckoff.util.Constants

class ClubsAdapter(val context: Context?) : RecyclerView.Adapter<ClubViewHolder>() {

    val clubsRef by lazy { FirebaseFirestore.getInstance().collection("clubs") }
    val clubs = ArrayList<Club>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.club_item, parent, false)
        return ClubViewHolder(view, this)
    }

    override fun getItemCount() = clubs.size

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
//        holder.bind(clubs[position])
    }

    fun addSnapshotListener() {
        clubsRef.whereArrayContains("id", CurrentState.user.clubs).addSnapshotListener { snapshot, firestoreException ->
            if (firestoreException != null) {
                Log.d(Constants.TAG, "Error: $firestoreException")
                return@addSnapshotListener
            }
            populateLocalMovieQuotes(snapshot)
        }
    }

    private fun populateLocalMovieQuotes(snapshot: QuerySnapshot?) {
        Log.d(Constants.TAG, "Populating Clubs")
        clubs.clear()
        snapshot?.let {
            clubs.addAll(it.map { doc -> Club.fromSnapshot(doc) })
        }
        notifyDataSetChanged()
    }

    fun selectClub(position: Int) {
        val intent = Intent(context, ClubActivity::class.java).apply {
            putExtra(Constants.ARG_INTENT_CLUB, clubs[position])
        }
        context?.startActivity(intent)
    }
}