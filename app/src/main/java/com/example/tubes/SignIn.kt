package com.example.tubes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.landing_page.*
import kotlinx.android.synthetic.main.sign_in.*

class SignIn : AppCompatActivity() {

    val context = this
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSignIn: Button
    private lateinit var textViewLinkSignUp : TextView
    lateinit var databaseHelper: DatabaseHelper
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in)

        editTextEmail = findViewById<EditText>(R.id.editText)
        editTextPassword = findViewById<EditText>(R.id.editText2)
        buttonSignIn = findViewById<Button>(R.id.button3)
        textViewLinkSignUp = findViewById<TextView>(R.id.textView7)
        var databaseHelper = DatabaseHelper(this)


        buttonSignIn.setOnClickListener {
            if (databaseHelper!!.checkUser(editTextEmail!!.text.toString().trim { it <= ' ' }, editTextPassword!!.text.toString().trim { it <= ' ' })) {
                val intent = Intent(this, BottomNavMenu::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Email/ password salah", Toast.LENGTH_SHORT).show()
            }
        }

        textViewLinkSignUp.setOnClickListener {
            val intent = Intent(context,SignUp::class.java)
            startActivity(intent)
        }
    }
}