package edu.rosehulman.attendancecheckoff.util

import android.os.Parcel
import com.google.firebase.Timestamp
import java.util.*

fun Parcel.readStringArrayList(): ArrayList<String> = this.readArrayList(String::class.java.classLoader) as ArrayList<String>

fun Parcel.readTimeStamp() = Timestamp(Date(this.readLong()))