package com.example.hp.splacer

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by HP on 04-10-2018.
 */
class MyAdapter1(private val myDataset: ArrayList<Company>) :
        RecyclerView.Adapter<MyAdapter1.MyViewHolder>() {
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


        init {
            name = textView.findViewById(R.id.Comp_Name)
            desg = textView.findViewById(R.id.Comp_Desg)
            loc = textView.findViewById(R.id.Comp_Loc)
            CTC = textView.findViewById(R.id.Comp_CTC)
            desp = textView.findViewById(R.id.Comp_Desp)
            type = textView.findViewById(R.id.Comp_Type)

        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter1.MyViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview1, parent, false)   as CardView
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


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}