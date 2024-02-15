package mock

import kotlin.math.min

/**
 * leetCode 的模拟题
 */
class MockCodeModel {

    /**
     * leetCode 1599. 经营摩天轮的最大利润(middle)
     * 你正在经营一座摩天轮，该摩天轮共有 4 个座舱 ，每个座舱 最多可以容纳 4 位游客 。
     * 你可以 逆时针 轮转座舱，但每次轮转都需要支付一定的运行成本 runningCost 。摩天轮每次轮转都恰好转动 1 / 4 周。
     * 给你一个长度为 n 的数组 customers ， customers[i] 是在第 i 次轮转（下标从 0 开始）之前到达的新游客的数量。
     * 这也意味着你必须在新游客到来前轮转 i 次。
     * 每位游客在登上离地面最近的座舱前都会支付登舱成本 boardingCost ，一旦该座舱再次抵达地面，他们就会离开座舱结束游玩。
     * 你可以随时停下摩天轮，即便是在服务所有游客之前 。
     * 如果你决定停止运营摩天轮，为了保证所有游客安全着陆，将免费进行所有后续轮转 。
     * 注意，如果有超过 4 位游客在等摩天轮，那么只有 4 位游客可以登上摩天轮，其余的需要等待 下一次轮转 。
     * 返回最大化利润所需执行的 最小轮转次数 , 如果不存在利润为正的方案，则返回 -1 。
     *
     */
    fun minOperationsMaxProfit(customers: IntArray, boardingCost: Int, runningCost: Int): Int {
        //最大化利润所需执行的最小轮转次数
        var ans = -1
        //当前轮转次数
        var index = 0
        //等待人数
        var waitCount = 0
        //总收益
        var earnings = 0
        //记录当前的最大收益
        var maxGet = 0
        //waitCount > 0 考虑一次只能上4个人,可能会有人先留下,下轮再上
        while (index < customers.size || waitCount > 0) {
            waitCount += if (index < customers.size) customers[index] else 0
            // 计算出这次上了摩天轮的人数
            val upCount = min(4.0, waitCount.toDouble()).toInt()
            //更新等待人数
            waitCount -= upCount
            index++
            //收益 = 上去的人数 * 登舱成本 - 运输成本
            earnings += upCount * boardingCost - runningCost
            //如果本次的收益超过了之前的最大收益,则更新最大收益和最小轮转次数ans
            if (earnings > maxGet) {
                maxGet = earnings
                //此时就是利润最大的最少次数
                ans = index
            }
        }
        return ans
    }
}