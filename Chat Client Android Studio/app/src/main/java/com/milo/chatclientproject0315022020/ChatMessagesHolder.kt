package com.milo.chatclientproject0315022020

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatMessagesHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private var txtUserName: TextView
    private var txtMessage: TextView
    private var txtDate: TextView

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(getAdapterPosition(), getItemViewType())
        }
        return this
    }

    init {
        txtUserName = itemView.findViewById(R.id.txtUserName)
        txtMessage = itemView.findViewById(R.id.txtMessage)
        txtDate = itemView.findViewById(R.id.txtDate)
    }

    fun setDetails(chatMessage: ChatMessage) {
        txtUserName.text = chatMessage.user
        txtMessage.text = chatMessage.message
        txtDate.text = chatMessage.createdDateTime
    }
}