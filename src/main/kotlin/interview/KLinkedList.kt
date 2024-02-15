package interview

class KLinkedList<E> {

    private var first: Node<E>? = null
    private var last: Node<E>? = null
    var size = 0
        private set

    fun addAll(vararg data: E) {
        data.forEach {
            addLast(it)
        }
    }

    fun add(index: Int, data: E) {
        checkRange(index)
        if (index == 0) {
            addFirst(data)
            return
        }
        if (index == size - 1) {
            addLast(data)
            return
        }
        //create new node
        val node = Node(data)
        //current node
        val currentNode = getNode(index)
        val previewItem = currentNode.previewItem
        //insert new node
        // |preview| <-> |new|
        node.previewItem = previewItem
        previewItem?.nextItem = node
        // |new| <-> |current|
        node.nextItem = currentNode
        currentNode.previewItem = node
        size++
    }

    fun addFirst(data: E) {
        val f = first
        val node = Node(data)
        first = node
        f?.let {
            it.previewItem = node
            node.nextItem = it
        }
        size++
    }

    fun addLast(data: E) {
        val l = last
        val node = Node(data)
        last = node
        if (l == null) {
            first = node
        } else {
            l.nextItem = node
            node.previewItem = l
        }
        size++
    }

    fun removeFirst() {
        first?.let {
            val next = it.nextItem
            first = next
            first?.previewItem = null
            size--
        }
    }

    fun removeLast() {
        last?.let {
            val preview = it.previewItem
            last = preview
            last?.nextItem = null
            size--
        }
    }

    fun remove(data: E) {
        for (i in 0..<size) {
            val node = getNode(i)
            if (node.element == data) {
                val previewItem = node.previewItem
                val nextItem = node.nextItem
                nextItem?.previewItem = previewItem
                previewItem?.nextItem = nextItem
                break
            }
        }
    }

    fun removeAt(index: Int) {
        val node = getNode(index)
        val previewItem = node.previewItem
        val nextItem = node.nextItem
        nextItem?.previewItem = previewItem
        previewItem?.nextItem = nextItem
    }

    fun indexOf(data: E): Int {
        for (i in 0..<size) {
            if (get(i) == data) {
                return i
            }
        }
        return -1
    }

    fun set(index: Int, data: E) {
        getNode(index).element = data
    }

    fun get(index: Int): E {
        checkRange(index)
        return getNode(index).element
    }

    private fun getNode(index: Int): Node<E> {
        checkRange(index)
        var item: Node<E>
        if (index <= size / 2) {
            item = first!!
            repeat(index) {
                item = item.nextItem!!
            }
        } else {
            item = last!!
            repeat(size - index - 1) {
                item = item.previewItem!!
            }
        }
        return item
    }

    private fun checkRange(index: Int) {
        if (index < 0 || index >= size) {
            throw IndexOutOfBoundsException("The index is from 0..${size}!!")
        }
    }

    fun foreach(block: (E) -> Unit) {
        repeat(size) {
            block.invoke(get(it))
        }
    }

    private class Node<E>(var element: E) {
        var previewItem: Node<E>? = null
        var nextItem: Node<E>? = null
    }

}