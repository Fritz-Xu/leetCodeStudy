package interview.tree

import withWhiteColor
import java.text.DecimalFormat
import java.util.*
import kotlin.math.max
import kotlin.math.pow


/**
 * avl tree
 * 平衡二叉搜索数
 */
class KAverageBinarySearchTree<E> {

    companion object {
        private const val ANSI_RESET = "\u001B[0m"
        private const val ANSI_BLACK = "\u001B[30m"
        private const val ANSI_RED = "\u001B[31m"
        private fun String.withBlackColor() = withColorInternal(ANSI_BLACK)
        private fun String.withRedColor() = withColorInternal(ANSI_RED)
        private fun String.withColorInternal(color: String): String {
            return color + this + ANSI_RESET
        }

        private fun padCenter(text: CharSequence, length: Int, padChar: Char): CharSequence? {
            require(length >= 0) { "Desired length $length is less than zero." }
            if (length <= text.length) return text.subSequence(0, text.length)
            val sb = java.lang.StringBuilder(length)
            val start = (length - text.length) / 2
            val isOdd = 0 != (length - text.length) % 2
            val pre = if (isOdd) start + 1 else start
            for (i in 0 until pre) sb.append(padChar)
            sb.append(text)
            for (i in 0 until start) sb.append(padChar)
            return sb
        }
    }

    private var root: TreeNode<E>? = null

    /**
     * 获取节点高度
     * */
    private fun height(node: TreeNode<E>?): Int {
        // 空节点高度为 -1 ，叶节点高度为 0
        return node?.height ?: -1
    }

    /**
     * 更新节点高度
     * */
    private fun updateHeight(node: TreeNode<E>?) {
        // 节点高度本质就是从自己开始,到最后一个节点(包括在内)的最长路径,上面共有几个节点
        // 所以:节点高度 = 最高子树高度 + 1(1就是自己当前的node)
        node?.height = max(height(node?.left), height(node?.right)) + 1
    }

    /**
     *  获取当前node的平衡因子
     *  假设平衡因子为 f，则一棵 AVL 树的任意节点的平衡因子皆满足 -1<= f <=1
     *  当f的绝对值大于1了,说明该node需要旋转了
     * */
    private fun balanceFactor(node: TreeNode<E>?): Int {
        if (node == null) {
            // 空节点平衡因子为 0
            return 0
        }
        // 节点平衡因子 = 左子树高度 - 右子树高度
        return height(node.left) - height(node.right)
    }

    private fun rotate(node: TreeNode<E>?): TreeNode<E>? {
        if (node == null) {
            // 空节点平衡因子为 0
            return null
        }
        val factor = balanceFactor(node)
        if (factor > 1) {
            //需要右旋
            if (balanceFactor(node) < 0) {
                //child的平衡因子区间方向相反,需要先左旋
                node.left = leftRotate(node.left)
            }
            //执行右旋
            return rightRotate(node)
        }
        if (factor < -1) {
            //需要左旋
            if (balanceFactor(node) > 0) {
                //child的平衡因子区间方向相反,需要先右旋
                node.right = rightRotate(node.left)
            }
            //执行左旋
            return leftRotate(node)
        }
        return node
    }

    /**
     * 右旋
     */
    private fun rightRotate(node: TreeNode<E>?): TreeNode<E>? {
        if (node == null) {
            return null
        }
        val child = node.left
        val grandChild = child?.right
        //child为原点,将node向右旋转,也就是变成了child的右child
        child?.right = node
        //二叉搜索树,左小右大
        if ((grandChild?.data?.hashCode() ?: -1) < node.data.hashCode()) {
            node.left = grandChild
        } else if ((grandChild?.data?.hashCode() ?: -1) > node.data.hashCode()) {
            node.right = grandChild
        }
        // 更新节点高度
        updateHeight(node)
        updateHeight(child)
        // 返回旋转后子树的根节点,用于覆盖node的parentNode的指向
        return child
    }

    /**
     * 左旋
     */
    private fun leftRotate(node: TreeNode<E>?): TreeNode<E>? {
        if (node == null) {
            return null
        }
        val child = node.right
        val grandChild = child?.left
        //child为原点,将node向左旋转,也就是变成了child的左child
        child?.left = node
        //二叉搜索树,左小右大
        if ((grandChild?.data?.hashCode() ?: -1) < node.data.hashCode()) {
            node.left = grandChild
        } else if ((grandChild?.data?.hashCode() ?: -1) > node.data.hashCode()) {
            node.right = grandChild
        }
        // 更新节点高度
        updateHeight(node)
        updateHeight(child)
        // 返回旋转后子树的根节点,用于覆盖node的parentNode的指向
        return child
    }

    fun search(data: E): E? {
        val list = LinkedList<TreeNode<E>?>()
        search(root, data, list)
        return if (list.isEmpty()) null else list.poll()?.data
    }

    private fun search(node: TreeNode<E>?, data: E, list: LinkedList<TreeNode<E>?>) {
        if (node == null) {
            return
        }
        if (node.data.hashCode() > data.hashCode()) {
            //二叉搜索树,左边小,右边大
            search(node.left, data, list)
        } else if (node.data.hashCode() < data.hashCode()) {
            //二叉搜索树,左边小,右边大
            search(node.right, data, list)
        }
        //找到了
        list.offer(node)
    }

    fun insert(data: E) {
        root = insert(root, data)
    }

    private fun insert(node: TreeNode<E>?, data: E): TreeNode<E>? {
        if (node == null) {
            return TreeNode(data)
        }
        if (node.data.hashCode() > data.hashCode()) {
            //二叉搜索树,左边小,右边大
            node.left = insert(node.left, data)
        } else if (node.data.hashCode() < data.hashCode()) {
            //二叉搜索树,左边小,右边大
            node.right = insert(node.right, data)
        } else {
            //重复节点不会处理
            return node
        }
        //插入后更新节点高度
        updateHeight(node)
        //检测是否需要旋转恢复平衡
        return rotate(node)
    }

    fun delete(data: E) {
        root = delete(root, data)
    }

    private fun delete(node: TreeNode<E>?, data: E): TreeNode<E>? {
        if (node == null) {
            // 如果节点为空,说明上一层node就是尾节点,直接返回null,不做修改
            return null
        }
        //递归覆盖上一层node的左右子node,实现删除
        if (node.data.hashCode() > data.hashCode()) {
            //开始查找并返回覆盖左节点
            node.left = delete(node.left, data)
        } else if (node.data.hashCode() < data.hashCode()) {
            //递归,开始查找并返回覆盖左节点
            node.right = delete(node.right, data)
        } else {
            //这里找到了删除的节点
            // 如果找到了要删除的节点,这时候有4种情况
            if (node.left == null && node.right == null) {
                // 情况1:如果是尾节点,直接删除
                return null
            } else if (node.right != null && node.left == null) {
                // 情况2:非尾节点,只有右node,直接返回
                return node.right
            } else if (node.right == null && node.left != null) {
                // 情况3:非尾节点,只有左node,直接返回
                return node.left
            } else {
                // 情况4-1:如果 key 左右子树都有,那么将当前node替换成右边的node
                var child = node.right
                // 情况4-2:如果发现右边的node左边有node,找到最尾部的node
                while (child?.left != null) {
                    child = child.left
                }
                //情况4-3:覆盖data,左右指针不变
                node.data = child?.data!!
                //情况4-4:递归,实现让左最尾部的node,给它的右node覆盖
                node.right = delete(node.right, node.data)
            }
        }
        //删除后更新节点高度
        updateHeight(node)
        //检测是否需要旋转恢复平衡
        return rotate(node)
    }

    fun allLooper() {
        val queue = LinkedList<TreeNode<E>>()
        val list = mutableListOf<TreeNode<E>>()
        queue.offer(root)
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

    private fun treeDepth(node: TreeNode<E>?): Int {
        return if (null == node) {
            0
        } else 1 + max(treeDepth(node.left), treeDepth(node.right))
    }

    /**
     * traverse the tree and output all the nodes to the StringBuilder as a visualized Binary Tree
     * @param node
     * @param <E>
    </E> */
    private fun treeTraversal(node: TreeNode<E>?): CharSequence? {
        val out = StringBuilder()
        val formatter = DecimalFormat("00")
        val queue: LinkedList<TreeNode<E>?> = LinkedList()
        queue.offer(node)
        var depth = 0
        var breadth = 0
        val treeNodeDepth: Int = treeDepth(node)
        val maxBreadthSize = 2.0.pow(treeNodeDepth.toDouble()).toInt()
        while (!queue.isEmpty()) {
            //Calculate the breath of the this level of the tree.
            val eNode: TreeNode<E>? = queue.poll()
            val breadthSize = Math.pow(2.0, depth.toDouble()).toInt()
            if (null != eNode) {
                queue.offer(eNode.left)
                queue.offer(eNode.right)
                val numberValue: String = formatter.format(eNode.data)
                val elementStr = padCenter(
                    "[$numberValue]", maxBreadthSize / breadthSize * 2, ' '
                )
                out.append(elementStr.toString().withRedColor())
            } else {
                queue.offer(null)
                queue.offer(null)
                out.append(
                    padCenter(
                        "[xx]", maxBreadthSize / breadthSize * 2, ' '
                    ).toString().withWhiteColor()
                )
            }
            breadth++
            if (breadth == breadthSize) {
                depth++
                breadth = 0
                if (depth == treeNodeDepth) {
                    break
                } else {
                    out.append("\n")
                    for (i in 0 until breadthSize) {
                        out.append(padCenter("/  \\", maxBreadthSize / breadthSize * 2, ' '))
                    }
                    out.append("\n")
                }
            }
        }
        return out.toString()
    }

    fun toHeapString(): String {
        val out = treeTraversal(root)
        return out.toString()
    }

    /**
     *  二叉树节点类
     */
    private class TreeNode<E>(var data: E) {
        var height: Int = 0            //默认的节点高度
        var left: TreeNode<E>? = null // 左子节点指针
        var right: TreeNode<E>? = null // 右子节点指针
    }
}