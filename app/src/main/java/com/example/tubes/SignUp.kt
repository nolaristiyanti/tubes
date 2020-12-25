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
import kotlinx.android.synthetic.main.landing_page.button
import kotlinx.android.synthetic.main.sign_up.*

class SignUp : AppCompatActivity() {

    val context = this
    private lateinit var editTextUsername: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonSignUp: Button
    private lateinit var textViewLinkSignIn : TextView
    lateinit var databaseHelper: DatabaseHelper
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        editTextUsername = findViewById<EditText>(R.id.editText3)
        editTextEmail = findViewById<EditText>(R.id.editText4)
        editTextPassword = findViewById<EditText>(R.id.editText5)
        editTextConfirmPassword = findViewById<EditText>(R.id.editText6)
        buttonSignUp = findViewById<Button>(R.id.button4)
        textViewLinkSignIn = findViewById<TextView>(R.id.textView14)
        var databaseHelper = DatabaseHelper(this)

        buttonSignUp.setOnClickListener {
            if(editTextUsername.text.toString().equals("") || editTextEmail.text.toString().equals("") ||
                editTextPassword.text.toString().equals("") || editTextConfirmPassword.text.toString().equals("")){
                Toast.makeText(this, "Tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else {
                if (editTextPassword.text.toString() == editTextConfirmPassword.text.toString()) {
                    if (!databaseHelper!!.checkUser(editTextEmail!!.text.toString().trim())) {
                        var user = User(
                            username = editTextUsername!!.text.toString().trim(),
                            email = editTextEmail!!.text.toString().trim(),
                            password = editTextPassword!!.text.toString().trim()
                        )
                        databaseHelper!!.addUser(user)
                        Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()

                        handler = Handler()
                        handler.postDelayed({

                            val intent = Intent(this, SignIn::class.java)
                            startActivity(intent)
                            finish()
                        }, 1000)
                    }
                    else {
                        Toast.makeText(this, "Registrasi gagal", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
                }
            }
        }

        textViewLinkSignIn.setOnClickListener {
            val intent = Intent(context,SignIn::class.java)
            startActivity(intent)
        }
    }
}