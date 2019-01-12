package edu.rosehulman.attendancecheckoff.util

import edu.rosehulman.attendancecheckoff.model.Club
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.model.Quarter
import edu.rosehulman.attendancecheckoff.model.User
import java.util.*

object Utils {

    val testClub = Club(
        "RHA",
        arrayListOf(),
        arrayListOf(),
        emptyMap()
    )

    val testEvent = Event(
        "Meeting",
        Date(System.nanoTime()),
        arrayListOf(),
        "DEFAULT MEETING",
        Quarter("Winter", Date(System.nanoTime()), Date(System.nanoTime()))
    )

    val testUser = User(
        "harnersa",
        "Sebastian Harner",
        "SE",
        "2019",
        arrayListOf(testClub),
        arrayListOf(testEvent)
    )
}