package link

import java.util.*

/**
 * leetCode 295：数据流的中位
 * 中位数是有序整数列表中的中间值。如果列表的大小是偶数，则没有中间值，中位数是两个中间值的平均值。
 *
 * 例如 arr = [2,3,4] 的中位数是 3 。
 * 例如 arr = [2,3] 的中位数是 (2 + 3) / 2 = 2.5 。
 * 实现 MedianFinder 类:
 * MedianFinder() 初始化 MedianFinder 对象。
 * void addNum(int num) 将数据流中的整数 num 添加到数据结构中。
 * double findMedian() 返回到目前为止所有元素的中位数。与实际答案相差 10-5 以内的答案将被接受
 * 例子：
 * 输入
 * ["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
 * [[], [1], [2], [], [3], []]
 * 输出
 * [null, null, null, 1.5, null, 2.0]
 *
 * 解释
 * MedianFinder medianFinder = new MedianFinder();
 * medianFinder.addNum(1);    // arr = [1]
 * medianFinder.addNum(2);    // arr = [1, 2]
 * medianFinder.findMedian(); // 返回 1.5 ((1 + 2) / 2)
 * medianFinder.addNum(3);    // arr[1, 2, 3]
 * medianFinder.findMedian(); // return 2.0
 *
 * 思路：
 * 解决这道题需要用到排序，但随着元素增多，排序耗时就越久
 * 解决方案：大小顶堆
 * 把元素分为两组，小得元素在小顶堆，大元素在大顶堆，性能提高一半以上
 */
class MedianFinder {
    /**
     * 出队时默认从大到小排序,这是大顶堆,保存较大的一半
     */
    private val bigList = PriorityQueue<Int>()

    /**
     *  出队时从小到大排序,这是小顶堆,保存较小的一半
     */
    private val smallList = PriorityQueue<Int> { x, y -> y - x }

    fun addNum(num: Int) {
        if (smallList.size != bigList.size) {
            //排序
            bigList.add(num)
            //拿到bigList的最大值
            smallList.add(bigList.poll())
        } else {
            //排序
            smallList.add(num)
            //拿到smallList里面的最小值
            bigList.add(smallList.poll())
        }
    }

    fun findMedian(): Double {
        //smallList.size != bigList.size 奇数
        return if (bigList.size != smallList.size) {
            (bigList.peek() ?: -1) / 1.0
        } else {
            //相同size,偶数
            (bigList.peek() + smallList.peek()) / 2.0
        }
    }
}