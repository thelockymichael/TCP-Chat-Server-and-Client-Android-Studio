package com.milo.chatclientproject0315022020

import com.milo.chatclientproject0315022020.MainActivity.Companion.writer
import kotlinx.serialization.json.Json
import java.nio.charset.Charset

class ServerWrite(private var message: String) : Runnable {
    override fun run() {
        write(message)
        writer.flush()
    }

    private fun write(message: String) {
        val messageAsJson =
            Json.stringify(
                ChatMessage.serializer(),
                ChatMessage(message, "userName", message, MainActivity.getCurrentTime())
            )

        writer.write((messageAsJson + '\n').toByteArray(Charset.defaultCharset()))
    }
}