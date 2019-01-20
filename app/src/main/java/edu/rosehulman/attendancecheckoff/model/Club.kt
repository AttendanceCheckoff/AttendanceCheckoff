package edu.rosehulman.attendancecheckoff.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import edu.rosehulman.attendancecheckoff.util.readStringArrayList

data class Club(
    val name: String = "",
    val members: ArrayList<String> = ArrayList(),
    val events: ArrayList<String> = ArrayList()
) : Parcelable {
    @get:Exclude
    var id: String = ""

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readStringArrayList(),
        parcel.readStringArrayList()
    ) {
        id = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeList(members)
        parcel.writeList(events)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Club> {
        override fun createFromParcel(parcel: Parcel): Club {
            return Club(parcel)
        }

        override fun newArray(size: Int): Array<Club?> {
            return arrayOfNulls(size)
        }

        fun fromSnapshot(document: DocumentSnapshot): Club {
            return document.toObject(Club::class.java)!!.apply {
                id = document.id
            }
        }
    }
}