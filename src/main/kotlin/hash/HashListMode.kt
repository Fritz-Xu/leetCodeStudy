package hash




/**
 * LettCode 981. 基于时间的键值存储(middle)
 * https://leetcode.cn/problems/time-based-key-value-store
 */
class HashListMode {

    data class Node(val timeStamp: Int, val value: String)

    private val map = mutableMapOf<String, ArrayList<Node>>()

    fun set(key: String, value: String, timestamp: Int) {
        if (map[key] == null) {
            map[key] = ArrayList()
        }
        map[key]?.add(Node(timestamp, value))
    }

    fun get(key: String, timestamp: Int): String {
        map[key]?.let { list ->
            if (list.size == 1) {
                return if (list[0].timeStamp > timestamp) "" else list[0].value
            }
            var start = 0
            var end = list.size - 1
            while (start < end) {
                val mid = (end + start + 1) shr 1
                if (list[mid].timeStamp > timestamp) {
                    end = mid - 1
                } else {
                    start = mid
                }
            }
            return if(list[end].timeStamp > timestamp) "" else list[end].value
        }
        return ""
    }
}