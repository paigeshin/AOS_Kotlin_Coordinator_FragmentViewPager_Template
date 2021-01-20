package com.paigesoftware.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//RecyclerView Setting
class DummyFragment(private val color: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_dummy, container, false)

        val frameLayout = view.findViewById<View>(R.id.dummyfrag_bg) as FrameLayout
        frameLayout.setBackgroundColor(color)

        val recyclerView = view.findViewById<View>(R.id.dummyfrag_scrollableview) as RecyclerView

        val linearLayoutManager = LinearLayoutManager(activity!!.baseContext)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)

        val adapter = DessertAdapter(context!!)
        recyclerView.adapter = adapter
        return view
//        return inflater.inflate(R.layout.fragment_dummy, container, false)
    }


}