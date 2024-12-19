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

    /**
     * leetCode 55. 跳跃游戏(middle)
     * https://leetcode.cn/problems/jump-game/description/
     */
    fun canJump(nums: IntArray): Boolean {
        var ans = 0
        for (index in nums.indices) {
            val item = nums[index]
            if (ans < index) {
                //不在当前最大的跳跃范围内
                //注定无法跳到这里
                return false
            }
            //获取最大跳跃范围
            ans = max(ans, item + index)
        }
        return true
    }

    /**
     * leetCode 45. 跳跃游戏 II(middle)
     * https://leetcode.cn/problems/jump-game-ii/description/
     */
    fun jump(nums: IntArray): Int {
        var ans = 0 //跳跃次数
        var mx = 0 //当前最大跳跃距离
        var last = 0 //记录上一次跳跃到的位置
        repeat(nums.size) {
            //获取当前最大跳跃距离
            mx = max(mx, it + nums[it])
            if (last == it) {
                //当前就是上次最大跳跃距离
                ans++
                last = mx
            }
        }
        return ans
    }

    /**
     * leetCode 179. 最大数(middle)
     * https://leetcode.cn/problems/largest-number/description/
     */
    fun largestNumber(nums: IntArray): String {
        return nums.sortedWith { a, b ->
            //对比前后数字组装在一起的大小,例如 a = 1,b = 9,看下 19 和 91,哪个组装更大
            "${b}${a}".compareTo("${a}${b}")
        }.joinToString("") //把 list 组装为 String
            .let {
            // 如果首位是0，代表后面全是0
            if (it[0] == '0') "0" else it
        }
    }
}

fun main() {
    val greedy = Greedy()
    println(greedy.largestNumber(intArrayOf(10,2)))
    println(greedy.largestNumber(intArrayOf(3,30,34,5,9)))

}