package edu.rosehulman.attendancecheckoff.event

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.attendancecheckoff.BarCodeActivity
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.model.User
import edu.rosehulman.attendancecheckoff.util.Constants
import edu.rosehulman.attendancecheckoff.util.Constants.BAR_CODE_REQUEST
import kotlinx.android.synthetic.main.event_activity.*

class EventActivity : AppCompatActivity() {

    lateinit var event: Event

    val userRef by lazy { FirebaseFirestore.getInstance().collection(User.KEY_COLLECTION) }

    lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_activity)

        setSupportActionBar(event_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        event = intent.getParcelableExtra(Constants.ARG_INTENT_EVENT)
        supportActionBar?.title = event.name

        event_description.text = event.description

        adapter = EventAdapter(this, event)
        adapter.addSnapshotListener()
        attended_members.layoutManager = LinearLayoutManager(this)
        attended_members.adapter = adapter

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
            else -> super.onOptionsItemSelected(item)
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BAR_CODE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val studentId = data?.getStringExtra(BarCodeActivity.KEY_DETECTED_VALUE)
                studentId?.let { studentID ->
                    userRef.whereEqualTo(User.KEY_STUDENT_ID, studentID).get().addOnSuccessListener {
                        val user = User.fromSnapshot(it.documents.first())
                        user.attendedEvents.add(event.id)
                        userRef.document(user.id).set(user)
                    }
                }
            }
        }
    }
}
