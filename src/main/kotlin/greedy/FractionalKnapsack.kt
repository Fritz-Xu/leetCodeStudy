package greedy

/* 物品 */
class Item(
    val w: Int, // 物品
    val v: Int  // 物品价值
)

/* 分数背包：贪心 */
fun fractionalKnapsack(wgt: IntArray, _val: IntArray, c: Int): Double {
    // 创建物品列表,包含两个属性：重量 和 价值
    var cap = c
    val items = arrayOfNulls<Item>(wgt.size)
    for (i in wgt.indices) {
        items[i] = Item(wgt[i], _val[i])
    }
    // 按照单位价值 item.v / item.w 从高到低进行排序
    items.sortBy { item: Item? -> -(item!!.v.toDouble() / item.w) }
    // 循环贪心选择
    var res = 0.0
    for (item in items) {
        if (item!!.w <= cap) {
            // 若剩余容量充足，则将当前物品整个装进背包
            res += item.v
            cap -= item.w
        } else {
            // 若剩余容量不足，则将当前物品的一部分装进背包
            res += item.v.toDouble() / item.w * cap
            // 已无剩余容量，因此跳出循环
            break
        }
    }
    return res
}