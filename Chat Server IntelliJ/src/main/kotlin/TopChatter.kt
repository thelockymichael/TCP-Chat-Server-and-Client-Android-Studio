/**
 * TopChatter singleton
 *
 * Methods for printing top chatters and mapping messages by user key.
 *
 * @author Michael Lock
 * @date 10.02.2020
 */

object TopChatter : ChatHistoryObserver {
    private val chatMessages = HashMap<String, Int>()

    init {
        println("Top chatter registered.")
        ChatHistory.registerObserver(this)
    }

    fun printTopChatters(): String {
        var topChattersString = ""
        val result = chatMessages.toList().sortedByDescending { (_, value) -> value }.toMap()

        var number = 1
        for ((key, value) in result) {
            if (number <= 4) {
                topChattersString += "Server ${key}, messages: $value, ${number}.\n"
                number++
            }
        }
        return "---- TOP CHATTERS ---- \n $topChattersString"
    }

    override fun newMessage(message: ChatMessage) {
        if (chatMessages.containsKey(message.user)) {
            chatMessages.computeIfPresent(message.user) { _, v -> v + 1 } // Increases user message amount by 1
        } else {
            chatMessages[message.user] = 1
        }
    }
}