package interview.tree

import withWhiteColor
import java.text.DecimalFormat
import java.util.*
import kotlin.math.max
import kotlin.math.pow


/**
 * 1.每个node不是红色就是黑色
 * 2.每个nil以及root都是黑色的
 * 3.从root到 每一个 nil(null节点) 的path,
 * 它们的black数量相同
 * 4.red的child只能是black
 */

private typealias COLOR = Boolean

open class RedBlackTree<E> {


    companion object {
        const val RED = true
        const val BLACK = false

        private const val ANSI_RESET = "\u001B[0m"
        private const val ANSI_BLACK = "\u001B[29m"
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
            for (i in 0..<pre) sb.append(padChar)
            sb.append(text)
            for (i in 0..<start) sb.append(padChar)
            return sb
        }
    }

    private var root: TreeNode<E>? = null

    private fun nodeIsRed(node: TreeNode<E>?): Boolean {
        return node != null && node.isRed()
    }

    private fun nodeIsBlack(node: TreeNode<E>?): Boolean {
        return node != null && node.isBlack()
    }

    private fun checkRotateAndReColor(node: TreeNode<E>?) {
        if (node == null) {
            return
        }
        //当有连续2个红色节点,uncle(grandParent的left或者right)是红色的,就需要重新着色,不需要旋转
        //当有连续2个红色节点,uncle(grandParent的left或者right)是黑色的,就需要旋转 + 重新着色
        //而红黑树旋转的本质其实就是将这3个节点的中序遍历结果,将中间元素上移成为最上面的节点
        // 然后按照left child < node < right child 重新调整
        var current: TreeNode<E>? = node
        var parent = current?.parent
        var grandParent = parent?.parent
        while ((nodeIsRed(current) && nodeIsRed(parent)) || (nodeIsRed(grandParent) && nodeIsRed(parent))) {
            //有两个连续的红色
            val uncle = if (grandParent?.left == parent) grandParent?.right else grandParent?.left
            if (nodeIsRed(uncle)) {
                //uncle是红色,只需要重新着色
                recolor(grandParent)
                if (nodeIsRed(grandParent) && nodeIsRed(grandParent?.parent)) {
                    current = grandParent
                    parent = current?.parent
                    grandParent = parent?.parent
                }
            } else {
                //uncle 是黑色 ,需要旋转 + 重新着色
                //旋转
                current = rotate(current, parent, grandParent)
                //旋转后,让当前节点变成红色,子节点变成黑色
                recolor(current)
                parent = current?.parent
                grandParent = parent?.parent
            }

        }
    }

    fun insert(data: E) {
        if (root == null) {
            root = TreeNode(data, BLACK, null)
        } else {
            //新增节点默认为红色
            insert(root, data)?.let {
                // 节点插入完成后,红黑树开始检测旋转和是否重新着色
                checkRotateAndReColor(it)
            }
        }
    }

    fun delete(data: E) {
        if (root == null) {
            return
        }
        if (root?.left == null && root?.right == null) {
            if (root?.data.hashCode() == data.hashCode()) {
                println("delete root")
                root = null
            }
            return
        }
        search(data)?.also {
            deleteNode(it)
        }
    }


    private fun deleteNode(current: TreeNode<E>) {
        if (current.left == null && current.right == null) {
            //没有孩子节点,直接删除
            if (nodeIsBlack(current)) {
                //删除了黑色尾节点,黑色尾节点也给视为双黑节点,开始双黑修正
                fixDoubleBlack(current)
            }
            //如果是红色尾节点,不影响整体黑色节点数量,直接删除无需处理
            //用null把当前节点替换掉,实现删除
            replaceNode(current, null)
            return
        }
        if (current.left != null && current.right != null) {
            //有两个孩子节点,这是最复杂的情况,因为真正删除的node,未必是current参数节点
            //        [current]                 [current]                  [current]
            //       /         \               /         \                  /       \
            //   left(target)  right   or    left(target)  right    or       left       right
            //    /      \                  /      \                      /    \
            //  nil      null            others    null                 other  target(find the end right)
            //--------------------------------------------------------------
            val leftChildNode = current.left!!
            if (leftChildNode.left == null && leftChildNode.right == null) {
                //第二种情况,current节点的left child节点,它没有任何child
                current.data = leftChildNode.data
                if (nodeIsBlack(leftChildNode)) {
                    //删除了一个黑色节
                    //如果leftChildNode是黑色,执行双黑修正
                    fixDoubleBlack(leftChildNode)
                }
                //如果是删除了红色节点,那么无需处理
                replaceNode(leftChildNode, null)
                return
            }
            if (leftChildNode.right == null) {
                //第二种情况,current节点的left child节点,它没有right child节点
                //        [current]
                //       /         \
                //   left(target)  right
                //    /      \
                // others    null
                current.data = leftChildNode.data
                val targetNode = leftChildNode.left
                replaceNode(leftChildNode, targetNode)
                if (nodeIsBlack(leftChildNode)) {
                    //删除了一个黑色节点
                    if (nodeIsRed(targetNode)) {
                        //如果leftChildNode是红色,那么将其染黑即可维持整体的黑色节点数量
                        targetNode?.color = BLACK
                        return
                    }
                    //如果leftChildNode是黑色,执行双黑修正
                    fixDoubleBlack(targetNode)
                }
                //如果是删除了红色节点,那么无需处理
                return
            }
            //第三种情况,current节点的left child节点,它有right child节点,找到最右边的子child节点
            //   [current]
            //    /       \
            //   left       right
            //  /    \
            //other  target(find the end right)
            //       /
            //    others
            var rightNode = leftChildNode.right
            //找到left child的最右边的子child节点
            while (rightNode?.right != null) {
                rightNode = rightNode.right
            }
            //将最右边子child节点的data赋予给current节点
            current.data = rightNode!!.data
            /**
             * 接下来执行删除rightNode的操作,这里又会有几个情况
             * 2.1 rightNode是红色,直接删除
             * 2.2 rightNode是黑色,没有left child,删除后执行双黑修正
             * 2.3 rightNode是黑色,有left child,删除rightNode后,那么又有下面的情况
             *     ->2.3.1 rightNode的left-child,是红色的,将直接染黑即可
             *     ->2.3.2 rightNode的left-child,是黑色的,执行双黑修正
             * */
            if (nodeIsBlack(rightNode)) {
                //删除了一个黑色节点
                if (rightNode.left != null) {
                    val leftResultNode = rightNode.left!!
                    //让leftResultNode替换rightNode,实现删除
                    replaceNode(rightNode, leftResultNode)
                    if (nodeIsRed(leftResultNode)) {
                        //这是2.3.1的情况
                        leftResultNode.color = BLACK
                        return
                    }
                    //如果leftResultNode是黑色,执行双黑修正
                    fixDoubleBlack(leftResultNode)
                    return
                }
                //走到这里,说明rightNode是黑色的尾节点,没有任何child了
                //执行双黑修正
                fixDoubleBlack(rightNode)
                //删除节点
                replaceNode(rightNode, null)
                return
            }
            //如果是删除了红色节点,那么直接让它的left child(可能是null)替换掉rightNode就可以了
            replaceNode(rightNode, rightNode.left)
            return
        }
        //只有一个孩子的情况,按照红黑树的特性,该节点必定是一个黑色节点,而孩子节点可能是红色或者黑色
        //因此这里也分了两种情况,孩子是红色或者黑色的情况
        val targetNode/*红或黑*/ = if (current.left != null) current.left else current.right
        if (nodeIsRed(targetNode)) {
            //第一种情况,孩子是红色的,这种处理起来很简单,直接把红色的child节点替换到current节点,然后变成黑色即可
            //      [current(black)]              [current(black)]
            //       /            \       or      /              \
            //    null           right(red)     left(red)       null
            val oldColor = current.color
            replaceNode(current, targetNode)
            targetNode?.color = oldColor
            return
        }
        //走到这里,孩子节点必然是黑色的
        //      [current(black)]              [current(black)]
        //       /            \       or      /              \
        //    null         right(black)     left(black)       null
        //使用targetNode替换current
        replaceNode(current, targetNode)
        //执行双黑修正
        fixDoubleBlack(nodeSibling(targetNode!!))
    }

    /**
     * 双黑修正主要看修正节点的sibling
     */
    private fun fixDoubleBlack(node: TreeNode<E>?) {
        nodeSibling(node)?.let { sibling ->
            if (nodeIsRed(sibling)) {
                //sibling是红色,旋转,sibling染黑色和父节点染红色,双黑节点保持不变,然后递归
                val parentNode = sibling.parent
                val isLeftRotate = parentNode?.left != sibling
                //1.sibling的升高,parent变成sibling的左child或者右child(要看是左旋还是右旋)
                val target = if (isLeftRotate) sibling.left else sibling.right
                replaceNode(parentNode, sibling)
                sibling.left = parentNode
                parentNode?.parent = sibling
                //按照旋转方向
                if (isLeftRotate) {
                    // 左旋：parent的right child 变成 sibling的原来的左child
                    parentNode?.right = target
                } else {
                    // 左旋：parent的left child 变成 sibling的原来的右child
                    parentNode?.left = target
                }
                target?.parent = parentNode
                //2.sibling染黑色和父节点染红色
                sibling.color = BLACK
                parentNode?.color = RED
                //3. 递归,继续双黑修正
                fixDoubleBlack(node)
                return@let
            }
            //下面就是sibling 是黑色的情况
            if (nodeIsRed(sibling.right)) {
                //sibling 是黑色, 其右子节是红色
                val parentNode = sibling.parent
                val target = sibling.right!!
                val treeNode = target.left
                //1.sibling的升高,parent变成sibling的左child(要看是左旋还是右旋)
                replaceNode(parentNode, sibling)
                sibling.left = parentNode
                parentNode?.parent = sibling
                parentNode?.right = treeNode
                //2.然后红色节点变成黑色
                target.color = BLACK
                return@let
            }
            if (nodeIsRed(sibling.left)) {
                //sibling 是黑色, 其左子节是红色：
                //1.sibling的左子节点升高，sibling降低到左红节点的right child
                val target = sibling.left!!
                val treeNode = target.right
                replaceNode(sibling, target)
                target.right = sibling
                sibling.parent = target
                sibling.left = treeNode
                //2. sibling变成红色，左红节点变成黑色，递归
                sibling.color = RED
                target.color = BLACK
                //3. 递归,继续双黑修正
                fixDoubleBlack(node)
                return@let
            }
            //下面开始,sibling的子节点就只有黑色了,这时候sibling染红色
            sibling.color = RED
            if (nodeIsRed(sibling.parent) || sibling.parent == root) {
                //如果parent是红色或者是根节点,保存黑色
                sibling.parent?.color = BLACK
                return@let
            }
            //如果parent是黑色, parent变成新的双黑节点,递归
            fixDoubleBlack(sibling.parent)
        }
    }

    fun search(data: E): TreeNode<E>? {
        return search(root, data)
    }

    private fun search(node: TreeNode<E>?, data: E): TreeNode<E>? {
        return node?.let {
            if (it.data.hashCode() > data.hashCode()) {
                search(it.left, data)
            } else if (it.data.hashCode() < data.hashCode()) {
                search(it.right, data)
            } else {
                it
            }
        }
    }

    /**
     * [current] 当前节点
     * [parent] 当前节点的父节点
     * [grandParent] 当前节点的祖父节点
     */
    private fun rotate(current: TreeNode<E>?, parent: TreeNode<E>?, grandParent: TreeNode<E>?): TreeNode<E>? {
        val currentIsParentLeft = parent?.left == current
        val parentIsGrandLeft = grandParent?.left == parent

        val parentLeft = parent?.left

        val currentLeft = current?.left
        val currentRight = current?.right

        if (currentIsParentLeft && parentIsGrandLeft) {
            //当前排序:三点 current(red),parent(red),grandParent(black) 在左边连接为一条直线
            //          [grandParent(black)]
            //         /                   \
            //      [parent(red)]           gpr?
            //      /           \
            // [current(red)]   parentRight?
            //交换两个node的元素以及parent
            replaceNode(grandParent, parent)
//            parent?.right = grandParent
//            grandParent?.left = parent?.right
            //将3个节点的中序遍历后,将中间元素上移成为最上面的节点,按照left child < node < right child调整,这个就是旋转
            //旋转后的排序:
            //            [parent(black)]
            //            /           \
            //  [current(red)]   [grandParent(red)]
            //                        /             \
            //                      parentRight?    gpr?
            return parent
        }
        if (parentIsGrandLeft && !currentIsParentLeft) {
            //当前排序:当前三点current,parent,grandParent 连接为三角形
            //        [grandParent(black)]
            //        /                  \
            //    [parent(red)]          gpRight?
            //     /          \
            //  pLeft?        [current(red)]
            //               /          \
            //             cLeft?          cRight?
            //交换两个node的元素
            replaceNode(grandParent, current)
            current?.left = parent
            current?.right = grandParent

            parent?.right = currentLeft
            grandParent?.left = currentRight
            //将3个节点的中序遍历后,将中间元素上移成为最上面的节点,按照left child < node < right child调整,这个就是旋转
            //旋转后的排序:
            //              [current(black)]
            //              /             \
            //     [parent(red)]        [grandParent(red)]
            //     /            \         /                \
            //  pLeft?        cRight?   cLeft?           gpRight?
            return current
        }
        if (!parentIsGrandLeft && currentIsParentLeft) {
            //当前排序:当前三点current,parent,grandParent 在右边连接为三角形
            //    [grandParent(black)]
            //    /                 \
            // gpLeft?              [parent(red)]
            //                      /           \
            //          [current(red)]         pRight
            //           /           \
            //        cLeft?        cRight?
            //交换两个node的元素
            replaceNode(grandParent, current)
            current?.left = grandParent
            current?.right = parent
            grandParent?.right = currentLeft
            parent?.left = currentRight
            //将3个节点的中序遍历后,将中间元素上移成为最上面的节点,按照left child < node < right child调整,这个就是旋转
            //旋转后的排序:
            //               [current(black)]
            //              /             \
            //   [grandParent(red)]      [parent(red)]
            //     /              \        /            \
            //  gpLeft?         cLeft?   cRight?       pRight
            return current
        } else {
            //这时候就只有最后一种需要旋转情况了
            //当前三点current,parent,grandParent 在右边连接为一条斜线
            //     [grandParent(black)]
            //     /                  \
            //  gpLeft?               [parent(red)]
            //                       /            \
            //                    pLeft?       [current(red)]
            replaceNode(grandParent, parent)
            parent?.left = grandParent
            grandParent?.right = parentLeft
            //将3个节点的中序遍历后,将中间元素上移成为最上面的节点,按照left child < node < right child调整,这个就是旋转
            //旋转后的排序:
            //                    [parent(black)]
            //                    /          \
            //   [grandParent(red)]      [current(red)]
            //    /               \
            // gpLeft?            pLeft?
            return parent
        }
    }


    private fun nodeSibling(node: TreeNode<E>?): TreeNode<E>? {
        if (node != root) {
            val iParent = node!!.parent
            val isLeft = iParent?.left == node
            return if (isLeft) {
                iParent?.right
            } else {
                iParent?.left
            }
        }
        return null
    }

    private fun replaceNode(current: TreeNode<E>?, target: TreeNode<E>?) {
        if (current == root) {
            root = target
            val isLeft = (target?.data?.hashCode() ?: 0) > (current?.data?.hashCode() ?: 0)
            if (isLeft) {
                target?.left = current
            } else {
                target?.right = current
            }
        } else {
            val iParent = current?.parent
            val isLeft = iParent?.left == current
            if (isLeft) {
                iParent?.left = target
            } else {
                iParent?.right = target
            }
            //交互后,更新parent
            target?.parent = iParent
        }
    }

    private fun recolor(current: TreeNode<E>?) {
        //重新着色规则:
        //1.当前节点取反
        //2.当前节点的left和right,对当前节点的颜色值(布尔值)取反
        //3.根节点固定为黑色(布尔值)
        current?.let {
            it.color = !it.color
            it.left?.color = if (it.color) BLACK else RED
            it.right?.color = if (it.color) BLACK else RED
        }
        if (isRoot(current)) {
            current?.color = BLACK
        }
    }

    private fun insert(node: TreeNode<E>?, data: E): TreeNode<E>? {
        return if (node?.data.hashCode() > data.hashCode()) {
            //二叉搜索树,左边小,右边大
            if (node?.left != null) {
                insert(node.left, data)
            } else {
                //需要记录parent
                node?.left = TreeNode(data, RED, node)
                node?.left
            }
        } else {
            //二叉搜索树,左边小,右边大
            if (node?.right != null) {
                insert(node.right, data)
            } else {
                //需要记录parent
                node?.right = TreeNode(data, RED, node)
                node?.right
            }
        }
    }

    private fun isRoot(node: TreeNode<E>?): Boolean {
        return root.hashCode() == node.hashCode()
    }

    private fun treeDepth(node: TreeNode<E>?): Int {
        return if (null == node) {
            0
        } else {
            print("tree_${node.data?.hashCode()},")
            1 + max(treeDepth(node.left), treeDepth(node.right))
        }
    }

    /**
     * traverse the tree and output all the nodes to the StringBuilder as a visualized Binary Tree
     * @param node
     * @param <E>
    </E> */
    private fun treeTraversal(node: TreeNode<E>?): CharSequence {
        if (node == null) {
            return "[]"
        }
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
                out.append(
                    if (eNode.isRed()) elementStr.toString().withRedColor()
                    else elementStr.toString().withBlackColor()
                )
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
        println()
        return out.toString()
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
        println()
    }


    /**
     * [data] 元素
     * [color] 节点颜色,红或者黑
     * [parent] 父节点
     */
    class TreeNode<E>(var data: E, var color: COLOR = RED, var parent: TreeNode<E>?) {
        var left: TreeNode<E>? = null // 左子节点指针
            set(value) {
                field = value
                value?.parent = this
            }
        var right: TreeNode<E>? = null // 右子节点指针
            set(value) {
                field = value
                value?.parent = this
            }

        fun isBlack(): Boolean {
            return color == BLACK
        }

        fun isRed(): Boolean {
            return color == RED
        }
    }
}