package edu.rosehulman.clubhub.event

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.clubhub.BarCodeActivity
import edu.rosehulman.clubhub.CurrentState
import edu.rosehulman.clubhub.R
import edu.rosehulman.clubhub.model.Event
import edu.rosehulman.clubhub.model.Official
import edu.rosehulman.clubhub.util.Constants
import edu.rosehulman.clubhub.util.Constants.BAR_CODE_REQUEST
import edu.rosehulman.clubhub.util.FirebaseUtils
import edu.rosehulman.clubhub.util.toReadableString
import kotlinx.android.synthetic.main.event_activity.*

class EventActivity : AppCompatActivity() {

    lateinit var event: Event
    lateinit var adapter: EventAdapter
    lateinit var activityMenu: Menu

    val officialsRef by lazy { FirebaseFirestore.getInstance().collection(Official.KEY_COLLECTION) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_activity)

        setSupportActionBar(event_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        event = intent.getParcelableExtra(Constants.ARG_INTENT_EVENT)
        supportActionBar?.title = event.name

        event_description.text = event.description
        event_time_text.text = event.dateTime.toReadableString()

        adapter = EventAdapter(this, event)
        adapter.addSnapshotListener()
        attended_members.layoutManager = LinearLayoutManager(this)
        attended_members.adapter = adapter
        attended_members.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_event, menu)
        activityMenu = menu
        checkOfficial()
        return true
    }

    private fun checkOfficial() {
        officialsRef.whereEqualTo(Official.KEY_USER_ID, CurrentState.user.id)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    return@addSnapshotListener
                }
                snapshot?.let {
                    activityMenu.findItem(R.id.action_qrcode).isVisible =
                        snapshot.map { doc -> Official.fromSnapshot(doc) }.any { it.clubId == event.clubId }
                }
            }
        if (event.dateTime < Timestamp.now()) {
            activityMenu.findItem(R.id.action_reminder).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_qrcode -> {
                Intent(this, BarCodeActivity::class.java).also { barCodeIntent ->
                    barCodeIntent.type = Intent.ACTION_VIEW
                    startActivityForResult(barCodeIntent, BAR_CODE_REQUEST)
                }
                true
            }
            R.id.action_reminder -> {
                setNotification()
                true
            }
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BAR_CODE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val studentId = data?.getStringExtra(BarCodeActivity.KEY_DETECTED_VALUE)
                studentId?.let { studentID ->
                    FirebaseUtils.addUserToEvent(this, studentID, event)
                }
            }
        }
    }

    private fun setNotification() {
        val startMillis = event.dateTime.seconds * 1000
        val intent = Intent(Intent.ACTION_INSERT)
            .setData(CalendarContract.Events.CONTENT_URI)
            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            .putExtra(CalendarContract.Events.TITLE, event.name)
            .putExtra(CalendarContract.Events.DESCRIPTION, event.description)
            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
            .putExtra(CalendarContract.Reminders.MINUTES, 15)
            .putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_domain, CurrentState.user.username)))
        startActivity(intent)
    }

    private fun cancelNotification() {
    }
}
