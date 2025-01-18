package hash

import java.util.*


/**
 * 收集一些 hash 的 leetCode 题目
 */
class HashCode {


    /**
     * leetCode 167. 两数之和 II - 输入有序数组(middle)
     * 给你一个下标从 1 开始的整数数组 numbers ，该数组已按 非递减顺序排列
     * 请你从数组中找出满足相加之和等于目标数 target 的两个数
     * 如果设这两个数分别是 numbers[index1] 和 numbers[index2] ，则 1 <= index1 < index2 <= numbers.length
     * 以长度为 2 的整数数组 [index1, index2] 的形式返回这两个整数的下标 index1 和 index2
     * 你可以假设每个输入 只对应唯一的答案 ，而且你 不可以 重复使用相同的元素。
     * 你所设计的解决方案必须只使用常量级的额外空间
     *
     * 示例 1：
     * 输入：numbers = [2,7,11,15], target = 9
     * 输出：[1,2]
     * 解释：2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。返回 [1, 2]
     *
     * 示例 2：
     * 输入：numbers = [2,3,4], target = 6
     * 输出：[1,3]
     * 解释：2 与 4 之和等于目标数 6 。因此 index1 = 1, index2 = 3 。返回 [1, 3]
     * 示例 3：
     *
     * 输入：numbers = [-1,0], target = -1
     * 输出：[1,2]
     * 解释：-1 与 0 之和等于目标数 -1 。因此 index1 = 1, index2 = 2 。返回 [1, 2]
     */
    fun twoSum(numbers: IntArray, target: Int): IntArray {
        val map = mutableMapOf<Int, Int>()
        run {
            numbers.forEachIndexed { index, value ->
                val result = target - value
                if (map.containsKey(result)) {
                    return IntArray(2).apply {
                        //原数组的下标从 1 开始，这里需要加 1
                        this[0] = map[result]!! + 1
                        this[1] = index + 1
                    }
                }
                map[value] = index
            }
        }
        throw IllegalArgumentException("No two sum solution")
    }

    /**
     * leetCode 15. 三数之和 (middle)
     * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k
     * 同时还满足 nums[i] + nums[j] + nums[k] == 0
     * 请返回所有和为 0 且不重复的三元组
     * 注意：答案中不可以包含重复的三元组
     *
     *示例 1：
     * 输入：nums = [-1,0,1,2,-1,-4]
     * 输出：[[-1,-1,2],[-1,0,1]]
     * 解释：
     * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0
     * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0
     * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0
     * 不同的三元组是 [-1,0,1] 和 [-1,-1,2]
     * 注意，输出的顺序和三元组的顺序并不重要
     *
     * 示例 2：
     * 输入：nums = [0,1,1]
     * 输出：[]
     * 解释：唯一可能的三元组和不为 0
     *
     * 示例 3：
     * 输入：nums = [0,0,0]
     * 输出：[[0,0,0]]
     * 解释：唯一可能的三元组和为 0
     *
     *提示:使用排序+双位移指针法+哨兵可以快速解决
     */
    fun threeSum(nums: IntArray): List<List<Int>> {
        if (nums.size < 3) {
            //剪枝,数组没有 3 个就返回空集合
            return emptyList()
        }
        val ans = mutableListOf<List<Int>>()
        //从小到大排序
        nums.sort()
        for (index in 0 until nums.size - 2) {
            //计算三数之和,因此减 2,避免计算越界
            val item = nums[index]
            if (item > 0) {
                //已排序,因此该数大于0,则从后面再找2个数,3个数的和必然大于0
                break
            }
            if (index > 0 && item == nums[index - 1]) {
                //说明出现了重复元素了,这里避免出现重复,去重
                continue
            }
            var start = index + 1
            var end = nums.size - 1
            while (start < end) {
                //计算三数之和
                val sum = nums[index] + nums[start] + nums[end]
                if (sum == 0) {
                    //符合要求
                    ans.add(listOf(nums[index], nums[start], nums[end]))
                    while (start < end && nums[start] == nums[++start]) {
                        //左指针前进,过滤重复数据
                    }
                    while (start < end && nums[end] == nums[--end]) {
                        //右指针回退,过滤重复数据
                    }
                    continue
                }
                if (sum < 0) {
                    //小于 0,start前进增大总和
                    start++
                    continue
                }
                //这时候就剩下 sum 大于 0 了
                //end - 1 ,降低总和
                end--
            }
        }
        return ans
    }

    /**
     * 整理全部的箱子
     */
    fun wardrobeFinishing(m: Int, n: Int, cnt: Int): Int {
        // 创建一个 3x3 的二维布尔数组，所有元素初始化为 false
        val vis = Array(m) { BooleanArray(n) { false } }
        return wardrobeFinishingDfs(m, n, cnt, vis, 0, 0)
    }

    private fun wardrobeFinishingDfs(
        m: Int, n: Int, k: Int,
        vis: Array<BooleanArray>, i: Int, j: Int
    ): Int {
        if ((i >= m || j >= n || vis[i][j]) || (i % 10 + i / 10 + j % 10 + j / 10) > k) {
            return 0
        }
        vis[i][j] = true
        return 1 + wardrobeFinishingDfs(m, n, k, vis, i + 1, j) + wardrobeFinishingDfs(m, n, k, vis, i, j + 1)
    }
}

fun main() {
    val hash = HashCode()
//    hash.threeSum(intArrayOf(-1, 0, 1, 2, -1, -4)).forEach { list ->
//        list.forEach {
//            print("$it,")
//        }
//        println()
//    }
    println("----- [-2, -1, 0, 1, 2, 3]")
    hash.threeSum(intArrayOf(-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6)).forEach { list ->
        list.forEach {
            print("$it,")
        }
        println()
    }
    println("----- [[-2,-1,3],[-2,0,2],[-1,0,1]]")

}