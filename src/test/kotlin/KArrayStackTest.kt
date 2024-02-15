import interview.KArrayStack
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


internal class KArrayStackTest {

    @Test
    fun push() {
        val stack = KArrayStack<String>()
        repeat(10) {
            stack.push(it.toString())
        }
        var count = 0
        stack.forEach {
            Assertions.assertEquals(count.toString(), it)
            count++
        }
    }

    @Test
    fun peek() {
        val stack = KArrayStack<String>()
        repeat(4) {
            stack.push(it.toString())
        }
        Assertions.assertEquals(stack.peek(), "3")
    }

    @Test
    fun pop() {
        val stack = KArrayStack<String>()
        repeat(20) {
            stack.push(it.toString())
        }
        val count = stack.size
        repeat(count) {
            Assertions.assertEquals(stack.pop(), (count - it - 1).toString())
        }
    }


}