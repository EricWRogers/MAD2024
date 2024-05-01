package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ConversationAdapter

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

    private fun getMessagesDocument(documentId: String): Task<MessagesDocument> {
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

        // create temp list of conversations
        val tempConversation = mutableListOf(
            Conversation("Title", "Message")
        )

        // create adapter
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ConversationAdapter(tempConversation)
        recyclerView.adapter = adapter


        getConversationsDocument(user!!.uid)
            .addOnSuccessListener { conversationsDocument ->
                // create conversation list
                val con = mutableListOf<Conversation>()

                for (id in conversationsDocument.messages) {
                    getMessagesDocument(id)
                        .addOnSuccessListener { messagesDocument ->
                            // add new elements to the list
                            con.add(
                                Conversation(
                                    messagesDocument.people[0],
                                    messagesDocument.messages[messagesDocument.messages.size - 1].message)
                            )
                            adapter.updateList(con)
                            Log.d(
                                "FIRESTORE",
                                "Conversations document retrieved successfully for user $id: $messagesDocument"
                            )
                        }
                        .addOnFailureListener { exception ->
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