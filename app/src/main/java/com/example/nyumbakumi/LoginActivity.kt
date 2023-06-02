package com.example.nyumbakumi

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var edt_email: EditText
    lateinit var edt_password: EditText
    lateinit var btn_login: Button
    lateinit var tx_register: TextView
    lateinit var tx_reset: TextView
    lateinit var progress: ProgressDialog
    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        edt_email = findViewById(R.id.edtemail)
        edt_password = findViewById(R.id.edtpassword)
        btn_login = findViewById(R.id.btnlogin)
        tx_register = findViewById(R.id.txregister)
        tx_reset = findViewById(R.id.txreset)

        mAuth = FirebaseAuth.getInstance()
        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("please wait...")
        btn_login.setOnClickListener {
            var email = edt_email.text.toString().trim()
            var password = edt_password.text.toString().trim()

            if (email.isEmpty()) {
                edt_email.setError("please fill this input")
                edt_email.requestFocus()
            } else if (password.isEmpty()) {
                edt_password.setError("please fill this input")
                edt_password.requestFocus()
            } else {
                progress.show()
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    progress.dismiss()
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Registration successfully", Toast.LENGTH_SHORT).show()
                        mAuth.signOut()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        displayMessage("ERROR", it.exception!!.message.toString())
                    }

                    tx_register.setOnClickListener {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                }
            }
        }

    }

    fun displayMessage(title:String, message:String){
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("ok",null)
        alertDialog.create().show()
    }
}