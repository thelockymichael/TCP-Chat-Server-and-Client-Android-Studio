package com.milo.chatclientproject0315022020

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.milo.chatclientproject0315022020.MainActivity.Companion.address
import com.milo.chatclientproject0315022020.MainActivity.Companion.connectToServerUI
import com.milo.chatclientproject0315022020.MainActivity.Companion.connected
import com.milo.chatclientproject0315022020.MainActivity.Companion.connectionError
import com.milo.chatclientproject0315022020.MainActivity.Companion.disconnectFromServer
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
        val newContext = context as Activity

        connectToServerUI(newContext)

        try {
            socket = Socket(address, port)
            writer = socket.getOutputStream()
            reader = Scanner(socket.getInputStream())

            serverFeed = Thread(ServerFeed(newContext))
            serverFeed.start()

            disconnectFromServer(newContext)

        } catch (e: Exception) {
            // Cannot find server
            connectionError(newContext)
            e.printStackTrace()
        }
    }
}