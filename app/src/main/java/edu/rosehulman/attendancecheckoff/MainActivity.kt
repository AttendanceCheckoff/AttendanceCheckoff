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

        userRef.whereEqualTo(User.KEY_USERNAME, "harnersa").get().addOnSuccessListener { snapshot ->
            snapshot.documents.forEach {
                CurrentState.user = User.fromSnapshot(it)
            }
            supportFragmentManager.beginTransaction().replace(R.id.content, ClubsFragment()).commit()
        }

        supportActionBar?.title = CurrentState.user.name

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.navigation_clubs -> {
                    ClubsFragment()
                }
                R.id.navigation_events -> {
                    EventsFragment()
                }
                R.id.navigation_id -> {
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
