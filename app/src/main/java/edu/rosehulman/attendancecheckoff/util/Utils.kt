package edu.rosehulman.attendancecheckoff.util

import edu.rosehulman.attendancecheckoff.model.Club
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.model.User
import java.util.*

object Utils {

    val testClub = Club(
        "RHA"
    )

    val testClub2 = Club(
        "Soccer"
    )

    val testEvent = Event(
        "Meeting",
        Date(System.nanoTime()),
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