package com.example.myapplication
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Conversation(val title: String, val lastMessage: String)

class ConversationAdapter(private val conversations: MutableList<Conversation>) :
    RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.conversation_item, parent, false)
        return ConversationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        val conversation = conversations[position]
        holder.titleTextView.text = conversation.title
        holder.messageTextView.text = conversation.lastMessage
    }

    override fun getItemCount() = conversations.size

    class ConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        var messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
    }

    // notify the adapter

}