package edu.rosehulman.clubhub.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude

data class Official(
    val clubId: String = "",
    val userId: String = "",
    val role: String = "",
    val rank: Int = -1
) :
    Parcelable {

    @get:Exclude
    var id = ""

    @get:Exclude
    var user = User()

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    ) {
        id = parcel.readString()
        user = parcel.readParcelable(User::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(clubId)
        parcel.writeString(userId)
        parcel.writeString(role)
        parcel.writeInt(rank)
        parcel.writeString(id)
        parcel.writeParcelable(user, 0)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Official> {
        const val KEY_COLLECTION = "officials"
        const val KEY_CLUB_ID = "clubId"
        const val KEY_USER_ID = "userId"

        override fun createFromParcel(parcel: Parcel) = Official(parcel)

        override fun newArray(size: Int): Array<Official?> = arrayOfNulls(size)

        fun fromSnapshot(document: DocumentSnapshot): Official {
            return document.toObject(Official::class.java)!!.apply {
                id = document.id
            }
        }
    }
}