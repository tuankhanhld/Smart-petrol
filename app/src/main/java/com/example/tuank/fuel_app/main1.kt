package com.example.tuank.fuel_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_screen1.*

class main1 : AppCompatActivity() {
    var auth = FirebaseAuth.getInstance()
    lateinit var database: DatabaseReference
    init {
        database = FirebaseDatabase.getInstance().getReference("Name")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        val btnsignout = findViewById<View>(R.id.signout) as ImageButton
        val btnscan = findViewById<View>(R.id.btnscan) as Button

        btnsignout.setOnClickListener(View.OnClickListener {
            view -> signout()
        })
        btnscan.setOnClickListener(View.OnClickListener {
            view -> Scanner()
        })
        database.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                val name = p0?.child("Name").toString()
//                txt1.text = "Xin chao" + name
            }
        })
    }
    private fun signout(){
        auth.signOut()
        startActivity(Intent(this, MainActivity ::class.java))
    }

    private fun Scanner(){
        startActivity(Intent(this, Scanner_screen ::class.java))
        finish()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main__menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.menusigout) {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

}
