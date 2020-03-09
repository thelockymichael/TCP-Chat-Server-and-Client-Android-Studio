package com.milo.chatclientproject0315022020

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
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

/**
 * Connects all classes together
 *
 * @author Michael Lock
 * @date 24.02.2020
 * */

open class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View) {
        if (v.id == R.id.backgroundLayout || v.id == R.id.recyclerView) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    companion object {
        lateinit var serverConnect: Thread
        lateinit var serverFeed: Thread

        internal var address = ""
            private set
        internal var port = 9999
            private set
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
            serverConnect.interrupt()
            serverFeed.interrupt()
            chatMessageArrayList.clear()
            adapter.notifyDataSetChanged()
        }

        fun connectToServerUI(context: Activity) {
            context.runOnUiThread {
                context.progressBar.visibility = View.VISIBLE
                context.window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            }
        }

        fun disconnectFromServer(context: Activity) {
            context.runOnUiThread {
                context.connectButton.text = "DISCONNECT"
                context.tvMessage.text = "Connected"
                connected = true

                // Connection screen
                context.connectToServerLayout.visibility = View.GONE

                // Chat room
                context.recyclerView.visibility = View.VISIBLE
                context.tapCommandButton.visibility = View.VISIBLE
                context.button_chatbox_send.visibility = View.VISIBLE
                context.edittext_chatbox.visibility = View.VISIBLE
            }
        }

        fun connectionError(context: Activity) {
            context.runOnUiThread {
                context.progressBar.visibility = View.GONE
                context.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                connected = false
                // Initialize a new instance of
                AlertDialog.Builder(context)
                    .setTitle("Connection error")
                    .setMessage("Could not connect to server. Have you got the right IP address?")
                    .setPositiveButton("Try again") { _, _ ->
                        Thread(ServerConnect(context)).start()
                    }
                    .setNegativeButton("OK", null)
                    .show()
            }
        }

        fun serverFeed(context: Activity, message: String) {
            context.runOnUiThread {
                context.progressBar.visibility = View.GONE
                context.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                chatMessageArrayList.add(Json.parse(ChatMessage.serializer(), message))

                adapter.notifyDataSetChanged()

                context.recyclerView.layoutManager!!.scrollToPosition(
                    chatMessageArrayList.size - 1
                )
            }
        }
    }

    private fun hideChatLayout() {
        edittext_chatbox.visibility = View.GONE
        button_chatbox_send.visibility = View.GONE
        tapCommandButton.visibility = View.INVISIBLE
        recyclerView.visibility = View.GONE
        button_chatbox_send.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        title = ""
        hideChatLayout()

        backgroundLayout.setOnClickListener(this)
        recyclerView.setOnClickListener(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        chatMessageArrayList = ArrayList()
        adapter = ChatMessageAdapter(this, chatMessageArrayList)
        recyclerView.adapter = adapter

        connectButton.setOnClickListener {
            connected = !connected
            if (connected) {
                connectToServer()
            } else {
                disconnectFromServer()
            }
        }

        button_chatbox_send.setOnClickListener {
            val message = edittext_chatbox.text.toString().trim()
            if (message.isNotEmpty()) {
                Thread(ServerWrite(message)).start()
                edittext_chatbox.setText("")
            }
        }

        tapCommandButton.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.list_users -> {
                        Thread(ServerWrite("LIST USERS")).start()
                        edittext_chatbox.setText("")
                        true
                    }
                    R.id.list_history -> {
                        Thread(ServerWrite("LIST HISTORY")).start()
                        edittext_chatbox.setText("")
                        true
                    }
                    R.id.list_top -> {
                        Thread(ServerWrite("LIST TOP")).start()
                        edittext_chatbox.setText("")
                        true
                    }
                    else -> false
                }
            }

            popupMenu.inflate(R.menu.command_popup_menu)
            popupMenu.show()
        }
    }

    private fun connectToServer() {
        address = ipAddressEditText.text.toString()
        port = Integer.parseInt(portEditText.text.toString())

        serverConnect = Thread(ServerConnect(this))
        serverConnect.start()
    }

    private fun disconnectFromServer() {
        connectButton.text = "CONNECT"
        tvMessage.text = "Disconnected"
        shutdown()

        // Connection screen enabled
        connectToServerLayout.visibility = View.VISIBLE

        // Chat room UI elements disabled
        hideChatLayout()

        Log.i("SERVER", "INTERRUPTED")
    }
}