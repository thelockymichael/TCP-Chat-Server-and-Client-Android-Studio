/**
 * Each class that implements observer calls newMessage method
 * when Observable method notifyObservers is called.
 *
 * @author Michael Lock
 * @date 10.02.2020
 */

interface Observer {

    fun newMessage(message: ChatMessage)
}