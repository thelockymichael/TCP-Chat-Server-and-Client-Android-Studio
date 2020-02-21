/**
 * Interface methods for Observable pattern.
 *
 * @author Michael Lock
 * @date 10.02.2020
 */

interface ChatHistoryObservable {
    fun registerObserver(observer: ChatHistoryObserver)
    fun deregisterObserver(observer: ChatHistoryObserver)
    fun notifyObservers(message: ChatMessage)
}
