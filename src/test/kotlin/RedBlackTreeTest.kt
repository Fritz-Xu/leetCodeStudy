import interview.tree.RedBlackTree
import java.util.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class RedBlackTreeTest {



    @Test
    fun testAddAndRemove() {
        val random = Random() //test 10 times, make sure all the operation is correct.
        for (i in 0..1000) {
            val tree = RedBlackTree<Int>()
            val list = mutableListOf<Int>()
            for (i1 in 0 until 100) {
                var value = random.nextInt(100)
                while (list.contains(value)) {
                    value = random.nextInt(100)
                }
                list.add(value)
                print("$value ,")
                tree.insert(value)
            } //For trace the test data.
            val currentList2 = mutableListOf<Int>()
            currentList2.addAll(list)
            try {
                while (list.isNotEmpty()) {
                    val value = list.removeFirst()
                    tree.delete(value)
                }
            } catch (e: Throwable) {
                println("currentQueue:$currentList2")
                throw e
            }
            Assertions.assertEquals("[]", tree.toHeapString())
        }
    }
}