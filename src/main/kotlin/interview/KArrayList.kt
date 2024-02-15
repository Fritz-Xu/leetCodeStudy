package interview



class KArrayList<E>(private var capacity: Int = 8) {

    private var elementData = arrayOfNulls<Any>(capacity)
    var size: Int = 0
        private set

    fun addAll(vararg data: E) {
        data.forEach {
            add(it)
        }
    }

    fun addAll(data: List<E>) {
        data.forEach {
            add(it)
        }
    }

    fun add(item: E) {
        add(size, item)
    }

    fun add(index: Int, item: E) {
        if (size == elementData.size) {
            capacity *= 2
            grow(capacity)
        }
        if (index in 1..<size) {
            System.arraycopy(
                elementData, index, elementData, index,
                size - index - 1
            )
        }
        elementData[index] = item
        size++
    }

    fun remove(item: E) {
        if (null == item) {
            return
        }
        for (index in elementData.indices) {
            val data = elementData[index]!!
            if (item == data) {
                remove(index)
                break
            }
        }

    }

    fun remove(index: Int) {
        if (checkRange(index)) {
            throw IndexOutOfBoundsException("The index is from 0..${size}!!")
        }
        System.arraycopy(elementData, index + 1, elementData, index, size - index - 1)
        size--
    }

    fun indexOf(item: E): Int {
        elementData.forEachIndexed { index, any ->
            if (any === item) {
                return index
            }
        }
        return -1
    }

    @Suppress("UNCHECKED_CAST")
    fun get(index: Int): E? {
        if (checkRange(index)) {
            throw IndexOutOfBoundsException("The index is from 0..${size}!!")
        }
        return elementData[index] as? E
    }

    @Suppress("UNCHECKED_CAST")
    fun set(index: Int, data: E) {
        if (checkRange(index)) {
            throw IndexOutOfBoundsException("The index is from 0..${size}!!")
        }
        elementData[index] = data
    }

    private fun checkRange(index: Int): Boolean {
        return index < 0 || index >= size
    }

    private fun grow(seed: Int) {
        elementData = elementData.copyOf(seed)
    }

    @Suppress("UNCHECKED_CAST")
    fun forEach(block: (item: E) -> Unit) {
        for (i in 0..<size) {
            if (elementData[i] == null) {
                //数组扩容后,会存在空数据在数组中
                break
            }
            block(elementData[i] as E)
        }
    }


}