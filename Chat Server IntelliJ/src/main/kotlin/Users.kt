/**
 * TopChatter singleton
 *
 * Methods for removing, inserting and checking username for its availability.
 * toString() method for returning all the user names.
 *
 * @author Michael Lock
 * @date 10.02.2020
 */

enum class Colors {
    RED, BLACK, GREEN
}

object Users {

    private val userNames = HashSet<String>()

    fun removeUsername(userName: String) {
        userNames.remove(userName)
    }

    private fun insertUserName(userName: String) {
        userNames.add(userName)
    }

    fun checkUsername(newUsername: String): Boolean {
        if (!userNames.contains(newUsername) && !newUsername.contains(" ")) {
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
        var listOfUsernames = "Current users are: \n"

        // Lists all users without a newline that creates an empty list item
        var index = 0
        for (username in userNames) {
            index++
            listOfUsernames += if (index == userNames.size) {
                "$username"
            } else {
                "$username\n"
            }
            println("Index $index")
        }

        return listOfUsernames
    }
}