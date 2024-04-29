package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class ConversationsDocument(
    val messages: List<String> = listOf()
)

data class Message(
    val id : String,
    val time : Timestamp,
    val message : String
)

data class MessagesDocument(
    val people: List<String> = listOf(),
    val messages: List<Message> = listOf()
)

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    private fun getConversationsDocument(messageId: String): Task<ConversationsDocument> {
        val messageRef = database.collection("conversations").document(messageId)

        return messageRef.get().continueWith { task ->
            val document = task.result
            if (document.exists()) {
                val data = document.data
                val messages = data?.get("messages") as? List<String> ?: emptyList()

                ConversationsDocument(messages)
            } else {
                throw IllegalStateException("conversations document not found")
            }
        }
    }

    fun getMessagesDocument(documentId: String): Task<MessagesDocument> {
        val docRef = database.collection("messages").document(documentId)

        return docRef.get().continueWith { task ->
            val document = task.result
            if (document.exists()) {
                val data = document.data
                val people = data?.get("people") as? List<String> ?: emptyList()
                val messagesList = data?.get("messages") as? List<Map<String, Any>> ?: emptyList()
                val messages = messagesList.map { messageData ->
                    val id = messageData["id"] as? String ?: ""
                    val time = messageData["time"] as? Timestamp ?: Timestamp.now()
                    val message = messageData["message"] as? String ?: ""
                    Message(id, time, message)
                }
                MessagesDocument(people, messages)
            } else {
                throw IllegalStateException("Messages document not found")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        firebaseAuth = Firebase.auth

        val user = firebaseAuth.currentUser

        database = Firebase.firestore

        val searchButton = findViewById<Button>(R.id.search);
        searchButton.setOnClickListener {
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
        }

        getConversationsDocument(user!!.uid)
            .addOnSuccessListener { conversationsDocument ->
                // Handle success
                Log.d(
                    "FIRESTORE",
                    "conversations document retrieved successfully: $conversationsDocument"
                )

                for (id in conversationsDocument.messages) {
                    getMessagesDocument(id)
                        .addOnSuccessListener { messagesDocument ->
                            // Handle success
                            Log.d(
                                "FIRESTORE",
                                "Conversations document retrieved successfully for user $id: $messagesDocument"
                            )
                        }
                        .addOnFailureListener { exception ->
                            // Handle failure
                            Log.e(
                                "FIRESTORE",
                                "Error retrieving conversations document for user $id: $exception"
                            )
                        }
                }
            }
            .addOnFailureListener { exception ->
                // Handle failure
                Log.d("FIRESTORE", "Error retrieving message document: $exception")
            }
    }
}