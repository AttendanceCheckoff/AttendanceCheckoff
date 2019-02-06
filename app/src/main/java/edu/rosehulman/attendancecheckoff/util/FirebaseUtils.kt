package edu.rosehulman.attendancecheckoff.util

import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.attendancecheckoff.model.Club
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.model.User

object FirebaseUtils {

    val userRef by lazy { FirebaseFirestore.getInstance().collection(User.KEY_COLLECTION) }

    val eventRef by lazy { FirebaseFirestore.getInstance().collection(Event.KEY_COLLECTION) }

    val clubRef by lazy { FirebaseFirestore.getInstance().collection(Club.KEY_COLLECTION) }

    fun addUserToEvent(studentID: String, event: Event) {
        userRef.whereEqualTo(User.KEY_STUDENT_ID, studentID).get().addOnSuccessListener {
            val user = User.fromSnapshot(it.documents.first())
            user.attendedEvents.add(event.id)
            userRef.document(user.id).set(user)

            event.attendedMembers.add(user.id)
            eventRef.document(event.id).set(event)
        }
    }

    fun addUserToClub(studentID: String, club: Club) {
        userRef.whereEqualTo(User.KEY_STUDENT_ID, studentID).get().addOnSuccessListener {
            val user = User.fromSnapshot(it.documents.first())
            user.clubs.add(club.id)
            userRef.document(user.id).set(user)

            club.members.add(user.id)
            clubRef.document(club.id).set(club)
        }
    }
}