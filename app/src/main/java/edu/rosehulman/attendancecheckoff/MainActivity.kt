package edu.rosehulman.attendancecheckoff

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.attendancecheckoff.model.Event
import edu.rosehulman.attendancecheckoff.model.User
import edu.rosehulman.attendancecheckoff.overview.clubs.ClubsFragment
import edu.rosehulman.attendancecheckoff.overview.events.EventsFragment
import edu.rosehulman.attendancecheckoff.util.Constants
import edu.rosehulman.attendancecheckoff.util.Constants.RC_ROSEFIRE_LOGIN
import edu.rosehulman.attendancecheckoff.util.FirebaseUtils
import edu.rosehulman.rosefire.Rosefire
import kotlinx.android.synthetic.main.main_activity.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val userRef by lazy { FirebaseFirestore.getInstance().collection("users") }
    val eventsRef = FirebaseFirestore.getInstance().collection("events")

    val auth by lazy { FirebaseAuth.getInstance() }
    lateinit var authListener: FirebaseAuth.AuthStateListener

    // Request code for launching the sign in Intent.
    private val RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(main_toolbar)
        initializeListeners()
        //initializeData()

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
        auth.addAuthStateListener(authListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        auth.removeAuthStateListener(authListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_logout -> {
                auth.signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun initializeListeners() {
        authListener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser
            if (user != null) {
                switchToNavigation(user)
            } else {
                switchToRoseFire()
            }
        }
    }

    private fun switchToRoseFire() {
        val signInIntent = Rosefire.getSignInIntent(this, getString(R.string.rosefire_token))
        startActivityForResult(signInIntent, RC_ROSEFIRE_LOGIN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_ROSEFIRE_LOGIN) {
            val result = Rosefire.getSignInResultFromIntent(data)
            if (!result.isSuccessful) {
                // The user cancelled the login
            }
            FirebaseAuth.getInstance().signInWithCustomToken(result.token)
                .addOnCompleteListener(this) { task ->
                    Log.d(Constants.TAG, "signInWithCustomToken:onComplete:" + task.isSuccessful)

                    if (!task.isSuccessful) {
                        Log.w(Constants.TAG, "signInWithCustomToken", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun switchToNavigation(user: FirebaseUser) {
        userRef.whereEqualTo(User.KEY_USERNAME, user.uid).get().addOnSuccessListener { snapshot ->
            if (snapshot.documents.isEmpty()) {
                FirebaseUtils.showNewUserDialogWithUsername(this, user.uid) { user ->
                    userRef.add(user)
                    CurrentState.user = user
                    supportActionBar?.title = CurrentState.user.name

                    supportFragmentManager.beginTransaction().replace(R.id.content, ClubsFragment()).commit()
                }
            } else {
                CurrentState.user = User.fromSnapshot(snapshot.documents.first())
                supportActionBar?.title = CurrentState.user.name

                supportFragmentManager.beginTransaction().replace(R.id.content, ClubsFragment()).commit()
            }
        }

    }

    private fun initializeData() {
        val date = Date(2019, 2, 11, 17, 0)
        eventsRef.add(Event("Test", Timestamp(date), "Test Notification", arrayListOf(), "PE8m5Y5kYmN6tMKx21mt"))
//        eventsRef.add(Event("Team meeting", Timestamp.now(), "Deciding team roster", arrayListOf(), "XjEQEzGdBNkVgXvI6OKE"))
//        eventsRef.add(Event("General", Timestamp.now(), "General", arrayListOf(), "XjEQEzGdBNkVgXvI6OKE"))
//        eventsRef.add(Event("Trophy ceremony", Timestamp.now(), "Handing trophies to winners", arrayListOf(), "XjEQEzGdBNkVgXvI6OKE"))
    }
}
