package array

/**
 * 差分算法，差分数组
 */
class ArrayDifference(val nums: IntArray) {

    private val diff: IntArray = IntArray(nums.size)

    init {
        diff[0] = nums[0]
        for (index in 1..<nums.size) {
            diff[index] = nums[index] - nums[index - 1]
        }
    }

    /**
     * 给数组区间添加某个值(value 是负数则为 1 减少)
     * 返回修改后的数组(长度不变)
     */
    fun increment(start: Int, end: Int, value: Int):IntArray {
        diff[start] += value
        if (end + 1 < diff.size) {
            diff[end + 1] -= value
        }
        return result()
    }

    fun result(): IntArray {
        val res = IntArray(diff.size)
        res[0] = diff[0]
        for (index in 1..<diff.size) {
            res[index] = res[index - 1] + diff[index]
        }
        return res
    }
}