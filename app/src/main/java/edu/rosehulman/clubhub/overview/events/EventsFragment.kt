package edu.rosehulman.clubhub.overview.events

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.rosehulman.clubhub.R
import kotlinx.android.synthetic.main.fragment_recycler_view.view.*

class EventsFragment : Fragment() {

    lateinit var adapter: EventsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_view, container, false) as RecyclerView
        adapter = EventsAdapter(activity, view)
        adapter.addSnapshotListener()
        view.recycler_view.layoutManager = LinearLayoutManager(activity)
        view.recycler_view.adapter = this@EventsFragment.adapter
        view.recycler_view.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
                override fun onMove(
                    p0: RecyclerView,
                    p1: RecyclerView.ViewHolder,
                    p2: RecyclerView.ViewHolder
                ): Boolean {
                    return false                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, p1: Int) {
                    Log.d("message","Removed")
                    adapter!!.remove(viewHolder!!.adapterPosition)                }

            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(view.recycler_view)
        return view

    }
}