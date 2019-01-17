package edu.rosehulman.attendancecheckoff

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.attendancecheckoff.model.User
import edu.rosehulman.attendancecheckoff.overview.clubs.ClubsFragment
import edu.rosehulman.attendancecheckoff.overview.events.EventsFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    val userRef by lazy { FirebaseFirestore.getInstance().collection("users") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(main_toolbar)

        userRef.whereEqualTo("username", "harnersa").get().addOnSuccessListener { snapshot ->
            snapshot?.toObjects(User::class.java)?.let {
                CurrentState.user = it.first()
            }
        }

        supportActionBar?.title = CurrentState.user.name

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
