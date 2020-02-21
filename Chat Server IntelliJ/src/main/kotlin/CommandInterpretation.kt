object CommandInterpretation {

    fun executeCommand(
        chatMessage: ChatMessage
    ): String? {
        println("${chatMessage.user} : ${chatMessage.message} || ${chatMessage.createdDateTime}")

        when (chatMessage.command) {
            "LIST USERS" -> return Users.toString()
            "LIST HISTORY" -> {
                return ChatHistory.toString()
            }
            "LIST TOP" -> {
                return TopChatter.printTopChatters()
            }
            "CHANGE USERNAME" -> return "CHANGE USERNAME"
            "EXIT" -> return "EXIT"

            else -> ChatHistory.insert(chatMessage)
        }

        return null
    }
}
