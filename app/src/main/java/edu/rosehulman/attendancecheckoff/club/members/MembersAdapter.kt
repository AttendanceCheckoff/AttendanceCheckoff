package edu.rosehulman.attendancecheckoff.club.members

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.model.Club
import edu.rosehulman.attendancecheckoff.model.User

class MembersAdapter(val context: Context?) : RecyclerView.Adapter<MembersViewHolder>() {

    val membersRef by lazy { FirebaseFirestore.getInstance().collection("users") }
    val members = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.officials_member_item, parent, false)
        return MembersViewHolder(view, this)
    }

    override fun getItemCount() = members.size

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        holder.bind(members[position])
    }

    fun addSnapshotListener(club: Club) {
        membersRef.whereArrayContains("clubs", club.id).addSnapshotListener { snapshot, firesoreException ->
            if (firesoreException != null) {
                return@addSnapshotListener
            }
            populateLocalMembers(snapshot)
        }
    }

    private fun populateLocalMembers(snapshot: QuerySnapshot?) {
        members.clear()
        snapshot?.let {
            members.addAll(it.map { doc -> User.fromSnapshot(doc) })
            notifyDataSetChanged()
        }
    }

    fun sendEmail(position: Int) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(context?.getString(R.string.email_domain, members[position].username)))
        }
        context?.startActivity(intent)
    }
}
