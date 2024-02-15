import interview.KArrayDeque
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


internal class KArrayDequeTest {

    private fun <T> KListOf(vararg items: T): KArrayDeque<T> {
        return KArrayDeque<T>().also {
            it.addAll(*items)
        }
    }

    @Test
    fun push() {
        val queue = KListOf("2", "3", "4", "5")
        queue.pushFirst("1")
        queue.pushFirst("0")
        queue.pushLast("6")
        queue.pushLast("7")
        queue.pushLast("8")
        queue.pushLast("9")
        var count = 0
        queue.forEach {
            Assertions.assertEquals(count.toString(), it)
            count++
        }
    }

    @Test
    fun poll() {
        val queue = KListOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        //出列
        Assertions.assertEquals("0",  queue.pollFirst())
        Assertions.assertEquals("9",  queue.pollLast())
        Assertions.assertEquals("1",  queue.pollFirst())
        Assertions.assertEquals("8",  queue.pollLast())
    }


}