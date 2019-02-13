package edu.rosehulman.clubhub.event

import android.support.v7.widget.RecyclerView
import android.view.View
import edu.rosehulman.clubhub.model.User
import kotlinx.android.synthetic.main.officials_member_item.view.*

class EventHolder(itemView: View, adapter: EventAdapter) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.email_button.setOnClickListener {
            adapter.sendEmail(adapterPosition)
        }
    }

    fun bind(user: User) {
        itemView.official_member_item_name.text = user.name
        itemView.role.visibility = View.INVISIBLE
        itemView.member_active.visibility = View.INVISIBLE
    }

}