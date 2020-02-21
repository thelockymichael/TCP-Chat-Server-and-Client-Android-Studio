/**
 * ChatHistory object contains all the ChatMessages and Observables.
 * When inserting a new ChatMessage all observables' newMessage method is called.
 *
 * @author Michael Lock
 * @date 10.02.2020
 */

object ChatHistory : ChatHistoryObservable {

    private val messages = ArrayList<ChatMessage>()
    private val observables = mutableListOf<ChatHistoryObserver>()

    fun insert(message: ChatMessage) {
        println("Inserting message.")
        messages.add(message)
        notifyObservers(message)
    }

    override fun registerObserver(observer: ChatHistoryObserver) {
        println("Registering observer.")
        observables.add(observer)
    }

    override fun deregisterObserver(observer: ChatHistoryObserver) {
        println("Deregistering observer.")
        observables.remove(observer)
    }

    override fun notifyObservers(message: ChatMessage) {
        println("Notifying observers.")
        observables.forEach { connector -> connector.newMessage(message) }
    }

    override fun toString(): String {
        var listOfMessages = "Server Current message history: ${Utils.getCurrentTime()}\n"

        // Lists all users without a newline that creates an empty list item
        var index = 0
        for (message in messages) {
            index++
            listOfMessages += if (index == messages.size)
                "${message.user} ${message.message} ${message.createdDateTime}"
            else
                "${message.user} ${message.message} ${message.createdDateTime}\n"
        }

        return listOfMessages
    }
}