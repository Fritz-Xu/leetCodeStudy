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
}