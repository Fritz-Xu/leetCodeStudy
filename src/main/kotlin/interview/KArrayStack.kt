package interview

/**
 * stack:先进后出
 * field:
 *  1. capacity
 *  2. elementData
 *  3. size
 * method:
 *  1. push()
 *  2. pop()
 *  3. peek()
 */
class KArrayStack<E>(private var capacity: Int = 8) {

    private var elementData = arrayOfNulls<Any>(capacity)
    var size: Int = 0
        private set

    private fun grow(seed: Int) {
        elementData = elementData.copyOf(seed)
    }

    fun pushAll(vararg data: E) {
        data.forEach {
            push(it)
        }
    }

    fun push(data: E) {
        if (size == elementData.size) {
            capacity *= 2
            grow(capacity)
        }
        elementData[size] = data
        size++
    }

    fun peek(): E {
        return elementData[size - 1] as E
    }

    fun pop(): E {
        val peek = peek()
        size--
        return peek
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