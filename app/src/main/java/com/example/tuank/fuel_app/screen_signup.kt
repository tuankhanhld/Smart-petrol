package com.example.tuank.fuel_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_screen_signup.*

class screen_signup : AppCompatActivity() {
       var auth = FirebaseAuth.getInstance()
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_signup)

        val btnsignup = findViewById<View>(R.id.btnsignup) as Button
        val btnsignin1 = findViewById<View>(R.id.btnsignin2) as Button
        database = FirebaseDatabase.getInstance().getReference("Name")
        btnsignup.setOnClickListener(View.OnClickListener {
            view -> register()
        })
        btnsignin1.setOnClickListener(View.OnClickListener {
            view -> exchangesignup()
        })
    }
    private fun register(){

        btnsignup.setBackgroundResource(R.drawable.sigupbtnchange)
        val emailtxt = findViewById<View>(R.id.Email) as EditText
        val usernametxt = findViewById<View>(R.id.name) as EditText
        val passwordtxt = findViewById<View>(R.id.password) as EditText
        val repasswordtxt = findViewById<View>(R.id.repassword) as EditText

        var email = emailtxt.text.toString()
        var password = passwordtxt.text.toString()
        var username = usernametxt.text.toString()
        var repassword = repasswordtxt.text.toString()

        if (!email.isEmpty() && !username.isEmpty() && !password.isEmpty() && !repassword.isEmpty())
        {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful){
                    val use = auth.currentUser
                    val id = use!!.uid
                    database.child(id).child("Name").setValue(username)
                    Toast.makeText(this, "Dang ky thanh cong!", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MainActivity ::class.java))
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }else{
                    Toast.makeText(this,"Loi dang ky! Vui long thu lai!", Toast.LENGTH_LONG).show()
                }
            })

        }else if(password != repassword)
        {
            Toast.makeText(this, "Mat khau va nhap lai khong khop!", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "Xin hay dien day du!", Toast.LENGTH_LONG).show()
        }

    }
    private fun exchangesignup(){
        startActivity(Intent(this, MainActivity ::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}
