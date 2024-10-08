package cache

/**
 * 不使用 LinkedHashMap 来实现 lru 算法的版本
 * 后面考虑用其他语言实现 lru 时,可以考虑
 */
class LRUCache(private val capacity: Int) {

    private val map = mutableMapOf<Int, Node>()
    private val cache = DoubleList()

    fun get(key: Int): Int {
        if (map[key] == null) {
            return -1
        }
        makeRecently(key)
        return map[key]!!.data
    }

    fun put(key: Int, value: Int) {
        if (map[key] != null) {
            //更新原来的数据
            deleteKey(key)
            addRecently(key, value)
            return
        }
        if (cache.size == capacity) {
            removeLeastRecently()
        }
        addRecently(key, value)
    }

    /**
     * 将某个 key 提升为最近使用的
     */
    private fun makeRecently(key: Int) {
        //查看 map 是否有该元素
        map[key]?.let {
            cache.remove(it)
            //双链表尾部就是最近使用的元素
            cache.addLast(it)
        }
    }

    /**
     * 添加为最近使用的元素
     */
    private fun addRecently(key: Int, data: Int) {
        val node = Node(key, data)
        //map 记录
        map[key] = node
        //双链表尾部就是最近使用的元素
        cache.addLast(node)
    }

    /**
     * 删除某一个 key 的数据
     */
    private fun deleteKey(key: Int) {
        map[key]?.let {
            cache.remove(it)
        }
        //map 也同步删除元素
        map.remove(key)
    }

    /**
     * 删除最老的,也是最久没有使用的元素
     */
    private fun removeLeastRecently() {
        //最近使用的元素都在尾部,因为头部就是最久没有使用的元素
        cache.removeFirst()?.let {
            //map 也同步删除元素
            map.remove(it.key)
        }
    }

    private inner class Node(val key: Int, val data: Int) {
        var next: Node? = null
        var prev: Node? = null
    }

    /**
     * 双链表
     */
    private inner class DoubleList {
        private val head: Node = Node(0, 0)
        private var tail: Node = Node(0, 0)
        var size = 0
            private set

        init {
            head.next = tail
            tail.prev = head
        }

        fun addLast(node: Node) {
            node.prev = tail.prev
            node.next = tail
            tail.prev?.next = node
            tail.prev = node
            size++
        }

        fun remove(node: Node?) {
            val next = node?.next
            val prev = node?.prev
            prev?.next = next
            next?.prev = prev
            node?.next = null
            node?.prev = null
            size--
        }

        fun removeFirst(): Node? {
            if (head.next == tail) {
                return null
            }
            val first = head.next
            remove(first)
            return first
        }


    }
}