package data

import kotlin.collections.set


/**
 * 380. O(1) 时间插入、删除和获取随机元素
 * https://leetcode.cn/problems/insert-delete-getrandom-o1/description/
 * 要求O(1),那么就只有数组 + hash 了
 */
class RandomizedSet {
    private val indexMap: MutableMap<Int, Int> = HashMap()
    private val dataList: MutableList<Int> = ArrayList()

    fun insert(`val`: Int): Boolean {
        if (indexMap.containsKey(`val`)) {
            return false
        }
        indexMap[`val`] = dataList.size
        dataList.add(`val`)
        return true
    }

    fun remove(`val`: Int): Boolean {
        if (!indexMap.containsKey(`val`)) {
            return false
        }
        val i = indexMap[`val`]!!
        indexMap[dataList[dataList.size - 1]] = i
        dataList[i] = dataList.last()
        dataList.removeLast()
        indexMap.remove(`val`)
        return true
    }

    fun getRandom(): Int {
        return dataList[kotlin.random.Random.nextInt(dataList.size)]
    }
}

