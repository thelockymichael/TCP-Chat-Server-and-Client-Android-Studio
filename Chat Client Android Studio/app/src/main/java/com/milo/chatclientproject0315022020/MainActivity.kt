package com.milo.chatclientproject0315022020

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
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
 * Connects all the seperate classes together
 *
 * @author Michael Lock
 * @date 24.02.2020
 * */

class MainActivity : AppCompatActivity(), View.OnClickListener {

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

        internal var address = "192.168.1.4"
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

        fun getCurrentTime(): String {
            return SimpleDateFormat("HH:mm:ss dd-MM-yyyy").format(Date())
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        backgroundLayout.setOnClickListener(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        chatMessageArrayList = ArrayList()
        adapter = ChatMessageAdapter(this, chatMessageArrayList)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter

        connectButton.setOnClickListener {
            connected = !connected
            if (connected) {
                address = ipAddressEditText.text.toString()
                port = Integer.parseInt(portEditText.text.toString())

                serverConnect = Thread(ServerConnect(this))
                serverConnect.start()
            } else {
                connectButton.text = "CONNECT"
                tvMessage.text = "Disconnected"
                shutdown()
                recyclerView.visibility = View.GONE
                connectToServerLayout.visibility = View.VISIBLE
                editText.visibility = View.GONE
                sendButton.visibility = View.GONE
                Log.i("SERVER", "INTERRUPTED")
            }
        }

        sendButton.setOnClickListener {
            val message = editText.text.toString().trim()
            if (message.isNotEmpty()) {
                Thread(ServerWrite(message)).start()
                editText.setText("")
            }
        }
    }
}