package com.example.tuank.fuel_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_verify.*

class verify : AppCompatActivity() {
    var auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        val btnverify = findViewById<View>(R.id.btnverify) as Button
        val back = findViewById<View>(R.id.back) as TextView

        back.setOnClickListener(View.OnClickListener {
            view -> backtomain()
        })
        btnverify.setOnClickListener(View.OnClickListener {
            view -> forgetpass()
        })
    }
    private fun forgetpass(){

        btnverify.setBackgroundResource(R.drawable.btnforgotchange)
        val emailtxt = findViewById<View>(R.id.Emailre) as EditText
        var emailre = emailtxt.text.toString()
        Thread.sleep(1000)
        btnverify.setBackgroundResource(R.drawable.reset)
        if(!emailre.isEmpty()){

            auth.sendPasswordResetEmail(emailre).addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "check email de dat lai mat khau!", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,MainActivity ::class.java))
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }
            }
        }else{
            Toast.makeText(this,"Vui long nhap vao Email!",Toast.LENGTH_LONG).show()
        }
    }

    private fun backtomain(){
        startActivity(Intent(this, MainActivity ::class.java))
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        finish()
    }
}
