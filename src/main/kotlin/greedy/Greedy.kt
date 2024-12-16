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

    /**
     * leetCode 2335. 装满杯子需要的最短总时长(easy)
     * https://leetcode.cn/problems/minimum-amount-of-time-to-fill-cups/description/
     * 将数组 amount 排序，设 a, b, c 分别为数组 amount 中的三个数，有以下两种情况：
     * 如果 a+b≤c，此时我们只需要 c 次操作即可将所有数变为 0，因此答案为 c。
     * 如果 a+b>c，每一次操作我们都可以将其中两个数减一，最终匹配完，或者剩下最后一个数（取决于总和是偶数还是奇数），因此答案为
     * (a+b+c+1)/2
     */
    fun fillCups(amount: IntArray): Int {
        amount.sort()
        if (amount[0] + amount[1] <= amount[2]) {
            return amount[2]
        }
        return (amount[0] + amount[1] + amount[2] + 1) / 2
    }
}