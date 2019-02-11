package edu.rosehulman.attendancecheckoff.event


import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.IBinder
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.model.Event

class MyNotification(val event: Event): Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val nSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val myNM = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        var intent = Intent(this, EventActivity::class.java)
//        var pendingevent = PendingIntent.getActivity(this, 0, intent, 0)

        val mNotify = Notification.Builder(this)
            .setContentTitle(event.name)
            .setContentText("The event is in 15 minutes")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setSound(nSound)
            .build()

        myNM.notify(0, mNotify)
    }


}
