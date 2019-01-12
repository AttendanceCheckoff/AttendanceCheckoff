package edu.rosehulman.attendancecheckoff.overview.clubs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.rosehulman.attendancecheckoff.R
import kotlinx.android.synthetic.main.fragment_recycler_view.view.*

class ClubsFragment : Fragment() {

    val adapter by lazy { ClubsAdapter(activity) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return (inflater.inflate(R.layout.fragment_recycler_view, container, false) as RecyclerView).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@ClubsFragment.adapter
        }
    }
}
