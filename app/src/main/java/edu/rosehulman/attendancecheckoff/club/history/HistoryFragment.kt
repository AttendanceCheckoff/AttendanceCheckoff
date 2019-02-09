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
import edu.rosehulman.attendancecheckoff.club.personal.PersonalAdapter
import edu.rosehulman.attendancecheckoff.model.Club
import kotlinx.android.synthetic.main.fragment_recycler_view.view.*

const val ARG_CLUB = "ARG_CLUB"

class HistoryFragment : Fragment() {

    lateinit var adapter: HistoryAdapter

    lateinit var club:Club

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            club = it.getParcelable(ARG_CLUB)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        adapter = HistoryAdapter(context, club)
        adapter.addSnapshotListener()
        view.recycler_view.layoutManager = LinearLayoutManager(context)
        view.recycler_view.adapter = adapter
        view.recycler_view.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        return view

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