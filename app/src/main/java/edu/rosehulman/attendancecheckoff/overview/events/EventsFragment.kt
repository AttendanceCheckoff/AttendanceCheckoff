package edu.rosehulman.attendancecheckoff.overview.events

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.rosehulman.attendancecheckoff.R

class EventsFragment : Fragment() {

    val adapter by lazy { EventsAdapter(activity) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return (inflater.inflate(R.layout.fragment_recycler_view, container, false) as RecyclerView).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@EventsFragment.adapter
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        }
    }
}