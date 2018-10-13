package com.example.hp.splacer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList

/**
 * Created by HP on 29-09-2018.
 */
class fragment_applied : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var mAuth = FirebaseAuth.getInstance()
    val myDataset = ArrayList<Company>()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // return inflater?.inflate(R.layout.fragment_applied,container,false)
        val view = inflater!!.inflate(R.layout.fragment_applied, container, false)

        viewManager = LinearLayoutManager(this.context)
        var mDatabase: DatabaseReference
        mDatabase = FirebaseDatabase.getInstance().reference

        var r = mDatabase.child("Applied").orderByChild("email").equalTo(mAuth.currentUser?.email.toString())

        r.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot?) {
                var pos = 0
                p0?.children?.forEach {
                    var o = mDatabase.child("Company").orderByChild("Name").equalTo(it.child("Company").getValue().toString())
                    o.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot?) {

                            p0?.children?.forEach {
                                var Com = it.getValue(Company::class.java) as Company

                                myDataset.add(Com)
                                viewAdapter = MyAdapter1(myDataset)
                                recyclerView = view.findViewById<RecyclerView>(R.id.my_recycler_view1).apply {
                                    // use this setting to improve performance if you know that changes
                                    // in content do not change the layout size of the RecyclerView
                                    setHasFixedSize(true)

                                    // use a linear layout manager
                                    layoutManager = viewManager

                                    // specify an viewAdapter (see also next example)
                                    adapter = viewAdapter
                                }
                            }

                        }

                        override fun onCancelled(p0: DatabaseError?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }
                    })




            }
            }

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })





        return view
    }
}