package com.milo.chatclientproject0315022020

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.milo.chatclientproject0315022020.MainActivity.Companion.adapter
import com.milo.chatclientproject0315022020.MainActivity.Companion.chatMessageArrayList
import com.milo.chatclientproject0315022020.MainActivity.Companion.reader
import com.milo.chatclientproject0315022020.MainActivity.Companion.serverFeed
import com.milo.chatclientproject0315022020.MainActivity.Companion.shutdown
import com.milo.chatclientproject0315022020.MainActivity.Companion.socket
import com.milo.chatclientproject0315022020.MainActivity.Companion.writer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.serialization.json.Json


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
                if (serverFeed.isInterrupted)
                // We've been interrupted: no more crunching.
                    break

                val message: String = reader.nextLine()
                Log.i("MESSAGE", "MESSAGE $message")

                if (message != null) {

                    if (serverFeed.isInterrupted) {
                        socket.close()
                        reader.close()
                        writer.close()
                        Log.i("SERVER", "CLIENT CLOSED")
                    }

                    serverFeed(context, message)

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