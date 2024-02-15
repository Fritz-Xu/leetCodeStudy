import array.RemoveArrayItems
import interview.tree.KBinaryTree
import link.LinkModel
import search.BinarySearchModel


fun main(args: Array<String>) {

//    //模拟hashMap的存储过程 Pair就是存储key-value
//    val array = arrayOfNulls<LinkedList<Pair<String,String>>>(16)
//    //16就是key,获取hash值,对数组长度取余
//    val index = "20".hashCode() % 16
//    //得到一个随机的位置,存入value
//    if (array[index] == null) {
//        array[index] = LinkedList<Pair<String,String>>().apply {
//            add(Pair("20","100"))
//        }
//    } else {
//        //发生了hash冲突,得到了同一个index,添加到链表后面
//        array[index]?.add(Pair("20","100"))
//        //todo 整体链表长度叠加超过N个(影响性能的时候),就要扩容了
//    }
//    println("index[$index]:${array[index]?.get(0)}")
    //RemoveArrayItems().mergeA(intArrayOf(1,2,3,0,0,0),3, intArrayOf(2,5,6),3)

//    val result = BinarySearchModel().binarySearchRange(intArrayOf(1, 2, 3, 5, 8, 10), 6)
//    println("[${result[0]} , ${result[1]}]")
//   println(BinarySearchModel().binarySearchFindInsert(intArrayOf(1, 2, 3, 5, 8, 10), 6))

//    val link = LinkModel.ListNode(1)
//    link.next = LinkModel.ListNode(4)
//    link.next?.next = LinkModel.ListNode(3)
//    link.next?.next?.next = LinkModel.ListNode(2)
//    link.next?.next?.next?.next = LinkModel.ListNode(5)
//    link.next?.next?.next?.next?.next = LinkModel.ListNode(7)
//
//    val link2 = LinkModel.ListNode(9)
//    link2.next = LinkModel.ListNode(11)
//    link2.next?.next = LinkModel.ListNode(2)
//    link2.next?.next?.next = LinkModel.ListNode(5)
//    link2.next?.next?.next?.next = LinkModel.ListNode(7)
//
//    val result = LinkModel().getIntersectionNode(link, link2)
//    println("[${result?.data} , ${result?.next?.data}]")

    val linkCycle = LinkModel.ListNode(1)
    linkCycle.next = LinkModel.ListNode(2).apply {
        next = linkCycle
    }
    println(LinkModel().detectCycle(linkCycle))


}
