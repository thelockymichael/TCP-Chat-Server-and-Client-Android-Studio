import kotlinx.serialization.json.Json
import java.io.OutputStream
import java.net.Socket
import java.nio.charset.Charset
import java.util.*

/**
 * ChatConnector consist of interface implementation and important class related functions.
 *
 * @author Michael Lock
 * @date 10.02.2020
 */


open class ChatConnector(client: Socket) : Runnable, Observer {
    private val client: Socket = client
    private val reader: Scanner = Scanner(client.getInputStream())
    private val writer: OutputStream = client.getOutputStream()
    private var running = false
    private var userName = ""

    private fun readUserInput(): ChatMessage {
        val input = reader.nextLine().replace("userName", userName).replace("createdDateAtTime", Utils.getCurrentTime())
        return Json.parse(ChatMessage.serializer(), input)
    }

    override fun run() {
        running = true

        // Welcome message // FIRST THING USER SEES.
        write("Welcome to server!", "Server")

        println("Welcome to server!")

        write("Choose username", "Server")

        // Loops until client inputs valid username.
        while (true) {
            try {
                val newMessage = readUserInput()
                // Validates username and it's not EMPTY
                if (Users.checkUsername(newMessage.message) && newMessage.message.trim().isNotEmpty()) {
                    ChatHistory.registerObserver(this)
                    userName = newMessage.message // SETS username of for THIS ChatConnector.

                    write("Username accepted. Welcome aboard!", "Server")
                    println("Server Username accepted. Welcome aboard! ${Utils.getCurrentTime()}")
                    println("Server Username: $userName ${Utils.getCurrentTime()}")
                    break
                }

                write("Username already taken or contains illegal characters. Try again.", "Server")
                println("Server Username already taken or contains illegal characters. Try again. ${Utils.getCurrentTime()}")

            } catch (e: java.lang.Exception) {
                shutdown() // Closes connection with client.
                e.printStackTrace()
                break
            }
        }


        while (running) {
            try {
                // Deserialize user message.
                val newMessage = readUserInput()
                // Checks that user message contains something.
                if (!newMessage.message.isBlank()) {
                    val interpretedInput = CommandInterpretation.executeCommand(newMessage)
                    if (interpretedInput != null) { // Checks for returnable value from CommandInterpretation object
                        when (interpretedInput) {
                            "EXIT" -> shutdown()
                            "CHANGE USERNAME" -> {

                                write("Choose new username.", "Server")

                                val newUsernameInput = readUserInput()
                                if (Users.changeUsername(
                                        userName,
                                        newUsernameInput.message
                                    )
                                ) {
                                    userName = newUsernameInput.message
                                    write("Username changed successfully.", "Server")
                                } else write("Username already exists.", "Server")
                            }
                            else -> {
                                ChatHistory.insert(newMessage)
                                write(CommandInterpretation.executeCommand(newMessage), userName)
                                println(newMessage.message)
                            }
                        }
                    }
                } else
                    write("Please type in something.", "Server")

            } catch (e: Exception) {
                println("Virhe.")
                shutdown() // Closes client connection.
            }
        }
    }

    // Sends USER or SERVER messages.
    private fun write(message: String?, user: String) {

        writer.write(
            (Json.stringify(
                ChatMessage.serializer(),
                ChatMessage(message!!, user, message, Utils.getCurrentTime())
            ) + "\n").toByteArray(Charset.defaultCharset())
        )

    }


    private fun shutdown() {
        running = false
        client.close()
        reader.close()
        writer.close()
        ChatHistory.deregisterObserver(this)
        Users.removeUsername(userName)
        println("${client.inetAddress.hostAddress} closed the connection ${Utils.getCurrentTime()}")
    }

    /*
    * newMessage method is called everytime a new message is added to ChatHistory.
    * */
    override fun newMessage(message: ChatMessage) {
        write(message.message, message.user)
    }
}