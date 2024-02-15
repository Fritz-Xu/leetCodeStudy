package tree

import java.util.*

class TreeCodeModel {

    class TreeNode(var data: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    /**
     * leetCode 102. 二叉树的层序遍历（middle）
     *
     * 提示：树的基本功,广度搜素+分层，递归的开端,
     */
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        val queue = LinkedList<TreeNode>()
        val list = mutableListOf<List<Int>>()
        root?.let { r ->
            queue.offer(r)
            while (queue.isNotEmpty()) {
                val dataList = mutableListOf<Int>()
                val size = queue.size
                for (index in 0..<size) {
                    val item = queue.poll()
                    dataList.add(item.data)
                    item.left?.let { left ->
                        queue.offer(left)
                    }
                    item.right?.let { right ->
                        queue.offer(right)
                    }
                }
                list.add(dataList)
            }
        }
        return list
    }

    /**
     * leetCode 103. 二叉树的锯齿形层序遍历（middle）
     *
     * 提示：树的基本功,广度搜素+分层 + 一次顺序 + 一次倒序
     */
    fun zigzagLevelOrder(root: TreeNode?): List<List<Int>> {
        val queue = LinkedList<TreeNode>()
        val list = mutableListOf<List<Int>>()
        root?.let { r ->
            queue.offer(r)
            while (queue.isNotEmpty()) {
                val dataList = mutableListOf<Int>()
                val size = queue.size
                for (index in 0..<size) {
                    val item = queue.poll()
                    dataList.add(item.data)
                    item.left?.let { left ->
                        queue.offer(left)
                    }
                    item.right?.let { right ->
                        queue.offer(right)
                    }
                }
                list.add(dataList)
                val sizeRecovered = queue.size - 1
                val dataListRecovered = mutableListOf<Int>()
                for (i in sizeRecovered downTo 0) {
                    val item = queue.poll()
                    dataListRecovered.add(item.data)
                    item.left?.let { left ->
                        queue.offer(left)
                    }
                    item.right?.let { right ->
                        queue.offer(right)
                    }
                }
                list.add(dataListRecovered)
            }
        }
        return list
    }

    /**
     * leetCode 236. 二叉树的最近公共祖先（middle）以及 235. 二叉搜索树的最近公共祖先（middle）
     *
     * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
     *
     * 对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，
     * 满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）
     *
     *提示：二叉树的深度优先搜索，使用前序遍历
     */
    fun lowestCommonAncestor(node: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        if (node == null || node == p || node == q) {
            //说明深度已经到底了
            return node
        }
        val left = lowestCommonAncestor(node.left, p, q)
        val right = lowestCommonAncestor(node.right, p, q)
        if (left == null && right == null) {
            //说明 dfs 搜索没有找到p，q 这两个 node
            return null
        }
        if (left == null) {
            //说明 dfs 搜索 node 的左边全部数据没有找到p，q 这两个 node
            //直接返回右边结果（ null 或者直接 p，q 其中一个 node 的祖先）
            return right
        }
        if (right == null) {
            //说明 dfs 搜索 node 的右边全部数据没有找到p，q 这两个 node
            //直接返回左边结果（不会为 null 了，p，q 其中一个 node 的祖先）
            return left
        }
        //说明通过 dfs 找到了p，q 的公共祖先
        return node
    }

    /**
     * leetCode 230. 二叉搜索树中第K小的元素（middle）
     *
     * 给定一个二叉搜索树的根节点 root ，和一个整数 k ，请你设计一个算法查找其中第 k 个最小元素（从 1 开始计数）
     *
     * 提示：
     * 二次搜索树，左子树上所有结点的值 小于 它根结点的值，右子树上所有结点的值 大于 它根结点的值
     * 使用 dfs 的中序排序，可以得到一个从小到大的队列
     */
    fun kthSmallest(root: TreeNode?, k: Int): Int {
        val list = mutableListOf<TreeNode>()
        root?.let {
            middleLooper(list, it, k)
        }
        if (list.isEmpty()) {
            return -1
        }
        return list[k].data
    }

    private fun middleLooper(list: MutableList<TreeNode>, rootNode: TreeNode? = null, k: Int) {
        if (rootNode == null || list.size > k) {
            return
        }
        middleLooper(list, rootNode.left, k)
        list.add(rootNode)
        middleLooper(list, rootNode.right, k)
    }

    /**
     * leetCode 235. 二叉搜索树的最近公共祖先（middle）
     *  给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
     *
     *  对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，
     *  满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）
     *
     * 提示：
     * 二次搜索树，左子树上所有结点的值 小于 它根结点的值，右子树上所有结点的值 大于 它根结点的值
     * 那么使用递归就非常方便
     */
    fun lowestCommonAncestorByBst(node: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        node?.let {
            if (it.data < (p?.data ?: it.data)
                && it.data < (q?.data ?: it.data)
            ) {
                return lowestCommonAncestorByBst(it.right, p, q)
            }
            if (it.data > (p?.data ?: it.data)
                && it.data > (q?.data ?: it.data)
            ) {
                return lowestCommonAncestorByBst(it.left, p, q)
            }
        }
        return node
    }
}