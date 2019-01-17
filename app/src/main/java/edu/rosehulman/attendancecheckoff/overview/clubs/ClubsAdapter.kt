package edu.rosehulman.attendancecheckoff.overview.clubs

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.club.ClubActivity
import edu.rosehulman.attendancecheckoff.util.Constants
import edu.rosehulman.attendancecheckoff.util.Utils

class ClubsAdapter(val context: Context?) : RecyclerView.Adapter<ClubViewHolder>() {

    val clubs = Utils.testUser.clubs

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.club_item, parent, false)
        return ClubViewHolder(view, this)
    }

    override fun getItemCount() = clubs.size

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
//        holder.bind(clubs[position])
    }

    fun selectClub(position: Int) {
        val intent = Intent(context, ClubActivity::class.java).apply {
            putExtra(Constants.ARG_INTENT_CLUB, clubs[position])
        }
        context?.startActivity(intent)
    }
}