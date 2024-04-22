package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Registration : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = Firebase.auth

        val loginButton = findViewById<Button>(R.id.login)
        loginButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish() // this way you can't go back
        }

        val registrationButton = findViewById<Button>(R.id.registration);
        registrationButton.setOnClickListener {
            val editTextEmail = findViewById<EditText>(R.id.email)
            val editTextPass = findViewById<EditText>(R.id.password)

            val email = editTextEmail.text.toString()
            val password = editTextPass.text.toString()

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("AUTH", "signInWithEmail:success")
                        Toast.makeText(
                            baseContext,
                            "Authentication Success.",
                            Toast.LENGTH_SHORT,
                        ).show()

                        val user = firebaseAuth.currentUser

                        var database = Firebase.firestore
                        val data = mapOf("uid" to user!!.uid)
                        val userResponse = database.collection("users").document(email).set(data)

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
                        //updateUI(null)
                    }
                }
        }
    }
}