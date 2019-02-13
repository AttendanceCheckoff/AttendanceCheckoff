package edu.rosehulman.clubhub.club.officials

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import edu.rosehulman.clubhub.R
import edu.rosehulman.clubhub.model.Club
import edu.rosehulman.clubhub.model.Official
import edu.rosehulman.clubhub.model.User
import edu.rosehulman.clubhub.util.Constants

class OfficialsAdapter(var context: Context?) : RecyclerView.Adapter<OfficialsViewHolder>() {

    val officialsRef = FirebaseFirestore.getInstance().collection(Official.KEY_COLLECTION)
    val usersRef = FirebaseFirestore.getInstance().collection(User.KEY_COLLECTION)

    var officials = ArrayList<Official>()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): OfficialsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.officials_member_item, parent, false)
        return OfficialsViewHolder(view, this)
    }

    override fun getItemCount() = officials.size

    override fun onBindViewHolder(holder: OfficialsViewHolder, position: Int) {
        holder.bind(officials[position])
    }

    fun addSnapshotListener(club: Club) {
        officialsRef.whereEqualTo(Official.KEY_CLUB_ID, club.id).addSnapshotListener { snapshot, firestoreException ->
            if (firestoreException != null) {
                Log.d(Constants.TAG, "Error: $firestoreException")
                return@addSnapshotListener
            }
            populateLocalEvents(snapshot)
        }
    }

    private fun populateLocalEvents(snapshot: QuerySnapshot?) {
        Log.d(Constants.TAG, "Populating Clubs")
        officials.clear()
        snapshot?.let {
            officials.addAll(it.map { doc -> Official.fromSnapshot(doc) })
            officials.forEachIndexed { index, official ->
                usersRef.document(official.userId).get().addOnSuccessListener { document ->
                    official.user = User.fromSnapshot(document)
                    notifyItemChanged(index)
                }
            }
            notifyDataSetChanged()
        }
    }

    fun sendEmail(position: Int) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf(context?.getString(R.string.email_domain, officials[position].user.username))
            )
        }
        context?.startActivity(intent)
    }

}