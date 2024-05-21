package search

import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt


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

    /**
     * leetCode 658. 找到 K 个最接近的元素 (middle)
     * 给定一个 排序好 的数组 arr (升序)，两个整数 k 和 x ，从数组中找到最靠近 x（两数之差最小）的 k 个数。
     * 返回的结果必须要是按升序排好的
     * 整数 a 比整数 b 更接近 x 需要满足：
     * |a - x| < |b - x| 或者
     * |a - x| == |b - x| 且 a < b
     *
     * 示例 1：
     * 输入：arr = [1,2,3,4,5], k = 4, x = 3
     * 输出：[1,2,3,4]
     * 示例 2：
     * 输入：arr = [1,2,3,4,5], k = 4, x = -1
     * 输出：[1,2,3,4]
     *
     * 提示：二分查找符合条件的最左边界
     */
    fun findClosestElements(arr: IntArray, k: Int, x: Int): List<Int> {
        var start = 0
        //要找到 k 个连续元素,因此 end 最大值就是 arr.size - k
        //避免越界
        var end = arr.size - k
        while (start < end) {
            val mid = start + (end - start) / 2
            val a = arr[mid]
            val b = arr[mid + k]
            //寻找最接近 x 的最左边界
            if (x - a > b - x) {
                start = mid + 1
            } else {
                // 下一轮搜索区间是 [left..mid]
                end = mid
            }
        }
        val list = mutableListOf<Int>()
        for (index in start..<start + k) {
            list.add(arr[index])
        }
        return list
    }

    /**
     * leetCode 719. 找出第 k 小的数对距离 (hard)
     * 数对 (a,b) 由整数 a 和 b 组成，其数对距离定义为 a 和 b 的绝对差值。
     * 给你一个整数数组 nums 和一个整数 k ，数对由 nums[i] 和 nums[j] 组成且满足 0 <= i < j < nums.length
     * 返回 所有数对距离中 第 k 小的数对距离
     *
     * 示例 1：
     * 输入：nums = [1,3,1], k = 1
     * 输出：0
     * 解释：数对和对应的距离如下：
     * (1,3) -> 2
     * (1,1) -> 0
     * (3,1) -> 2
     * 距离第 1 小的数对是 (1,1) ，距离为 0 。
     * 示例 2：
     * 输入：nums = [1,1,1], k = 2
     * 输出：0
     * 示例 3：
     * 输入：nums = [1,6,1], k = 3
     * 输出：5
     *
     * 提示：二分 + 双指针查找
     */
    fun smallestDistancePair(nums: IntArray, k: Int): Int {
        //排序,方便二分,也方便锁定区间
        nums.sort()
        var start = 0
        //end 设置为最大的数对距离
        var end = nums.last() - nums[start]
        var ans = 0
        //二分找第k小
        while (start <= end) {
            //对数对的距离，进行二分
            val mid = start + (end - start) / 2
            var position = 0
            var count = 0
            for (index in nums.indices) {
                while (nums[index] - nums[position] > mid) {
                    //如果数对的值比当前二分的数对 mid 要大,说明index位于大数区间
                    //position 前进，要进入数对值小的区间
                    position++
                }
                //统计小数对的数量
                count += index - position
            }
            if (count >= k) {
                //数对的数量大于k了,说明mid 可能就是第 k 小的数对的数量
                ans = mid
                //继续二分查找
                end = mid - 1
            } else {
                start = mid + 1
            }
        }
        return ans
    }

    /**
     * leetCode 786. 第 K 个最小的质数分数(middle)
     * 给你一个按递增顺序排序的数组 arr 和一个整数 k 。数组 arr 由 1 和若干 质数 组成，且其中所有整数互不相同。
     * 对于每对满足 0 <= i < j < arr.length 的 i 和 j ，可以得到分数 arr[i] / arr[j] 。
     * 那么第 k 个最小的分数是多少呢?  以长度为 2 的整数数组返回你的答案, 这里 answer[0] == arr[i] 且 answer[1] == arr[j] 。
     *
     * 示例 1：
     * 输入：arr = [1,2,3,5], k = 3
     * 输出：[2,5]
     * 解释：已构造好的分数,排序后如下所示:
     * 1/5, 1/3, 2/5, 1/2, 3/5, 2/3
     * 很明显第三个最小的分数是 2/5
     * 示例 2：
     * 输入：arr = [1,7], k = 1
     * 输出：[1,7]
     *
     * 提示：二分 + 双指针查找
     */
    fun kthSmallestPrimeFraction(arr: IntArray, k: Int): IntArray {
        var start = 0.0
        var end = 1.0
        var a: Int
        var b: Int
        while (true) {
            val mid = (start + end) / 2
            var cnt: Int
            check(mid, arr).apply {
                a = this[0]
                b = this[1]
                cnt = this[2]
            }
            if (cnt > k) {
                end = mid
            } else if (cnt < k) {
                start = mid
            } else {
                break
            }
        }
        return intArrayOf(a, b)
    }

    private fun check(x: Double, arr: IntArray): IntArray {
        var large = 0.0
        var a = 0
        var b = 0
        var ans = 0
        var start = 0
        var end = 1
        while (end < arr.size) {
            while (arr[start + 1] * 1.0 / arr[end] <= x) {
                start++
            }
            if (arr[start] * 1.0 / arr[end] <= x) {
                ans += start + 1
                if (arr[start] * 1.0 / arr[end] > large) {
                    a = arr[start]
                    b = arr[end]
                    large = arr[start] * 1.0 / arr[end]
                }
            }
            end++
        }
        return intArrayOf(a, b, ans)
    }

    /**
     * leetCode 349. 两个数组的交集(easy)
     * https://leetcode.cn/problems/intersection-of-two-arrays
     */
    fun intersection(nums1: IntArray, nums2: IntArray): IntArray {
        return intersectionBySort(
            if (nums1.size > nums2.size) nums1 else nums2, if (nums1.size > nums2.size) nums2 else nums1
        )
    }

    private fun intersectionBySort(longArr: IntArray, shortArr: IntArray): IntArray {
        longArr.sort()
        val list = mutableListOf<Int>()
        shortArr.forEach { num ->
            if (!list.contains(num)) {
                if (intersectionBinarySearch(longArr, num) != -1) {
                    list.add(num)
                }
            }
        }
        return list.toIntArray()
    }

    private fun intersectionBinarySearch(arr: IntArray, num: Int): Int {
        var start = 0
        var end = arr.size - 1
        while (start <= end) {
            val mid = start + (end - start) / 2
            if (arr[mid] == num) {
                return num
            }
            if (arr[mid] > num) {
                end = mid - 1
            } else {
                start = mid + 1
            }
        }
        return -1
    }

    fun intersect(nums1: IntArray, nums2: IntArray): IntArray {
        val map = mutableMapOf<Int, Int>()
        val list = mutableListOf<Int>()
        for (index in nums1.indices) {
            map[nums1[index]] = map.getOrDefault(nums1[index], 0) + 1
        }
        for (index in nums2.indices) {
            if (map.getOrDefault(nums2[index], 0) != 0) {
                list.add(nums2[index])
                map[nums2[index]] = map[nums2[index]]!! - 1
            }
        }
        val ans = IntArray(list.size)
        for (index in ans.indices) {
            ans[index] = list[index]
        }
        return ans
    }

    /**
     * leetCode 410. 分割数组的最大值(hard)
     * https://leetcode.cn/problems/split-array-largest-sum/
     * 提示：二分找最大
     */
    fun splitArray(nums: IntArray, k: Int): Int {
        var sum = 0;
        var mx = 0
        nums.forEach { s ->
            sum += s
            mx = max(mx, s)
        }
        //锁定二分的范围
        var start = max(mx - 1, ((sum) - 1) / k)
        var end = sum
        while (start + 1 < end) {
            val mid = start + (end - start) / 2
            if (splitArrayCheck(nums, k, mid)) {
                end = mid
            } else {
                start = mid
            }
        }
        return end
    }

    private fun splitArrayCheck(nums: IntArray, k: Int, data: Int): Boolean {
        var cnt = 1
        var s = 0
        for (item in nums) {
            if (s + item <= data) {
                s += item
            } else {
                if (cnt == k) {
                    return false
                }
                cnt += 1
                s = item
            }
        }
        return true
    }

    /**
     * leetCode 1011. 在 D 天内送达包裹的能力（middle）
     * https://leetcode.cn/problems/capacity-to-ship-packages-within-d-days
     */
    fun shipWithinDays(weights: IntArray, days: Int): Int {
        var max = 0
        var sum = 0
        //寻找最低运力,那么[start,ans)必然不满足要求，[ans,end)必然满足要求
        //此时就有二分性
        weights.forEach { s ->
            max = s.coerceAtLeast(max)
            sum += s
        }
        //要满足要求,最低运力就是数组最大值,最大运力就是数组之和
        var start = max
        var end = sum
        while (start < end) {
            val mid = start + (end - start) / 2
            if (shipWithinDaysCheck(weights, mid, days)) {
                end = mid
            } else {
                start = mid + 1
            }
        }
        return end
    }

    private fun shipWithinDaysCheck(weights: IntArray, mid: Int, days: Int): Boolean {
        var ans = 1
        var sum = weights[0]
        var index = 1
        //模拟装货,计算装货的天数
        while (index < weights.size) {
            while (index < weights.size && sum + weights[index] <= mid) {
                sum += weights[index]
                index++
            }
            ans++
            sum = 0
        }
        //判断是否超过了指定时间
        return ans - 1 <= days
    }

    /**
     * leetCode 875. 爱吃香蕉的珂珂(middle)
     * https://leetcode.cn/problems/koko-eating-bananas
     */
    fun minEatingSpeed(piles: IntArray, h: Int): Int {
        var max = 0
        piles.forEach { s ->
            max = s.coerceAtLeast(max)
        }
        //最小速度
        var start = 1
        //最大速度
        var end = max
        while (start < end) {
            val mid = start + (end - start) / 2
            if (minEatingSpeedCheck(piles, mid, h)) {
                end = mid
            } else {
                start = mid + 1
            }
        }
        return end
    }

    private fun minEatingSpeedCheck(piles: IntArray, k: Int, h: Int): Boolean {
        //模拟吃香蕉,能否在 h 个小时内，每小时吃 k 条香蕉，把香蕉吃完
        var sum = 0
        piles.forEach { s ->
            //除法,向上取整
            sum += (s + k - 1) / k
        }
        //判断是不是吃个太快,这时候 end 要 减少了
        return sum < h
    }

    /**
     * leetCode  1385. 两个数组间的距离值(easy)
     * https://leetcode.cn/problems/find-the-distance-value-between-two-arrays
     */
    fun findTheDistanceValue(arr1: IntArray, arr2: IntArray, d: Int): Int {
        var ans = 0
        arr2.sort()
        for (item in arr1) {
            val low = item - d
            val high = item + d
            if (!findTheDistanceValueBinarySearch(arr2, low, high)) {
                ans++
            }
        }
        return ans
    }

    private fun findTheDistanceValueBinarySearch(arr: IntArray, low: Int, high: Int): Boolean {
        var start = 0
        var end = arr.size - 1
        while (start <= end) {
            val mid = start + (end - start) / 2
            if (arr[mid] in low..high) {
                return true
            } else if (arr[mid] < low) {
                start = mid + 1
            } else if (arr[mid] > high) {
                end = mid - 1
            }
        }
        return false
    }

    /**
     *2594. 修车的最少时间(middle)
     * https://leetcode.cn/problems/minimum-time-to-repair-cars
     */
    fun repairCars(ranks: IntArray, cars: Int): Long {
        var min = ranks[0]
        ranks.forEach { s ->
            min = s.coerceAtMost(min)
        }
        var start = 0L
        //修车需要的最长时间
        //这里min.toLong()为了避免损失精度
        var end = min.toLong() * cars * cars
        while (start < end) {
            val mid = start + (end - start) / 2
            var sum = 0L
            ranks.forEach { s ->
                //从公式 总时间 = 能力 * 汽车数量 * 汽车数量
                //因此 时间 / 能力 == 汽车数量 * 汽车数量
                //使用 sqrt 去平方
                sum += sqrt((mid / s.toLong()).toDouble()).toLong()
            }
            if (sum >= cars) {
                end = mid
            } else {
                start = mid + 1
            }
        }
        return end
    }

    /**
     * leetCode 1482. 制作 m 束花所需的最少天数(middle)
     * https://leetcode.cn/problems/minimum-number-of-days-to-make-m-bouquets
     */
    fun minDays(bloomDay: IntArray, m: Int, k: Int): Int {
        if (bloomDay.size / m < k) {
            //判断每束花的数量是否满足要求
            return -1
        }
        var start = bloomDay[0]
        var end = bloomDay[0]
        bloomDay.forEach { s ->
            start = min(s, start)
            end = max(s, end)
        }
        while (start < end) {
            val mid = start + (end - start) / 2
            if (minDaysCheck(bloomDay, mid, m, k)) {
                end = mid
            } else {
                start = mid + 1
            }
        }
        return end
    }

    private fun minDaysCheck(bloomDay: IntArray, days: Int, m: Int, k: Int): Boolean {
        //计算此时开花的数量,能否满足 m 束花,然后每束花有 k 朵
        //统计有几束花
        var ans = 0L
        //统计每束花的数量
        var flowers = 0L
        bloomDay.forEach { count ->
            if (count <= days) {
                //满足了开花的时间
                flowers++
                if (flowers >= k) {
                    //满足了一束花的数量
                    ans++
                    flowers = 0
                }
            } else {
                //不满足连续区间了
                flowers = 0
            }
        }
        return ans >= m
    }

    /**
     * leetCode 3048. 标记所有下标的最早秒数 I(middle)
     * https://leetcode.cn/problems/earliest-second-to-mark-indices-i/
     */
    fun earliestSecondToMarkIndices(nums: IntArray, changeIndices: IntArray): Int {
        if (nums.size > changeIndices.size) {
            return -1
        }
        val lastT = IntArray(nums.size)
        var start = 0
        var end = changeIndices.size + 1
        while (start < end) {
            val mid = start + (end - start) / 2
            //模拟读秒
            if (earliestSecondToMarkIndicesCheck(nums, changeIndices, lastT, mid)) {
                end = mid
            } else {
                start = mid + 1
            }
        }
        return if (end > changeIndices.size) -1 else end
    }

    private fun earliestSecondToMarkIndicesCheck(
        nums: IntArray,
        changeIndices: IntArray,
        lastT: IntArray,
        mid: Int
    ): Boolean {
        Arrays.fill(lastT, -1)
        for (i in 0 until mid) {
            lastT[changeIndices[i] - 1] = i
        }
        for (item in lastT) {
            if (item < 0) {
                return false
            }
        }
        var ans = 0
        for (i in 0 until mid) {
            val idx = changeIndices[i] - 1
            if (i == lastT[idx]) {
                if (nums[idx] > ans) {
                    return false
                }
                ans -= nums[idx]
            } else {
                ans++
            }
        }
        return true
    }

    /**
     * leetCode 2226. 每个小孩最多能分到多少糖果(middle)
     * https://leetcode.cn/problems/maximum-candies-allocated-to-k-children
     */
    fun maximumCandies(candies: IntArray, k: Long): Int {
        //每个孩子可以拿到的糖果数最小是1
        var start = 1L
        //每个孩子可以拿到的最大糖果数
        var end = candies.max().toLong() + 1L
        while (start < end) {
            //二分,得到一个分给孩子糖果的数量
            val mid = start + (end - start) / 2
            //假设给每个孩子分mid数量的糖果,可以分给多少个孩子
            val sum = candies.sumOf { it / mid }
            //能分的孩子比k多,说明k个孩子拿到的糖果不够多
            if (sum >= k) {
                start = mid + 1
            } else {
                end = mid
            }
        }
        //当 left < right 不再成立时，循环结束，返回 left.toInt() - 1，即找到的最大可分配糖果数减1
        //因为 left 已经超过了满足条件的最大值，需要回退一步
        return start.toInt() - 1
    }
}

fun main() {
    val model = BinarySearchModel()
    //println(model.pivotIndex(intArrayOf(-1, 0, 1, -1, 0)))
    println()
    println(model.splitArray(intArrayOf(7, 2, 5, 10, 8), 2))


}



