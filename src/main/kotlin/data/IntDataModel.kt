package data

import kotlin.math.sqrt

class IntDataModel {

    /**
     * leetCode633. 平方数之和 (middle)
     *
     * 给定一个非负整数 c ，你要判断是否存在两个整数 a 和 b，使得 a^2 + b^2 = c 。
     * 示例 1：
     * 输入：c = 5
     * 输出：true
     * 解释：1 * 1 + 2 * 2 = 5
     * 示例 2：
     * 输入：c = 3
     * 输出：false
     *
     * 提示:双指针+二分
     */
    fun judgeSquareSum(c: Int): Boolean {
        var start = 0
        var end = sqrt(c.toDouble()).toLong()
        val result = c.toLong()
        if (end * end > c || end * end < c) {
            return false
        }
        while (start <= end) {
            val cur = start * start + end * end
            if (cur == result) {
                return true
            } else if (cur > c) {
                end--
            } else {
                start++
            }
        }
        return false
    }
}