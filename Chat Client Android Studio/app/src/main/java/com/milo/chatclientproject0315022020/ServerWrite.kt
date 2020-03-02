package com.milo.chatclientproject0315022020

import com.milo.chatclientproject0315022020.MainActivity.Companion.writer
import kotlinx.serialization.json.Json
import java.nio.charset.Charset

/**
 * Reads user input and sends it to server to be handled.
 *
 * @author Michael Lock
 * @date 24.02.2020
 */

class ServerWrite(private var message: String) : Runnable {
    override fun run() {
        write(message)
        writer.flush()
    }

    private fun write(message: String) {
        val messageAsJson =
            Json.stringify(
                ChatMessage.serializer(),
                ChatMessage(message, "userName", message, "createdDateAtTime")
            )

        writer.write((messageAsJson + '\n').toByteArray(Charset.defaultCharset()))
    }
}