package com.example.hp.splacer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth




class MainActivity : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var editText1 = findViewById<EditText>(R.id.email)
        var editText2 = findViewById<EditText>(R.id.psswd)
        var btnLogin = findViewById<Button>(R.id.login)
        var btnRegister = findViewById<Button>(R.id.register)
        var progress = findViewById<ProgressBar>(R.id.progressBar3)
        progress.visibility = View.GONE
        btnLogin.setOnClickListener { view ->
            if (!(editText1.text.toString().trim().isNullOrEmpty() || editText2.text.toString().trim().isNullOrEmpty())) {
                progress.visibility = View.VISIBLE
                mAuth.signInWithEmailAndPassword(editText1.text.toString(), editText2.text.toString())
                        .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
                                var intent = Intent(this, Home::class.java)
                                intent.putExtra("id", mAuth.currentUser?.email)
                                startActivity(intent)
                                progress.visibility = View.GONE
                            } else {
                                Toast.makeText(this, "Failed to Login", Toast.LENGTH_LONG).show()
                                progress.visibility = View.GONE
                            }
                        })
            }
            else {
                Toast.makeText(this,"Fill All fields",Toast.LENGTH_LONG).show()
            }
        }
        btnRegister.setOnClickListener{view->

            var intent = Intent(this,Register::class.java)
            startActivity(intent)


        }

        }
    }
