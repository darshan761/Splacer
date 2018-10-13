package com.example.hp.splacer

import android.app.Activity
import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.ContentProvider

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.view.*

/**
 * Created by HP on 04-10-2018.
 */
class MyAdapter2(private val myDataset: ArrayList<Company>) :
        RecyclerView.Adapter<MyAdapter2.MyViewHolder>() {
    private var mAuth = FirebaseAuth.getInstance()
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val textView: CardView) : RecyclerView.ViewHolder(textView){
        var name: TextView
        var desg: TextView
        var loc: TextView
        var CTC: TextView
        val desp: TextView
        val type: TextView
        val remove:Button


        init {
            name = textView.findViewById(R.id.Comp_Name)
            desg = textView.findViewById(R.id.Comp_Desg)
            loc = textView.findViewById(R.id.Comp_Loc)
            CTC = textView.findViewById(R.id.Comp_CTC)
            desp = textView.findViewById(R.id.Comp_Desp)
            type = textView.findViewById(R.id.Comp_Type)
            remove= textView.findViewById(R.id.remove)
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter2.MyViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview2, parent, false)   as CardView
        // set the view's size, margins, paddings and layout parameters

        return MyViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.name.text = "Name: "+myDataset[position]?.Name
        holder.desg.text = "Designation: "+myDataset[position]?.Designation
        holder.loc.text = "Location: "+myDataset[position]?.Location
        holder.CTC.text = "CTC: "+myDataset[position]?.CTC.toString()
        holder.desp.text = "Description: "+myDataset[position]?.Description
        holder.type.text = "Type: "+myDataset[position]?.Type
        holder.remove.setOnClickListener{view ->
            var mDatabase: DatabaseReference
            mDatabase = FirebaseDatabase.getInstance().reference
            val ref = mDatabase.child("Waitlist").orderByChild("Company").equalTo(myDataset[position].Name).limitToFirst(1)
            ref.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot?) {
                    p0?.children?.forEach {
                       if(mAuth.currentUser?.email.toString().equals(it.child("email").getValue().toString())) {
                      it.child("Company").ref.removeValue()
                           it.child("email").ref.removeValue()
                           Toast.makeText(view.context,"Removed Successfully",Toast.LENGTH_LONG).show()

                       }
                }
                    }

                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
            val con : Context =view.context
          /* val ft :android.support.v4.app.FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.detach(fragment_waitlist())
            ft.attach(fragment_waitlist())
            ft.commit()*/

                }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}