package com.milo.chatclientproject0315022020

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.serialization.json.Json
import java.io.OutputStream
import java.net.Socket
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var thread1: Thread
        lateinit var thread2: Thread
        lateinit var thread3: Thread

        internal const val address = "192.168.105.13"
        internal const val port = 9999
        lateinit var socket: Socket
        lateinit var writer: OutputStream
        lateinit var reader: Scanner
        lateinit var adapter: ChatMessageAdapter
        lateinit var chatMessageArrayList: ArrayList<ChatMessage>

        var connected = false

        fun shutdown() {

            socket.close()
            writer.close()
            reader.close()
            thread1.interrupt()
            thread2.interrupt()
            chatMessageArrayList.clear()
            adapter.notifyDataSetChanged()
        }

        fun getCurrentTime(): String {
            return SimpleDateFormat("HH:mm:ss dd-MM-yyyy").format(Date())
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        chatMessageArrayList = ArrayList()
        adapter = ChatMessageAdapter(this, chatMessageArrayList)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter

        connectButton.setOnClickListener {
            connected = !connected
            if (connected) {
                connectButton.text = "DISCONNECT"
                tvMessage.text = "Connected"
                thread1 = Thread(Thread1(this))
                thread1.start()
            } else {
                connectButton.text = "CONNECT"
                tvMessage.text = "Disconnected"
                shutdown()
                Log.i("SERVER", "INTERRUPTED")
            }
        }

        sendButton.setOnClickListener {
            val message = editText.text.toString().trim()
            if (message.isNotEmpty()) {
                Thread(Thread3(message)).start()
            }
        }
    }

    class Thread2(private var context: Activity) : Runnable {
        override fun run() {

            while (true) {
                try {
                    if (thread2.isInterrupted) {
                        // We've been interrupted: no more crunching.
                        Log.i("SERVER", "WE'VE BEEN INTERRUPTED")
                        break
                    }
                    val message: String = reader.nextLine()
                    if (message != null) {

                        if (thread2.isInterrupted) {
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
                        Thread(Thread1(context)).start()
                        return
                    }
                } catch (e: Exception) {
                    shutdown()
                    e.printStackTrace()
                }
            }
        }
    }

    class Thread1(private var context: Context) : Runnable {
        override fun run() {
            Log.i("SERVER", "Thread1 above runOnUiThread")
            val newContext = context as Activity

            try {
                Log.i("SERVER", "IS CONNECTING?")
                socket = Socket(address, port)

                writer = socket.getOutputStream()
                reader = Scanner(socket.getInputStream())

/*                newContext.runOnUiThread {
                    newContext.tvMessage.text = "Connected \n"
                }*/
                thread2 = Thread(Thread2(newContext))
                thread2.start()
            } catch (e: java.lang.Exception) {
                /*if (!socket.isBound) {
                    Log.i("SERVER", "ISN'T CONNECTED.")
                }
            } catch (e: Exception) {
                newContext.tvMessage.text = "Could not connect :( \n"
                Log.i("SERVER", "COULD NOT CONNECT")

                AlertDialog.Builder(newContext)
                    .setIcon(android.R.drawable.stat_notify_error)
                    .setTitle("Connection error.")
                    .setMessage("Could not connect to server. Have you got the right IP address?")
                    .setPositiveButton(
                        "Try again",
                        { dialog, which ->
                            Thread(Thread1(newContext)).start()
                        })
                    .setNegativeButton("OK", null)
                    .show()*/
                shutdown()
                newContext.tvMessage.text = "Connected \n"

                e.printStackTrace()
            }
        }
    }

    class Thread3(private var message: String) : Runnable {
        override fun run() {
            write(message)
            writer.flush()
        }

        private fun write(message: String) {
            val messageAsJson =
                Json.stringify(
                    ChatMessage.serializer(),
                    ChatMessage(message, "userName", message, getCurrentTime())
                )


            writer.write((messageAsJson + '\n').toByteArray(Charset.defaultCharset()))
        }
    }
}
