package edu.rosehulman.attendancecheckoff.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Event(
    val name: String,
    val dateTime: Date,
    val attendedMembers: ArrayList<User>,
    val description: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        Date(parcel.readLong()),
        parcel.readArrayList(User::class.java.classLoader) as ArrayList<User>,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeLong(dateTime.time)
        parcel.writeList(attendedMembers)
        parcel.writeString(description)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel) = Event(parcel)

        override fun newArray(size: Int): Array<Event?> = arrayOfNulls(size)
    }
}