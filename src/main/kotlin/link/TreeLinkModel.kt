package link

import kotlin.math.max

class TreeLinkModel {

    private var depth = 0
    private var father: TreeNode? = null

    fun isCousins(root: TreeNode?, x: Int, y: Int): Boolean {
        return dfs(root, null, 1, x, y)
    }

    private fun dfs(node: TreeNode?, fa: TreeNode?, d: Int, x: Int, y: Int): Boolean {
        if (node == null || depth in 1..<d) {
            return false
        }
        if (node.data == x || node.data == y) {
            // 找到 x 或 y
            if (depth > 0) {
                // 之前已找到 x y 其中一个
                return depth == d && father !== fa // 表示 x 和 y 都找到
            }
            depth = d // 之前没找到，记录信息
            father = fa
        }
        return dfs(node.left, node, d + 1, x, y) || dfs(node.right, node, d + 1, x, y)
    }

    private fun treeDepth(node: TreeNode?): Int {
        return if (null == node) {
            0
        } else 1 + max(treeDepth(node.left), treeDepth(node.right))
    }

    class TreeNode(var data: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }
}