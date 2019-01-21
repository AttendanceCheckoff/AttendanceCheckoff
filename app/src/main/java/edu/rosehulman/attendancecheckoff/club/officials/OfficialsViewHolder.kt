package edu.rosehulman.attendancecheckoff.club.officials

import android.support.v7.widget.RecyclerView
import android.view.View
import edu.rosehulman.attendancecheckoff.model.Official
import kotlinx.android.synthetic.main.officials_member_item.view.*

class OfficialsViewHolder(itemView: View, adapter: OfficialsAdapter) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.email_button.setOnClickListener {
            adapter.sendEmail(adapterPosition)
        }
    }

    fun bind(official: Official) {
        itemView.official_member_item_name.text = official.user.name
        itemView.role.text = official.role
        itemView.member_active.visibility = View.INVISIBLE
    }
}