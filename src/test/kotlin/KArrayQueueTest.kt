import interview.KArrayList
import interview.KArrayQueue
import interview.KLinkedList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


internal class KArrayQueueTest {

    private fun <T> KListOf(vararg items: T): KArrayQueue<T> {
        return KArrayQueue<T>().also {
            it.addAll(*items)
        }
    }

    @Test
    fun offer() {
        val queue = KListOf("0", "1", "2", "3")
        queue.offer("4")
        queue.offer("5")
        var count = 0
        queue.forEach {
            Assertions.assertEquals(count.toString(), it)
            count++
        }
    }

    @Test
    fun peekAndPoll() {
        val queue = KListOf("0", "1", "2", "3", "4", "5", "6", "7")
        //出列
        queue.poll()
        //入列
        queue.offer("8")
        Assertions.assertEquals("1", queue.peekFirst())
        Assertions.assertEquals("8", queue.peekLast())
    }


}