package array

import java.util.*


/**
 * leetCode 1146. 快照数组(middle)
 */
class SnapshotArray(length: Int) {

    private val map: MutableMap<Int, TreeMap<Int, Int>> = HashMap()
    private var snap_id = 0

    init {
        snap_id = 0
        for (index in 0 until length) {
            val tree = TreeMap<Int, Int>()
            tree[0] = 0
            map[index] = tree
        }
    }

    fun set(index: Int, data: Int) {
        map[index]?.let { tree ->
            tree[snap_id] = data
        }
    }

    fun snap(): Int {
        return snap_id++
    }

    fun get(index: Int, snap_id: Int): Int {
        map[index]?.let { tree ->
            return tree.floorEntry(snap_id).value
        }
        return -1
    }


}