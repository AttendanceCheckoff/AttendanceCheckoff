package edu.rosehulman.attendancecheckoff.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import java.util.*
import kotlin.collections.ArrayList

data class Event(
    val name: String = "",
    val dateTime: Timestamp = Timestamp.now(),
    val description: String = "",
    val attendedMembers: ArrayList<String> = ArrayList(),
    val clubId: String = ""
    ) : Parcelable {

    @get:Exclude var id = ""

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        Timestamp(Date(parcel.readLong())),
        parcel.readString(),
        parcel.readArrayList(String::class.java.classLoader) as ArrayList<String>,
        parcel.readString()
    ) {
        this.id = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeLong(dateTime.seconds)
        parcel.writeString(description)
        parcel.writeList(attendedMembers)
        parcel.writeString(clubId)
        parcel.writeString(id)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel) = Event(parcel)

        override fun newArray(size: Int): Array<Event?> = arrayOfNulls(size)
    }

}