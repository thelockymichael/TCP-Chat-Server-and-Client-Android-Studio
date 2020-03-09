package com.milo.chatclientproject0315022020

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Sets each item_row.xml with either server side or client side information like message,
 * date and username.
 *
 * @author Michael Lock
 * @date 24.02.2020
 */

class ChatMessagesHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private var txtUserName: TextView
    private var txtMessage: TextView
    private var txtDate: TextView

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