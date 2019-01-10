package edu.rosehulman.attendancecheckoff

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import edu.rosehulman.attendancecheckoff.overview.clubs.ClubsFragment
import edu.rosehulman.attendancecheckoff.overview.events.EventsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            var fragment: Fragment = ClubsFragment()
            when (item.itemId) {
                R.id.navigation_home -> {
                    fragment = ClubsFragment()
                }
                R.id.navigation_dashboard -> {
                    fragment = EventsFragment()
                }
                R.id.navigation_notifications -> {
                }
                else -> {
                    fragment = ClubsFragment()
                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
            true
        }
    }
}
