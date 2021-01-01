package com.example.tubes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.tubes.helper.Constant
import com.example.tubes.helper.PreferencesHelper
import kotlinx.android.synthetic.main.landing_page.*
import kotlinx.android.synthetic.main.sign_in.*

class SignIn : AppCompatActivity() {

    lateinit var databaseHelper: DatabaseHelper
    lateinit var sharedPref: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in)

        val editTextEmail = findViewById<EditText>(R.id.editText)
        val editTextPassword = findViewById<EditText>(R.id.editText2)
        val buttonSignIn = findViewById<Button>(R.id.button3)
        val textViewLinkSignUp = findViewById<TextView>(R.id.textView7)
        databaseHelper = DatabaseHelper(this)
        sharedPref = PreferencesHelper(this)


        buttonSignIn.setOnClickListener {
            if (!databaseHelper.checkUser(editTextEmail.text.toString().trim())) {
                Toast.makeText(this, "Email anda belum terdatar", Toast.LENGTH_SHORT).show()
            }

            else if (databaseHelper.checkUser(editTextEmail.text.toString().trim { it <= ' ' }, editTextPassword.text.toString().trim { it <= ' ' })) {

                var data = databaseHelper.readData()
                var username: String? = ""
                var email: String? = ""
                var password: String? = ""
                var image: String? = ""

                for(i in 0 until data.size) {
                    if (editTextEmail.text.toString() == data[i].email && editTextPassword.text.toString() == data[i].password) {
                        email = data[i].email
                    }
                }

                sharedPref.put(Constant.PREF_EMAIL, email)
                sharedPref.put(Constant.PREF_IS_LOGIN, true)

                startActivity(Intent(this,BottomNavMenu::class.java))
                finish()
            } else {
                Toast.makeText(this, "Email/ password salah", Toast.LENGTH_SHORT).show()
            }
        }

        textViewLinkSignUp.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        if(sharedPref.getBoolean(Constant.PREF_IS_LOGIN)){
            startActivity(Intent(this,BottomNavMenu::class.java))
            finish()
        }
    }
}