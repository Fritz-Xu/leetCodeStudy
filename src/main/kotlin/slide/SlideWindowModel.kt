package slide

import java.util.*


/**
 * 滑动窗口的算法集合
 */
class SlideWindowModel {

    /**
     * leetCode 239. 滑动窗口最大值(hard)
     *
     * 给你一个整数数组 nums，有一个长度为 k 的滑动窗口，从数组的最左侧移动到数组的最右侧
     * 你只可以看到在滑动窗口的区间内的 k 个数字。滑动窗口每次只向右移动一位
     * 返回滑动窗口在每个位置中的最大值
     *
     * 示例 1：
     * 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
     * 输出：[3,3,5,5,6,7]
     * 解释：
     * 滑动窗口的位置                最大值
     * ---------------               -----
     * [1  3  -1] -3  5  3  6  7       3
     *  1 [3  -1  -3] 5  3  6  7       3
     *  1  3 [-1  -3  5] 3  6  7       5
     *  1  3  -1 [-3  5  3] 6  7       5
     *  1  3  -1  -3 [5  3  6] 7       6
     *  1  3  -1  -3  5 [3  6  7]      7
     *
     * 示例 2：
     * 输入：nums = [1], k = 1
     * 输出：[1]
     *
     * 提示：使用大小为 k 的队列,维持递减,从而快速找到每个窗口的最大值
     */
    fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
        if (nums.isEmpty() || k < 0 || k > nums.size) {
            //先处理好边界
            return intArrayOf()
        }
        if (nums.size == 1 || k == 1) {
            //处理长度为 1 或者 k 为 1的情况
            return nums
        }
        //递减队列并且最大 size 是 k
        val deque: Deque<Int> = LinkedList()
        val res = IntArray(nums.size - k + 1)
        //滑动窗口的左边起点,此时为负数,方便维持 deque 是一个递减的队列
        var startPoint = 1 - k
        //滑动窗口的右边终点
        var endPoint = 0
        while (endPoint < nums.size) {
            // 删除 deque 中对应的 nums[i-1]
            if (startPoint > 0 && deque.peekFirst() == nums[startPoint - 1]) {
                //每次窗口滑动移除了元素 nums[i−1]
                //那么需将 deque 内的对应元素一起删除
                deque.removeFirst()
            }
            // 保持 deque 递减
            // 维持deque是递减队列,这样子第一个元素就是最大值
            while (!deque.isEmpty() && deque.peekLast() < nums[endPoint]) {
                //如果新入队的元素比滑动窗口的起点还要小
                //那么移除刚入队的元素,保持deque是递减队列
                deque.removeLast()
            }
            //入队
            deque.addLast(nums[endPoint])
            // 记录窗口最大值
            if (startPoint >= 0) {
                res[startPoint] = deque.peekFirst()
            }
            //窗口开始移动
            startPoint++
            endPoint++
        }
        return res
    }
}

fun main() {
    val item = SlideWindowModel()
    item.maxSlidingWindow(intArrayOf(7, 2, 4), 2).forEach {
        //[3,3,5,5,6,7]
        print("$it,")
    }
}