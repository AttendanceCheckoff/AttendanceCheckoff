package edu.rosehulman.attendancecheckoff.overview.Officials

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.model.User
import kotlinx.android.synthetic.main.officials_member_item.view.*

class OfficialsAdapter(var context: Context?): RecyclerView.Adapter<OfficialsViewHolder>() {
    var officials = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): OfficialsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.officials_member_item,parent, false)
        return OfficialsViewHolder(view)
    }

    override fun getItemCount()=officials.size

    override fun onBindViewHolder(holder: OfficialsViewHolder, position: Int) {
        holder.bind(officials[position])

    }

}