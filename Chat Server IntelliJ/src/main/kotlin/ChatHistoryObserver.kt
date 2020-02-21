/**
 * ChatHistoryObserver.
 *
 * @author Michael Lock
 * @date 10.02.2020
 */

interface ChatHistoryObserver {

    fun newMessage(message: ChatMessage)
}