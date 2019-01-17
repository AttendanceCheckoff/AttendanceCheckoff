package edu.rosehulman.attendancecheckoff.util

import com.google.firebase.Timestamp
import edu.rosehulman.attendancecheckoff.model.Club
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.model.User

object Utils {

    val testClub = Club(
        "RHA"
    )

    val testClub2 = Club(
        "Soccer"
    )

    val testEvent = Event(
        "Meeting",
        Timestamp.now(),
        "DEFAULT MEETING",
        arrayListOf(),
        "")

    val testUser = User(
        "harnersa",
        "Sebastian Harner",
        "SE",
        "2019",
        arrayListOf("","")
    )
}