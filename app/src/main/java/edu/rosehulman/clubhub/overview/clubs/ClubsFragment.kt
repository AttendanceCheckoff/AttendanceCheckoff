package edu.rosehulman.clubhub.overview.clubs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.rosehulman.clubhub.R

class ClubsFragment : Fragment() {

    val adapter by lazy { ClubsAdapter(activity) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return (inflater.inflate(R.layout.fragment_recycler_view, container, false) as RecyclerView).apply {
            this@ClubsFragment.adapter.addSnapshotListener()
            layoutManager = LinearLayoutManager(activity)
            adapter = this@ClubsFragment.adapter
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        }
    }
}
