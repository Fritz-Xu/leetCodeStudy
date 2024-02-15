package dynamic_programming

import java.util.*
import kotlin.math.min


class DynamicCoinModel {

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



}