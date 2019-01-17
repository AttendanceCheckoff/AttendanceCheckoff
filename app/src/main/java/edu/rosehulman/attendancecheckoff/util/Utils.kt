package edu.rosehulman.attendancecheckoff.util

import edu.rosehulman.attendancecheckoff.model.Club
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.model.User
import java.util.*

object Utils {

    val testClub = Club(
        "RHA",
        arrayListOf(),
        arrayListOf(),
        emptyMap()
    )

    val testClub2 = Club(
        "Soccer",
        arrayListOf(),
        arrayListOf(),
        emptyMap()
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
        arrayListOf(testClub, testClub2),
        arrayListOf(testEvent)
    )
}