package dynamic_programming

import java.util.*
import kotlin.math.min


class DynamicRunModel {

    /**
     * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
     * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
     * 你可以认为每种硬币的数量是无限的。
     */
    fun coinChange(coins: IntArray, amount: Int): Int {
        if (amount == 0) return 0
        if (amount < 0) return -1
        val dp = IntArray(amount + 1)
        Arrays.fill(dp, amount + 1)
        dp[0] = 0
        repeat(dp.size) { money ->
            coins.forEach { coin ->
                if (money - coin >= 0) {
                    dp[money] = min(dp[money], dp[money - coin] + 1)
                }
            }
        }
        return if (dp[amount] == amount + 1) -1 else dp[amount]
    }

    /**
     * leetCode 509. 斐波那契数(easy)
     * 斐波那契数公式 ：F(n)=F(n - 1)+F(n - 2)
     * https://leetcode.cn/problems/fibonacci-number/description/
     */
    fun fib(n: Int): Int {
        val mem = IntArray(n + 1).apply {
            repeat(this.size) {
                this[it] = -1
            }
        }
        return fibDp(n, mem)
    }

    private fun fibDp(size: Int, mem: IntArray): Int {
        if (size == 0) {
            return 0
        }
        if (size <= 2) {
            return 1
        }
        if (mem[size] != -1) {
            return mem[size]
        }
        //斐波那契数公式 ：F(n)=F(n - 1)+F(n - 2)
        val count = fibDp(size - 1, mem) + fibDp(size - 2, mem)
        mem[size] = count
        return count
    }

    /**
     * leetCode 97. 交错字符串(middle)
     * https://leetcode.cn/problems/interleaving-string/description/
     */
    fun isInterleave(s1: String, s2: String, s3: String): Boolean {
        if (s1.length + s2.length != s3.length) {
            //因为是判断 s1 + s2 通过组合拼接后的是否等于 s3
            //所以一定满足 s1.length + s2.length == s3.length)
            return false
        }
        //定义记忆画搜索
        val cache = Array(s1.length + 10) { IntArray(s2.length + 10) }
        return dfsIsInterleave(cache, s1, s2, s3, 0, 0)
    }

    private fun dfsIsInterleave(cache: Array<IntArray>, s1: String, s2: String, s3: String, i: Int, j: Int): Boolean {
        if (cache[i][j] != 0) {
            return cache[i][j] == 1
        }
        if (i + j == s3.length) {
            return true
        }
        var ans = false
        //定义状态转移方程,也就是递归函数
        //这里就是 s3[i + j] = s1[i] + s2[j]
        if (i < s1.length && s1[i] == s3[i + j]) {
            ans = ans or dfsIsInterleave(cache, s1, s2, s3, i + 1, j)
        }
        if (j < s2.length && s2[j] == s3[i + j]) {
            ans = ans or dfsIsInterleave(cache, s1, s2, s3, i, j + 1)
        }
        cache[i][j] = if (ans) 1 else -1
        return ans
    }
}