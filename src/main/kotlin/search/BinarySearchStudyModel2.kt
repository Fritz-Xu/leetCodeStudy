package search


/**
 * 二分查找模板 2
 *
 */
class BinarySearchStudyModel2 {

    /**
     * 模板2
     * 用于查找需要访问数组中当前索引及其直接右邻居索引的元素或条件
     */
    fun binarySearch(nums: IntArray, target: Int): Int {
        if (nums.isEmpty()) {
            return -1
        }
        var start = 0
        var end = nums.size
        while (start < end) {
            val mid = start + (end - start) / 2
            if (nums[mid] == target) {
                //如果不 return 就是寻找边界
                return mid
            }
            if (nums[mid] < target) {
                start = mid + 1
            } else {
                end = mid
            }
        }
        if (start != nums.size && nums[start] == target) {
            return start
        }
        return -1
    }

    /**
     * leetCode 153. 寻找旋转排序数组中的最小值(middle)
     */
    fun findMin(nums: IntArray): Int {
        var start = 0
        var end = nums.size - 1
        while (start < end){
            val  mid = start + (end - start) / 2
            if (nums[mid] < nums[end]){
                end = mid
            } else {
                start = mid + 1
            }
        }
        return  end
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