package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class UserInfoAdapter(private val userInfoList: MutableList<UserInfo>) : RecyclerView.Adapter<UserInfoAdapter.UserInfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_button, parent, false)
        return UserInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {
        val userInfo = userInfoList[position]
        holder.bind(userInfo)
    }

    override fun getItemCount(): Int {
        return userInfoList.size
    }

    inner class UserInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val button: Button = itemView.findViewById(R.id.button)

        fun bind(userInfo: UserInfo) {
            button.text = userInfo.email
            button.setOnClickListener {
                // change activity
                // pass in the email and uid
            }
        }
    }

    fun updateList(newUserInfoList: List<UserInfo>) {
        userInfoList.clear()
        userInfoList.addAll(newUserInfoList)
        notifyDataSetChanged()
    }
}
