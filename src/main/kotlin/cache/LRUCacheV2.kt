package cache

/**
 * 使用 LinkedHashMap 来实现 lru 算法的版本
 */
class LRUCacheV2(private val capacity: Int) {

    private val cache = LinkedHashMap<Int, Int>()

    fun get(key: Int): Int {
        if (cache[key] == null) {
            return -1
        }
        makeRecently(key)
        return cache[key]!!
    }

    fun put(key: Int, value: Int) {
        if (cache[key] != null) {
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
        if (cache[key] != null) {
            val data = cache[key]!!
            cache.remove(key)
            cache[key] = data
        }
    }

    /**
     * 添加为最近使用的元素
     */
    private fun addRecently(key: Int, data: Int) {
        cache[key] = data
    }

    /**
     * 删除某一个 key 的数据
     */
    private fun deleteKey(key: Int) {
        cache.remove(key)
    }

    /**
     * 删除最老的,也是最久没有使用的元素
     */
    private fun removeLeastRecently() {
        //最近使用的元素都在尾部,因为头部就是最久没有使用的元素
        val oldLeastKey = cache.keys.iterator().next()
        cache.remove(oldLeastKey)
    }

}