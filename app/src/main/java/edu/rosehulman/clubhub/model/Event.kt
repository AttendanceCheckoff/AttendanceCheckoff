package edu.rosehulman.clubhub.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import edu.rosehulman.clubhub.util.readStringArrayList
import edu.rosehulman.clubhub.util.readTimeStamp

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
        parcel.readTimeStamp(),
        parcel.readString(),
        parcel.readStringArrayList(),
        parcel.readString()
    ) {
        this.id = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeLong(dateTime.seconds * 1000)
        parcel.writeString(description)
        parcel.writeList(attendedMembers)
        parcel.writeString(clubId)
        parcel.writeString(id)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Event> {
        const val KEY_ATTENDED_MEMBERS = "attendedMembers"
        const val KEY_CLUB_ID = "clubId"
        const val KEY_COLLECTION = "events"
        const val KEY_DATE_TIME = "dateTime"
        const val KEY_DESCRIPTION = "description"
        const val KEY_NAME = "name"

        override fun createFromParcel(parcel: Parcel) = Event(parcel)

        override fun newArray(size: Int): Array<Event?> = arrayOfNulls(size)

        fun fromSnapshot(document: DocumentSnapshot): Event {
            return document.toObject(Event::class.java)!!.apply {
                id = document.id
            }
        }
    }

}