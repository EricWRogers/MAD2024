package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.fragment_button_one);
        button1.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("title", "Fragment 1 From Activity")

            val myFragment = Fragment1()
            replaceFragment(myFragment, bundle)
        }

        val button2 = findViewById<Button>(R.id.fragment_button_two);
        button2.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("title", "Fragment 2 From Activity")

            replaceFragment(Fragment2(), bundle)
        }
    }

    private fun replaceFragment(fragment: Fragment, bundle: Bundle)
    {
        fragment.arguments = bundle
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}