package backtrace

/**
 * 这里练习回溯的 leetCode
 * 全排列的两道题是最基础的回溯模板
 * 回溯最好的学习方法： https://leetcode.cn/problems/restore-ip-addresses/solutions/100433/hui-su-suan-fa-hua-tu-fen-xi-jian-zhi-tiao-jian-by/
 */
class BacktraceModel {

    /**
     * leetCode 046. 全排列(middle)
     * https://leetcode.cn/problems/VvJkup/description/
     */
    fun permute(nums: IntArray): List<List<Int>> {
        val result = ArrayList<ArrayList<Int>>()
        permuteByDfs(ArrayList(), nums, BooleanArray(nums.size), result)
        return result
    }

    private fun permuteByDfs(
        state: ArrayList<Int>,
        choices: IntArray,
        selects: BooleanArray,
        result: ArrayList<ArrayList<Int>>
    ) {
        if (state.size == choices.size) {
            //达成条件,退出递归,copy 保存数据
            result.add(ArrayList(state))
            return
        }
        // 遍历所有选择
        for (index in choices.indices) {
            val choice = choices[index]
            // 剪枝,不允许重复选择元素
            if (!selects[index]) {
                // 尝试做出选择并更新状态
                selects[index] = true
                state.add(choice)
                //递归
                permuteByDfs(state, choices, selects, result)
                // 回退,撤销选择,恢复到之前的状态
                selects[index] = false
                state.removeAt(state.size - 1)
            }
        }
    }

    /**
     * leetCode 047. 全排列II(middle)
     * https://leetcode.cn/problems/permutations-ii/description/
     */
    fun permuteUnique(nums: IntArray): List<List<Int>> {
        val result = ArrayList<ArrayList<Int>>()
        permuteUniqueByDfs(ArrayList(), nums, BooleanArray(nums.size), result)
        return result
    }

    private fun permuteUniqueByDfs(
        state: ArrayList<Int>,
        choices: IntArray,
        selects: BooleanArray,
        result: ArrayList<ArrayList<Int>>
    ) {
        if (state.size == choices.size) {
            result.add(ArrayList(state))
            return
        }
        val set = mutableSetOf<Int>()
        choices.forEachIndexed { index, item ->
            if (set.contains(item)) {
                return@forEachIndexed
            }
            if (!selects[index]) {
                set.add(item)
                selects[index] = true
                state.add(item)
                permuteUniqueByDfs(state, choices, selects, result)
                selects[index] = false
                state.removeLastOrNull()
            }
        }
    }

    /**
     * leetCode 039. 组合总和(middle)
     * https://leetcode.cn/problems/combination-sum/description/
     */
    fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
        val result = ArrayList<ArrayList<Int>>()
        //子集和
        val total = 0
        //排序
        candidates.sort()
        combinationSumDfs(ArrayList(), candidates, result, total, target)
        return result
    }

    private fun combinationSumDfs(
        state: ArrayList<Int>,
        choices: IntArray,
        result: ArrayList<ArrayList<Int>>,
        start: Int,
        target: Int
    ) {
        if (target == 0) {
            //记录解答
            result.add(ArrayList(state))
            return
        }
        //从 start 开始遍历,避免生成重复子集
        for (index in start..<choices.size) {
            val item = choices[index]
            //还差多少到原定目标值
            val leftNumber = target - item
            if (leftNumber < 0) {
                //说明已经超过了原定目标值
                //排序后,后边元素更大,子集和一定超过 原定目标值 target
                break
            }
            state.add(item)
            combinationSumDfs(state, choices, result, index, leftNumber)
            state.removeLastOrNull()
        }
    }

    /**
     * leetCode 40. 组合总和 II(middle)
     * https://leetcode.cn/problems/combination-sum-ii/
     */
    fun combinationSum2(candidates: IntArray, target: Int): List<List<Int>> {
        val result = ArrayList<ArrayList<Int>>()
        //子集和
        val total = 0
        //排序
        candidates.sort()
        combinationSum2Dfs(ArrayList(), candidates, result, total, target)
        return result
    }

    private fun combinationSum2Dfs(
        state: ArrayList<Int>,
        choices: IntArray,
        result: ArrayList<ArrayList<Int>>,
        start: Int,
        target: Int
    ) {
        if (target == 0) {
            //记录解答
            result.add(ArrayList(state))
            return
        }
        //从 start 开始遍历,避免生成重复子集
        for (index in start..<choices.size) {
            val item = choices[index]
            //还差多少到原定目标值
            val leftNumber = target - item
            if (leftNumber < 0) {
                //说明已经超过了原定目标值
                //排序后,后边元素更大,子集和一定超过 原定目标值 target
                break
            }
            if (index > start && choices[index] == choices[index - 1]) {
                //如果该元素与左边元素相等,说明是使用过的重复元素,直接跳过
                continue
            }
            state.add(item)
            combinationSum2Dfs(state, choices, result, index + 1, leftNumber)
            state.removeLastOrNull()
        }
    }


    /**
     * leetCode 17. 电话号码的字母组合(middle)
     * https://leetcode.cn/problems/letter-combinations-of-a-phone-number/
     */
    fun letterCombinations(digits: String): List<String> {
        if (digits.isEmpty() || digits.toIntOrNull() == 1) {
            return emptyList()
        }
        val letter = arrayOf("", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz")
        val result = mutableListOf<String>()
        if (digits.length == 1) {
            //电话号码从 1 开始
            letter[digits.toInt() - 1].forEach {
                result.add(it.toString())
            }
            return result
        }
        letterCombinationsDfs(StringBuilder(), result, letter, digits, 0)
        return result
    }

    private fun letterCombinationsDfs(
        stringBuilder: StringBuilder,
        result: MutableList<String>,
        letter: Array<String>,
        digits: String,
        index: Int
    ) {
        if (index == digits.length) {
            result.add(stringBuilder.toString())
            return
        }
        //电话号码从 1 开始
        letter[digits[index] - '1'].forEach {
            stringBuilder.append(it)
            letterCombinationsDfs(stringBuilder, result, letter, digits, index + 1)
            stringBuilder.deleteAt(stringBuilder.length - 1)
        }
    }

    /**
     * leetCode 93. 复原 IP 地址
     * https://leetcode.cn/problems/restore-ip-addresses/description/
     */
    fun restoreIpAddresses(s: String): List<String> {
        if (s.length < 4 || s.length > 12) {
            return emptyList()
        }
        val result = mutableListOf<String>()
        val builder = StringBuilder()
        builder.append(s)
        restoreIpAddressesBt(builder, result, 0, 0)
        return result
    }

    private fun restoreIpAddressesBt(builder: StringBuilder, result: MutableList<String>, start: Int, position: Int) {
        if (position == 3) {
            if (restoreIpAddressesCheck(builder.substring(start, builder.length))) {
                result.add(builder.toString())
                return
            }
        }
        for (index in start..<builder.length) {
            if (restoreIpAddressesCheck(builder.substring(start, index + 1))) {
                builder.insert(index + 1, ".")
                restoreIpAddressesBt(builder, result, index + 2, position + 1)
                builder.deleteAt(index + 1)
            } else {
                break
            }
        }
    }

    /**
     * 判断字符串是否符合一个 ip 地址的一部分
     * 位于 0 到 255 之间组成，且不能含有前导 0
     */
    private fun restoreIpAddressesCheck(s: String?): Boolean {
        if (s.isNullOrEmpty()) {
            return false
        }
        //不能有前导 0
        if (s.startsWith("0") && s.length > 1) {
            return false
        }
        //判断是否在 0 到 255之间
        return (s.toIntOrNull() ?: -1) in 0..255
    }

}

fun main() {
    val item = BacktraceModel()
    val list = item.letterCombinations("23")
    println(list)
}