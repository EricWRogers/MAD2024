package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class UserInfo(
    val email : String,
    val uid : String
)

class Search : AppCompatActivity() {
    private lateinit var database: FirebaseFirestore

    private fun getUserInfo(messageId: String): Task<List<UserInfo>> {
        val users = mutableListOf<UserInfo>()
        database.collection("users")
            .whereLessThanOrEqualTo("email", messageId)
            .get().continueWith() { documents ->
            for (document in documents.result){
                if (document.exists()) {
                    val data = document.data
                    val email = document.id
                    val id = document.data.get("uid") as? String ?: ""

                    users.add(UserInfo(email, id))
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val searchBox = findViewById<EditText>(R.id.search_box)

        searchBox.addTextChangedListener {
            val name = searchBox.text.toString()

            database = Firebase.firestore

            if (name.isEmpty() == false){
                getUserInfo(name).addOnSuccessListener {userInfo ->
                    Log.d("FIRESTORE",
                        "UserInfo $userInfo")
                }
                    .addOnFailureListener {
                            exception ->
                        // Handle failure
                        Log.e(
                            "FIRESTORE",
                            "UserInfo Error: $exception"
                        )
                    }
            }
        }
    }
}