@file:Suppress("UNCHECKED_CAST")

package interview


/**
 * field
 *  1. head
 *  2. tail
 *  3. elementData
 * method:
 *  offer()
 *  pollFirst()
 *  pollLast()
 *  peekFirst()
 *  peekLast()
 *  push()
 *  pop()
 *
 *  queue start from 0 and use tail 0->tail++
 *  stack start from len-1 and use head length-1 -> head--
 */
class KArrayQueue<E>(private var capacity: Int = 8) {

    private var elementData = arrayOfNulls<Any>(capacity)
    private var front = 0
    private var tail = 0

    var size: Int = 0
        private set

    fun addAll(vararg data: E) {
        data.forEach {
            offer(it)
        }
    }


    fun offer(data: E) {
        if (size == elementData.size) {
            capacity *= 2
            grow(capacity)
        }
        // 计算尾指针，指向队尾索引
        // 通过取余操作，实现 tail 越过数组尾部后回到头部
        tail = (front + size) % elementData.size
        elementData[tail] = data
        size++
    }

    fun peekFirst(): E {
        return elementData[front] as E
    }

    fun peekLast(): E {
        return elementData[tail] as E
    }

    fun poll(): E {
        val data = peekFirst()
        // 队首指针向后移动一位
        // 通过取余操作，实现 front 越过数组尾部后回到头部
        elementData[front] = null
        front = (front + 1) % elementData.size
        size--
        return data
    }

    private fun checkRange(index: Int) {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("The index is from 0..${size}!!")
        }
    }

    private fun grow(seed: Int) {
        val newArray = arrayOfNulls<Any>(seed)
        //copy front - end
        System.arraycopy(elementData, front, newArray, 0, elementData.size - front)
        if (front > tail) {
            //copy 0 - tail
            System.arraycopy(elementData, 0, newArray, elementData.size - front, tail + 1)
        }
        front = 0
        tail = elementData.size - 1
        elementData = newArray
    }

    @Suppress("UNCHECKED_CAST")
    fun forEach(block: (item: E) -> Unit) {
        for (i in front..<elementData.size) {
            if (elementData[i] == null) {
                //数组扩容后,会存在空数据在数组中
                break
            }
            block(elementData[i] as E)
        }
        if (front > tail) {
            //copy 0 - tail
            for (i in 0..<tail) {
                if (elementData[i] == null) {
                    //数组扩容后,会存在空数据在数组中
                    break
                }
                block(elementData[i] as E)
            }
        }
    }

}