package copy

import java.lang.IllegalArgumentException

class KCArrayList<E>(private var capacity: Int = 8) {

    private var elements = arrayOfNulls<Any>(capacity)
    var size: Int = 0
        private set

    fun addAll(vararg data: E) {
        data.forEach {
            add(it)
        }
    }

    fun add(data: E) {
        add(size, data)
    }

    fun add(index: Int, data: E) {
        if (size == elements.size) {
            capacity *= 2
            grow(capacity)
        }
        checkRange(index)
        if (index >= 1 && index <= size - 1) {
            System.arraycopy(elements, index, elements, index + 1, size - index)
        }
        elements[index] = data
        size++
    }

    fun indexOf(data: E): Int {
        elements.forEachIndexed { index, item ->
            if (item == data) {
                return index
            }
        }
        return -1
    }

    fun removeAt(index: Int) {
        checkRange(index)
        System.arraycopy(elements, index + 1, elements, index, size - index - 1)
        size--
    }

    fun remove(data: E) {
        val index = indexOf(data)
        if (index >= 0) {
            removeAt(index)
        }
    }

    private fun checkRange(index: Int) {
        if (index < 0 || index >= size) {
            throw IllegalArgumentException("the list range is 0..${size - 1}")
        }
    }

    private fun grow(newSize: Int) {
        elements = elements.copyOf(size)
    }

    fun forEach(block: (E) -> Unit) {
        for (i in 0..<size) {
            if (elements[i] == null) {
                //数组扩容后,会存在空数据在数组中
                break
            }
            block(elements[i] as E)
        }
    }
}