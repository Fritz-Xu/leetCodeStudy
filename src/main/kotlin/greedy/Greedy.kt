package greedy

import kotlin.math.max
import kotlin.math.min

class Greedy {

    fun maxArea(height: IntArray): Int {
        var cap = 0
        var start = 0
        var end = height.size - 1
        while (start < end) {
            //容量的计算公式
            val result = min(height[start], height[end]) * (end - start)
            cap = max(cap, result)
            if (height[start] < height[end]) {
                start++
            } else {
                end--
            }
        }
        return cap
    }
}