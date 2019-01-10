package edu.rosehulman.attendancecheckoff.overview.clubs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.rosehulman.attendancecheckoff.R
import kotlinx.android.synthetic.main.fragment_recycler_view.view.*

class ClubsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false).apply {
            recycler_view
        }
    }
}
