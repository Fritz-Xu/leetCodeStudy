package char

import java.util.Arrays

class IntCharCodeModel {

    /**
     * leetCode 556. 下一个更大元素 III(middle)
     * 给你一个正整数 n ，请你找出符合条件的最小整数，其由重新排列 n 中存在的每位数字组成，并且其值大于 n
     * 如果不存在这样的正整数，则返回 -1
     * 注意 ，返回的整数应当是一个 32 位整数 ，如果存在满足题意的答案，但不是 32 位整数 ，同样返回 -1
     *
     * 示例 1：
     * 输入：n = 12
     * 输出：21
     * 示例 2：
     * 输入：n = 21
     * 输出：-1
     *
     * 提示：
     */
    fun nextGreaterElement(n: Int): Int {
        val result = n.toString()
        if (result.length <= 1) {
            return -1
        }
        if (result.length == 2) {
            val toInt = result.reversed().toInt()
            return if (toInt > n) toInt else -1
        }
        //转为字符数组
        val charArray = result.toCharArray()
        var index = charArray.size - 2
        //逆序遍历取到数组中非单调递增的索引 index
        while (index >= 0 && charArray[index] >= charArray[index + 1]) {
            //如['2','3','1'],会找到 index 是 0
            index--
        }
        if (index < 0) {
            //说明 n 是当前排列的最大值
            //返回 -1
            return -1
        }
        var position = charArray.size - 1
        //逆序遍历数组获取比 index 位置大的元素索引 position
        while (position >= 0 && charArray[index] >= charArray[position]) {
            //如['2','3','1'],会找到 position 是 1
            position--
        }
        //交换
        val swap = charArray[index]
        charArray[index] = charArray[position]
        charArray[position] = swap
        //交换后,['2','3','1'] 变成了 ['3','2','1']
        //对 index 后面的元素进行排序,以确保生成的数字是最小
        Arrays.sort(charArray, index + 1, charArray.size)
        //如排序后，['3','2','1'] 就变成了 ['3','1','2'],此时是最小的大值
        val ans = String(charArray)
        return ans.toIntOrNull() ?: -1
    }
}

fun main() {
    val item = IntCharCodeModel()
    println(item.nextGreaterElement(21))
    println(item.nextGreaterElement(12))
    println(item.nextGreaterElement(101))


}