package backtrace

/**
 * 这里练习回溯的 leetCode
 * 全排列的两道题是最基础的回溯模板
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


}