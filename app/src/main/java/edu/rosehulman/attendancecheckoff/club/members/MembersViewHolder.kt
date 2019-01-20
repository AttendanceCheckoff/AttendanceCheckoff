package edu.rosehulman.attendancecheckoff.club.members

import android.support.v7.widget.RecyclerView
import android.view.View
import edu.rosehulman.attendancecheckoff.model.User
import kotlinx.android.synthetic.main.officials_member_item.view.*

class MembersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(member: User) {
        itemView.official_member_item_name.text = member.name
        itemView.profile_image.visibility = View.INVISIBLE
        itemView.role.visibility= View.INVISIBLE
    }
}
