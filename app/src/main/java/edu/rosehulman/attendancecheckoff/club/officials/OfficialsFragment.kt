package edu.rosehulman.attendancecheckoff.club.officials

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

const val ARG_CLUB = "club"

class OfficialsFragment : Fragment() {

    val adapter by lazy { OfficialsAdapter(activity) }

    lateinit var club: Club

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            club = it.getParcelable(ARG_CLUB)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return (inflater.inflate(R.layout.fragment_recycler_view, container, false) as RecyclerView).apply {
            this@OfficialsFragment.adapter.addSnapshotListener(club)
            layoutManager = LinearLayoutManager(activity)
            adapter = this@OfficialsFragment.adapter
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(club: Club) =
            OfficialsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CLUB, club)
                }
            }
    }
}