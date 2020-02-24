package com.milo.chatclientproject0315022020

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Toast
import com.milo.chatclientproject0315022020.MainActivity.Companion.address
import com.milo.chatclientproject0315022020.MainActivity.Companion.connected
import com.milo.chatclientproject0315022020.MainActivity.Companion.port
import com.milo.chatclientproject0315022020.MainActivity.Companion.reader
import com.milo.chatclientproject0315022020.MainActivity.Companion.serverFeed
import com.milo.chatclientproject0315022020.MainActivity.Companion.socket
import com.milo.chatclientproject0315022020.MainActivity.Companion.writer
import kotlinx.android.synthetic.main.activity_main.*
import java.net.Socket
import java.util.*

/**
 * Connects client to server and uses a UI thread to display information to the user
 *
 * @author Michael Lock
 * @date 24.02.2020
 */

class ServerConnect(private var context: Context) : Runnable {
    override fun run() {
        Log.i("SERVER", "Thread1 above runOnUiThread")
        val newContext = context as Activity

        try {
            Log.i("SERVER", "IS CONNECTING?")
            socket = Socket(address, port)

            writer = socket.getOutputStream()
            reader = Scanner(socket.getInputStream())

            serverFeed = Thread(ServerFeed(newContext))
            serverFeed.start()

            newContext.runOnUiThread {
                newContext.connectButton.text = "DISCONNECT"
                newContext.tvMessage.text = "Connected"
                connected = true
                newContext.recyclerView.visibility = View.VISIBLE
                newContext.connectToServerLayout.visibility = View.GONE
                newContext.sendButton.visibility = View.VISIBLE
                newContext.editText.visibility = View.VISIBLE
            }
        } catch (e: Exception) {

            newContext.runOnUiThread {
                connected = false
                // Initialize a new instance of
                AlertDialog.Builder(newContext)
                    .setTitle("Connection error")
                    .setMessage("Could not connect to server. Have you got the right IP address?")
                    .setPositiveButton("Try again") { _, _ ->
                        Thread(ServerConnect(newContext)).start()
                    }
                    .setNegativeButton("OK", null)
                    .show()
            }

            e.printStackTrace()
        }
    }
}