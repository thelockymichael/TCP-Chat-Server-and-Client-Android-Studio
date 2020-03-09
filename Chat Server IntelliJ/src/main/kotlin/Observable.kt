/**
 * Used for classes that want to execute the same method for each registered
 * Observer.
 *
 * @author Michael Lock
 * @date 10.02.2020
 */

interface Observable {
    fun registerObserver(observer: Observer)
    fun deregisterObserver(observer: Observer)
    fun notifyObservers(message: ChatMessage)
}
