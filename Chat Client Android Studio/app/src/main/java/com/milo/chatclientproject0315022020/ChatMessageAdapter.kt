package com.milo.chatclientproject0315022020

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Adapter for chat messages.
 * RecyclerView recycles each item row.
 *
 * @author Michael Lock
 * @date 24.02.2020
 */


class ChatMessageAdapter(var context: Context, var chatMessages: ArrayList<ChatMessage>) :
    RecyclerView.Adapter<ChatMessagesHolder>() {

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessagesHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false)
        return ChatMessagesHolder(view)
    }

    override fun onBindViewHolder(holder: ChatMessagesHolder, position: Int) {
        var message: ChatMessage = chatMessages[position]
        holder.setDetails(message)
    }
}