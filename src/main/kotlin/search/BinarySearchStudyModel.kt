package search

import java.util.*
import kotlin.math.max
import kotlin.math.min


/**
 * 二分查找
 * 闭区间的模板，也是通用模板:
 * var start = 0
 * var end = size - 1
 * while (start <= end) {
 *     // 这里 + 1 是为了避免死循环
 *     long mid = (start + end + 1) / 2
 *     if (check(mid)) {
 *         start = mid;
 *     } else {
 *         end = mid - 1;
 *     }
 * }
 *
 * 开区间的模板
 * var start = -1
 * var end = nums.size - 1 // 开区间 (-1, n-1)
 * while (start + 1 < end) { // 开区间不为空
 *     val mid = start + (end - start) / 2
 *     if (check(mid)) {
 *         end = mid
 *     } else {
 *         start = mid
 *     }
 * }
 * return end
 *
 */
class BinarySearchStudyModel {

    /**
     * leetCode 4.寻找两个正序数组的中位数(hard)
     */
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val totalLength = nums1.size + nums2.size
        if (totalLength % 2 == 0) {
            //偶数
            val left = findKth(nums1, 0, nums2, 0, totalLength / 2)
            val right = findKth(nums1, 0, nums2, 0, totalLength / 2 + 1)
            return (left + right) / 2.0
        } else {
            //奇数
            return findKth(nums1, 0, nums2, 0, totalLength / 2 + 1).toDouble()
        }
    }

    private fun findKth(nums1: IntArray, key1: Int, nums2: IntArray, key2: Int, k: Int): Int {
        if (nums1.size - key1 > nums2.size - key2) {
            return findKth(nums2, key2, nums1, key1, k)
        }
        if (key1 >= nums1.size) {
            return nums2[key2 + k - 1]
        }
        if (k == 1) {
            return min(nums1[key1], nums2[key2])
        }
        val result = min(key1 + (k / 2), nums1.size)
        val target = key1 + key2 - k / 2
        if (nums1[result - 1] > nums2[target - 1]) {
            return findKth(nums1, key1, nums2, target, k - (target - key2))
        }
        return findKth(nums1, result, nums2, key2, k - (result - key1))
    }

    /**
     * LeetCode 744.寻找比目标字母大的最小字母(easy)
     * https://leetcode.cn/problems/find-smallest-letter-greater-than-target/
     */
    fun nextGreatestLetter(letters: CharArray, target: Char): Char {
        if (letters[0] > target) {
            return letters[0]
        }
        var start = 0
        var end = letters.size - 1
        while (start < end) {
            val mid = (start + end) / 2
            if (letters[mid] > target) {
                end = mid
            } else {
                start = mid + 1
            }
        }
        return if (letters[end] > target) letters[end] else letters[0]
    }

    /**
     * LeetCode 1351. 统计有序矩阵中的负数(easy)
     * https://leetcode.cn/problems/count-negative-numbers-in-a-sorted-matrix/description
     * 思路:二分查找 + 数组递减
     */
    fun countNegatives(grid: Array<IntArray>): Int {
        var ans = 0
        for (array in grid) {
            if (array[0] < 0) {
                ans += array.size
                continue
            }
            if (array.last() >= 0) {
                continue
            }
            var start = 0
            var end = array.size - 1
            while (start < end) {
                val mid = (start + end) / 2
                if (array[mid] >= 0) {
                    start = mid + 1
                } else {
                    end = mid
                }
            }
            ans += array.size - end
        }
        return ans
    }

    /**
     * LeetCode 34. 在排序数组中查找元素的第一个和最后一个位置(middle)
     * https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array
     * 思路:二分查找
     */
    fun searchRange(nums: IntArray, target: Int): IntArray {
        if (nums.size == 1) {
            if (nums[0] == target) {
                return intArrayOf(0, 0)
            }
            return intArrayOf(-1, -1)
        }
        if (nums.size == 2) {
            if (nums[0] == target && nums[1] == target) {
                return intArrayOf(0, 1)
            }
            if (nums[0] == target) {
                return intArrayOf(0, 0)
            }
            if (nums[1] == target) {
                return intArrayOf(1, 1)
            }
            return intArrayOf(-1, -1)
        }
        val ans = IntArray(2)
        Arrays.fill(ans, -1)
        var start = 0
        var end = nums.size - 1
        var index = -1
        while (start <= end) {
            val mid = (start + end) / 2
            if (nums[mid] > target) {
                end = mid - 1
            } else if (nums[mid] < target) {
                start = mid + 1
            } else {
                index = mid
                break
            }
        }
        if (index != -1) {
            //说明找到了
            start = index
            end = index
            while (start >= 0 && nums[start] == target) {
                start--
            }
            while (end < nums.size && nums[end] == target) {
                end++
            }
            ans[0] = start + 1
            ans[1] = end - 1
        }
        return ans
    }

    /**
     * LeetCode 436. 寻找右区间(middle)
     * https://leetcode.cn/problems/find-right-interval/description
     * 思路:二分查找
     */
    fun findRightInterval(intervals: Array<IntArray>): IntArray {
        val ans = IntArray(intervals.size)
        val cache = TreeMap<Int, Int>()
        for (index in intervals.indices) {
            cache[intervals[index][0]] = index
        }
        for (index in intervals.indices) {
            val entry = cache.ceilingEntry(intervals[index][1])
            ans[index] = if (entry == null) -1 else entry.value
        }
        return ans
    }

    /**
     * leetCode 29. 两数相除(middle)
     * https://leetcode.cn/problems/divide-two-integers/description/
     */
    fun divide(dividend: Int, divisor: Int): Int {
        //避免溢出,使用 long
        var x: Long = dividend.toLong()
        var y: Long = divisor.toLong()
        val isNeg = x > 0 && y < 0 || x < 0 && y > 0
        if (x < 0) {
            x = -x
        }
        if (y < 0) {
            y = -y
        }
        var start = 0L
        var end = x
        while (start < end) {
            val mid = (start + end + 1) / 2
            if (divideMul(mid, y) <= x) {
                start = mid
            } else {
                end = mid - 1
            }
        }
        val ans = if (isNeg) -start else start
        if (ans > Int.MAX_VALUE || ans < Int.MIN_VALUE) {
            return Int.MAX_VALUE
        }
        return ans.toInt()
    }

    private fun divideMul(x: Long, y: Long): Long {
        var k = y
        var a = x
        var ans = 0L
        while (k > 0) {
            if ((k and 1) == 1L) {
                ans += x
            }
            k = k shr 1
            a += a
        }
        return ans
    }

    /**
     * leetCode 33. 搜索旋转排序数组(middle)
     * 尽量一次二分
     */
    fun search(nums: IntArray, target: Int): Int {
        if (nums.isEmpty()) {
            return -1
        }
        if (nums.size == 1) {
            return if (nums[0] == target) 0 else -1
        }
        if (nums.size <= 3) {
            return when (target) {
                nums[0] -> {
                    0
                }

                nums[1] -> {
                    1
                }

                nums[min(2, nums.size - 1)] -> {
                    2
                }

                else -> {
                    -1
                }
            }
        }
        var start = 0
        var end = nums.size - 1
        while (start < end) {
            val mid = (start + end + 1) / 2
            if (searchByCheck(nums, target, mid)) {
                end = mid
            } else {
                start = mid
            }
        }
        return if (nums[start] == target) start else -1
    }

    private fun searchByCheck(nums: IntArray, target: Int, mid: Int): Boolean {
        val last = nums.last()
        if (nums[mid] > last) {
            return target > last && nums[mid] >= target
        }
        return target > last || nums[mid] >= target
    }

    /**
     * leetCode 162. 寻找峰值(middle)
     */
    fun findPeakElement(nums: IntArray): Int {
        var start = -1
        var end = nums.size - 1 // 开区间 (-1, n-1)
        while (start + 1 < end) { // 开区间不为空
            val mid = start + (end - start) / 2
            if (nums[mid] > nums[mid + 1]) {
                end = mid
            } else {
                start = mid
            }
        }
        return end
    }

    /**
     * leetCode 35. 搜索插入位置(easy)
     */
    fun searchInsert(nums: IntArray, target: Int): Int {
        if (target < nums[0]) {
            return 0
        }
        if (target > nums.last()) {
            return nums.size - 1
        }
        var start = -1
        var end = nums.size - 1
        while (start + 1 < end) {
            val mid = start + (end - start) / 2
            if (nums[mid] > target) {
                end = mid
            } else if (nums[mid] < target) {
                start = mid
            } else {
                return mid
            }
        }
        return end
    }

    /**
     * leetCode 74. 搜索二维矩阵(middle)
     * https://leetcode.cn/problems/search-a-2d-matrix/
     * 提示：把二维当成一维数组，然后二分查找
     */
    fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
        val length = matrix[0].size
        if (length == 1 && matrix.size == 1) {
            return matrix[0][0] == target
        }
        var start = -1
        var end = matrix.size * length - 1
        while (start + 1 < end) {
            val mid = start + (end - start) / 2
            //间接转为一维
            val result = matrix[mid / length][mid % length]
            if (result < target) {
                start = mid
            } else if (result > target) {
                end = mid
            } else {
                return true
            }
        }
        //如果上面二分找不到,就判断最后的情况
        return matrix[end / length][end % length] == target
    }

    /**
     * leetCode 154. 寻找旋转排序数组中的最小值 II(hard)
     * https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array-ii/
     */
    fun findMin(nums: IntArray): Int {
        var start = -1
        var end = nums.size - 1
        while (start + 1 < end) {
            val mid = start + (end - start) / 2
            if (nums[mid] < nums[end]) {
                //旋转数组，元素不重复时可以这么判断是否靠近右边
                end = mid
            } else if (nums[mid] == nums[end]) {
                //针对相同的元素情况
                end--
            } else {
                start = mid
            }
        }
        return nums[end]
    }

    /**
     * leetCode 74. 搜索二维矩阵II(middle)
     * https://leetcode.cn/problems/search-a-2d-matrix-ii/
     *
     */
    fun searchMatrix2(matrix: Array<IntArray>, target: Int): Boolean {
        val size = matrix.size
        if (size == 1) {
            if (matrix[0].size == 1) {
                return matrix[0][0] == target
            }
            if (matrix[0].size == 2) {
                return matrix[0][1] == target || matrix[0][0] == target
            }
        }
        for (index in 0 until size) {
            val array = matrix[index]
            if (array.size == 1 && array[0] == target) {
                return true
            }
            var start = 0
            var end = array.size - 1
            while (start <= end) { // 开区间不为空
                val mid = start + (end - start) / 2
                if (array[mid] > target) {
                    end = mid - 1
                } else if (array[mid] < target) {
                    start = mid + 1
                } else {
                    return true
                }
            }
        }
        return false
    }

    /**
     * leetCode 274. H 指数(middle)
     * https://leetcode.cn/problems/h-index/
     */
    fun hIndex(citations: IntArray): Int {
        if (citations.size == 1) {
            return if (citations[0] <= 0) 0 else 1
        }
        var start = 0
        var end = citations.size
        while (start < end) {
            val mid = (start + end + 1) / 2
            if (hIndexCheck(citations, mid)) {
                start = mid
            } else {
                end = mid - 1
            }
        }
        return end
    }

    private fun hIndexCheck(citations: IntArray, mid: Int): Boolean {
        var ans = 0
        citations.forEach {
            if (it >= mid) {
                ans++
            }
        }
        return ans >= mid
    }

    /**
     * leetCode 367. 有效的完全平方数(easy)
     * https://leetcode.cn/problems/valid-perfect-square
     */
    fun isPerfectSquare(num: Int): Boolean {
        var start = 0L
        var end = num.toLong()
        while (start < end) {
            val mid = (start + end + 1) / 2L
            if (mid * mid <= num) start = mid
            else end = mid - 1L
        }
        return (start * start).toInt() == num
    }

    /**
     * leetCode 2300. 咒语和药水的成功对数(middle)
     */
    fun successfulPairs(spells: IntArray, potions: IntArray, success: Long): IntArray {
        potions.sort()
        val ans = IntArray(spells.size)
        spells.forEachIndexed { index, item ->
            var start = -1
            var end = potions.size - 1
            while (start + 1 < end) {
                val mid = start + (end - start)
                //使用 long 防止溢出
                val result = 1L * item * potions[mid]
                if (result < success) {
                    start = mid
                } else {
                    end = mid
                }
            }
            //使用 long 防止溢出
            ans[index] = if (1L * potions[end] * item < success) 0 else potions.size - end
        }
        return ans
    }

    /**
     * leetCode 2563. 统计公平数对的数目(middle)
     * https://leetcode.cn/problems/count-the-number-of-fair-pairs/
     * 提示：这里是统计数量，排序不影响答案，二分处理
     */
    fun countFairPairs(nums: IntArray, lower: Int, upper: Int): Long {
        nums.sort()
        var ans = 0L
        nums.forEachIndexed { index, item ->
            //以 index 为终点，计算出小于 lower 的闭区间的起点
            val start = countFairPairsFind(nums, index, lower - item)
            //以 index 为终点，计算出大于 upper 的闭区间的终点
            val end = countFairPairsFind(nums, index, upper - item + 1)
            ans += end - start
        }
        return ans
    }

    private fun countFairPairsFind(nums: IntArray, item: Int, target: Int): Int {
        var start = -1
        var end = item
        while (start + 1 < end) {
            val mid = start + (end - start) / 2
            if (nums[mid] < target) {
                // 范围缩小到 (mid, right)
                start = mid
            } else {
                // 范围缩小到 (left, mid)
                end = mid
            }
        }
        return end
    }

    /**
     * leetCode 1283. 使结果不超过阈值的最小除数(middle)
     * https://leetcode.cn/problems/find-the-smallest-divisor-given-a-threshold/
     */
    fun smallestDivisor(nums: IntArray, threshold: Int): Int {
        var end = 1
        nums.forEach { item ->
            end = max(end, item)
        }
        //开始二分
        var start = -1
        while (start + 1 < end) {
            val mid = start + (end - start) / 2
            if (smallestDivisorCheck(nums, mid) > threshold) {
                end = mid
            } else {
                start = mid
            }
        }
        return end
    }

    private fun smallestDivisorCheck(nums: IntArray, mid: Int): Int {
        var ans = 0
        nums.forEach { item ->
            ans += item / mid
            //这里要向上取整
            if (item % mid != 0) {
                ans++
            }
        }
        return ans
    }

    /**
     * leetCode 2187. 完成旅途的最少时间(middle)
     * https://leetcode.cn/problems/minimum-time-to-complete-trips
     */
    fun minimumTime(time: IntArray, totalTrips: Int): Long {
        //找到耗时最少的时间
        var minT = time.min().toLong()
        var start = minT - 1
        //这个是符合要求的最少耗时,也就是我们要找的 end
        var end = totalTrips * minT
        while (start + 1 < end) {
            val mid = (start + end) / 2
            if (minimumTimeCheck(mid, time, totalTrips)) {
                end = mid
            } else {
                start = mid
            }
        }
        //此时 start == end - 1
        return end
    }

    private fun minimumTimeCheck(mid: Long, time: IntArray, totalTrips: Int): Boolean {
        var ans = 0L
        for (item in time) {
            ans += mid / item
            if (ans >= totalTrips) {
                return true
            }
        }
        return false
    }
}


fun main() {
    val item = BinarySearchStudyModel()
    //println(item.searchRange(intArrayOf(2, 2), 2))
//    item.searchRange(intArrayOf(5,7,7,8,8,10), 8).forEach {
//        print("$it,")
//    }
//    item.searchRange(intArrayOf(1, 2, 3), 1).forEach {
//        print("$it,")
//    }
    //[4,0,3]
    (item.successfulPairs(intArrayOf(1, 2, 3, 4, 5, 6, 7), intArrayOf(1, 2, 3, 4, 5, 6, 7), 25)).forEach {
        println("$it,")
    }
}