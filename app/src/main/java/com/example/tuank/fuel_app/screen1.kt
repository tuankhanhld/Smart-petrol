package com.example.tuank.fuel_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_screen1.*

class screen1 : AppCompatActivity() {
    var mauth = FirebaseDatabase.getInstance()
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen1)
        database = FirebaseDatabase.getInstance().getReference("name")
        val btnsignout = findViewById<View>(R.id.signout) as Button
        btnsignout.setOnClickListener(View.OnClickListener {
            view -> logout()
        })
        database.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                val name = p0?.child("Name").toString()
                txt1.text = "Xin chao" + name
            }
        })
    }
    private fun logout(){

    }
}

