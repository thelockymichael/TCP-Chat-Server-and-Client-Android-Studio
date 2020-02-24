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
            //connectButton.text = "DISCONNECT"
            //tvMessage.text = "Connected"
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

                /*val builder = AlertDialog.Builder(newContext)

                // Set the alert dialog title
                builder.setTitle("App background color")

                // Display a message on alert dialog
                builder.setMessage("Are you want to set the app background color to RED?")

                // Set a positive button and its click listener on alert dialog
                builder.setPositiveButton("YES") { dialog, which ->
                    // Do something when user press the positive button
                    Toast.makeText(newContext, "Ok, we change the app background.", Toast.LENGTH_SHORT)
                        .show()

                    // Change the app background color
                    //root_layout.setBackgroundColor(Color.RED)
                }


                // Display a negative button on alert dialog
                builder.setNegativeButton("No") { dialog, which ->
                    Toast.makeText(newContext, "You are not agree.", Toast.LENGTH_SHORT).show()
                }


                // Display a neutral button on alert dialog
                builder.setNeutralButton("Cancel") { _, _ ->
                    Toast.makeText(newContext, "You cancelled the dialog.", Toast.LENGTH_SHORT)
                        .show()
                }

                // Finally, make the alert dialog using builder
                val dialog: AlertDialog = builder.create()

                // Display the alert dialog on app interface
                dialog.show()*/
            }

            //shutdown()
            //newContext.tvMessage.text = "Connected \n"

            e.printStackTrace()
        }
    }
}