package edu.rosehulman.attendancecheckoff

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import edu.rosehulman.attendancecheckoff.overview.clubs.ClubsFragment
import edu.rosehulman.attendancecheckoff.overview.events.EventsFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        supportFragmentManager.beginTransaction().replace(R.id.content, ClubsFragment()).commit()

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.navigation_home -> {
                    ClubsFragment()
                }
                R.id.navigation_dashboard -> {
                    EventsFragment()
                }
                R.id.navigation_notifications -> {
                    IDFragment()
                }
                else -> {
                    ClubsFragment()
                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
            true
        }
    }
}
