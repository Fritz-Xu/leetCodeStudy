package interview.code

import kotlin.math.max
import kotlin.math.min

/**
 * leetCode 155. 最小栈
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * 实现 MinStack 类:
 *
 * MinStack() 初始化堆栈对象。
 * void push(int val) 将元素val推入堆栈。
 * void pop() 删除堆栈顶部的元素。
 * int top() 获取堆栈顶部的元素。
 * int getMin() 获取堆栈中的最小元素。
 */
class MinStack {

    private var headTop: Node? = null

    /**
     * 入栈
     */
    fun push(data: Int) {
        if (headTop == null) {
            headTop = Node(data, data)
            return
        }
        //min会按照从小到大排序
        val newNode = Node(data, min(data, headTop!!.min))
        newNode.next = headTop
        headTop = newNode
    }

    /**
     * 删除堆栈顶部的元素
     */
    fun pop() {
        if (headTop == null) {
            return
        }
        val value = headTop!!.min
        headTop = headTop!!.next
        headTop?.min = max(value, headTop?.min ?: value)
    }

    /**
     * top() 获取堆栈顶部的元素。
     */
    fun top(): Int {
        return headTop?.data ?: -1
    }

    fun getMin(): Int {
        return headTop?.min ?: -1
    }

    class Node(val data: Int, var min: Int) {
        var next: Node? = null
    }
}

fun main() {
    val stack = MinStack()
    stack.push(-2)
    stack.push(0)
    stack.push(-3)

    println("min:" + stack.getMin() + ",ans is -3")
    stack.pop()
    println("top:" + stack.top() + ",ans is 0")
    println("min:" + stack.getMin() + ",ans is -2")

//    stack.push(2147483646)
//    stack.push(2147483646)
//    stack.push(2147483647)
//
//    println("top:" + stack.top() + ",ans is 2147483647")
//    println("pop:" + stack.pop())
//    println("min:" + stack.getMin() + ",ans is 2147483646")
//    println("pop:" + stack.pop())
//    println("min:" + stack.getMin() + ",ans is 2147483646")
//    println("pop:" + stack.pop())
//    stack.push(2147483647)
//    println("top:" + stack.top() + ",ans is 2147483647")
//    println("min:" + stack.getMin() + ",ans is 2147483647")
//    stack.push(-2147483648)
//    println("pop:" + stack.pop())
//    println("min:" + stack.getMin() + ",ans is -2147483648")
}