package edu.rosehulman.attendancecheckoff.model

import java.util.*
import kotlin.collections.ArrayList

data class Event(val name: String, val dateTime: Date, val attendedMembers: ArrayList<User>, val description: String, val quarter: Quarter) {
}