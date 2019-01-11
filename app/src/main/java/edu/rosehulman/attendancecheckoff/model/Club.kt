package edu.rosehulman.attendancecheckoff.model

import android.os.Parcel
import android.os.Parcelable

data class Club(val name: String, val members: ArrayList<User>, val events: ArrayList<Event>, val officials: Map<User, String>) :
    Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        TODO("members"),
        TODO("events"),
        TODO("officials")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeParcelable(members)
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