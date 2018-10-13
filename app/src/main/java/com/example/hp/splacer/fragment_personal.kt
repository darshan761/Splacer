package com.example.hp.splacer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_personal.view.*
import com.google.firebase.database.DataSnapshot



/**
 * Created by HP on 29-09-2018.
 */
class fragment_personal:Fragment() {


    private var mMsg: DatabaseReference? = null
    private var mAuth = FirebaseAuth.getInstance()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return inflater?.inflate(R.layout.fragment_personal,container,false)

        val view = inflater!!.inflate(R.layout.fragment_personal, container, false)
        var e = view.findViewById<Button>(R.id.edit)
        var  s = view.findViewById<Button>(R.id.save)
        var name = view.findViewById<EditText>(R.id.user_name)
        var em = view.findViewById<EditText>(R.id.user_email)
        var mob = view.findViewById<EditText>(R.id.user_mobile)
        var clss = view.findViewById<EditText>(R.id.user_clss)
        var cgp = view.findViewById<EditText>(R.id.user_cgp)
        var title = view.findViewById<TextView>(R.id.user_profile_name)
        var ee = view.findViewById<TextView>(R.id.user_profile_short_bio)
        var up = view.findViewById<ImageView>(R.id.userphoto)
        em?.setText(mAuth?.currentUser?.email.toString())

        var mDatabase: DatabaseReference
        mDatabase = FirebaseDatabase.getInstance().reference
         var r = mDatabase.child("User").orderByChild("email").equalTo(mAuth.currentUser?.email).limitToFirst(1)
         r.addListenerForSingleValueEvent(object : ValueEventListener{
             override fun onDataChange(p0: DataSnapshot?) {
                 p0?.children?.forEach{
                     val uu = it?.getValue(User::class.java)
                     name.setText(uu?.Name)
                     mob.setText(uu?.mobile)
                     clss.setText(uu?.Class)
                     cgp.setText(uu?.cgp.toString())
                     title.setText(uu?.Name)
                     Toast.makeText(context,"Welcome "+uu?.Name,Toast.LENGTH_LONG).show()
                 }

             }

             override fun onCancelled(p0: DatabaseError?) {

             }
         })
        ee.setText(mAuth.currentUser?.email.toString())
        s.setOnClickListener{ view ->

            val u = User(name.text.toString(),mAuth.currentUser!!.email.toString(),mob.text.toString(),clss.text.toString(),cgp.text.toString().toDouble())
            e.isEnabled = false
            name.isEnabled = false
            mob.isEnabled = false
            clss.isEnabled = false
            cgp.isEnabled = false
           r.addListenerForSingleValueEvent(object :ValueEventListener{
               override fun onDataChange(p0: DataSnapshot?) {
                  p0?.children?.forEach {
                      it?.ref?.setValue(u)
                  }
               }

               override fun onCancelled(p0: DatabaseError?) {

               }
           })



        }

        e.setOnClickListener{ view ->
              e.isEnabled = true
              name.isEnabled = true
             mob.isEnabled = true
             clss.isEnabled = true
             cgp.isEnabled = true

        }
    return view
    }
}