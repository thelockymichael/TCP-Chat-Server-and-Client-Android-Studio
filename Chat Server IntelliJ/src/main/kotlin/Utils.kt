import java.text.SimpleDateFormat
import java.util.*

/**
 * Class for utility functions.
 *
 * @author Michael Lock
 * @date 11.02.2020
 */

object Utils {

    fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm:ss dd-MM-yyyy").format(Date())
    }
}