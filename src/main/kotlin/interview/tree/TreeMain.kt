package interview.tree


val test_String =
    "3,20,16,17,18,56,27,54,59,81,87,28,6,9,10,55,98,51,34,31,62,15,86,21,38,73,75,89,5,77,37,72,64,99,45,82,29,52,78,68,92,83,46,58,48,30,94,69,23,2,71,74,66,60,47,44,41,4,95,63"

fun main(args: Array<String>) {
//    val avlTree = KAverageBinarySearchTree<Int>()
//    avlTree.insert(5)
//    avlTree.insert(3)
//    avlTree.insert(6)
//    avlTree.insert(1)
//    avlTree.insert(4)
//    //avlTree.insert(20)
//    println(avlTree.toHeapString())
//    avlTree.insert(0)
//    println("插入0,启动二叉树旋转")
//    println(avlTree.toHeapString())

    val tree = RedBlackTree<Int>()
//    tree.insert(3)
//    tree.insert(20)
//    tree.insert(16)
//    println(tree.toHeapString())

    test_String.split(",").subList(0,10).forEach {
        val data = it.trim().toInt()
        print("${data},")
        tree.insert(data)
    }
    println()
    println(tree.toHeapString())
    println("------------------------------------------")
    tree.delete(81)
    tree.delete(59)
    tree.delete(20)
    tree.delete(18)
    tree.delete(17)
    tree.delete(16)
    println(tree.toHeapString())
    println("------------------------------------------")
    tree.delete(54)
    println(tree.toHeapString())
    println("------------------------------------------")
    tree.delete(27)

    println(tree.toHeapString())
//    test_String.split(",").subList(0,10).forEach {
//        val data = it.trim().toInt()
//        print("${data},")
//        tree.delete(data)
//        println(tree.toHeapString())
//    }
//    println()
//    println(tree.toHeapString())
//    tree.insert(5)
//    tree.insert(3)
//    tree.insert(6)
//    tree.insert(1)
//    tree.insert(4)
//    tree.insert(2)
//    tree.insert(8)
//    tree.insert(10)
//    tree.insert(16)
//    tree.insert(12)
//    tree.insert(9)
//    tree.insert(12)
//    tree.insert(11)
//    tree.insert(13)
//    tree.insert(7)
//    tree.insert(20)
//    println()
//    println(tree.toHeapString())
//
//    tree.delete(3)
//    println()
//    println(tree.toHeapString())
//
//    tree.delete(8)
//    println()
//    println(tree.toHeapString())
//
//    tree.delete(12)
//    println()
//    println(tree.toHeapString())
//
//
//    tree.delete(16)
//    println()
//    println(tree.toHeapString())

}
