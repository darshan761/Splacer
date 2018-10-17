package com.example.hp.splacer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var editText1 = findViewById<EditText>(R.id.email)
        var editText2 = findViewById<EditText>(R.id.pssd)
        var n = findViewById<EditText>(R.id.input_name)
        var m = findViewById<EditText>(R.id.user_mobile)
        var clss = findViewById<EditText>(R.id.Class)
        var cgp = findViewById<EditText>(R.id.user_cgp)
        var btnRegister = findViewById<Button>(R.id.register)
        var progress = findViewById<ProgressBar>(R.id.prgss)
        progress.visibility = View.GONE
        var mDatabase: DatabaseReference
        btnRegister.setOnClickListener{view->
            if(!(TextUtils.isEmpty(editText1.text.toString()) || TextUtils.isEmpty(editText2.text.toString())
                    || TextUtils.isEmpty(n.text.toString()) || TextUtils.isEmpty(m.text.toString()) ||
                    TextUtils.isEmpty(clss.text.toString()) || TextUtils.isEmpty(cgp.text.toString())) ) {
                progress.visibility = View.VISIBLE
                mAuth.createUserWithEmailAndPassword(editText1.text.toString(), editText2.text.toString())
                        .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                            if (task.isSuccessful) {
                                val u = User(n.text.toString(), mAuth.currentUser!!.email.toString(), m.text.toString(), clss.text.toString(), cgp.text.toString().toDouble())
                                mDatabase = FirebaseDatabase.getInstance().reference
                                val i = mDatabase.child("User").push()
                                i.setValue(u)
                                Toast.makeText(this, "Register successful", Toast.LENGTH_LONG).show()
                                progress.visibility = View.GONE
                            }
                        })
            }
            else {
                Toast.makeText(this,"Please fill all fields",Toast.LENGTH_LONG).show()
            }
        }
    }
}

