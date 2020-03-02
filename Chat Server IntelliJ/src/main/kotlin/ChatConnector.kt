import kotlinx.serialization.json.Json
import java.io.OutputStream
import java.net.Socket
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*

/**
 * ChatConnector compromises interface and class related functions.
 *
 * @author Michael Lock
 * @date 10.02.2020
 */


open class ChatConnector(client: Socket) : Runnable, ChatHistoryObserver {
    private val client: Socket = client
    private val reader: Scanner = Scanner(client.getInputStream())
    private val writer: OutputStream = client.getOutputStream()
    private var running = false
    private var userName = ""

    private fun readUserInput(): ChatMessage {
        val input = reader.nextLine().replace("userName", userName)
            .replace("createdDateAtTime", Utils.getCurrentTime())

        println("INPUT: $input")
        println("PARSED: ${Json.parse(ChatMessage.serializer(), input)}")
        return Json.parse(ChatMessage.serializer(), input)
    }

    override fun run() {
        running = true

        // Welcome message
        write("Welcome to server!", "Server")

        println("Welcome to server!")

        write("Choose username", "Server")

        while (true) {
            try {
                val newMessage = readUserInput()
                write("Input username: ", "Server")

                // Loops until client inputs valid username
                if (Users.checkUsername(newMessage.message) && newMessage.message.trim().isNotEmpty()) {
                    ChatHistory.registerObserver(this)
                    println("TRIMMED: ${newMessage.message.trim()}")
                    userName = newMessage.message

                    write("Username accepted. Welcome aboard!", "Server")

                    println("Server Username accepted. Welcome aboard! ${Utils.getCurrentTime()}")
                    println("Server Username: $userName ${Utils.getCurrentTime()}")
                    break
                }

                write("Username already taken or contains illegal characters. Try again.", "Server")

                println("Server Username already taken or contains illegal characters. Try again. ${Utils.getCurrentTime()}")

            } catch (e: java.lang.Exception) {
                shutdown()
                e.printStackTrace()
                break
            }
        }


        while (running) {
            try {
                // Deserialize (parse)
                val newMessage = readUserInput()

                if (!newMessage.message.isBlank()) {
                    val interpretedInput = CommandInterpretation.executeCommand(newMessage)
                    if (interpretedInput != null) { // Checks for returnable value from CommandInterpretation object
                        when (interpretedInput) {
                            "EXIT" -> shutdown()
                            "CHANGE USERNAME" -> {
                                //write("Server Choose new username. ${Utils.getCurrentTime()}")

                                write("Choose new username.", "Server")

                                val newUsernameInput = readUserInput()
                                if (Users.changeUsername(
                                        userName,
                                        newUsernameInput.message
                                    )
                                ) {
                                    userName = newUsernameInput.message
                                    write("Username changed successfully.", "Server")
                                    //write("Server Username changed successfully. ${Utils.getCurrentTime()}")
                                } else write("Username already exists.", "Server")
                                //write("Username already exists. ${Utils.getCurrentTime()}")

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
                //write("Server Please input something.")

            } catch (e: Exception) {
                println("Virhe.")
                shutdown()
            }
        }
    }

    private fun write(message: String?, user: String) {

        val json = Json.stringify(ChatMessage.serializer(), ChatMessage(message!!, user, message, Utils.getCurrentTime()))

        println("JSON: " + json)

        writer.write(
            (Json.stringify(
                ChatMessage.serializer(),
                ChatMessage(message!!, user, message, Utils.getCurrentTime())
            ) + "\n").toByteArray(Charset.defaultCharset())
        )

        println(
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

    override fun newMessage(message: ChatMessage) {
        //write("${userName} : ${message.message} || ${message.createdDateTime}")
        println("1st LINE: ${message.user} ${message.message} ${message.createdDateTime}")
        //write("${message.user} ${message.message} ${message.createdDateTime}")
        Json.stringify(
            ChatMessage.serializer(),
            ChatMessage(message.message, message.user, message.message, message.createdDateTime)
        )
        write(message.message, message.user)

        println(
            "JSON STRINGIFY: " +
                    Json.stringify(
                        ChatMessage.serializer(),
                        ChatMessage(message.message, message.user, message.message, message.createdDateTime)
                    )
        )

    }
}