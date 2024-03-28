package search

import kotlin.math.min

/**
 * 二分查找
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
}

fun main() {
    val item = BinarySearchStudyModel()
    println(
        item.countNegatives(
            arrayOf(
                intArrayOf(4, 3, 2, -1), intArrayOf(3, 2, 1, -1), intArrayOf(3, 2, 1, -1), intArrayOf(1, 1, -1, -2),
                intArrayOf(-1, -1, -2, -3)
            )
        )
    )
    //println(item.countNegatives(arrayOf(intArrayOf(3, 2), intArrayOf(1, 0))))
}