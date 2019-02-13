package edu.rosehulman.clubhub.overview.clubs

import android.support.v7.widget.RecyclerView
import android.view.View
import edu.rosehulman.clubhub.model.Club
import kotlinx.android.synthetic.main.club_item.view.*

class ClubViewHolder(itemView: View, adapter: ClubsAdapter) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            adapter.selectClub(adapterPosition)
        }
    }

    fun bind(club: Club) {
        itemView.clubs_item_name.text = club.name
    }
}