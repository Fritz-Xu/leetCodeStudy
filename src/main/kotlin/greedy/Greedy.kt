package greedy

import java.util.*
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

    /**
     * leetCode 397. 整数替换(middle)
     */
    fun integerReplacement(n: Int): Int {
        var ans = 0
        var result = n
        while (result != 1) {
            if ((result and 1) == 0) {
                result = result ushr 1
            } else if (result != 3 && (result and 3) == 3) {
                ++result
            } else {
                --result
            }
            ++ans
        }
        return ans
    }

    /**
     *  leetCode 134. 加油站(middle)
     * https://leetcode.cn/problems/gas-station/
     * 配合灵神的分析
     * https://leetcode.cn/problems/gas-station/solutions/2933132/yong-zhe-xian-tu-zhi-guan-li-jie-pythonj-qccr/
     */
    fun canCompleteCircuit(gas: IntArray, cost: IntArray): Int {
        var ans = 0
        var minS = 0//最小油量
        var s = 0 //当前油量
        repeat(gas.size) {
            // 在 i 处加油，然后车子开到了 i+1 是消耗
            s += gas[it] - cost[it]
            if (s < minS) {
                //此时 s 为负数
                minS = s
                ans = it + 1 //那么 it + 1 就是下一个站
            }
        }
        // 循环结束后，s 即为 gas 之和减去 cost 之和
        return if (s < 0) -1 else ans
    }

    /**
     * leetCode 553.最优除法(middle)
     * https://leetcode.cn/problems/optimal-division/description/
     * 提示:数组第一个元素固定为分子,第二个元素固定为分母,那么最大值就是让分母只有一个元素
     * 例如数组为[1,2,3,4],那么最优除法最大值就是(1*3*4)/2,也就是1/(2/3/4)
     */
    fun optimalDivision(nums: IntArray): String {
        val size = nums.size
        val builder = StringBuilder()
        builder.append(nums[0])
        for (index in 1..<size) {
            if (index == 1 && size > 2) {
                builder.append("/(").append(nums[index])
            } else {
                builder.append("/").append(nums[index])
            }
        }
        return if (nums.size > 2) builder.append(")").toString() else builder.toString()
    }

    /**
     * leetCode 561. 数组拆分(easy)
     * https://leetcode.cn/problems/array-partition/description/
     */
    fun arrayPairSum(nums: IntArray): Int {
        nums.sort()
        var count = 0
        var ans = 0
        while (count < nums.size) {
            ans += nums[count]
            count += 2
        }
        return ans
    }

    /**
     * leetCode 575. 分糖果(easy)
     * https://leetcode.cn/problems/distribute-candies/description/
     */
    fun distributeCandies(candyType: IntArray): Int {
        //先判断有多少种类的糖果,然后对比糖果种类和可以吃的糖果数量
        val ans = mutableSetOf<Int>()
        candyType.forEach {
            ans.add(it)
        }
        return min(ans.size, candyType.size / 2)
    }

    /**
     * 630. 课程表 III
     *https://leetcode.cn/problems/course-schedule-iii/description/
     */
    fun scheduleCourse(courses: Array<IntArray>): Int {
        // 按课程结束时间升序排列, 首先考虑当前最先结束的课程
        Arrays.sort(courses) { a: IntArray, b: IntArray ->
            a[1] - b[1]
        }
        //大根堆,将课程持续时间最长的课放在堆顶
        val ans = PriorityQueue { a: Int, b: Int -> b - a }
        var sum = 0
        //从课程结束时间最早开始加入堆中,如果碰到总课程时间大于课程结束时间
        //就把当前队列中最耗时的课程给弹出队列,借此贪心保证学习的课程最多
        for (item in courses) {
            val d = item[0]
            val e = item[1]
            sum += d
            ans.add(d)
            if (sum > e) sum -= ans.poll()
        }
        return ans.size
    }

    /**
     * 646. 最长数对链(middle)
     * https://leetcode.cn/problems/maximum-length-of-pair-chain/description/
     * 排序 + 贪心
     */
    fun findLongestChain(pairs: Array<IntArray>): Int {
        //将pairs二维数组按照数对中后一个数进行排序
        Arrays.sort(pairs) { a: IntArray, b: IntArray ->
            if (a[1] == b[1]) a[0] - b[0] else a[1] - b[1]
        }
        var ans = 1
        var last = pairs[0][1]
        for (index in 1 until pairs.size) {
            val cur = pairs[index]
            if (cur[0] > last) {
                ans++
                last = cur[1]
            }
        }
        return ans
    }
}

fun main() {
    val greedy = Greedy()
    println(greedy.optimalDivision(intArrayOf(1000, 100, 10, 2)))  // 1000/(100/10/2)
    println(greedy.optimalDivision(intArrayOf(2, 3, 4)))  // 2/(3/4)
    println(greedy.optimalDivision(intArrayOf(1, 2, 3, 4)))

}