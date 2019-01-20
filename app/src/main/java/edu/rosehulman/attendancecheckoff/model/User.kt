package edu.rosehulman.attendancecheckoff.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import edu.rosehulman.attendancecheckoff.util.readStringArrayList

data class User(
    var username: String = "",
    var name: String = "",
    var major: String = "",
    var year: String = "",
    var clubs: ArrayList<String> = ArrayList(),
    var attendedEvents: ArrayList<String> = ArrayList()
) : Parcelable {
    @get: Exclude var id: String = ""

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readStringArrayList(),
        parcel.readStringArrayList()
    ) {
        id = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(name)
        parcel.writeString(major)
        parcel.writeString(year)
        parcel.writeList(clubs)
        parcel.writeList(attendedEvents)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        const val KEY_COLLECTION = "users"

        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }

        fun fromSnapshot(document: DocumentSnapshot): User {
            return document.toObject(User::class.java)!!.apply {
                id = document.id
            }
        }
    }
}
