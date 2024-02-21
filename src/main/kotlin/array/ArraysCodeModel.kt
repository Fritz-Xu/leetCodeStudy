package array

import java.util.*
import kotlin.collections.HashSet
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class ArraysCodeModel {


    /**
     * leetCode 11. 盛最多水的容器(middle)
     * 给定一个长度为 n 的整数数组 height
     * 有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i])
     * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水
     * 返回容器可以储存的最大水量
     * 说明：你不能倾斜容器
     *
     * 示例 1：
     * 输入：[1,8,6,2,5,4,8,3,7]
     * 输出：49
     * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
     * 示例 2：
     * 输入：height = [1,1]
     * 输出：1
     *
     */
    fun maxArea(height: IntArray): Int {
        var start = 0
        var end = height.size - 1
        var ans = 0
        while (start != end) {
            val result = (end - start) * height[start].coerceAtMost(height[end])
            if (ans < result) {
                ans = result
            }
            if (height[start] > height[end]) {
                end--
            } else {
                start++
            }
        }
        return ans
    }

    /**
     * leetCode 16. 最接近的三数之和(middle)
     * 给你一个长度为 n（在 3 到 1000之间）的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。
     * 返回这三个数的和
     * 假定每组输入只存在恰好一个解。
     *
     * 示例 1：
     * 输入：nums = [-1,2,1,-4], target = 1
     * 输出：2
     * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
     * 示例 2：
     * 输入：nums = [0,0,0], target = 1
     * 输出：0
     *
     */
    fun threeSumClosest(nums: IntArray, target: Int): Int {
        if (nums.size == 3) {
            return nums.sum()
        }
        nums.sort()
        var ans = nums[0] + nums[1] + nums[2]
        for (index in 0..<nums.size - 1) {
            if (index > 0 && nums[index] == nums[index - 1]) {
                //因为只存在唯一解,因此跳过相同的数据
                continue
            }
            var start = index + 1
            var end = nums.size - 1
            while (start < end) {
                val sum = nums[index] + nums[start] + nums[end]
                if (abs(sum - target) < abs(ans - target)) {
                    //说明 sum 更加接近 target
                    ans = sum
                }
                if (sum > target) {
                    end--
                } else if (sum < target) {
                    start++
                } else {
                    return target
                }
            }
        }
        return ans
    }

    /**
     * leetCode 16. 四数之和(middle)
     * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。
     * 请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]]
     * （若两个四元组元素一一对应，则认为两个四元组重复）
     * 0 <= a, b, c, d < n
     * a、b、c 和 d 互不相同
     * nums[a] + nums[b] + nums[c] + nums[d] == target
     * 你可以按 任意顺序 返回答案
     *
     * 示例 1：
     * 输入：nums = [1,0,-1,0,-2,2], target = 0
     * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
     * 示例 2：
     * 输入：nums = [2,2,2,2,2], target = 8
     * 输出：[[2,2,2,2]]
     *
     */
    fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
        Arrays.sort(nums)
        val ans: MutableList<List<Int>> = ArrayList()
        //避免出现越界的小习惯
        val size = nums.size
        for (a in 0..<size - 3) {
            // 枚举第一个数,使用 long 避免溢出 或者损失精度
            val itemFirst = nums[a].toLong()
            if (a > 0 && itemFirst.toInt() == nums[a - 1]
                // 跳过重复数字
                || itemFirst + nums[size - 3] + nums[size - 2] + nums[size - 1] < target
            //数组排序后,后面 3 个元素为最大值,但与 itemFirst 都比目标小
            //说明 itemFirst 与后面的连续 3 个元素相加也肯定比目标小
            ) {
                continue
            }
            if (itemFirst + nums[a + 1] + nums[a + 2] + nums[a + 3] > target) {
                //数组排序后,连续 4个元素之和都比目标大了，后面也不会无法找打符合要求的组合了
                break
            }
            for (b in a + 1..<size - 2) {
                // 枚举第二个数,使用 long 避免溢出 或者损失精度
                val itemSecond = nums[b].toLong()
                if (b > a + 1 && itemSecond.toInt() == nums[b - 1]
                    // 跳过重复数字
                    || itemFirst + itemSecond + nums[size - 2] + nums[size - 1] < target
                //数组排序后,后面 2 个元素为最大值,但与 itemFirst,itemSecond 相加都比目标小
                //说明 itemFirst，itemSecond 与后面的连续元素相加也肯定比目标小
                ) {
                    continue
                }
                if (itemFirst + itemSecond + nums[b + 1] + nums[b + 2] > target) {
                    //数组排序后,连续 4个元素之和都比目标大了，后面也不会无法找打符合要求的组合了
                    break
                }
                // 双指针枚举第三个数和第四个数
                var c = b + 1
                var d = size - 1
                while (c < d) {
                    // 四数之和
                    val sum = itemFirst + itemSecond + nums[c] + nums[d]
                    if (sum > target) {
                        d--
                    } else if (sum < target) {
                        c++
                    } else {
                        //找到目标了
                        ans.add(listOf(itemFirst.toInt(), itemSecond.toInt(), nums[c], nums[d]))
                        c++
                        while (c < d && nums[c] == nums[c - 1]) {
                            // 跳过重复数字
                            c++
                        }
                        d--
                        while (d > c && nums[d] == nums[d + 1]) {
                            // 跳过重复数字
                            d--
                        }
                    }
                }
            }
        }
        return ans
    }

    /**
     * leetCode 287. 寻找重复数(middle)
     * 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
     * 假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
     * 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间,也就是禁止使用 map 和 Set,也禁止修改数组
     *
     * 示例 1：
     * 输入：nums = [1,3,4,2,2]
     * 输出：2
     *
     * 示例 2：
     * 输入：nums = [3,1,3,4,2]
     * 输出：3
     *
     * 提示:本质是leetCode 142. 环形链表 II（middle）的变种, See [LinkModel.detectCycle]
     * 考虑建立一个 n 个节点的链表：
     * n 个下标的值的节点：1 , 2 , ⋯⋯ , n ；
     * 每个下标节点 ，其 next 引用指向节点 nums[1]nums[2]nums[3]
     * 如: 1->nums[1](值为2)->nums[2](值为 3)->nums[3](值为 2)->nums[2]
     * 转为了链表,如果有重复的元素,那么就是一个环形链表
     */
    fun findDuplicate(nums: IntArray): Int {
        var slow = 0
        var fast = 0

        while (true) {
            slow = nums[slow]
            fast = nums[nums[fast]]
            if (slow == fast) {
                //第一次相遇
                break
            }
        }
        fast = 0
        while (fast != slow) {
            fast = nums[fast]
            slow = nums[slow]
        }
        return fast
    }

    /**
     * leetCode 215. 数组中的第K个最大元素(middle)
     * 给定整数数组 nums 和整数 k(1 <= k <= nums.length <= 105)，请返回数组中第 k 个最大的元素。
     * 请注意，你需要找的是数组排序后的第 k 个最大的元素(可能是重复元素)，而不是第 k 个不同的元素。
     * 你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
     *
     * 示例 1:
     * 输入: [3,2,1,5,6,4], k = 2
     * 输出: 5
     *
     * 示例 2:
     * 输入: [3,2,3,1,2,4,5,5,6], k = 4
     * 输出: 4
     *
     */
    fun findKthLargest(nums: IntArray, k: Int): Int {
        return nums.sorted()[nums.size - k]
    }

    /**
     * leetCode 283. 移动零（easy）
     *
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
     *
     * 示例 1:
     * 输入: nums = [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     * 示例 2:
     * 输入: nums = [0]
     * 输出: [0]
     *
     *提示：快慢指针法
     *
     */
    fun moveZeroes(nums: IntArray) {
        var zeroCount = 0
        var intCount = 0
        for (index in 0 until nums.size) {
            if (intCount + zeroCount == nums.size) {
                break
            }
            if (nums[index] == 0) {
                zeroCount++
                continue
            }
            nums[intCount] = nums[index]
            intCount++
        }
        repeat(zeroCount) {
            nums[nums.size - 1 - it] = 0
        }
    }

    /**
     * leetCode 344. 反转字符串（easy）
     *
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题
     * 示例 1：
     * 输入：s = ['h','e','l','l','o']
     * 输出：['o','l','l','e','h']
     * 示例 2：
     * 输入：s = ['H','a','n','n','a','h']
     * 输出：['h','a','n','n','a','H']
     *
     *提示：快慢指针法
     *
     */
    fun reverseString(s: CharArray) {
        repeat(s.size / 2) { index ->
            val preview = s[index]
            val end = s[s.size - 1 - index]
            s[index] = end
            s[s.size - 1 - index] = preview
        }
    }

    /**
     * leetCode 2706. 购买两块巧克力（easy）
     * 给你一个整数数组 prices ，它表示一个商店里若干巧克力的价格。同时给你一个整数 money ，表示你一开始拥有的钱数。
     * 你必须购买 恰好 两块巧克力，而且剩余的钱数必须是 非负数 。同时你想最小化购买两块巧克力的总花费。
     * 请你返回在购买两块巧克力后，最多能剩下多少钱。如果购买任意两块巧克力都超过了你拥有的钱，请你返回 money 。注意剩余钱数必须是非负数。
     *
     * 示例 1：
     * 输入：prices = [1,2,2], money = 3
     * 输出：0
     * 解释：分别购买价格为 1 和 2 的巧克力。你剩下 3 - 3 = 0 块钱。所以我们返回 0
     *
     * 示例 2：
     * 输入：prices = [3,2,3], money = 3
     * 输出：3
     * 解释：购买任意 2 块巧克力都会超过你拥有的钱数，所以我们返回 3
     */
    fun buyChoco(prices: IntArray, money: Int): Int {
        if (prices.size < 2) {
            return money
        }
        prices.sort()
        if (prices[0] + prices[1] > money) {
            return money
        }
        var count = 0
        var cost = 0
        while (cost < money && count < 2) {
            cost += prices[count++]
        }
        return money - cost
    }

    /**
     * leetCode 2397. 被列覆盖的最多行数（middle）
     *
     * 给你一个下标从 0 开始、大小为 m x n (1 <= m, n <= 12)的二进制矩阵 matrix(只有 0 和 1组成) ；
     * 另给你一个整数 numSelect，表示你必须从 matrix 中选择的 不同 列的数量
     * 如果一行中所有的 1 都被你选中的列所覆盖，则认为这一行被 覆盖 了
     * 形式上，假设 s = {c1, c2, ...., cnumSelect} 是你选择的列的集合。
     * 对于矩阵中的某一行 row ，如果满足下述条件，则认为这一行被集合 s 覆盖：
     * 对于满足 matrix[row][col] == 1 的每个单元格 matrix[row][col]（0 <= col <= n - 1），col 均存在于 s 中，或者
     * row 中 不存在 值为 1 的单元格。
     * 你需要从矩阵中选出 numSelect 个列，使集合覆盖的行数最大化。
     * 返回一个整数，表示可以由 numSelect 列构成的集合 覆盖 的 最大行数 。
     *
     * 示例 1：
     * 输入：matrix = [[0,0,0],[1,0,1],[0,1,1],[0,0,1]], numSelect = 2
     * 输出：3
     * 解释：
     * 图示中显示了一种覆盖 3 行的可行办法。
     * 选择 s = {0, 2} 。
     * - 第 0 行被覆盖，因为其中没有出现 1 。
     * - 第 1 行被覆盖，因为值为 1 的两列（即 0 和 2）均存在于 s 中。
     * - 第 2 行未被覆盖，因为 matrix[2][1] == 1 但是 1 未存在于 s 中。
     * - 第 3 行被覆盖，因为 matrix[2][2] == 1 且 2 存在于 s 中。
     * 因此，可以覆盖 3 行。
     * 另外 s = {1, 2} 也可以覆盖 3 行，但可以证明无法覆盖更多行。
     *
     * 示例 2：
     * 输入：matrix = [[1],[0]], numSelect = 1
     * 输出：2
     * 解释：
     * 选择唯一的一列，两行都被覆盖了，因为整个矩阵都被覆盖了。
     * 所以我们返回 2 。
     */
    fun maximumRows(matrix: Array<IntArray>, numSelect: Int): Int {
        val m = matrix.size
        val n = matrix[0].size
        val mask = IntArray(m)
        for (i in 0..<m) {
            for (j in 0..<n) {
                mask[i] = mask[i] or (matrix[i][j] shl j)
            }
        }
        var ans = 0
        for (subset in 0..<(1 shl n)) {
            if (Integer.bitCount(subset) == numSelect) {
                var coveredRows = 0
                for (row in mask) {
                    if ((row and subset) == row) {
                        coveredRows++
                    }
                }
                ans = max(ans, coveredRows)
            }
        }
        return ans
    }

    /**
     * leetCode 1944. 队列中可以看到的人数(easy)
     * 有 n 个人排成一个队列，从左到右 编号为 0 到 n - 1
     * 给你以一个整数数组 heights ，每个整数 互不相同，heights[i] 表示第 i 个人的高度
     *
     * 一个人能 看到 他右边另一个人的条件是这两人之间的所有人都比他们两人 矮
     * 更正式的，第 i 个人能看到第 j 个人的条件是 i < j 且 min(heights[i], heights[j]) > max(heights[i+1], heights[i+2], ..., heights[j-1]) 。
     *
     * 请你返回一个长度为 n 的数组 answer ，其中 answer[i] 是第 i 个人在他右侧队列中能 看到 的 人数
     *
     * 示例 1：
     * 输入：heights = [10,6,8,5,11,9]
     * 输出：[3,1,2,1,1,0]
     * 解释：
     * 第 0 个人能看到编号为 1 ，2 和 4 的人。
     * 第 1 个人能看到编号为 2 的人。
     * 第 2 个人能看到编号为 3 和 4 的人。
     * 第 3 个人能看到编号为 4 的人。
     * 第 4 个人能看到编号为 5 的人。
     * 第 5 个人谁也看不到因为他右边没人。
     * 示例 2：
     * 输入：heights = [5,1,2,3,10]
     * 输出：[4,1,1,1,0]
     *
     * 提示：循环 + 高度判断与统计,只有区间内的身高都小于区间值，才可以看到
     * 如
     * [8,9,11] 8 只可以看到 9，看不到 11
     * [8,5,11] 8 可以看到 5 和 11
     */
    fun canSeePersonsCount(heights: IntArray): IntArray {
        if (heights.isEmpty() || heights.size == 1) {
            //没有人或者 1个人,都看不到别人
            return intArrayOf(0)
        }
        if (heights.size == 2) {
            //2个人,只能看到对方
            return intArrayOf(1, 0)
        }
        //余下就是超过2个人的情况了
        val ans = IntArray(heights.size)
        val st = LinkedList<Int>()
        //队伍都往右边看,身高高的可以挡住视线,因此使用倒序
        for (index in heights.size - 1 downTo 0) {
            val height = heights[index]
            //区间内的身高都小于区间值，才可以看到
            //[8,9,11] 8 只可以看到 9，看不到 11
            //[8,5,11] 8 可以看到 5 和 11
            while (st.isNotEmpty() && height > st.peek()) {
                //当前height比前一个人高,必然可以看到
                st.pop()
                ans[index]++
            }
            if (!st.isEmpty()) {
                //不是队尾最后一个,那么肯定右边至少有一个人
                //因此未尾数都加1
                ans[index]++
            }
            //入队
            st.push(height)
        }
        return ans
    }

    /**
     * leetCode 1094. 拼车(middle)
     * 车上最初有 capacity(1 <= capacity <= 10^5) 个空座位。车 只能 向一个方向行驶（也就是说，不允许掉头或改变方向）
     * 给定整数 capacity 和一个数组 trips(1 <= trips.length <= 1000)
     * trip[i] = [numPassengersi, fromi, toi] 表示第 i 次旅行有 numPassengersi 乘客，接他们和放他们的位置分别是 fromi 和 toi
     * 这些位置是从汽车的初始位置向东的公里数
     * 当且仅当你可以在所有给定的行程中接送所有乘客时，返回 true，否则请返回 false
     *
     * 示例 1：
     * 输入：trips = [[2,1,5],[3,3,7]], capacity = 4
     * 输出：false
     * 示例 2：
     * 输入：trips = [[2,1,5],[3,3,7]], capacity = 5
     * 输出：true
     *
     */
    fun carPooling(trips: Array<IntArray>, capacity: Int): Boolean {
        //最多 1001 个车站
        val nums = IntArray(1001)
        //引入差分数组
        val diff = ArrayDifference(nums)
        for (trip in trips) {
            //区间变化的人数
            val change = trip[0]
            //起点，上车站点
            val startIndex = trip[1]
            //trip[2]是下车的站点
            //那么乘客在车上区间是[trip[1],trip[2] - 1]
            val endIndex = trip[2] - 1
            diff.increment(startIndex, endIndex, change)
        }
        //更新数组的差分结果
        val result = diff.result()
        for (item in result) {
            //差分结果出现了超载
            if (item > capacity) {
                return false
            }
        }
        return true
    }

    /**
     * leetCode 447. 回旋镖的数量(middle)
     * 给定平面上 n (1 <= n <= 500) 对 互不相同 的点 points(每组长度固定为2) ，其中 points[i] = [x, y]
     * 回旋镖 是由点 (i, j, k) 表示的元组 ，其中 i 和 j 之间的距离和 i 和 k 之间的欧式距离相等（需要考虑元组的顺序）
     * 返回平面上所有回旋镖的数量
     *
     * 示例 1：
     * 输入：points = [[0,0],[1,0],[2,0]]
     * 输出：2
     * 解释：两个回旋镖为 [[1,0],[0,0],[2,0]] 和 [[1,0],[2,0],[0,0]]
     * 示例 2：
     * 输入：points = [[1,1],[2,2],[3,3]]
     * 输出：2
     * 示例 3：
     * 输入：points = [[1,1]]
     * 输出：0
     *
     * 提示：使用哈希记录距离
     */
    fun numberOfBoomerangs(points: Array<IntArray>): Int {
        if (points.size <= 2) {
            //需要3组数组才可以组成回旋镖
            return 0
        }
        var ans = 0
        //key是距离,value是有这个距离的数量
        val map = mutableMapOf<Int, Int>()
        for (p1 in points) {
            map.clear()
            for (p2 in points) {
                //计算欧式距离 distance = (x1-x2)^2 + (y1 -y2)^2 ,算出两点直线距离
                val d2 = (p1[0] - p2[0]) * (p1[0] - p2[0]) + (p1[1] - p2[1]) * (p1[1] - p2[1])
                //查看map是否存下这个距离为key,就有拿出来
                val c = map.getOrDefault(d2, 0)
                //从提示可知,发现一组回旋镖数组,就有2个回旋镖
                ans += c * 2
                //数量+1
                map[d2] = c + 1
            }
        }
        return ans
    }

    /**
     * leetCode 2707. 字符串中的额外字符(middle)
     *
     * 给你一个下标从 0 开始的字符串 s 和一个单词字典 dictionary 。
     * 你需要将 s 分割成若干个 互不重叠 的子字符串，每个子字符串都在 dictionary 中出现过。
     * s 中可能会有一些 额外的字符 不在任何子字符串中。
     * 请你采取最优策略分割 s ，使剩下的字符 最少 。
     *
     * 示例 1：
     * 输入：s = "leetscode", dictionary = ["leet","code","leetcode"]
     * 输出：1
     * 解释：将 s 分成两个子字符串：下标从 0 到 3 的 "leet" 和下标从 5 到 8 的 "code" 。只有 1 个字符没有使用（下标为 4），所以我们返回 1 。
     * 示例 2：
     * 输入：s = "sayhelloworld", dictionary = ["hello","world"]
     * 输出：3
     * 解释：将 s 分成两个子字符串：下标从 3 到 7 的 "hello" 和下标从 8 到 12 的 "world" 。下标为 0 ，1 和 2 的字符没有使用，所以我们返回 3 。
     *
     * 提示：动态规划
     */
    fun minExtraChar(s: String, dictionary: Array<String>): Int {
        val set = mutableSetOf<String>()
        dictionary.forEach {
            set.add(it)
        }
        val find = IntArray(s.length + 1)
        repeat(s.length) { index ->
            find[index + 1] = find[index] + 1
            println()
            repeat(index + 1) { position ->
                val substring = s.substring(position, index + 1)
                if (set.contains(substring)) {
                    find[index + 1] = find[index + 1].coerceAtMost(find[position])
                }
            }
        }
        return find[s.length]
    }

    /**
     * leetCode 2182. 构造限制重复的字符串(middle)
     * 给你一个字符串 s (s 由小写英文字母组成)和一个整数 repeatLimit
     * 用 s 中的字符构造一个新字符串 repeatLimitedString,使任何字母 连续 出现的次数都不超过 repeatLimit 次,你不必使用 s 中的全部字符
     * 返回 字典序最大的 repeatLimitedString
     * 如果在字符串 a 和 b 不同的第一个位置，字符串 a 中的字母在字母表中出现时间比字符串 b 对应的字母晚，则认为字符串 a 比字符串 b 字典序更大 。
     * 如果字符串中前 min(a.length, b.length) 个字符都相同，那么较长的字符串字典序更大。
     *
     * 示例 1：
     * 输入：s = "cczazcc", repeatLimit = 3
     * 输出："zzcccac"
     * 解释：使用 s 中的所有字符来构造 repeatLimitedString "zzcccac"。
     * 字母 'a' 连续出现至多 1 次。
     * 字母 'c' 连续出现至多 3 次。
     * 字母 'z' 连续出现至多 2 次。
     * 因此，没有字母连续出现超过 repeatLimit 次，字符串是一个有效的 repeatLimitedString 。
     * 该字符串是字典序最大的 repeatLimitedString ，所以返回 "zzcccac" 。
     * 注意，尽管 "zzcccca" 字典序更大，但字母 'c' 连续出现超过 3 次，所以它不是一个有效的 repeatLimitedString 。
     * 示例 2：
     * 输入：s = "aababab", repeatLimit = 2
     * 输出："bbabaa"
     * 解释：
     * 使用 s 中的一些字符来构造 repeatLimitedString "bbabaa"。
     * 字母 'a' 连续出现至多 2 次。
     * 字母 'b' 连续出现至多 2 次。
     * 因此，没有字母连续出现超过 repeatLimit 次，字符串是一个有效的 repeatLimitedString 。
     * 该字符串是字典序最大的 repeatLimitedString ，所以返回 "bbabaa" 。
     * 注意，尽管 "bbabaaa" 字典序更大，但字母 'a' 连续出现超过 2 次，所以它不是一个有效的 repeatLimitedString 。
     *
     *
     * 提示：
     */
    fun repeatLimitedString(s: String, repeatLimit: Int): String {
        val ans = IntArray(26)
        s.forEach { char ->
            ans[char - 'a'] += 1
        }
        val builder = StringBuilder()
        //使用双指针
        var firstPoint = ans.size - 1
        var secondPoint = ans.size - 2
        //计数，判断是否超出了repeatLimit
        var insertCount = 0
        while (firstPoint >= 0 && secondPoint >= 0) {
            if (ans[firstPoint] == 0) {
                // 当前字符已经填完,填入后面的字符,重置 insertCount
                insertCount = 0
                firstPoint--
            } else if (insertCount < repeatLimit) {
                //重复字符没有超出限制
                //字符数量减 1
                ans[firstPoint]--
                builder.append(('a' + firstPoint))
                insertCount++
            } else if (secondPoint >= firstPoint || ans[secondPoint] == 0) {
                //重复字符超出了限制
                //启动第二个指针,找到第二个符合要求的字符
                secondPoint--
            } else {
                //当前字符已经超过了限制,填入其他字符,重置insertCount
                ans[secondPoint]--
                builder.append(('a' + secondPoint))
                insertCount = 0
            }
        }
        return builder.toString()
    }

    /**
     * leetCode 1109. 航班预订统计(middle)
     * 这里有 n 个航班，它们分别从 1 到 n 进行编号
     *
     * 有一份航班预订表 bookings ，表中第 i 条预订记录 bookings[i] = [first_i, last_i, seats_i]
     * 意味着在从 first_i 到 last_i （包含 first_i 和 last_i ）的 每个航班 上预订了 seats_i 个座位
     * 请你返回一个长度为 n 的数组 answer，里面的元素是每个航班预定的座位总数
     *
     * 示例 1：
     * 输入：bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
     * 输出：[10,55,45,25,25]
     * 解释：
     * 航班编号        1   2   3   4   5
     * 预订记录 1 ：   10  10
     * 预订记录 2 ：       20  20
     * 预订记录 3 ：       25  25  25  25
     * 总座位数：      10  55  45  25  25
     * 因此，answer = [10,55,45,25,25]
     * 示例 2：
     *
     * 输入：bookings = [[1,2,10],[2,2,15]], n = 2
     * 输出：[10,25]
     * 解释：
     * 航班编号        1   2
     * 预订记录 1 ：   10  10
     * 预订记录 2 ：       15
     * 总座位数：      10  25
     * 因此，answer = [10,25]
     */
    fun corpFlightBookings(bookings: Array<IntArray>, n: Int): IntArray {
        val nums = IntArray(n)
        val df = ArrayDifference(nums)
        bookings.forEach { book ->
            val start = book[0] - 1
            val end = book[1] - 1
            val change = book[2]
            df.increment(start, end, change)
        }
        return df.result()
    }

    fun removeDuplicates(nums: IntArray): Int {
        val length = nums.size
        var position = 0
        repeat(length) { index ->
            if (nums[index] != nums[position]) {
                position++
                nums[position] = nums[index]
            }
        }
        return position + 1
    }


    /**
     * leetCode 45. 跳跃游戏 II(middle)
     * 给定一个长度为 n 的 0 索引整数数组 nums。初始位置为 nums[0]
     * 每个元素 nums[i] 表示从索引 i 向前跳转的最大长度
     * 换句话说，如果你在 nums[i] 处，你可以跳转到任意 nums[i + j] 处:
     * 0 <= j <= nums[i]
     * i + j < n
     * 返回到达 nums[n - 1] 的最小跳跃次数。生成的测试用例可以到达 nums[n - 1]
     *
     *
     * 示例 1:
     * 输入: nums = [2,3,1,1,4]
     * 输出: 2
     * 解释: 跳到最后一个位置的最小跳跃数是 2。
     *      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置
     *
     * 示例 2:
     * 输入: nums = [2,3,0,1,4]
     * 输出: 2
     *
     * 提示:双指针 + 贪心 + 动态规划
     */
    fun jump(nums: IntArray): Int {
        val length = nums.size
        //双指针 j，用于记录能够跳跃到当前位置 i 的最远位置
        var j = 0
        //记录到达每个位置所需的最小跳跃次数
        val ans = IntArray(length)
        for (i in 1..<length) {
            //不断向右移动 j，直到 j + nums[j] 大于等于 i
            //表示能够跳跃到当前位置 i
            while (j + nums[j] < i) {
                j++
            }
            //记录跳到i 需要的最少步数
            ans[i] = ans[j] + 1
        }
        return ans[length - 1]
    }

    /**
     * leetCode 413. 等差数列划分 (middle)
     * 如果一个数列 至少有三个元素 ，并且任意两个相邻元素之差相同，则称该数列为等差数列。
     *
     * 例如，[1,3,5,7,9]、[7,7,7,7] 和 [3,-1,-5,-9] 都是等差数列。
     * 给你一个整数数组 nums（1 <= nums.length <= 5000，最小最大值是 -1000 ～ 1000）
     * 返回数组 nums 中所有为等差数组的 子数组 个数
     * 子数组 是数组中的一个连续序列
     *
     * 示例 1：
     * 输入：nums = [1,2,3,4]
     * 输出：3
     * 解释：nums 中有三个子等差数组：[1, 2, 3]、[2, 3, 4] 和 [1,2,3,4] 自身。
     * 示例 2：
     * 输入：nums = [1]
     * 输出：0
     *
     * 提示：动态规划基础题目
     * 动态规划，状态转移方程为dp[i] = dp[i - 1] + 1
     */
    fun numberOfArithmeticSlices(nums: IntArray): Int {
        if (nums.size < 3) {
            return 0
        }
        //纪录每个序列的等差数组的数量
        val dp = IntArray(nums.size)
        var ans = 0
        //序列从 2 开始是因为满足等差数组大于等于 3 的要求
        for (index in 2..<nums.size) {
            //是否连续的 3 个元素组成了等差数组
            if (nums[index] - nums[index - 1] == nums[index - 1] - nums[index - 2]) {
                //让dp[index] 纪录 index 这个下标的可以形成的等差数组的个数
                dp[index] = dp[index - 1] + 1
                //更新等差数组的总数
                ans += dp[index]
            }
        }
        return ans
    }

    /**
     * leetCode 300. 最长递增子序列 (middle)
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。
     * 例如，[0,3,6,7] 是数组 [0,3,1,6,2,2,7] 的最长严格递增子序列，而 [3,1,6,7] 是普通子序列
     *
     * 示例 1：
     * 输入：nums = [10,9,2,5,3,7,101,18]
     * 输出：4
     * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4
     * 示例 2：
     * 输入：nums = [0,1,0,3,2,3]
     * 输出：4
     * 示例 3：
     * 输入：nums = [7,7,7,7,7,7,7]
     * 输出：1
     *
     * 提示：动态规划基础
     */
    fun lengthOfLIS(nums: IntArray): Int {
        if (nums.isEmpty()) {
            return 0
        }
        //记录以每个位置为结尾的最长递增子序列的长度
        val dp = IntArray(nums.size)
        var ans = 0
        Arrays.fill(dp, 1)
        for (index in 0..nums.lastIndex) {
            var position = 0
            while (position < index) {
                //从 0 开始往右边移动指针(这里耗时)
                if (nums[position] < nums[index]) {
                    //说明当前 position 和 index 是递增元素的下标
                    dp[index] = max(dp[index], dp[position] + 1)
                }
                position++
            }
            //记录当前的最大长度
            ans = max(ans, dp[index])
        }
        return ans
    }

    /**
     * leetCode 53. 最大和的连续子数组 (middle)
     * 给你一个整数数组 nums(1<= nums.size <= 50k) ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * 子数组 是数组中的一个连续部分。
     *
     * 示例 1：
     * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出：6
     * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
     * 示例 2：
     * 输入：nums = [1]
     * 输出：1
     * 示例 3：
     * 输入：nums = [5,4,-1,7,8]
     * 输出：23
     *
     * 提示：动态规划基础
     */
    fun maxSubArray(nums: IntArray): Int {
        if (nums.isEmpty()) {
            return 0
        }
        if (nums.size == 1) {
            return nums[0]
        }
        var ans = 0
        //纪录每个下标作为区间 end 时，和的最大值
        val dp = IntArray(nums.size)
        dp[0] = nums[0]
        for (index in 1..nums.lastIndex) {
            if (dp[index - 1] > 0) {
                dp[index] = dp[index - 1] + nums[index]
            } else {
                dp[index] = nums[index]
            }
            ans = max(ans, dp[index])
        }
        return ans
    }

    /**
     * leetCode 209. 长度最小的子数组 (middle)
     * 给定一个含有 n 个正整数的数组和一个正整数 target 。
     * 找出该数组中满足其总和大于等于 target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。
     *
     * 示例 1：
     * 输入：target = 7, nums = [2,3,1,2,4,3]
     * 输出：2
     * 解释：子数组 [4,3] 是该条件下的长度最小的子数组。
     * 示例 2：
     * 输入：target = 4, nums = [1,4,4]
     * 输出：1
     * 示例 3：
     * 输入：target = 11, nums = [1,1,1,1,1,1,1,1]
     * 输出：0
     *
     * 提示：双指针
     */
    fun minSubArrayLen(target: Int, nums: IntArray): Int {
        //取一个大值的长度,方便对比 min
        var ans = target + nums.size
        //双指针,指向区间
        var start = 0
        var end = 0
        //双指针的区间和
        var sum = 0
        while (end < nums.size) {
            //从 0(start) 开始叠加
            sum += nums[end]
            while (sum >= target) {
                //区间和满足目标值
                //对比取 min
                ans = min(ans, end - start + 1)
                //左指针前进, sum 重新统计
                sum -= nums[start++]
            }
            //右指针前进
            end++
        }
        //因为是正整数数组,最小值是 1,那么长度最大就是 target 个 1 的数组
        //所以最大长度就是 target，小于等于它就必然是有符合要求的长度
        return if (ans <= target) ans else 0
    }

    /**
     * leetCode 713. 乘积小于 K 的子数组 (middle)
     * 给你一个正整数数组 num 和一个整数 k ，请你返回子数组内所有元素的乘积严格小于 k 的连续子数组的数目。
     * 示例 1：
     * 输入：nums = [10,5,2,6], k = 100
     * 输出：8
     * 解释：8 个乘积小于 100 的子数组分别为：[10]、[5]、[2],、[6]、[10,5]、[5,2]、[2,6]、[5,2,6]。
     * 需要注意的是 [10,5,2] 并不是乘积小于 100 的子数组。
     * 示例 2：
     * 输入：nums = [1,2,3], k = 0
     * 输出：0
     *
     *
     * 提示：双指针
     */
    fun numSubarrayProductLessThanK(nums: IntArray, k: Int): Int {
        if (k <= 1) {
            //按照题意,nums是正整数数组,最小值是 1
            return 0
        }
        var ans = 0
        //双指针,指向区间
        var start = 0
        var end = 0
        //双指针区间的乘积
        var sum = 1
        while (end < nums.size) {
            sum *= nums[end]
            while (sum >= k) {
                sum /= nums[start++]
            }
            //区间长度即为可组合的子数组的数量
            ans += end - start + 1
            end++
        }
        return ans
    }

    /**
     * leetCode 532. 数组中的 k-diff 数对 (middle)
     * 给你一个整数数组 nums 和一个整数 k，请你在数组中找出 不同的 k-diff 数对，并返回不同的 k-diff 数对 的数目
     *
     * k-diff 数对定义为一个整数对 (nums[i], nums[j]) ，并满足下述全部条件：
     * 0 <= i, j < nums.length
     * i != j
     * |nums[i] - nums[j]| == k
     * 注意，|val| 表示 val 的绝对值。
     *
     * 示例 1：
     * 输入：nums = [3, 1, 4, 1, 5], k = 2
     * 输出：2
     * 解释：数组中有两个 2-diff 数对, (1, 3) 和 (3, 5)。
     * 尽管数组中有两个 1 ，但我们只应返回不同的数对的数量。
     * 示例 2：
     * 输入：nums = [1, 2, 3, 4, 5], k = 1
     * 输出：4
     * 解释：数组中有四个 1-diff 数对, (1, 2), (2, 3), (3, 4) 和 (4, 5) 。
     * 示例 3：
     * 输入：nums = [1, 3, 1, 5, 4], k = 0
     * 输出：1
     * 解释：数组中只有一个 0-diff 数对，(1, 1)
     * 提示：排序 + 双指针
     */
    fun findPairs(nums: IntArray, k: Int): Int {
        if (k < 0) {
            //相减后的绝对值,不存在负数
            return 0
        }
        //排序
        nums.sort()
        var start = 0
        var end = 1
        var count = 0
        while (start < nums.size - 1 && end < nums.size) {
            if (start > 0 && nums[start] == nums[start - 1]) {
                //已经排序,过滤重复
                start++
                continue
            }
            if (end <= start) {
                //对重复数据造成的 end 下标修正
                end = start + 1
            }
            //排序后从小到大,化解绝对值计算为：nums[start] + k = nums[end]
            while (end < nums.size && (nums[end] < nums[start] + k)) {
                end++
            }
            if (end < nums.size && (nums[start] + k == nums[end])) {
                //找到目标
                count++
            }
            start++
        }
        return count
    }

    /**
     * leetCode 581. 最短无序连续子数组 (middle)
     * 给你一个整数数组 nums ，你需要找出一个 连续子数组 ，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序
     * 请你找出符合题意的 最短 子数组，并输出它的长度。
     *
     * 示例 1：
     * 输入：nums = [2,6,4,8,10,9,15]
     * 输出：5
     * 解释：你只需要对 [6, 4, 8, 10, 9] 进行升序排序，那么整个表都会变为升序排序
     * 示例 2：
     * 输入：nums = [1,2,3,4]
     * 输出：0
     * 示例 3：
     * 输入：nums = [1]
     * 输出：0
     * 提示：先排序,然后双指针找不同
     */
    fun findUnsortedSubarray(nums: IntArray): Int {
        val sorted = nums.sortedArray()
        //找到左右两边,第一个不同的下标
        var start = 0
        var end = nums.lastIndex
        while (start <= end && nums[start] == sorted[start]) {
            start++
        }
        while (start <= end && nums[end] == sorted[end]) {
            end--
        }
        return end - start + 1
    }

    /**
     * leetCode 611. 有效三角形的个数 (middle)
     * 给定一个包含非负整数的数组 nums(1 <= nums.length<=1000) ，返回其中可以组成三角形三条边的三元组个数。
     * (三角形判断:任意两边之和大于第三边)
     * 示例 1:
     * 输入: nums = [2,2,3,4]
     * 输出: 3
     * 解释:有效组合是:
     * 2,3,4 (使用第一个 2)
     * 2,3,4 (使用第二个 2)
     * 2,2,3
     * 示例 2:
     * 输入: nums = [4,2,3,4]
     * 输出: 4
     *
     *
     */
    fun triangleNumber(nums: IntArray): Int {
        if (nums.size <= 2) {
            return 0
        }
        //从小到大排序
        nums.sort()
        var ans = 0
        var index = 2
        while (index < nums.size) {
            var dimple = 0
            var position = index - 1
            while (dimple < position) {
                if (nums[dimple] + nums[position] > nums[index]) {
                    // nums 从小到大排序
                    // nums[dimple]+nums[position] > nums[index] 同时意味着：
                    // position 不变,dimple 递增的情况，都会满足三角形的数组
                    //因此 position - dimple，计算其中有多少个组合
                    ans = position - dimple
                    position--
                } else {
                    //nums 从小到大排序
                    //nums[dimple]+nums[position] <= nums[index]，不满足三角形数组
                    //dimple继续递增
                    dimple++
                }
            }
            index++
        }
        return ans
    }
}

fun main() {
    val item = ArraysCodeModel()
//    println(item.findPairs(intArrayOf(3, 1, 4, 1, 5), 2))  //2
//    println(item.findPairs(intArrayOf(1, 2, 3, 4, 5), 1))   //4
//    println(item.findPairs(intArrayOf(1, 3, 1, 5, 4), 0))   // 1
    //println(item.triangleNumber(intArrayOf(2,2,3,4))) //3
    println(item.triangleNumber(intArrayOf(4, 2, 3, 4))) //4
}
