package edu.rosehulman.attendancecheckoff.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.Exclude

data class Official(val clubId: String = "", val userId: String = "", val role: String = "", val rank: Int = -1) : Parcelable {

    @get:Exclude
    var id = ""

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
        id = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(clubId)
        parcel.writeString(userId)
        parcel.writeString(role)
        parcel.writeInt(rank)
        parcel.writeString(id)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Official> {
        override fun createFromParcel(parcel: Parcel) = Official(parcel)

        override fun newArray(size: Int): Array<Official?> = arrayOfNulls(size)
    }


}