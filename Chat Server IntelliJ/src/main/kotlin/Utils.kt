import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm:ss dd-MM-yyyy").format(Date())
    }
}