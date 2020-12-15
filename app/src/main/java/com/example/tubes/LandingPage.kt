package com.example.tubes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.landing_page.*

class LandingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page)

        val context = this
        button.setOnClickListener {
            val intent = Intent(context,SignIn::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            val intent = Intent(context,SignUp::class.java)
            startActivity(intent)
        }

    }
}