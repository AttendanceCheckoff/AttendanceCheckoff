package edu.rosehulman.attendancecheckoff.model

import android.os.Parcel
import android.os.Parcelable

data class User(
    val username: String,
    val name: String,
    val major: String,
    val year: String,
    val clubs: ArrayList<Club>,
    val events: ArrayList<Event>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readArrayList(Club::class.java.classLoader) as ArrayList<Club>,
        parcel.readArrayList(Event::class.java.classLoader) as ArrayList<Event>
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(name)
        parcel.writeString(major)
        parcel.writeString(year)
        parcel.writeList(clubs)
        parcel.writeList(events)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel) = User(parcel)

        override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
    }

}
