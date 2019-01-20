package edu.rosehulman.attendancecheckoff.club

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.club.history.HistoryFragment
import edu.rosehulman.attendancecheckoff.club.members.MembersFragment
import edu.rosehulman.attendancecheckoff.club.officials.OfficialsFragment
import edu.rosehulman.attendancecheckoff.club.personal.PersonalFragment
import edu.rosehulman.attendancecheckoff.model.Club
import edu.rosehulman.attendancecheckoff.util.Constants
import kotlinx.android.synthetic.main.club_activity.*

class ClubActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
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
