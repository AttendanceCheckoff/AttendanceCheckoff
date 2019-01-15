package edu.rosehulman.attendancecheckoff.model

import android.os.Parcel
import android.os.Parcelable

data class Club(
    val name: String,
    val members: ArrayList<User>,
    val events: ArrayList<Event>,
    val officials: Map<User, String> = HashMap()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readArrayList(User::class.java.classLoader) as ArrayList<User>,
        parcel.readArrayList(Event::class.java.classLoader) as ArrayList<Event>
    ) {
        parcel.readMap(officials, String::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeList(members)
        parcel.writeList(events)
        parcel.writeMap(officials)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Club> {
        override fun createFromParcel(parcel: Parcel): Club {
            return Club(parcel)
        }

        override fun newArray(size: Int): Array<Club?> {
            return arrayOfNulls(size)
        }
    }
}