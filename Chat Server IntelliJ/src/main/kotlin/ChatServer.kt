import java.net.ServerSocket
import kotlin.concurrent.thread

/**
 * Creates ServerSocket and instantiates ChatConsole and TopChatter
 * to be registered as observers.
 * Each time a client connects a new ChatConnector object is instantiated.
 *
 * @author Michael Lock
 * @date 10.02.2020
 */

class ChatServer {

    open fun server() {

        ChatConsole
        TopChatter
        try {
            val server = ServerSocket(9999)

            while (true) {
                val client = server.accept()
                println("Client connected " + client.inetAddress.hostAddress + " " + client.port)

                thread { ChatConnector(client).run() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}