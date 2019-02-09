package edu.rosehulman.attendancecheckoff.event

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import edu.rosehulman.attendancecheckoff.BarCodeActivity
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.util.Constants
import edu.rosehulman.attendancecheckoff.util.Constants.BAR_CODE_REQUEST
import edu.rosehulman.attendancecheckoff.util.FirebaseUtils
import kotlinx.android.synthetic.main.event_activity.*

class EventActivity : AppCompatActivity() {

    lateinit var event: Event

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
}
