package com.example.tuank.fuel_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var auth = FirebaseAuth.getInstance()
    var k: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnshow = findViewById<View>(R.id.btnshow) as Button
        val btnsignin = findViewById<View>(R.id.btnsignin) as Button
        val btnsignup = findViewById<View>(R.id.btnsignup1) as Button
        val btnforgot = findViewById<View>(R.id.btnforgot) as Button
        btnsignin.setOnClickListener(View.OnClickListener {
            view -> login()
        })
        btnsignup.setOnClickListener(View.OnClickListener {
            view -> register()
        })
        btnforgot.setOnClickListener(View.OnClickListener {
            view -> forgot()
        })
        btnshow.setOnClickListener(View.OnClickListener {
            view -> showpass()
        })

    }
    private fun login() {
        val Emailtxt = findViewById<View>(R.id.Emaillog) as EditText
        val Passwordtxt = findViewById<View>(R.id.password) as EditText


        var email = Emailtxt.text.toString()
        var password = Passwordtxt.text.toString()

        if(!email.isEmpty() && !password.isEmpty()){
             this.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, OnCompleteListener {task ->
                if (task.isSuccessful){
                    startActivity(Intent(this, main1 ::class.java))
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                    Toast.makeText(this, "dang nhap thanh cong!", Toast.LENGTH_LONG).show()
                }else{

                    Toast.makeText(this, "Loi dang nhap!", Toast.LENGTH_LONG).show()
                }
            })
        }else{
            Toast.makeText(this, "xin dien dien day du!",Toast.LENGTH_LONG).show()
        }
    }
    private fun register(){
        startActivity(Intent(this,screen_signup ::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
    private fun forgot(){
        startActivity(Intent(this, verify ::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
    private fun showpass(){
        if (k == 1) {
            btnshow.setBackgroundResource(R.drawable.show)
            password.inputType = InputType.TYPE_CLASS_TEXT
            k = 0
        }else{
            btnshow.setBackgroundResource(R.drawable.hide)
            password.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            k = 1


        }
    }
}
