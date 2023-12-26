package com.example.suitmediatest

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AdapterUser(private val userList: List<User>) : RecyclerView.Adapter<AdapterUser.ViewHolder>() {
    private lateinit var mListener : OnItemClickListener

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var avatarImageView: ImageView
        var fullNameTextView: TextView
        var emailTextView: TextView

        init {
            avatarImageView = itemView.findViewById(R.id.ivGambar)
            fullNameTextView = itemView.findViewById(R.id.tvName)
            emailTextView = itemView.findViewById(R.id.tvEmail)

            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mListener != null) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    mListener!!.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        val fName = user.first_name
        val lName = user.last_name

        val fullName = "$fName $lName"
        holder.fullNameTextView.text = fullName
        holder.emailTextView.text = user.email
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .into(holder.avatarImageView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }
}
