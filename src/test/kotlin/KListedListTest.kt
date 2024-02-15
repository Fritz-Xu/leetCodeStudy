import interview.KLinkedList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


internal class KListedListTest {

    private fun <T> KListOf(vararg items: T): KLinkedList<T> {
        return KLinkedList<T>().also {
            it.addAll(*items)
        }
    }

    @Test
    fun addIndex() {
        val list = KListOf("1", "2", "5", "6")
        list.add(0, "0")
        list.add(3, "3")
        list.add(4, "4")
        list.add(list.size - 1, "7")
        var index = 0
        list.foreach { data ->
            Assertions.assertEquals(data, index.toString())
            index++
        }
    }

    @Test
    fun add() {
        val list = KListOf("1", "2", "3", "4")
        list.addFirst("0")
        list.addLast("5")
        var index = 0
        list.foreach { data ->
            Assertions.assertEquals(data, index.toString())
            index++
        }
    }

    @Test
    fun removeFirstAndLast() {
        val list = KListOf("3", "1", "2", "3", "4", "4")
        list.removeFirst()
        list.removeLast()
        var index = 0
        list.foreach { data ->
            Assertions.assertEquals(data, (index + 1).toString())
            index++
        }
    }

    @Test
    fun removeItem() {
        val list = KListOf("3", "3", "2", "3", "3", "3")
        list.remove("2")
        list.foreach { data ->
            Assertions.assertEquals(data, "3")
        }
    }

    @Test
    fun removeIndex() {
        val list = KListOf("3", "3", "2", "3", "3", "3")
        list.removeAt(2)
        list.foreach { data ->
            Assertions.assertEquals(data, "3")
        }
    }

    @Test
    fun setItem() {
        val list = KListOf("0", "0", "2", "3")
        list.set(1, "1")
        var index = 0
        list.foreach { data ->
            Assertions.assertEquals(data, index.toString())
            index++
        }
    }

    @Test
    fun indexOf() {
        val list = KListOf("0", "1", "2", "3", "4", "5")
        Assertions.assertEquals(list.indexOf("2"), 2)
    }
}