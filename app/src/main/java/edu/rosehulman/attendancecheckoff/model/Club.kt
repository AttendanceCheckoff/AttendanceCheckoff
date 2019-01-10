package edu.rosehulman.attendancecheckoff.model

data class Club(val name: String, val members: ArrayList<User>, val events: ArrayList<Event>, val officials: Map<User, String>) {
}