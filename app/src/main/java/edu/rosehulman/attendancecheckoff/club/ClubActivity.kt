package edu.rosehulman.attendancecheckoff.club

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
import edu.rosehulman.attendancecheckoff.BarCodeActivity
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.club.history.HistoryFragment
import edu.rosehulman.attendancecheckoff.club.members.MembersFragment
import edu.rosehulman.attendancecheckoff.club.officials.OfficialsFragment
import edu.rosehulman.attendancecheckoff.club.personal.PersonalFragment
import edu.rosehulman.attendancecheckoff.model.Club
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.model.User
import edu.rosehulman.attendancecheckoff.util.Constants
import edu.rosehulman.attendancecheckoff.util.Constants.BAR_CODE_REQUEST
import edu.rosehulman.attendancecheckoff.util.FirebaseUtils
import kotlinx.android.synthetic.main.add_event.view.*
import kotlinx.android.synthetic.main.club_activity.*

class ClubActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    val userRef by lazy { FirebaseFirestore.getInstance().collection(User.KEY_COLLECTION) }

    lateinit var club: Club

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
            R.id.action_add_event -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Add new Event")
                    val view = LayoutInflater.from(this@ClubActivity).inflate(R.layout.add_event, null, false)
                    setView(view)
                    setPositiveButton(android.R.string.ok) { dialog, which ->
                        val date = view.Event_Date.minDate
                        val time = view.Event_Time.drawingTime
                        val event = Event(
                            name = view.Event_Name.text.toString(),
                            description = view.Event_Description.text.toString()
                        )
                        Log.d(Constants.TAG, "Event: $event, Date: $date, Time: $time")
                    }
                }.create().show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BAR_CODE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val studentId = data?.getStringExtra(BarCodeActivity.KEY_DETECTED_VALUE)
                studentId?.let { studentID ->
                    FirebaseUtils.addUserToClub(studentID, club)
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
