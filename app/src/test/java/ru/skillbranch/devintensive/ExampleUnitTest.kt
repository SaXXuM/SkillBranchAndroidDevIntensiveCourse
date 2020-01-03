package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.*
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.Chat
import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_instance() {
        val user = User("1")
    }

    @Test
    fun test_factory() {
        val user = User.makeUser("John Wick")
        val user2 = user.copy("2", lastVisit = Date().add(1, TimeUnits.DAY))
        println(user2)
    }

    @Test
    fun test_abstract_factory() {
        val user = User.makeUser("John Wick")
        val txtMessage = BaseMessage.makeMessage(user, Chat("0"), type = "text", payload = "any text message")
        val imgMessage = BaseMessage.makeMessage(user, Chat("0"), type = "image", payload = "any image message")
        println(txtMessage.formatMessage())
        println(imgMessage.formatMessage())
    }

    @Test
    fun test_parseFullName() {
        assertEquals(null to null, Utils.parseFullName(null))
        assertEquals(null to null, Utils.parseFullName(""))
        assertEquals(null to null, Utils.parseFullName(" "))
        assertEquals("John" to null, Utils.parseFullName("John"))
    }

    @Test
    fun test_dateFormat() {
        val date = Date(2019, 5, 27, 14, 0, 0)
        assertEquals("14:00:00 27.06.19", date.format())
        assertEquals("14:00", date.format("HH:mm"))
    }

    @Test
    fun test_dateAdd() {
        val date = Date(2019, 5, 27, 14, 0, 0)
        assertEquals("14:00:02 27.06.19", date.add(2, TimeUnits.SECOND).format())
        assertEquals("14:00:02 23.06.19", date.add(-4, TimeUnits.DAY).format())
    }

    @Test
    fun test_toInitials() {
        assertEquals("JD", Utils.toInitials("john" ,"doe"))
        assertEquals("J", Utils.toInitials("John", null))
        assertEquals(null, Utils.toInitials(null, null))
        assertEquals(null, Utils.toInitials(" ", ""))
    }

    @Test
    fun test_transliteration() {
        assertEquals("Zhenya Stereotipov", Utils.transliteration("Женя Стереотипов", " "))
        assertEquals("Amazing_Petr", Utils.transliteration("Amazing Петр","_"))
    }

    @Test
    fun test_humanizeDiff() {
        assertEquals("только что", Date().add(0, TimeUnits.SECOND).humanizeDiff())
        assertEquals("несколько секунд назад", Date().add(-1, TimeUnits.SECOND).humanizeDiff())
        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff())
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через 7 дней", Date().add(7, TimeUnits.DAY).humanizeDiff())
        assertEquals("более года назад", Date().add(-400, TimeUnits.DAY).humanizeDiff())
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff())
    }

    @Test
    fun test_builder() {
        val now = Date()
        val user1 = User("1", "Zhenya", "Stereotipov", "ava", 5, 1,
            now, false)
        val user2 = User.Builder().id("1")
            .firstName("Zhenya")
            .lastName("Stereotipov")
            .avatar("ava")
            .rating(5)
            .respect(1)
            .lastVisit(now)
            .isOnline(false)
            .build()

        assertEquals(user1, user2)
    }

    @Test
    fun test_truncate() {
        assertEquals("Bender Bending R...", "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate())
        assertEquals("Bender Bending...", "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(15))
        assertEquals("A", "A     ".truncate(3))
    }

    @Test
    fun test_stripHtml() {
        assertEquals("Образовательное IT-сообщество Skill Branch", "<p class=\"title\">Образовательное IT-сообщество Skill Branch</p>".stripHtml())
        assertEquals("Образовательное IT-сообщество Skill Branch", "<p>Образовательное       IT-сообщество Skill Branch</p>".stripHtml())
    }
}
