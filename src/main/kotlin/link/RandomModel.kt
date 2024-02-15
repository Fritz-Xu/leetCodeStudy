package link

class RandomModel {

    class Node(var data: Int) {
        var next: Node? = null
        var random: Node? = null
    }

    /**
     * https://leetcode.cn/problems/copy-list-with-random-pointer/description/?envType=study-plan-v2&envId=selected-coding-interview
     * leetCode 138. 随机链表的复制
     * 一个单链表,每个节点都有 next 和 random , random是指向任意节点,也可能是null
     * 要求：现在给你头节点，对这个单链表进行深度克隆,包括 random 的值
     * 难点:random 可以指向任意节点,但这是一个单链表,如何维护一个preview,避免多次循环单链表查找对应节点呢？
     * 解法1:hashMap+多次循环
     */
    fun copyRandomListMap(node: Node?): Node? {
        var current = node
        val dumpNode = current
        val map = mutableMapOf<Node?, Node?>()
        while (current != null) {
            //把链表里面的node 存储到 map 里面
            map[current] = Node(current.data)
            current = current.next
        }
        current = dumpNode
        while (current != null) {
            //构建新链表和random
            map[current]?.next = current.next
            map[current]?.random = map[current.random]
            current = current.next
        }
        return map[dumpNode]
    }

    /**
     * https://leetcode.cn/problems/copy-list-with-random-pointer/description/?envType=study-plan-v2&envId=selected-coding-interview
     * leetCode 138. 随机链表的复制
     * 一个单链表,每个节点都有 next 和 random , random是指向任意节点,也可能是null
     * 要求：现在给你头节点，对这个单链表进行深度克隆,包括 random 的值
     * 难点:random 可以指向任意节点,但这是一个单链表,如何维护一个preview,避免多次循环单链表查找对应节点呢？
     * 解法2:拼接 + 拆分
     * 例如单链表：7->3->4->1
     * 第一步：拼接为:7->7'->3->-3'->4->4'->1->1'
     * 第二步: 拆分为:7->3->4->1 和 7'->3'->4'->1'
     * 最后返回 7‘ 这个头部节点，就是我们copy的新链表
     */
    fun copyRandomList(node: Node?): Node? {
        node?.let {
            var current: Node? = it
            var dumpNode: Node? = Node(-1)
            val head = dumpNode
            while (current != null) {
                //完成数据复制,变成7->7'->3->-3'->4->4'->1->1'的模式
                val copy = Node(current.data)
                copy.random = current.random
                copy.next = current.next
                current.next = copy
                current = copy.next
            }
            //拆分链表为7->3->4->1 和 7'->3'->4'->1'
            //因为经历了复制,长度为2n,取出偶数序列的node即可
            current = it
            var count = 0
            while (current != null) {
                //抽取count + 1 是偶数的node
                if ((count + 1) % 2 == 0) {
                    dumpNode?.next = current
                    dumpNode = dumpNode?.next
                }
                current = current.next
                count++
            }
            return head?.next
        }
        return null
    }


}

fun main(args: Array<String>) {
    val item = RandomModel.Node(7).apply {
        random = null
    }
    item.next = RandomModel.Node(13).apply {
        random = RandomModel.Node(0)
    }
    item.next?.next = RandomModel.Node(11).apply {
        random = RandomModel.Node(4)
    }
    item.next?.next?.next = RandomModel.Node(10).apply {
        random = RandomModel.Node(2)
    }
    item.next?.next?.next?.next = RandomModel.Node(1).apply {
        random = RandomModel.Node(0)
    }
    var mapHead = RandomModel().copyRandomList(item)
    while (mapHead != null) {
        print("[${mapHead.data},${mapHead.random?.data}],")
        mapHead = mapHead.next
    }
}