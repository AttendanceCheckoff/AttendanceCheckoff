package edu.rosehulman.attendancecheckoff.model

import android.os.Parcel
import android.os.Parcelable

data class User(
    val username: String,
    val name: String,
    val major: String,
    val year: String,
    val clubs: ArrayList<String>
) : Parcelable {
    var id: String = ""

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readArrayList(String::class.java.classLoader) as ArrayList<String>
    ) {
        id = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(name)
        parcel.writeString(major)
        parcel.writeString(year)
        parcel.writeList(clubs)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}
