package edu.rosehulman.attendancecheckoff.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Quarter(val season: String, val startDate: Date, val endDate: Date) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        Date(parcel.readLong()),
        Date(parcel.readLong())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(season)
        parcel.writeLong(startDate.time)
        parcel.writeLong(endDate.time)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Quarter> {
        override fun createFromParcel(parcel: Parcel) = Quarter(parcel)

        override fun newArray(size: Int): Array<Quarter?> = arrayOfNulls(size)
    }
}
