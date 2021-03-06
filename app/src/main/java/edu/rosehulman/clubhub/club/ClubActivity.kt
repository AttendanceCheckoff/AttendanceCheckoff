package edu.rosehulman.clubhub.club

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.clubhub.BarCodeActivity
import edu.rosehulman.clubhub.CurrentState
import edu.rosehulman.clubhub.R
import edu.rosehulman.clubhub.club.history.HistoryFragment
import edu.rosehulman.clubhub.club.members.MembersFragment
import edu.rosehulman.clubhub.club.officials.OfficialsFragment
import edu.rosehulman.clubhub.club.personal.PersonalFragment
import edu.rosehulman.clubhub.model.Club
import edu.rosehulman.clubhub.model.Event
import edu.rosehulman.clubhub.model.Official
import edu.rosehulman.clubhub.util.*
import edu.rosehulman.clubhub.util.Constants.BAR_CODE_REQUEST
import kotlinx.android.synthetic.main.add_date.view.*
import kotlinx.android.synthetic.main.add_event.view.*
import kotlinx.android.synthetic.main.add_time.view.*
import kotlinx.android.synthetic.main.club_activity.*
import java.util.*

class ClubActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    lateinit var club: Club

    val officialsRef by lazy { FirebaseFirestore.getInstance().collection(Official.KEY_COLLECTION) }

    lateinit var activityMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.club_activity)

        setSupportActionBar(club_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        club = intent.getParcelableExtra(Constants.ARG_INTENT_CLUB)
        supportActionBar?.title = club.name

        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_club, menu)
        activityMenu = menu
        checkOfficial()
        return true
    }

    private fun checkOfficial() {
        officialsRef.whereEqualTo(Official.KEY_USER_ID, CurrentState.user.id).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                return@addSnapshotListener
            }
            snapshot?.let {
                if (snapshot.map { doc -> Official.fromSnapshot(doc) }.any { it.clubId == club.id}) {
                    activityMenu.findItem(R.id.action_qrcode).isVisible = true
                    activityMenu.findItem(R.id.action_export).isVisible = true
                    activityMenu.findItem(R.id.action_add_event).isVisible = true
                } else {
                    activityMenu.findItem(R.id.action_qrcode).isVisible = false
                    activityMenu.findItem(R.id.action_export).isVisible = false
                    activityMenu.findItem(R.id.action_add_event).isVisible = false
                }
            }
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
            R.id.action_export -> {
                CSVGenerator(club, this).exportData()
                true
            }
            R.id.action_add_event -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Add new Event")
                    val view = LayoutInflater.from(this@ClubActivity).inflate(R.layout.add_event, null, false)
                    setView(view)
                    setPositiveButton(android.R.string.ok) { _, _ ->
                        showDateDialog(
                            name = view.Event_Name.text.toString(),
                            description = view.Event_Description.text.toString()
                        )
                    }
                    setNegativeButton(android.R.string.cancel) { _, _ -> }
                }.create().show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun showDateDialog(name: String, description: String) {
        AlertDialog.Builder(this).apply {
            val view = LayoutInflater.from(this@ClubActivity).inflate(R.layout.add_date, null, false)
            setView(view)
            setPositiveButton(android.R.string.ok) { _, _ ->
                val date = view.Event_Date.getDate()
                Log.d(Constants.TAG, "Name: $name, Description: $description, Date: $date")
                showTimeDialog(name, description, date)
            }
            setNegativeButton(android.R.string.cancel) { _, _ -> }
        }.create().show()
    }

    private fun showTimeDialog(name: String, description: String, date: Date) {
        AlertDialog.Builder(this).apply {
            val view = LayoutInflater.from(this@ClubActivity).inflate(R.layout.add_time, null, false)
            setView(view)
            setPositiveButton(android.R.string.ok) { _, _ ->
                val timestamp = view.Event_Time.getTimestamp(date)
                Log.d(Constants.TAG, "Name: $name, Description: $description, Timestamp: ${timestamp.toReadableString()}")
                val event = Event(name = name, description = description, clubId = club.id, dateTime = timestamp)
                FirebaseUtils.addNewEventToClub(club, event)
            }
            setNegativeButton(android.R.string.cancel) { _, _ -> }
        }.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BAR_CODE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val studentId = data?.getStringExtra(BarCodeActivity.KEY_DETECTED_VALUE)
                studentId?.let { studentID ->
                    FirebaseUtils.addUserToClub(this, studentID, club)
                }
            }
        }
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> OfficialsFragment.newInstance(club)
            1 -> MembersFragment.newInstance(club)
            2 -> PersonalFragment.newInstance(club)
            3 -> HistoryFragment.newInstance(club)
            else -> OfficialsFragment.newInstance(club)
        }

        override fun getCount() = 4

    }
}
