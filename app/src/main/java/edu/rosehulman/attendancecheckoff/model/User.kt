package edu.rosehulman.attendancecheckoff.model

data class User(
    val username: String,
    val name: String,
    val major: String,
    val year: String,
    val clubs: ArrayList<Club>,
    val events: ArrayList<Event>
) {

}
