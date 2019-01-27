package edu.rosehulman.attendancecheckoff.event

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.club.ClubActivity
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.util.Constants
import kotlinx.android.synthetic.main.event_activity.*

class EventActivity : AppCompatActivity() {

    lateinit var event: Event


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_activity)

        setSupportActionBar(event_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        event = intent.getParcelableExtra(Constants.ARG_INTENT_EVENT)
        supportActionBar?.title = event.name
        event_description.text = event.description
        attended_members.adapter = EventAdapter(this, event)
    }
}
