package search

import java.util.*


/**
 * 二分查找模板 3
 *
 */
class BinarySearchStudyModel3 {

    /**
     * 模板3
     * 用于搜索需要访问当前索引及其在数组中的直接左右邻居索引的元素或条件。
     */
    fun binarySearch(nums: IntArray, target: Int): Int {
        if (nums.isEmpty()) return -1
        var start = 0
        var end = nums.size - 1
        while (start + 1 < end) {
            // Prevent (left + right) overflow
            val mid = start + (end - start) / 2
            if (nums[mid] == target) {
                return mid
            } else if (nums[mid] < target) {
                start = mid
            } else {
                end = mid
            }
        }
        // Post-processing:
        // End Condition: left + 1 == right
        if (nums[start] == target) return start
        if (nums[end] == target) return end
        return -1
    }

    /**
     * leetCode 34. 在排序数组中查找元素的第一个和最后一个位置(middle)
     */
    fun searchRange(nums: IntArray, target: Int): IntArray {
        val ans = IntArray(2)
        Arrays.fill(ans, -1)
        var start = 0
        var end = nums.size - 1
        var index = -1
        while (start + 1 < end) {
            val mid = start + (end - start) / 2
            if (nums[mid] == target) {
                index = mid
                break
            } else if (nums[mid] < target) {
                start = mid
            } else {
                end = mid
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
     * leetCode 658. 找到K 个最接近的元素(middle)
     * 使用模板 2
     */
    fun findClosestElements(arr: IntArray, k: Int, x: Int): List<Int> {
        var start = 0
        //避免越界
        var end = arr.size - k
        while (start < end) {
            val mid = start + (end - start) / 2
            val a = arr[mid]
            val b = arr[mid + k]
            if (x - a > b - x) {
                start = mid + 1
            } else {
                end = mid
            }
        }
        val ans = mutableListOf<Int>()
        for (i in start..(start + k)) {
            ans.add(arr[i])
        }
        return ans
    }

    fun isPerfectSquare(num: Int): Boolean {
        var start = 0L
        var end = num.toLong()
        while (start <= end) {
            val mid = start + (end - start) / 2
            val result = mid * mid
            if (result == num.toLong()) {
                return true
            }
            if (result > num.toLong()) {
                end = mid - 1
            } else {
                start = mid + 1
            }
        }
        return end * end == num.toLong()
    }

    fun nextGreatestLetter(letters: CharArray, target: Char): Char {
        var start = 0
        var end = letters.size - 1
        while (start < end) {
            val mid = start + (end - start) / 2
            if (letters[mid] <= target) {
                start = mid + 1
            } else {
                end = mid
            }
        }
        return if (letters[end] > target) letters[end] else letters[0]
    }

    fun findMin(nums: IntArray): Int {
        var start = 0
        var end = nums.size - 1
        while (start < end) {
            val mid = start + (end - start) / 2
            if (nums[mid] > nums[end]) {
                start = mid + 1
            } else {
                end = mid
            }
        }
        return nums[start]
    }

    fun findMin2(nums: IntArray): Int {
        var start = 0
        var end = nums.size - 1
        while (start < end) {
            val mid = start + (end - start) / 2
            if (nums[mid] > nums[end]) {
                start = mid + 1
            } else if (nums[mid] == nums[end]) {
                end--
            } else {
                end = mid
            }
        }
        return nums[start]
    }

    fun twoSum(numbers: IntArray, target: Int): IntArray {
        var start = 0
        var end = numbers.size - 1
        while (start < end) {
            val sum = numbers[start] + numbers[end]
            if (sum == target) {
                return intArrayOf(start + 1, end + 1)
            }
            if (sum > target) {
                end--
            } else {
                start++
            }
        }
        return intArrayOf()
    }

    fun findDuplicate(nums: IntArray): Int {
        //数组内容是 1到 n ，说明是有序的
        var start = 1
        var end = nums.size - 1
        if (nums[start] == nums[end]) {
            return nums[start]
        }
        while (start < end) {
            val mid = start + (end - start) / 2
            var count = 0
            nums.forEach {
                if (it <= mid) {
                    count++
                }
            }
            if (count > mid){
                //超过了 mid，说明重复的数在 [1, mid] 的范围内
                end = mid
            } else {
                //反之在 [mid + 1 ,end] 内
                start = mid + 1
            }
        }
        return start
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
}