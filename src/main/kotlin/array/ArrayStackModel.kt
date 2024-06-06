package array

import java.util.*

/**
 * 主要是解决单调栈的问题
 */
class ArrayStackModel {

    /**
     * 单调栈的模板题,核心是从后到前扫描元素,倒着入栈,正着出栈
     */
    fun nextGreaterElement(nums: IntArray): IntArray {
        val ans = IntArray(nums.size)
        val stack = Stack<Int>()
        //倒序
        for (index in nums.size - 1 downTo 0) {
            val item = nums[index]
            //判断个子高矮
            while (stack.isNotEmpty() && stack.peek() <= item) {
                //矮子出栈
                stack.pop()
            }
            ans[index] = if (stack.isEmpty()) -1 else stack.peek()
            stack.push(item)
        }
        return ans
    }

    /**
     * leetCode 739. 每日温度(middle)
     * https://leetcode.cn/problems/daily-temperatures/description/
     */
    fun dailyTemperatures(temperatures: IntArray): IntArray {
        if (temperatures.size == 1) {
            return intArrayOf(0)
        }
        if (temperatures.size == 2) {
            return intArrayOf(if (temperatures[0] < temperatures[1]) 1 else 0, 0)
        }
        val stack = LinkedList<Int>()
        val ans = IntArray(temperatures.size)
        if (temperatures.isNotEmpty()) {
            //倒序
            for (index in temperatures.size - 1 downTo 0) {
                val item = temperatures[index]
                while (stack.isNotEmpty() && temperatures[stack.last] <= item) {
                    stack.removeLast()
                }
                ans[index] = if (stack.isNotEmpty()) stack.last - index else 0
                stack.add(index)
            }
        }
        return ans
    }

    /**
     * leetCode 496. 下一个更大元素 I
     * https://leetcode.cn/problems/next-greater-element-i/description/
     */
    fun nextGreaterElement(nums1: IntArray, nums2: IntArray): IntArray {
        val ans = IntArray(nums1.size)
        // 使用 map 记录下标
        val map = mutableMapOf<Int, Int>()
        for (index in nums1.indices) {
            map[nums1[index]] = index
        }
        val stack = LinkedList<Int>()
        for (index in nums2.indices) {
            val item = nums2[index]
            while (stack.isNotEmpty()
                && map[stack.peek()] != null
                && item > stack.peek()
            ) {
                // 记录结果
                ans[map[stack.peek()]!!] = item
                stack.pop()
            }
            // 在nums1中才入栈处理
            if (map.contains(item)) {
                stack.push(item)
            }
        }
        return ans
    }

    /**
     * leetCode 496. 下一个更大元素 II
     * https://leetcode.cn/problems/next-greater-element-ii/
     */
    fun nextGreaterElements(nums: IntArray): IntArray {
        val ans = IntArray(nums.size)
        val stack = LinkedList<Int>()
        for (i in nums.size * 2 - 1 downTo 0) {
            val index = i % nums.size
            val item = nums[index]
            while (stack.isNotEmpty() && stack.peek() <= item) {
                stack.pop()
            }
            ans[index] = if (stack.isEmpty()) -1 else stack.peek()
            stack.push(item)
        }
        return ans
    }


}

fun main() {
    val item = ArrayStackModel()
    println(item.nextGreaterElement(intArrayOf(4, 1, 2), intArrayOf(1, 3, 4, 2)))
}

