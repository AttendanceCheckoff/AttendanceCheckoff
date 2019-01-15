package edu.rosehulman.attendancecheckoff.club.officials

import android.support.v7.widget.RecyclerView
import android.view.View
import edu.rosehulman.attendancecheckoff.model.User
import kotlinx.android.synthetic.main.officials_member_item.view.*

class OfficialsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    fun bind(official: Pair<User, String>) {
        itemView.official_member_item_name.text = official.first.name
    }
}