package array

/**
 * 给你一个数组 nums和一个值 val，你需要 原地 移除所有数值等于val的元素，并返回移除后数组的新长度。
 *
 * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
 *
 * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
 * 实例:
 * 输入：nums = [3,2,2,3], val = 3
 * 输出：2, nums = [2,2]
 * 解释：函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。
 * 你不需要考虑数组中超出新长度后面的元素。例如，函数返回的新长度为 2 ，
 * 而 nums = [2,2,3,3] 或 nums = [2,2,0,0]，也会被视作正确答案。
 *
 */
class RemoveArrayItems {

    fun removeElement(nums: IntArray, data: Int): Int {
        return nums.filter {
            it == data
        }.size
    }

    /**
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引
     * 如果目标值不存在于数组中，返回它将会被按顺序插入的位置
     * 输入: nums = [1,3,5,6], target = 5
     * 输出: 2
     */
    fun searchInsert(nums: IntArray, target: Int): Int {
        var start = 0
        var end = nums.size
        var mid = (start + end) / 2
        var min = 0
        var index = 0
        while (start <= end) {
            if (mid > nums.size - 1) {
                index = mid
                break
            }
            if (target == nums[mid]) {
                return mid
            }
            if (target < nums[mid]) {
                end = mid - 1
                if (min == 0 || min > nums[mid]) {
                    min = nums[mid]
                    index = mid
                }
            } else {
                start = mid + 1
            }
            mid = (start + end) / 2
        }
        if (target == nums[mid.coerceAtMost(nums.size - 1)]) {
            return mid
        }
        return index
    }

    /**
     * 给你两个按非递减顺序 排列的整数数组nums1 和 nums2，
     * 另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
     *
     * 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
     *
     * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。
     * 为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。
     * nums2 的长度为 n 。
     */
    fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int) {

    }

    /**
     * list1和list2都是从小到大的有序列表，将他们合并为一个列表,并且保持顺序
     *  双指针法
     */
    fun merge(list1: List<Int>, list2: List<Int>): List<Int> {
        val list = mutableListOf<Int>()
        var count = 0
        var index = 0
        while (count < list1.size || index < list2.size) {
            if (count < list1.size && index < list2.size) {
                val check = list1[count] < list2[index]
                list.add(if (check) list1[count] else list2[index])
                if (check) count++ else index++
            } else if (count < list1.size) {
                list.add(list1[count])
                count++
            } else if (index < list2.size) {
                list.add(list2[index])
                index++
            }
        }
        return list
    }

    fun mergeA(nums1: IntArray, m: Int, nums2: IntArray, n: Int) {
        val total = m + n
        val array = IntArray(total)
        var position = 0
        var count = 0
        var index = 0
        while (count < m || index < n) {
            if (count < m && index < n) {
                array[position++] = if (nums1[count] < nums2[index]) nums1[count++] else nums2[index++]
            } else if (count < m) {
                array[position++] = nums1[count++]
            } else {
                array[position++] = nums2[index++]
            }
        }
        System.arraycopy(array, 0, nums1, 0, total)
    }
}

