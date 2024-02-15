package interview.code

import java.util.Stack

/**
 * leetCode 232. 用栈实现队列
 *
 * 请仅使用两个栈实现先入先出队列。队列应当支持一般队列支持的所有操作（push、pop、peek、empty）：
 *
 * 实现 MyQueue 类：
 *
 * void push(int x) 将元素 x 推到队列的末尾
 * int pop() 从队列的开头移除并返回元素
 * int peek() 返回队列开头的元素
 * boolean empty() 如果队列为空，返回 true ；否则，返回 false
 * 说明：
 *
 * 只能使用标准的栈操作 —— 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的
 * 你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
 */
class MyQueue {

    /**
     * 队列是先进先出，栈是先进后出
     * 用一个栈的倒序来模拟队列
     */
    private val stack = Stack<Int>()

    /**
     * stack的倒序
     */
    private val reverseStack = Stack<Int>()

    /**
     * 入队
     */
    fun push(data: Int) {
        stack.push(data)
    }

    /**
     * 从队列的开头移除并返回元素
     */
    fun pop(): Int {
        val peek = peek()
        //出栈,这里等同出队了
        reverseStack.pop()
        return peek
    }

    /**
     * 返回队列开头的元素
     */
    fun peek(): Int {
        if (reverseStack.isNotEmpty()) {
            return reverseStack.peek()
        }
        if (stack.isEmpty()) {
            return -1
        }
        while (stack.isNotEmpty()) {
            reverseStack.push(stack.pop())
        }
        return reverseStack.peek()
    }

    fun empty(): Boolean {
        return stack.isEmpty() && reverseStack.isEmpty()
    }

}