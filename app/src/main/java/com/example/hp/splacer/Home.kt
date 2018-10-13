package com.example.hp.splacer

import android.app.FragmentTransaction
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import com.example.hp.splacer.R.layout.header
import kotlinx.android.synthetic.main.header.view.*


class Home : AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout
    private var mAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
       supportFragmentManager.beginTransaction().replace(R.id.content_frame, fragment_home()).commit()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        mDrawerLayout = findViewById(R.id.drawer_layout)


        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val hed = navigationView.getHeaderView(0)
        val dp = hed.findViewById<TextView>(R.id.dp)
        dp.setText(mAuth.currentUser?.email.toString())
        navigationView.setNavigationItemSelectedListener { menuItem ->

            supportFragmentManager.beginTransaction().replace(R.id.content_frame,fragment_home()).commit()
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            if (menuItem.itemId == R.id.home) {
                supportFragmentManager.beginTransaction().replace(R.id.content_frame,fragment_home()).commit()

            }
            if (menuItem.itemId == R.id.personal) {
                supportFragmentManager.beginTransaction().replace(R.id.content_frame,fragment_personal()).commit()

            }
            if (menuItem.itemId == R.id.applied) {
                supportFragmentManager.beginTransaction().replace(R.id.content_frame,fragment_applied()).commit()

            }
            if (menuItem.itemId == R.id.waitlisted) {
                supportFragmentManager.beginTransaction().replace(R.id.content_frame,fragment_waitlist()).commit()

            }
            if (menuItem.itemId == R.id.logout) {
                showDialog()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialog() {
        // Late initialize an alert dialog object
         var dialog: AlertDialog

        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("Logout")

        // Set a message for alert dialog
        builder.setMessage("Do you want to logout?")

        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    var i = Intent(this,MainActivity::class.java)
                    startActivity(i)
                }
                DialogInterface.BUTTON_NEUTRAL -> {
                    mDrawerLayout.openDrawer(GravityCompat.START)
                }
            }
        }

        // Set the alert dialog positive/yes button
        builder.setPositiveButton("YES",dialogClickListener)

        // Set the alert dialog neutral/cancel button
        builder.setNeutralButton("NO",dialogClickListener)

        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }
}
