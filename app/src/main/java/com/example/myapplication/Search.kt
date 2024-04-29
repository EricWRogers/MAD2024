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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val query = database.collection("users")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.id.contains(messageId)) {
                        val email = document.id
                        val id = document.getString("uid") ?: ""
                        users.add(UserInfo(email, id))
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("FIRESTORE", "Error getting user documents: $exception")
            }

        // Return the task associated with the query
        return query.continueWith { users }
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

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val userInfoList = mutableListOf<UserInfo>() // Initialize an empty list
        val adapter = UserInfoAdapter(userInfoList)
        recyclerView.adapter = adapter

        searchBox.addTextChangedListener {
            val name = searchBox.text.toString()

            database = Firebase.firestore

            if (name.isEmpty() == false){
                getUserInfo(name).addOnSuccessListener {userInfoList ->
                    adapter.updateList(userInfoList)
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