/**
 * TopChatter singleton
 *
 * Methods for removing, inserting and checking username for its availability.
 * toString() method for returning all the user names.
 *
 * @author Michael Lock
 * @date 10.02.2020
 */

object Users {

    private val userNames = HashSet<String>()

    fun removeUsername(userName: String) {
        userNames.remove(userName)
    }

    private fun insertUserName(userName: String) {
        userNames.add(userName)
    }

    fun checkUsername(newUsername: String): Boolean {
        if (!userNames.contains(newUsername)) {
            insertUserName(newUsername)
            println("Username accepted")
            return true
        }
        println("Username not accepted.")
        return false
    }

    fun changeUsername(oldUsername: String, newUsername: String): Boolean {
        if (!userNames.contains(newUsername)) {
            removeUsername(oldUsername)
            insertUserName(newUsername)
            println("Username changed successfully.")
            return true
        }
        println("Cannot change username.")
        return false
    }

    override fun toString(): String {
        var listOfUsernames = "Server Current users are: ${Utils.getCurrentTime()}\n"

        // Lists all users without a newline that creates an empty list item
        var index = 0
        for (username in userNames) {
            index++
            listOfUsernames += if (index == userNames.size) {
                "Server $username ${Utils.getCurrentTime()}"
            } else {
                "Server $username ${Utils.getCurrentTime()}\n"
            }
            println("Index $index")
        }

        return listOfUsernames
    }
}