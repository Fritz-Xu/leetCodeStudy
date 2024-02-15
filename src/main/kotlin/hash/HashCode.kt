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
        val result = mutableListOf<List<Int>>()
        if (nums.size < 3) {
            //不足 3 个,不符合题目要求
            return result
        }
        //先从小到大排序
        nums.sort()
        for (index in 0..<nums.size - 2) {
            //减2,是为了确保nums[index]后面一定有两个数,避免出现数组越界和重复数据
            if (nums[index] > 0) {
                //如果该数大于0，则从后面再找2个数，3个数的和必然大于0
                break
            }
            if (index > 0 && nums[index - 1] == nums[index]) {
                //说明出现了重复元素了,这里避免出现重复,去重
                continue
            }
            var start = index + 1
            var end = nums.size - 1
            while (start < end) {
                //start < end ,这个是前提
                val sum = nums[index] + nums[start] + nums[end]
                when {
                    sum > 0 -> {
                        //如果三数之和大于0，则说明最大数nums[end]偏大了，end向左移
                        end--
                    }
                    sum == 0 -> {
                        result.add(listOf(nums[index], nums[start], nums[end]))
                        while (start < end && nums[start] == nums[++start]) {
                            //左指针前进并去重
                        }
                        while (start < end && nums[end] == nums[--end]) {
                            //右指针前进并去重
                        }
                    }
                    else -> {
                        //如果三数之和小于0，则说明nums[start]偏小了，start向右移
                        start++
                    }
                }
            }
        }
        return result
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
    hash.threeSum(intArrayOf(-4,-2,-2,-2,0,1,2,2,2,3,3,4,4,6,6)).forEach { list ->
        list.forEach {
            print("$it,")
        }
        println()
    }
    println("----- [[-2,-1,3],[-2,0,2],[-1,0,1]]")

}