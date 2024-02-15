package interview.tree

import java.util.LinkedList


class KBinaryTree<E> {

    fun createTree() {
        // 初始化节点
        val rootNode = TreeNode(1)
        val n2 = TreeNode(2)
        val n3 = TreeNode(3)
        val n4 = TreeNode(4)
        val n5 = TreeNode(5)
        val n6 = TreeNode(6)
        // 构建引用指向（即指针）
        rootNode.left = n2
        rootNode.right = n3
        n2.left = n4
        n2.right = n5
        n3.left = n6

        topLooper(rootNode)
        val frontList = mutableListOf<TreeNode<Int>>()
        frontLooper(frontList, rootNode)
        print("前序遍历:")
        frontList.forEach { front ->
            print("${front.data},")
        }
        println()

        val middleList = mutableListOf<TreeNode<Int>>()
        middleLooper(middleList, rootNode)
        print("中序遍历:")
        middleList.forEach { middle ->
            print("${middle.data},")
        }
        println()

        val endList = mutableListOf<TreeNode<Int>>()
        endLooper(endList, rootNode)
        print("后序遍历:")
        endList.forEach { middle ->
            print("${middle.data},")
        }
        println()
    }

    /**
     * 广度遍历
     * 从头部开始,每一层的节点都从左到右遍历一次
     */
    private fun <E> topLooper(rootNode: TreeNode<E>) {
        val nodeList = LinkedList<TreeNode<E>>()
        nodeList.offer(rootNode)
        val list = mutableListOf<TreeNode<E>>()
        while (nodeList.isNotEmpty()) {
            val node = nodeList.poll()
            list.add(node)
            node.left?.let {
                nodeList.offer(it)
            }
            node.right?.let {
                nodeList.offer(it)
            }
        }
        print("广度遍历:")
        list.forEach {
            print("${it.data},")
        }
        println()
    }

    /**
     * 深度优先遍历,
     * 前序、中序、后序遍历
     * 这里是前序遍历,访问优先级：根节点 -> 左子树 -> 右子树
     */
    private fun <E> frontLooper(list: MutableList<TreeNode<E>>, rootNode: TreeNode<E>? = null) {
        if (rootNode == null) {
            return
        }
        list.add(rootNode)
        frontLooper(list, rootNode.left)
        frontLooper(list, rootNode.right)
    }

    /**
     * 深度优先遍历,
     * 前序、中序、后序遍历
     * 这里是中序遍历,访问优先级：左子树 -> 根节点 -> 右子树
     */
    private fun <E> middleLooper(list: MutableList<TreeNode<E>>, rootNode: TreeNode<E>? = null) {
        if (rootNode == null) {
            return
        }
        middleLooper(list, rootNode.left)
        list.add(rootNode)
        middleLooper(list, rootNode.right)
    }

    /**
     * 深度优先遍历,
     * 前序、中序、后序遍历
     * 这里是后序遍历,访问优先级：左子树 -> 右子树 -> 根节点
     */
    private fun <E> endLooper(list: MutableList<TreeNode<E>>, rootNode: TreeNode<E>? = null) {
        if (rootNode == null) {
            return
        }
        middleLooper(list, rootNode.left)
        middleLooper(list, rootNode.right)
        list.add(rootNode)
    }

    /**
     *  二叉树节点类
     */
    private class TreeNode<E>(var data: E) {
        var left: TreeNode<E>? = null // 左子节点指针
        var right: TreeNode<E>? = null // 右子节点指针
    }

}