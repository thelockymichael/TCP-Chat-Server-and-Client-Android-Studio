/**
 * ChatConsole registers as observer and prints to server console client input.
 *
 * @author Michael Lock
 * @date 10.02.2020
 */

object ChatConsole : ChatHistoryObserver {

    init {
        ChatHistory.registerObserver(this)
    }

    override fun newMessage(message: ChatMessage) {
        println("${message.user}: ${message.message} || ${message.createdDateTime}")
    }
}