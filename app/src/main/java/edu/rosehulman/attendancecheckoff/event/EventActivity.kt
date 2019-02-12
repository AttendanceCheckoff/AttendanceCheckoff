package edu.rosehulman.attendancecheckoff.event

import android.app.Activity
import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import edu.rosehulman.attendancecheckoff.BarCodeActivity
import edu.rosehulman.attendancecheckoff.CurrentState
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.util.Constants
import edu.rosehulman.attendancecheckoff.util.Constants.BAR_CODE_REQUEST
import edu.rosehulman.attendancecheckoff.util.FirebaseUtils
import kotlinx.android.synthetic.main.event_activity.*
import java.util.*

class EventActivity : AppCompatActivity() {

    lateinit var event: Event
//    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//    val myIntent = Intent(this, MyNotification(event)::class.java)
//
//    val pendingIntent = PendingIntent.getService(this, 0, myIntent, 0)

    lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_activity)

        reminder.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d(Constants.TAG, isChecked.toString())
            if (isChecked){
                setNotification()
            }
        }

        setSupportActionBar(event_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        event = intent.getParcelableExtra(Constants.ARG_INTENT_EVENT)
        supportActionBar?.title = event.name

        event_description.text = event.description

        adapter = EventAdapter(this, event)
        adapter.addSnapshotListener()
        attended_members.layoutManager = LinearLayoutManager(this)
        attended_members.adapter = adapter
        attended_members.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_club, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_qrcode -> {
                Intent(this, BarCodeActivity::class.java).also { barCodeIntent ->
                    barCodeIntent.type = Intent.ACTION_VIEW
                    startActivityForResult(barCodeIntent, BAR_CODE_REQUEST)
                }
                true
            }
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BAR_CODE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val studentId = data?.getStringExtra(BarCodeActivity.KEY_DETECTED_VALUE)
                studentId?.let { studentID ->
                    FirebaseUtils.addUserToEvent(studentID, event)
                }
            }
        }
    }

    private fun setNotification(){
        val startMillis = event.dateTime.toDate().time
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            .putExtra(CalendarContract.Events.TITLE, event.name)
            .putExtra(CalendarContract.Events.DESCRIPTION, event.description)
            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
            .putExtra(CalendarContract.Reminders.MINUTES, 15)
            .putExtra(Intent.EXTRA_EMAIL, CurrentState.user.username+"@rose-hulman.edu")
        startActivity(intent)


//        val calendar = Calendar.getInstance()
//        calendar.set(Calendar.SECOND, event.dateTime.toDate().seconds)
//        calendar.set(Calendar.MINUTE, event.dateTime.toDate().minutes - 5)
//        calendar.set(Calendar.HOUR, event.dateTime.toDate().hours)
//        calendar.set(Calendar.DATE, event.dateTime.toDate().date)
//
//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun cancelNotification(){
//        alarmManager.cancel(pendingIntent)
    }
}
