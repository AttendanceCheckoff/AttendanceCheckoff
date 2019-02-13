package edu.rosehulman.attendancecheckoff.util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.model.Club
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.model.User
import kotlinx.android.synthetic.main.add_new_user.view.*

object FirebaseUtils {

    val userRef by lazy { FirebaseFirestore.getInstance().collection(User.KEY_COLLECTION) }
    val eventRef by lazy { FirebaseFirestore.getInstance().collection(Event.KEY_COLLECTION) }
    val clubRef by lazy { FirebaseFirestore.getInstance().collection(Club.KEY_COLLECTION) }

    fun addUserToEvent(context: Context, studentID: String, event: Event) {
        userRef.whereEqualTo(User.KEY_STUDENT_ID, studentID).get().addOnSuccessListener {
            if (it.documents.isEmpty()) {
                showNewUserDialog(context, studentID) { addNewUserToEvent(it, event) }
            } else {
                val user = User.fromSnapshot(it.documents.first())
                user.attendedEvents.add(event.id)
                userRef.document(user.id).set(user)

                event.attendedMembers.add(user.id)
                eventRef.document(event.id).set(event)
            }
        }
    }

    fun showNewUserDialog(context: Context, studentID: String, addUserAction: (User) -> Unit) {
        AlertDialog.Builder(context).apply {
            setTitle("We don't know you! Please complete this form")
            val view = LayoutInflater.from(context).inflate(R.layout.add_new_user, null, false)
            setView(view)
            view.new_user_studentID.setText(studentID)
            setPositiveButton(android.R.string.ok) { _, _ ->
                val user = User(
                    username = view.new_user_username.text.toString(),
                    name = view.new_user_name.text.toString(),
                    studentID = view.new_user_studentID.text.toString(),
                    major = view.new_user_major.text.toString(),
                    year = view.new_user_year.text.toString()
                )
                addUserAction(user)
            }
            setNegativeButton(android.R.string.cancel) { _, _ -> }
        }.show()
    }

    private fun addNewUserToEvent(user: User, event: Event) {
        user.clubs.add(event.clubId)
        user.attendedEvents.add(event.id)
        userRef.add(user).addOnSuccessListener { ref ->
            event.attendedMembers.add(ref.id)
            eventRef.document(event.id).set(event)
        }
    }

    fun addUserToClub(context: Context, studentID: String, club: Club) {
        userRef.whereEqualTo(User.KEY_STUDENT_ID, studentID).get().addOnSuccessListener {
            if (it.documents.isEmpty()) {
                showNewUserDialog(context, studentID) { addNewUserToClub(it, club) }
            } else {
                val user = User.fromSnapshot(it.documents.first())
                user.clubs.add(club.id)
                userRef.document(user.id).set(user)

                club.members.add(user.id)
                clubRef.document(club.id).set(club)
            }
        }
    }

    private fun addNewUserToClub(user: User, club: Club) {
        user.clubs.add(club.id)
        userRef.add(user).addOnSuccessListener { ref ->
            club.members.add(ref.id)
            clubRef.document(club.id).set(club)
        }
    }

    fun addNewEventToClub(club: Club, event: Event) {
        eventRef.add(event).addOnSuccessListener { doc ->
            club.events.add(doc.id)
            clubRef.document(club.id).set(club)
        }
    }

    fun showNewUserDialogWithUsername(context: Context, username: String, addUserAction: (User) -> Unit) {
        AlertDialog.Builder(context).apply {
            setTitle("We don't know you! Please complete this form")
            val view = LayoutInflater.from(context).inflate(R.layout.add_new_user, null, false)
            setView(view)
            view.new_user_username.setText(username)
            setPositiveButton(android.R.string.ok) { _, _ ->
                val user = User(
                    username = view.new_user_username.text.toString(),
                    name = view.new_user_name.text.toString(),
                    studentID = view.new_user_studentID.text.toString(),
                    major = view.new_user_major.text.toString(),
                    year = view.new_user_year.text.toString()
                )
                addUserAction(user)
            }
            setNegativeButton(android.R.string.cancel) { _, _ -> }
        }.show()
    }
}