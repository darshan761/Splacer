package com.example.hp.splacer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import java.util.ArrayList

/**
 * Created by HP on 06-10-2018.
 */
class afterSerch : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.activity_aftersearch, container, false)

        viewManager = LinearLayoutManager(context)
        val i = 0
        var myDataset = ArrayList<String?>()
      //  myDataset = i.getStringArrayListExtra("Companies")
        for(i in myDataset)
            Toast.makeText(context,"FROM NEXT SCREEN:"+i, Toast.LENGTH_LONG).show()
      //  viewAdapter = MyAdapter(myDataset)

        recyclerView = view.findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        return view
    }

}