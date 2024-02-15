package interview.tree

import java.util.LinkedList

/**
 * 二叉搜索树
 *
 * 对于根节点，左子树中所有节点的值<根节点的值<右子树中所有节点的值
 * 任意节点的左、右子树也是二叉搜索树，即同样满足上面条件
 */
class KBinarySearchTree<E> {

    private var rootNode: TreeNode<Int>? = null

    fun createTree() {
        // 初始化节点
        rootNode = TreeNode(8)
        val n2 = TreeNode(4)
        val n3 = TreeNode(12)
        rootNode?.left = n2
        rootNode?.right = n3

        val n4 = TreeNode(2)
        val n5 = TreeNode(6)
        n2.left = n4
        n2.right = n5

        val n6 = TreeNode(1)
        val n7 = TreeNode(3)
        n4.left = n6
        n4.right = n7

        val n8 = TreeNode(5)
        val n9 = TreeNode(7)
        n5.left = n8
        n5.right = n9
        //-------spilt-----------

        val n10 = TreeNode(10)
        val n11 = TreeNode(14)
        n3.left = n10
        n3.right = n11

        val n12 = TreeNode(9)
        val n13 = TreeNode(11)
        n10.left = n12
        n10.right = n13

        val n14 = TreeNode(13)
        val n15 = TreeNode(15)
        n11.left = n14
        n11.right = n15
    }

    fun search(data: Int) {
        search(rootNode?.copy(), data)
    }


    private fun search(node: TreeNode<Int>?, data: Int) {
        node?.let {
            if (it.data == data) {
                println("find it!")
                return
            }
            if (it.left == null && it.right == null) {
                println("not found")
                return
            }
            if (it.data > data) {
                println("turn left! node is ${it.data}")
                search(it.left, data)
            }
            if (it.data < data) {
                println("turn right! node is ${it.data}")
                search(it.right, data)
            }
        }
    }

    fun insert(data: Int) {
        insert(rootNode, data)
    }

    private fun insert(node: TreeNode<Int>?, data: Int) {
        if (node == null || node.data == data) {
            println("data is null or same data!!!can not insert!!!")
            return
        }
        if (node.data > data) {
            if (node.left == null) {
                val newNode = TreeNode(data)
                println("insert new Node(${newNode.data})in node(${node.data})'s left")
                node.left = newNode
                return
            } else {
                println("turn left! node is ${node.data}")
                insert(node.left, data)
            }
        }
        if (node.data < data) {
            if (node.right == null) {
                val newNode = TreeNode(data)
                println("insert new Node(${newNode.data})in node(${node.data})'s right")
                node.right = newNode
            } else {
                println("turn right! node is ${node.data}")
                insert(node.right, data)
            }
        }
    }

    fun delete(data: Int) {
        delete(rootNode, data)
    }

    private fun delete(root: TreeNode<Int>?, key: Int): TreeNode<Int>? {
        if (root == null) {
            // 如果节点为空,说明上一层node就是尾节点,直接返回null,不做修改
            return null
        }
        //递归覆盖上一层node的左右子node,实现删除
        if (root.data > key) {
            //开始查找并返回覆盖左节点
            root.left = delete(root.left, key)
        } else if (root.data < key) {
            //递归,开始查找并返回覆盖左节点
            root.right = delete(root.right, key)
        } else {
            // 如果找到了要删除的节点,这时候有4种情况
            if (root.left == null && root.right == null) {
                // 情况1:如果是尾节点,直接删除
                return null
            } else if (root.right != null && root.left == null) {
                // 情况2:非尾节点,只有右node,直接返回
                return root.right
            } else if (root.right == null && root.left != null) {
                // 情况3:非尾节点,只有左node,直接返回
                return root.left
            } else {
                // 情况4-1:如果 key 左右子树都有,那么将当前node替换成右边的node
                var node = root.right
                // 情况4-2:如果发现右边的node左边有node,找到最尾部的node
                while (node?.left != null) {
                    node = node.left
                }
                //情况4-3:覆盖data,左右指针不变
                root.data = node?.data!!
                //情况4-4:递归,实现让左最尾部的node,给它的右node覆盖
                root.right = delete(root.right, node.data)
            }
        }
        return root
    }

    private fun minNode(it: TreeNode<Int>): TreeNode<Int> {
        if (it.left == null) {
            return it
        }
        minNode(it.left!!)
        return it
    }

    fun allLooper() {
        val queue = LinkedList<TreeNode<Int>>()
        val list = mutableListOf<TreeNode<Int>>()
        queue.offer(rootNode)
        while (queue.isNotEmpty()) {
            val item = queue.poll()
            list.add(item)
            item.left?.let {
                queue.offer(it)
            }
            item.right?.let {
                queue.offer(it)
            }
        }
        list.forEach {
            print("${it.data},")
        }
    }

    fun middleLooper() {
        val list = ArrayList<TreeNode<Int>>()
        middleLooper(rootNode, list)
        list.forEach {
            print("${it.data},")
        }
        println()
    }

    private fun middleLooper(rootNode: TreeNode<Int>?, list: ArrayList<TreeNode<Int>>) {
        if (rootNode == null) {
            return
        }
        middleLooper(rootNode.left, list)
        list.add(rootNode)
        middleLooper(rootNode.right, list)
    }

    /**
     *  二叉树节点类
     */
    private class TreeNode<E>(var data: E) {
        var left: TreeNode<E>? = null // 左子节点指针
        var right: TreeNode<E>? = null // 右子节点指针

        fun copy(): TreeNode<E> {
            val treeNode = TreeNode(data)
            treeNode.left = left
            treeNode.right = right
            return treeNode
        }
    }
}