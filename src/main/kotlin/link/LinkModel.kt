package link

import java.util.*
import kotlin.math.abs


/**
 * leetCode 链表题目
 */
class LinkModel {

    data class ListNode(var data: Int) {
        var next: ListNode? = null
    }

    /**
     * leetCode地址:https://leetcode.cn/problems/reverse-linked-list/?envType=study-plan-v2&envId=selected-coding-interview
     *  要求:给你单链表的头节点 head ，请你反转链表，并返回反转后的链表
     *  如：1->2->3->4 变成 4->3->2->1
     *  思路：递归或者循环
     */
    fun reverseList(head: ListNode?): ListNode? {
        var current = head
        var preview: ListNode? = null
        while (current != null) {
            val next = current.next
            //断开原来节点的指向
            current.next = preview
            //修正新的指向
            preview = current
            current = next
        }
        return preview
    }

    /**
     * https://leetcode.cn/problems/partition-list/description/?envType=study-plan-v2&envId=selected-coding-interview
     *  要求:分隔链表，给你单链表的头节点 head和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
     *  如：1->2->5->3->4->7->2,特定值是3，输出为：1->2->2->3->5->4->7
     *  思路：双指针,一次循环解决
     */
    fun partition(head: ListNode?, target: Int): ListNode? {
        var current = head
        //使用双指针移动+双定位指针
        var spiltMin: ListNode? = ListNode(-1)
        var spiltMax: ListNode? = ListNode(-1)
        //这里定位
        val dumpMin = spiltMin
        val dumpMax = spiltMax
        while (current != null) {
            if (current.data < target) {
                //小于目标值在min链表
                spiltMin?.next = ListNode(current.data)
                spiltMin = spiltMin?.next
            } else {
                //大于等于目标值在max链表
                spiltMax?.next = ListNode(current.data)
                spiltMax = spiltMax?.next
            }
            current = current.next
        }
        //合并新的链表
        spiltMin?.next = dumpMax?.next
        return dumpMin?.next
    }

    /**
     * https://leetcode.cn/problems/delete-node-in-a-linked-list/?envType=study-plan-v2&envId=selected-coding-interview
     * 237. 删除链表中的节点（middle）
     *  要求:删除链表中指定节点，给你单链表里面的头节点和尾节点之间的一个随机node，请你删除该节点
     *  请注意，你无法访问链表的第一个节点,而且也不会给你尾节点
     *
     *  如：1->2->3->4，删除3， 变成 1->2->4
     *  思路:无法访问第一个节点，那么无法直接通过修改指针来实现删除，可以考虑使用数据copy + 断尾来实现
     */
    fun deleteNode(node: ListNode?) {
        if (node != null) {
            val next = node.next
            node.data = next?.data ?: -1
            node.next = next?.next
        }
    }

    /**
     * leetCode地址:https://leetcode.cn/problems/merge-k-sorted-lists/description/
     *  要求:合并k个从小到大的链表
     *  思路：设置一个新的结点作为哨兵，然后多路归并 + 优先队列
     */
    fun mergeKSortedLists(lists: Array<ListNode?>): ListNode? {
        //创建哨兵
        val dump = ListNode(0)
        //创建哨兵代理指针
        var tail: ListNode? = dump
        //创建一个先进先出的优先队列(从小到大)
        val priorityQueue = PriorityQueue<ListNode> { a, b ->
            a.data - b.data
        }
        //把全部的头结点按从小到大排列起来
        for (node in lists) {
            node?.let {
                //入队
                priorityQueue.offer(it)
            }
        }
        while (priorityQueue.isNotEmpty()) {
            //头部出队,得到一个链表的结点
            val poll = priorityQueue.poll()
            //哨兵新增后续结点
            tail?.next = poll
            tail = tail?.next
            poll.next?.let {
                //当前链表的下一个结点入队
                priorityQueue.offer(it)
            }
        }
        return dump.next
    }

    /**
     * leetCode 876. 链表的中间结点
     * 给一个单链表的头结点 head ，请找出并返回链表的中间结点node
     * 如果有两个中间结点，则返回第二个中间结点。
     *
     * 提示：返回数据是 node,链表长度是偶数或者奇数,因此可以使用快慢双指针
     */
    fun middleNode(head: ListNode?): ListNode? {
        var fast = head
        var slow = head
        while (fast != null && fast.next?.next != null) {
            fast = fast.next?.next
            slow = slow?.next
        }
        return slow
    }

    /**
     * leetCode 160. 相交链表（easy）
     *
     * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点
     * 如果两个链表不存在相交节点，返回 null
     * 题目数据 保证 整个链式结构中不存在环。
     * 注意，函数返回结果后，链表必须 保持其原始结构 。
     *
     * 提示：返回数据是 node,链表长度是偶数或者奇数,因此可以使用快慢双指针
     */
    fun getIntersectionNode(headA: ListNode?, headB: ListNode?): ListNode? {
        //双指针法
        var aNode = headA
        var bNode = headB
        while (aNode != bNode) {
            aNode = (aNode?.next) ?: headB
            bNode = (bNode?.next) ?: headA
        }
        return aNode
    }


    /**
     * leetCode 142. 环形链表 II（middle）
     *
     * 给定一个链表的头节点  head ，返回链表开始入环的第一个节点。
     * 如果链表无环，则返回 null。
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
     * 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。
     * 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
     *
     */
    fun detectCycle(head: ListNode?): ListNode? {
        //快慢步长指针法
        //一个快指针，一个慢指针，它们总会相遇
        //
        var fast = head
        var slow = head
        while (true) {
            //判断是否循环链表
            if (fast?.next == null) {
                return null
            }
            fast = fast.next?.next
            slow = slow?.next
            if (fast == slow) {
                break
            }
        }
        fast = head
        while (slow != fast) {
            slow = slow?.next
            fast = fast?.next
        }
        return fast

        //map 解题法
//        var node = head
//        val set = mutableSetOf<ListNode>()
//        while (null != node) {
//            if (!set.contains(node)) {
//                set.add(node)
//                node = node.next
//            } else {
//                return node
//            }
//        }
//        return null
    }

    /**
     * leetCode 82. 删除排序链表中的重复元素 II（middle）
     *
     * 给定一个已排序的链表的头 head ， 删除原始链表中所有重复数字的节点，只留下不同的数字
     * 返回没有重复元素的有序的链表
     *
     *提示：快慢指针法
     *
     */
    fun deleteDuplicates(head: ListNode?): ListNode? {
        var tmp: ListNode? = ListNode(-1)
        var result: ListNode? = null
        head?.let {
            var slow: ListNode? = it
            var fast: ListNode? = slow?.next
            var dimple: Int? = null
            while (fast?.next != null) {
                if (fast.data != slow?.data) {
                    if (dimple != slow?.data) {
                        tmp?.next = ListNode(slow!!.data)
                        if (result == null) {
                            result = tmp?.next
                        }
                        tmp = tmp?.next
                    }
                } else {
                    dimple = slow.data
                }
                fast = fast.next
                slow = slow?.next
            }
            fast = slow?.next
            while (slow != null) {
                if (fast?.data != slow.data) {
                    if (dimple != slow.data) {
                        tmp?.next = ListNode(slow.data)
                        if (result == null) {
                            result = tmp?.next
                        }
                        tmp = tmp?.next
                    }
                } else {
                    dimple = slow.data
                }
                slow = slow.next
                fast = fast?.next
            }
        }
        return result
    }

    /**
     * leetCode 2487. 从链表中移除节点（middle）
     *
     * 给你一个链表的头节点 head 开始循环
     * 当遇到一个右边更大值的节点时,就是大值节点前面小于它的节点,都删除
     * 返回修改后链表的头节点 head
     *
     * 示例 1：
     * 输入：head = [5,2,13,3,8]
     * 输出：[13,8]
     * 解释：需要移除的节点是 5 ，2 和 3 。
     * - 节点 13 在节点 5 右侧。
     * - 节点 13 在节点 2 右侧。
     * - 节点 8 在节点 3 右侧。
     * 示例 2：
     * 输入：head = [1,1,1,1]
     * 输出：[1,1,1,1]
     * 解释：每个节点的值都是 1 ，所以没有需要移除的节点
     * 示例 3：
     * 输入：head = [5,2,13,3,8,24,15]
     * 输出：[24,15]
     * 解释：需要移除的节点是5,2,13,3和8,因为它们走在 24 的左侧
     *
     *提示：递归,只要 next 的值大于当前值,直接替换
     *
     */
    fun removeNodes(head: ListNode?): ListNode? {
        if (head == null) {
            return null
        }
        head.next = removeNodes(head.next)
        //next 的值大于当前值,直接替换
        return if (head.next != null && head.data < head.next!!.data) {
            head.next
        } else {
            head
        }
    }

    /**
     * leetCode 2807. 在链表中插入最大公约数（middle）
     *
     * 给你一个链表的头 head ，每个结点包含一个整数值。
     * 在相邻结点之间，请你插入一个新的结点，结点值为这两个相邻结点值的 最大公约数
     * 请你返回插入之后的链表。
     * 两个数的 最大公约数 是可以被两个数字整除的最大正整数
     *
     * 示例1：
     * 输入：head = [18,6,10,3]
     * 输出：[18,6,6,2,10,1,3]
     * 解释：第一幅图是一开始的链表，第二幅图是插入新结点后的图（蓝色结点为新插入结点）
     * - 18 和 6 的最大公约数为 6 ，插入第一和第二个结点之间
     * - 6 和 10 的最大公约数为 2 ，插入第二和第三个结点之间
     * - 10 和 3 的最大公约数为 1 ，插入第三和第四个结点之间
     * 所有相邻结点之间都插入完毕，返回链表。
     *
     * 示例2：
     * 输入：head = [7]
     * 输出：[7]
     * 解释：第一幅图是一开始的链表，第二幅图是插入新结点后的图（蓝色结点为新插入结点）。
     * 没有相邻结点，所以返回初始链表。
     *
     *提示：快慢指针法
     *
     */
    fun insertGreatestCommonDivisors(head: ListNode?): ListNode? {
        if (head == null) {
            return null
        }
        if (head.next == null) {
            return head
        }
        var slow = head
        var fast = head.next
        val result = slow
        while (fast != null) {
            slow!!.next = ListNode(getMaxDivisor(fast.data, slow.data))
            slow.next?.next = fast
            slow = slow.next?.next
            fast = fast.next
        }
        return result
    }

    /**
     *  最大公约数
     */
    private fun getMaxDivisor(target: Int, result: Int): Int {
        val max = if ((target > result)) target else result
        val min = if ((target < result)) target else result
        return if (max % min != 0) {
            getMaxDivisor(min, max % min)
        } else {
            min
        }
    }

    /**
     * leetCode 19. 删除链表的倒数第 N 个结点（middle）
     *
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     *
     * 示例1：
     * 输入：head = [1,2,3,4,5], n = 2
     * 输出：[1,2,3,5]
     * 示例 2：
     * 输入：head = [1], n = 1
     * 输出：[]
     * 示例 3：
     * 输入：head = [1,2], n = 1
     * 输出：[1]
     *
     */
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        if (head?.next == null) {
            return null
        }
        var fast = head
        var slow = head
        val dimple = slow
        //先让 fast 前进 n 步
        repeat(n) {
            fast = fast?.next
        }
        if (fast == null) {
            //说明删除节点在前面
            return slow.next
        }
        while (fast?.next != null) {
            //fast走完余下的 size - n 步
            slow = slow?.next
            //slow 也开始前进
            fast = fast?.next
        }
        //fast走完余下的 size - n 步，此时 slow 走到了倒数 n - 1 步了
        //所以直接覆盖next节点, 实现删除
        slow?.next = slow?.next?.next
        return dimple
    }

    /**
     * leetCode 25. K 个一组翻转链表(hard)
     *
     * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
     * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
     * 你不能只是单纯改变节点内部的值，而是需要实际进行节点交换。
     *
     * 示例1：
     * 输入：head = [1,2,3,4,5], k = 2
     * 输出：[2,1,4,3,5]
     * 示例 2：
     * 输入：head = [1,2,3,4,5], k = 3
     * 输出：[3,2,1,4,5]
     *
     */
    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        var p0 = head
        //判断当前长度是否小于 k
        for (index in 0..<k) {
            if (p0 == null) {
                return head
            }
            p0 = p0.next
        }
        var pre: ListNode? = null
        var cur = head
        var next = head
        for (index in 0..<k) {
            next = cur?.next
            cur?.next = pre
            pre = cur
            cur = next
        }
        //继续下一组
        head?.next = reverseKGroup(cur, k)
        return pre
    }

}

fun main() {
    val link = LinkModel.ListNode(1)
    link.next = LinkModel.ListNode(2)
    link.next?.next = LinkModel.ListNode(3)
    link.next?.next?.next = LinkModel.ListNode(4)
    link.next?.next?.next?.next = LinkModel.ListNode(5)
//    link.next?.next?.next?.next?.next = LinkModel.ListNode(24)
//    link.next?.next?.next?.next?.next?.next = LinkModel.ListNode(15)

    var data = LinkModel().removeNthFromEnd(link, 2)
    //[18,6,6,2,10,1,3]
    while (data != null) {
        print(data.data)
        print(",")
        data = data.next
    }

}