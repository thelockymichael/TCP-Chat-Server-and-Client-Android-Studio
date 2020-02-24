package com.milo.chatclientproject0315022020

import android.app.Activity
import android.util.Log
import com.milo.chatclientproject0315022020.MainActivity.Companion.adapter
import com.milo.chatclientproject0315022020.MainActivity.Companion.chatMessageArrayList
import com.milo.chatclientproject0315022020.MainActivity.Companion.reader
import com.milo.chatclientproject0315022020.MainActivity.Companion.serverFeed
import com.milo.chatclientproject0315022020.MainActivity.Companion.shutdown
import com.milo.chatclientproject0315022020.MainActivity.Companion.socket
import com.milo.chatclientproject0315022020.MainActivity.Companion.writer
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Receives data from server and adds to chatMessagesArray (that's in MainActivity!)
 * and notifies it.
 *
 * @author Michael Lock
 * @date 24.02.2020
 */

class ServerFeed(private var context: Activity) : Runnable {
    override fun run() {

        while (true) {
            try {
                if (serverFeed.isInterrupted) {
                    // We've been interrupted: no more crunching.
                    Log.i("SERVER", "WE'VE BEEN INTERRUPTED")
                    break
                }
                val message: String = reader.nextLine()
                if (message != null) {

                    if (serverFeed.isInterrupted) {
                        socket.close()
                        reader.close()
                        writer.close()
                        Log.i("SERVER", "CLIENT CLOSED")
                    }

                    context.runOnUiThread {

                        var messageArray: List<String> = message.split(" ")

                        // Create message string property for ChatMessage
                        var messageString = ""
                        for (i in 1 until messageArray.size - 2) {
                            messageString += " ${messageArray[i]}"
                            Log.i("SERVER", message)
                        }

                        // Creates date string property for ChatMessage
                        var messageDataString = ""
                        for (i in messageArray.size - 2 until messageArray.size) {
                            messageDataString += " ${messageArray[i]}"
                            Log.i("SERVER", messageDataString)
                        }

                        Log.i(
                            "SERVER", "1. ${messageArray[0]}" // Username or Server
                        )
                        Log.i("SERVER", "2. $messageString")
                        Log.i(
                            "SERVER", "3. $messageDataString"
                        )

                        Log.i("MESSAGE", messageArray.size.toString())
                        Log.i("SERVER", messageArray.toString())
                        chatMessageArrayList.add(
                            ChatMessage(
                                messageString, // Message string
                                messageArray[0], // Username or server
                                messageString,  // Command string is same as message
                                messageDataString // Date string
                            )
                        )
                        adapter.notifyDataSetChanged()

                        context.recyclerView.layoutManager!!.scrollToPosition(
                            chatMessageArrayList.size - 1
                        )
                    }
                } else {
                    Thread(ServerConnect(context)).start()
                    return
                }
            } catch (e: Exception) {
                shutdown()
                e.printStackTrace()
            }
        }
    }
}