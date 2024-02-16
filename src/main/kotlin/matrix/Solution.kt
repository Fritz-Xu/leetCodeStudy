package matrix

import kotlin.random.Random

/**
 * leetCode 519. 随机翻转矩阵(middle)
 * 给你一个 m x n (1 <= m, n <= 104 )的二元矩阵 matrix ，且所有值被初始化为 0
 * 请你设计一个算法，随机选取一个满足 matrix[i][j] == 0 的下标 (i, j) ，并将它的值变为 1
 * 所有满足 matrix[i][j] == 0 的下标 (i, j) 被选取的概率应当均等
 * 尽量最少调用内置的随机函数，并且优化时间和空间复杂度。
 *
 * 实现 Solution 类：
 * Solution(int m, int n) 使用二元矩阵的大小 m 和 n 初始化该对象
 * int[] flip() 返回一个满足 matrix[i][j] == 0 的随机下标 [i, j] ，并将其对应格子中的值变为 1
 * void reset() 将矩阵中所有的值重置为 0
 *
 * 示例：
 * 输入
 * ["Solution", "flip", "flip", "flip", "reset", "flip"]
 * [[3, 1], [], [], [], [], []]
 * 输出
 * [null, [1, 0], [2, 0], [0, 0], null, [2, 0]]
 * 解释
 * Solution solution = new Solution(3, 1);
 * solution.flip();  // 返回 [1, 0]，此时返回 [0,0]、[1,0] 和 [2,0] 的概率应当相同
 * solution.flip();  // 返回 [2, 0]，因为 [1,0] 已经返回过了，此时返回 [2,0] 和 [0,0] 的概率应当相同
 * solution.flip();  // 返回 [0, 0]，根据前面已经返回过的下标，此时只能返回 [0,0]
 * solution.reset(); // 所有值都重置为 0 ，并可以再次选择下标返回
 * solution.flip();  // 返回 [2, 0]，此时返回 [0,0]、[1,0] 和 [2,0] 的概率应当相同
 *
 * 提示：哈希表以及一维数组与二维数组的转换
 * 二维(m x n)转一维(idx)：idx = m * n+ n，即拿横坐标乘以列数，再加上纵坐标；
 * 一维(idx)转二维(m x n)：i=idx / n, j= idx % n，即一维坐标除以列数等于二维的纵坐标，一维坐标除以列数的余数等于二维的纵坐标
 *
 */
class Solution(val m: Int, val n: Int) {

    /**
     * 纪录已经翻转的下标
     */
    private val map = mutableMapOf<Int, Int>()
    private var size = 0

    init {
        size = m * n
    }

    fun flip(): IntArray {
        //随机一个未被翻转的下标
        val x = Random.nextInt(size--)
        //过滤,避免该下标已经翻转过了,然后给覆盖新的一维数组的下标
        val idx = map.getOrDefault(x, x)
        //把随机一维数组的下标存入 map
        map[x] = map.getOrDefault(size, size)
        //一维数组转为二维数组
        return intArrayOf(idx / n, idx % n)
    }

    fun reset() {
        //重置
        map.clear()
        size = m * n
    }
}