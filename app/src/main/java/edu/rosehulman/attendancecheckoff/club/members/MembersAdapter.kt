package edu.rosehulman.attendancecheckoff.club.members

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.model.User

class MembersAdapter(val context: Context?) : RecyclerView.Adapter<MembersViewHolder>() {

    val members = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.officials_member_item, parent, false)
        return MembersViewHolder(view)
    }

    override fun getItemCount() = members.size

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        holder.bind(members[position])
    }

}
