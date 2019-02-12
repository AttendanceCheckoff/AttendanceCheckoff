package edu.rosehulman.attendancecheckoff.util

import android.os.Parcel
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun Parcel.readStringArrayList(): ArrayList<String> = this.readArrayList(String::class.java.classLoader) as ArrayList<String>

fun Parcel.readTimeStamp() = Timestamp(Date(this.readLong()))

fun Timestamp.toReadableString(): String {
    val sdf = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.US)
    return sdf.format(this.seconds*1000)
}

fun DatePicker.getDate(): Date {
    Calendar.getInstance().apply {
        set(year, month, dayOfMonth)
        return time
    }
}

fun TimePicker.getTimestamp(date: Date): Timestamp {
    Calendar.getInstance().apply {
        set(date.year, date.month, date.day, hour, minute)
        Log.d(Constants.TAG, "Date: $time")
        return Timestamp(time)
    }
}