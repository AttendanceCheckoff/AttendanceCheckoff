package edu.rosehulman.attendancecheckoff.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.Exclude

data class Club(
    val name: String = ""
) : Parcelable {
    @get:Exclude var id: String =""

    constructor(parcel: Parcel) : this(parcel.readString()) {
        id = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
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
    }


}