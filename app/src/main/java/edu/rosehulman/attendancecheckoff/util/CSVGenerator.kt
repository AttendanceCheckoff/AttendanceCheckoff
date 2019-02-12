package edu.rosehulman.attendancecheckoff.util

import android.os.Environment
import com.google.firebase.firestore.FirebaseFirestore
import com.opencsv.CSVWriter
import edu.rosehulman.attendancecheckoff.model.Club
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.model.User
import java.io.FileWriter

class CSVGenerator(val club: Club) {

    val eventRef by lazy { FirebaseFirestore.getInstance().collection(Event.KEY_COLLECTION) }
    val userRef by lazy { FirebaseFirestore.getInstance().collection(User.KEY_COLLECTION) }

    fun generate() {
        val csvFile = "${Environment.getExternalStorageDirectory().absolutePath}/${club.id}${System.nanoTime()}.csv"
        CSVWriter(FileWriter(csvFile)).use { writer ->
            eventRef.whereEqualTo(Event.KEY_CLUB_ID, club.id).get().addOnSuccessListener {
                it.map { doc -> Event.fromSnapshot(doc) }.forEach {
                    
                }
            }
        }
    }
}