import interview.KArrayList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


internal class KArrayListTest {

    private fun <T> KListOf(vararg items: T): KArrayList<T> {
        return KArrayList<T>(items.size).also {
            it.addAll(*items)
        }
    }

    @Test
    fun add() {
        val list = KListOf(1, 2, 3, 4)
        listOf(5, 6, 7, 8).forEach {
            list.add(it)
        }
        var index = 1
        list.forEach { item ->
            Assertions.assertEquals(index++, item)
        }
    }

    @Test
    fun addIndex() {
        val list = KListOf(1, 2, 3, 4)
        list.add(1, 6)
        var index = 0
        list.forEach { item ->
            if (index == 1) {
                Assertions.assertEquals(6, item)
            }
            if (index == 4) {
                Assertions.assertEquals(4, item)
            }
            index++
        }
    }

    @Test
    fun remove() {
        val list = KListOf("1", "2", "4", "3", "7")
        list.remove(2)
        list.remove("7")
        print("list size is ${list.size}")
        Assertions.assertEquals(list.size, 3)
        var index = 1
        list.forEach { item ->
            Assertions.assertEquals((index++).toString(), item)
        }
    }

    @Test
    fun getAndSet() {
        val list = KListOf("0", "1", "2", "3", "4")
        repeat(list.size) { index ->
            Assertions.assertEquals((index).toString(), list.get(index))
        }
        list.set(2, "000")
        Assertions.assertEquals("000", list.get(2))

    }

    @Test
    fun indexOf() {
        val list = KListOf("0", "1", "2", "3", "4")
        Assertions.assertEquals(1, list.indexOf("1"))

    }
}