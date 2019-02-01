package edu.rosehulman.attendancecheckoff.event

import android.support.v7.widget.RecyclerView
import android.view.View
import edu.rosehulman.attendancecheckoff.model.User
import kotlinx.android.synthetic.main.officials_member_item.view.*

class EventHolder(itemView: View, adapter: EventAdapter): RecyclerView.ViewHolder(itemView) {

    fun bind(user: User){
        itemView.official_member_item_name.text = user.name
        itemView.role.visibility=View.INVISIBLE
    }

}