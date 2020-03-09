/**
 * Contains methods for printing top chatters, adding new pairs and increasing
 * the key (username) value each time user sends a chat message.
 *
 * @author Michael Lock
 * @date 10.02.2020
 */

object TopChatter : Observer {
    private val chatMessages = HashMap<String, Int>()

    init {
        println("Top chatter registered.")
        ChatHistory.registerObserver(this)
    }

    fun printTopChatters(): String {
        var topChattersString = ""
        val result = chatMessages.toList().sortedByDescending { (_, value) -> value }.toMap()

        // Lists top chatters by username, messages amount and sorts them in ascending order: 1, 2, 3...
        var number = 1
        for ((key, value) in result) {
            if (number <= 4) {
                topChattersString += "${number}. ${key}, message amount: $value.\n"
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