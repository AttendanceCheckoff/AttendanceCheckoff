package edu.rosehulman.attendancecheckoff.club.history

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.model.Club

const val ARG_CLUB = "ARG_CLUB"

class HistoryFragment : Fragment() {

    val adapter by lazy { HistoryAdapter(activity) }

    lateinit var club:Club

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            club = it.getParcelable(ARG_CLUB)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return (inflater.inflate(R.layout.fragment_recycler_view, container, false) as RecyclerView).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@HistoryFragment.adapter
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(club: Club) =
                HistoryFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_CLUB, club)
                    }
                }
    }
}