package com.example.tubes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tubes.fragments.CartFragment
import com.example.tubes.fragments.HomeFragment
import com.example.tubes.fragments.ProfileFragment
import kotlinx.android.synthetic.main.bottom_nav_menu.*

class BottomNavMenu : AppCompatActivity() {

    var username: String? = ""
    var email: String? = ""
    var password: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_nav_menu)

        username = intent.getStringExtra("Username")
        email = intent.getStringExtra("Email")
        password = intent.getStringExtra("Password")
        //textView16.text = "Welcome " + name

        val homeFragment = HomeFragment()
        val cartFragment = CartFragment()
        val profileFragment = ProfileFragment()

        makeCurrentFragment(homeFragment)

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_cart -> makeCurrentFragment(cartFragment)
                R.id.ic_profile -> makeCurrentFragment(profileFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            val bundle = Bundle()
            bundle.putString("Username", username)
            bundle.putString("Email", email)
            bundle.putString("Password", password)
            fragment.arguments = bundle
            replace(R.id.container, fragment)
            commit()
        }
}