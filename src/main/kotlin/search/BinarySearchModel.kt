package search

import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


/**
 * 二分查找
 */
class BinarySearchModel {

    /**
     * 输入一个有序数组(升序),二分查找(双闭区间)
     * 二分查找，递归版本
     */
    fun binarySearchDfs(nums: IntArray, target: Int): Int {
        return dfs(nums, target, 0, nums.size)
    }

    private fun dfs(nums: IntArray, target: Int, start: Int, end: Int): Int {
        if (start > end) {
            return -1
        }
        val mid = (start + end) / 2
        val midValue = nums[mid]
        return when {
            //比中间大，在右边
            midValue < target -> dfs(nums, target, mid + 1, end)
            //比中间小，在左边
            midValue > target -> dfs(nums, target, start, mid - 1)
            //找到了
            else -> mid
        }
    }

    /**
     * 输入一个有序数组(升序),二分查找(双闭区间)
     */
    fun binarySearch(nums: IntArray, target: Int): Int {
        var index = 0
        var end = nums.size - 1
        while (index <= end) {
            val position = index + (end - index) / 2
            if (nums[position] < target) {
                index = position + 1
            } else if (nums[position] > target) {
                end = position - 1
            } else {
                return position
            }
        }
        return -1
    }

    /**
     * 输入一个有序数组(升序),二分查找合适插入位置(双闭区间)
     * 这到底其实就是找符合要求的左边界
     * 返回一个下标
     */
    fun binarySearchFindInsert(nums: IntArray, target: Int): Int {
        var index = -1
        var key = target
        //寻找最左边界
        while (index == -1) {
            if (key <= nums[0]) {
                index = 0
                break
            }
            index = binarySearch(nums, key)
            key--
        }
        return index
    }

    /**
     * 输入一个有序数组(升序),二分查找(双闭区间)
     * 返回一个区间
     */
    fun binarySearchRange(nums: IntArray, target: Int): IntArray {
        var index = -1
        var key = target
        val result = intArrayOf(-1, -1)
        //寻找最左边界
        while (index == -1) {
            if (key <= nums[0]) {
                index = 0
                break
            }
            index = binarySearch(nums, key)
            key--
        }
        result[0] = index
        //寻找最右边界
        index = -1
        while (index == -1) {
            if (key >= nums[0]) {
                index = nums.size
                break
            }
            index = binarySearch(nums, key)
            key++
        }
        result[1] = index
        return result
    }

    /**
     * leetCode 278. 第一个错误的版本(easy)
     * 你是产品经理，目前正在带领一个团队开发新的产品。不幸的是，你的产品的最新版本没有通过质量检测。
     * 由于每个版本都是基于之前的版本开发的，所以错误的版本之后的所有版本都是错的。
     * 假设你有 n 个版本 [1, 2, ..., n]，你想找出导致之后所有版本出错的第一个错误的版本。
     * 你可以通过调用 bool isBadVersion(version) 接口来判断版本号 version 是否在单元测试中出错。
     * 实现一个函数来查找第一个错误的版本。你应该尽量减少对调用 API 的次数。
     *
     * 示例 1：
     * 输入：n = 5, bad = 4
     * 输出：4
     * 解释：
     * 调用 isBadVersion(3) -> false
     * 调用 isBadVersion(5) -> true
     * 调用 isBadVersion(4) -> true
     * 所以，4 是第一个错误的版本。
     *
     * 示例 2：
     * 输入：n = 1, bad = 1
     * 输出：1
     *
     * 提示:这是二分区间查找
     */
    fun firstBadVersion(n: Int): Int {
        var startPoint = 1
        var endPoint = n
        while (startPoint <= endPoint) {
            // 向下取整除法计算中点 middle
            val middle = startPoint + (endPoint - startPoint) / 2
            if (isBadVersion(middle)) {
                //middle 是错误版本， 那么最后一个正确版本一定在闭区间 [startPoint, middle - 1]
                endPoint = middle - 1
            } else {
                //middle 是正确版本， 那么最后一个正确版本一定在闭区间 [middle + 1, endPoint]
                startPoint = middle + 1
            }
        }
        // 二分查找区间完成后,startPoint 指向首个错误版本，endPoint 指向最后一个正确版本
        return startPoint
    }

    private fun isBadVersion(version: Int): Boolean {
        //这里是 10在 leetCode 里面,是一个运行时的随机数
        return version == 10
    }

    /**
     * leetCode 724. 寻找数组的中心下标(easy)
     * 给你一个整数数组 nums ，请计算数组的 中心下标
     * 数组 中心下标 是数组的一个下标，其左侧所有元素相加的和等于右侧所有元素相加的和
     * 如果中心下标位于数组最左端，那么左侧数之和视为 0 ，因为在下标的左侧不存在元素。这一点对于中心下标位于数组最右端同样适用
     * 如果数组有多个中心下标，应该返回 最靠近左边 的那一个
     * 如果数组不存在中心下标，返回 -1
     *
     * 提示:遍历数组,首指针往移动,统计指针分割的 sum_left 和 sum_right的值,二者相等就是中心下标
     */
    fun pivotIndex(nums: IntArray): Int {
        val sum = nums.sumOf { it }
        var sumLeft = 0
        for (index in nums.indices) {
            if (sum - nums[index] - sumLeft == sumLeft) {
                return index
            }
            sumLeft += nums[index]
        }
        return -1
    }

    /**
     * leetCode 154. 寻找旋转排序数组中的最小值 II (hard)
     *
     * 已知一个长度为 n(1 <= n <= 5000) 的数组，按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。
     * 例如，原数组 nums = [0,1,4,4,5,6,7] 在变化后可能得到：
     * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,4]
     * 若旋转 7 次，则可以得到 [0,1,4,4,5,6,7]
     * 注意数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为:
     * 数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]]
     *
     * 给你一个可能存在 重复 元素值的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转
     * 请你找出并返回数组中的 最小元素
     * 你必须尽可能减少整个过程的操作步骤
     *
     * 示例 1：
     * 输入：nums = [1,3,5]
     * 输出：1
     *
     * 示例 2：
     * 输入：nums = [2,2,2,0,1]
     * 输出：0
     *
     * 提示：二分查找,最小值在旋转点，如下：
     *   4 5 6 7 ｜ 1 2 3
     *   看到旋转后，在旋转分界的左右两边，左边是大值，右边是小值
     */
    fun findMin(nums: IntArray): Int {
        if (nums.isEmpty()) {
            return -1
        }
        var startPoint = 0
        var endPoint = nums.size - 1
        while (startPoint < endPoint) {
            //startPoint == endPoint 就找到了旋转点了
            val middle = (startPoint + endPoint) / 2
            if (nums[middle] > nums[endPoint]) {
                //说明旋转点在左边大值区间，就是 middle + 1 到 endPoint，继续二分查找
                startPoint = middle + 1
            } else if (nums[middle] < nums[endPoint]) {
                //说明旋转点在右边小值区间，就是 startPoint 到 middle，继续二分查找
                endPoint = middle
            } else {
                //说明找到了重复元素，无法判断区间，于是直接缩减 endPoint的范围
                endPoint--
            }
        }
        return nums[startPoint]
    }

    /**
     * leetCode 475. 供暖器 (middle)
     * 你的任务是设计一个有固定加热半径的供暖器向所有房屋供暖。在加热器的加热半径范围内的每个房屋都可以获得供暖。
     * 现在，给出位于一条水平线上的房屋 houses 和供暖器 heaters 的位置，请你找出并返回可以覆盖所有房屋的最小加热半径。
     * 注意：所有供暖器 heaters 都遵循你的半径标准，加热的半径也一样。
     *
     * 示例 1:
     * 输入: houses = [1,2,3], heaters = [2]
     * 输出: 1
     * 解释: 仅在位置 2 上有一个供暖器。如果我们将加热半径设为 1，那么所有房屋就都能得到供暖。
     * 示例 2:
     * 输入: houses = [1,2,3,4], heaters = [1,4]
     * 输出: 1
     * 解释: 在位置 1, 4 上有两个供暖器。我们需要将加热半径设为 1，这样所有房屋就都能得到供暖。
     * 示例 3：
     * 输入：houses = [1,5], heaters = [2]
     * 输出：3
     *
     * 提示：二分查找的应用
     */
    fun findRadius(houses: IntArray, heaters: IntArray): Int {
        Arrays.sort(heaters)
        var res = 0
        houses.forEach { h ->
            val rightHeater = bsr(h, heaters)
            val leftHeater = bsl(h, heaters)
            val dis = min(abs(h - rightHeater), abs(h - leftHeater))
            res = max(res, dis)
        }
        return res
    }

    private fun bsr(target: Int, arr: IntArray): Int {
        var l = 0
        var r = arr.size - 1
        while (l < r) {
            val mid = (l + r) / 2
            if (arr[mid] >= target) {
                r = mid
            } else {
                l = mid + 1
            }
        }
        return arr[l]
    }

    private fun bsl(target: Int, arr: IntArray): Int {
        var l = 0
        var r = arr.size - 1
        while (l < r) {
            val mid = (l + r + 1) / 2
            if (arr[mid] <= target) {
                l = mid
            } else {
                r = mid - 1
            }
        }
        return arr[l]
    }
}

fun main() {
    val model = BinarySearchModel()
    //println(model.pivotIndex(intArrayOf(-1, 0, 1, -1, 0)))
    println(model.findMin(intArrayOf(3, 1, 3)))

}