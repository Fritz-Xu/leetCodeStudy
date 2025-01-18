package dynamic_programming

import java.util.*
import kotlin.math.max

/**
 * 爬楼梯的 dp
 */
class DynamicStaircaseModel {

    /**
     *  爬楼梯：回溯,细节
     */
    private fun backtrack(choices: List<Int>, state: Int, n: Int, res: MutableList<Int>) {
        //爬到第 n 阶时,方案数量+1
        if (state == n) {
            res[0] = res[0] + 1
        }
        choices.forEach { choice ->
            // 剪枝：不允许越过第 n 阶
            if (state + choice > n) {
                return@forEach
            }
            // 尝试：做出选择，更新状态
            backtrack(choices, state + choice, n, res);
            // 回退
        }
    }

    /**
     *  爬楼梯：回溯
     */
    fun climbingStairsBacktrack(n: Int): Int {
        // 可选择向上爬 1 阶或 2 阶
        val choices: List<Int> = mutableListOf(1, 2)
        // 从第 0 阶开始爬
        val state = 0
        val res: MutableList<Int> = mutableListOf()
        // 使用 res[0] 记录方案数量
        res.add(0)
        backtrack(choices, state, n, res)
        return res[0]
    }

    /**
     *  爬楼梯：暴力搜索
     */
    fun climbingStairsDFS(n: Int): Int {
        return dfs(n)
    }

    /**
     *  爬楼梯：暴力搜索，详细
     *  因为每次只能上 1 或者 2 个的阶梯
     *  因此依据楼梯数量,减1或者减2,总计好全部情况即可
     */
    private fun dfs(num: Int): Int {
        if (num == 1 || num == 2) {
            return num
        }
        val count = dfs(num - 1) + dfs((num - 2))
        return count
    }

    /**
     *  爬楼梯：记忆化搜索
     */
    fun climbingStairsRememberDFS(n: Int): Int {
        val mem = IntArray(n + 1).apply {
            repeat(size) {
                this[it] = -1
            }
        }
        return rememberDfs(n, mem)
    }

    /**
     *  爬楼梯：记忆化搜索，详细
     *  因为每次只能上 1 或者 2 个的阶梯
     *  因此依据楼梯数量,减1或者减2,总计好全部情况即可
     *  同时统计每个情况的计算结果，避免重复计算
     */
    private fun rememberDfs(num: Int, mem: IntArray): Int {
        if (num == 1 || num == 2) {
            return num
        }
        // 若存在记录 dp[num] ，则直接返回之
        if (mem[num] != -1) {
            return mem[num]
        }
        val count = rememberDfs(num - 1, mem) + rememberDfs(num - 2, mem)
        // 记录 dp[num]
        mem[num] = count
        return count
    }

    fun climbingStairsDp(num: Int): Int {
        if (num == 1 || num == 2) {
            return num
        }
        // 初始化 dp 表，用于存储子问题的解
        val dp = IntArray(num + 1)
        // 初始状态：预设最小子问题的解
        dp[1] = 1
        dp[2] = 2
        for (i in 3..<dp.size) {
            // 状态转移：从较小子问题逐步求解较大子问题
            dp[i] = dp[i - 1] + dp[i - 2]
        }
        dp.size
        val memo = IntArray(1)
        // 备忘录初始化为一个不会被取到的特殊值，代表还未被计算
        Arrays.fill(memo, -666)
        return dp[num]
    }

    /**
     * 343. 整数拆分(middle)
     * https://leetcode.cn/problems/integer-break/description/
     */
    fun integerBreak(n: Int): Int {
        val ans = IntArray(n + 1)
        ans[1] = 1
        for (index in 2..n) {
            for (position in 1..<index) {
                // 状态转移方程
                ans[index] = max(ans[index], max(ans[index - position] * position,(index - position) * position))
            }
        }
        return ans[n]
    }
}

fun main() {
    val model = DynamicStaircaseModel()
    println(model.climbingStairsBacktrack(5))
    println(model.climbingStairsRememberDFS(5))
    println(model.climbingStairsDp(5))
}