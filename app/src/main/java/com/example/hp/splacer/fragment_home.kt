package com.example.hp.splacer

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import android.net.sip.SipErrorCode.TIME_OUT



/**
 * Created by HP on 29-09-2018.
 */
class fragment_home : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val TIME_OUT = 10000
    private var mAuth = FirebaseAuth.getInstance()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return inflater?.inflate(R.layout.fragment_home,container,false)
        val view = inflater!!.inflate(R.layout.fragment_home, container, false)
        val d = view.findViewById<Spinner>(R.id.desg)

        val l = view.findViewById<Spinner>(R.id.loc)

        val t = view.findViewById<Spinner>(R.id.typ)
        val search: Button = view.findViewById(R.id.search)
        ArrayAdapter.createFromResource(
                context,
                R.array.Designation,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            d.adapter = adapter
        }

        ArrayAdapter.createFromResource(
                context,
                R.array.Loc,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            l.adapter = adapter
        }
        ArrayAdapter.createFromResource(
                context,
                R.array.Type,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            t.adapter = adapter
        }

        search.setOnClickListener { view ->
            val myDataset = ArrayList<Company?>()

            var mDatabase: DatabaseReference
            mDatabase = FirebaseDatabase.getInstance().reference
            var r = mDatabase.child("Company").orderByChild("Designation").equalTo(d.selectedItem.toString())
            r.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot?) {
                   var  count  = 0
                    p0?.children?.forEach {
                        var Com = it?.getValue(Company::class.java)
                        if (Com?.Location == l.selectedItem.toString() && Com.Type == t.selectedItem.toString()) {

                            myDataset.add(Com)

                        }
                    }
                    val i = Intent(context,aftersearch::class.java)
                    i.putExtra("Companies",myDataset)
                    startActivity(i)

                }


                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })

        }

        return view
    }
}