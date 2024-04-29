import com.sopt.now.feature.util.DateKeyStorage
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {
    val today: LocalDate = LocalDate.now()
    val yesterday: LocalDate = today.minusDays(1)

    fun getDateOrder(inputDate: LocalDate): Int = when (inputDate) {
        today -> DateKeyStorage.TODAY
        yesterday -> DateKeyStorage.YESTERDAY
        else -> DateKeyStorage.NOTHING
    }

    fun getDateString(inputDate: LocalDate): String {
        val dateFormatKorean: DateTimeFormatter = DateTimeFormatter.ofPattern("M월 d일")
        return when (inputDate) {
            today -> "오늘 ${inputDate.format(dateFormatKorean)}"
            yesterday -> "어제 ${inputDate.format(dateFormatKorean)}"
            else -> ""
        }
    }
}
