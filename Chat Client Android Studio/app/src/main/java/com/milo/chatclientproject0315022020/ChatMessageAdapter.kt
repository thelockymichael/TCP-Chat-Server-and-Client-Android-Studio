package com.milo.chatclientproject0315022020

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

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
        var planet: ChatMessage = chatMessages[position]
        holder.setDetails(planet)

        //var layoutManager = holder as LinearLayoutManager
        //layoutManager.scrollToPo

        /*recyclerView.layoutManager = LinearLayoutManager(this)
        MainActivity.chatMessageArrayList = ArrayList()
        MainActivity.adapter = ChatMessageAdapter(this, MainActivity.chatMessageArrayList)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = MainActivity.adapter*/




/*        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("txtName", planet.planetName)
            context.startActivity(intent)
        }*/
    }
}