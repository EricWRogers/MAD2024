package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class Login : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Firebase.initialize(this)
        firebaseAuth = Firebase.auth

        val registrationButton = findViewById<Button>(R.id.registration);
        registrationButton.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
            finish() // this way you can't go back
        }

        val loginButton = findViewById<Button>(R.id.login)
        loginButton.setOnClickListener {
            val editTextEmail = findViewById<EditText>(R.id.email)
            val editTextPass = findViewById<EditText>(R.id.password)

            val email = editTextEmail.text.toString()
            val password = editTextPass.text.toString()

            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("AUTH", "signInWithEmail:success")
                        Toast.makeText(
                            baseContext,
                            "Authentication Success.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        //val user = auth.currentUser
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish() // this way you can't go back
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("AUTH", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()

                        val errorText = findViewById<TextView>(R.id.error_text)
                        errorText.text = task.exception.toString()
                    }
                }
        }
    }
}