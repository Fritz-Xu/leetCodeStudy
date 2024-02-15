package sort

import java.util.Collections


/**
 * 这里收集各种排序的方法
 */
class SortModel {

    /**
     * 归并排序,基于动态规划的一种排序
     * 下面看下如何将一个数组划分为左右子数组,然后合并左子数组和右子数组
     */
    private fun merge(nums: IntArray, left: Int, mid: Int, right: Int) {
        // 左子数组区间 [left, mid], 右子数组区间 [mid+1, right]
        // 创建一个临时数组 tmp ，用于存放合并后的结果
        val tmp = IntArray(right - left + 1)
        // 初始化左子数组和右子数组的起始索引
        var startIndex = left
        var centerIndex = mid + 1
        var position = 0
        // 当左右子数组都还有元素时，比较并将较小的元素复制到临时数组中
        while (startIndex <= mid && centerIndex <= right) {
            if (nums[startIndex] <= nums[centerIndex]) {
                tmp[position++] = nums[startIndex++]
            } else {
                tmp[position++] = nums[centerIndex++]
            }
        }
        // 将左子数组和右子数组的剩余元素复制到临时数组中
        while (startIndex <= mid) {
            tmp[position++] = nums[startIndex++]
        }
        while (centerIndex <= right) {
            tmp[position++] = nums[centerIndex++]
        }
        println("--------------")
        println("start:$left , mid:$mid,right:$right")
        tmp.forEach {
            print("$it,")
        }
        println()
        // 将临时数组 tmp 中的元素复制回原数组 nums 的对应区间
        position = 0
        while (position < tmp.size) {
            nums[left + position] = tmp[position]
            position++
        }
    }

    /* 归并排序 */
    fun mergeSort(nums: IntArray, left: Int, right: Int) {
        // 终止条件
        if (left >= right) {
            // 当子数组长度为 1 时终止递归
            return
        }
        // 划分阶段
        // 计算中点
        val mid = (left + right) / 2
        // 递归左子数组
        mergeSort(nums, left, mid)
        // 递归右子数组
        mergeSort(nums, mid + 1, right)
        // 合并阶段
        merge(nums, left, mid, right)
    }

    /**
     * 桶排序
     */
    fun bucketSort(nums: FloatArray) {
        val length = nums.size
        val k = length / 2
        //桶集合
        val buckets = mutableListOf<ArrayList<Float>>()
        repeat(k) {
            buckets.add(ArrayList())
        }
        //1.将元素分配到各个桶里面
        for (item in nums) {
            //这里 item * k 映射 [0,k-1] 这个区间
            val key = (item * k).toInt()
            //把 item 添加进第 key 个 桶里面
            buckets[key].add(item)
        }
        //2.对每个桶进行排序
        for (bucket in buckets) {
            //暂时使用内置的排序算法
            bucket.sort()
        }
        //3.遍历桶,合并结果
        var count = 0
        for (bucket in buckets) {
            for (item in bucket) {
                nums[count++] = item
            }
        }
    }
}

fun main() {
    val sort = SortModel()
    //val nums = intArrayOf(7, 3, 2, 6, 0, 1, 5, 4)
    val nums = intArrayOf(7, 3)
    sort.mergeSort(nums, 0, 1)
    println()
    print("end:")
    nums.forEach {
        print("$it,")
    }
}