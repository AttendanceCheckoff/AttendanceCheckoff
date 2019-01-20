package edu.rosehulman.attendancecheckoff.club.personal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.rosehulman.attendancecheckoff.R
import edu.rosehulman.attendancecheckoff.model.Club

const val ARG_CLUB = "ARG_CLUB"

class PersonalFragment : Fragment() {

    lateinit var club: Club

    override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        arguments?.let {
            club = it.getParcelable(ARG_CLUB)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.personal_fragment, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(club: Club) =
                PersonalFragment().apply {
                    arguments= Bundle().apply {
                        putParcelable(ARG_CLUB, club)
                    }
                }
    }
}