package edu.rosehulman.clubhub.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.opencsv.CSVWriter
import edu.rosehulman.clubhub.CurrentState
import edu.rosehulman.clubhub.R
import edu.rosehulman.clubhub.model.Club
import edu.rosehulman.clubhub.model.Event
import edu.rosehulman.clubhub.model.User
import java.io.File
import java.io.FileWriter

class CSVGenerator(val club: Club, val context: Activity) {

    val eventRef by lazy { FirebaseFirestore.getInstance().collection(Event.KEY_COLLECTION) }
    val userRef by lazy { FirebaseFirestore.getInstance().collection(User.KEY_COLLECTION) }

    fun exportData() {
        val csvFile = "${Environment.getExternalStorageDirectory().absolutePath}/${club.id}${System.nanoTime()}.csv"
        generate(csvFile)
    }

    private fun sendTo(csvFile: String) {
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.email_domain, CurrentState.user.username)))
            putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject))
            putExtra(Intent.EXTRA_TEXT, "body")

            putExtra(Intent.EXTRA_STREAM, Uri.fromFile(File(csvFile)))
            context.startActivity(Intent.createChooser(this, "Choose your Email provider"))
        }
    }

    private fun generate(csvFile: String) {
        eventRef.orderBy(Event.KEY_DATE_TIME, Query.Direction.ASCENDING).whereEqualTo(Event.KEY_CLUB_ID, club.id)
            .get()
            .addOnSuccessListener { eventSnapshot ->
                val eventList = eventSnapshot.map { doc -> Event.fromSnapshot(doc) }
                userRef.whereArrayContains(User.KEY_CLUBS, club.id).get().addOnSuccessListener { userSnapshot ->
                    val userList = userSnapshot.map { doc -> User.fromSnapshot(doc) }
                    val cols = arrayListOf("")
                    val userMap = userList.map { user -> user.id to arrayListOf<String>() }.toMap()
                    val userIDMap = userList.map { user -> user.id to user }.toMap()

                    eventList.forEach { event ->
                        cols.add(event.dateTime.toReadableString())
                        userList.forEach { user ->
                            if (event.attendedMembers.contains(user.id)) {
                                userMap.getValue(user.id).add("X")
                            } else {
                                userMap.getValue(user.id).add("")
                            }
                        }
                    }

                    val data = ArrayList<Array<String>>()
                    data.add(cols.toTypedArray())
                    userMap.forEach { entry ->
                        val (key, value) = entry
                        value.add(0, userIDMap.getValue(key).name)
                        data.add(value.toTypedArray())
                    }

                    CSVWriter(FileWriter(csvFile)).use { writer ->
                        writer.writeAll(data)
                    }

                    sendTo(csvFile)
                }
            }
    }


}