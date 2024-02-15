package array

/**
 * leetCode 303. 区域和检索 - 数组不可变(easy)
 * 给定一个整数数组  nums，处理以下类型的多个查询:
 * 计算索引 left 和 right （包含 left 和 right）之间的 nums 元素的 和 ，其中 left <= right
 * 实现 NumArray 类：
 * NumArray(int[] nums) 使用数组 nums 初始化对象
 * int sumRange(int i, int j) 返回数组 nums 中索引 left 和 right 之间的元素的 总和 ，包含 left 和 right 两点（也就是 nums[left] + nums[left + 1] + ... + nums[right] )
 *
 * 示例 1：
 * 输入：
 * ["NumArray", "sumRange", "sumRange", "sumRange"]
 * [[[-2, 0, 3, -5, 2, -1]], [0, 2], [2, 5], [0, 5]]
 * 输出：
 * [null, 1, -1, -3]
 * 解释：
 * NumArray numArray = new NumArray([-2, 0, 3, -5, 2, -1]);
 * numArray.sumRange(0, 2); // return 1 ((-2) + 0 + 3)
 * numArray.sumRange(2, 5); // return -1 (3 + (-5) + 2 + (-1))
 * numArray.sumRange(0, 5); // return -3 ((-2) + 0 + 3 + (-5) + 2 + (-1))
 *
 * 提示:使用好前缀和,便可以做到一次 for 循环解决
 */
class NumArray(nums: IntArray) {

    private val preIntArrays = IntArray(nums.size + 1)

    init {
        for (index in 1 until preIntArrays.size) {
            //提前一次循环把元素叠加的数值都算好了
            preIntArrays[index] = preIntArrays[index - 1] + nums[index - 1]
        }
        //例如         nums:   3， 5，2，-2，4， 1
        //那么 preIntArrays：0，3，8，10，8，12，13
    }

    fun sumRange(left: Int, right: Int): Int {
        //利用前缀和快速一次运算得出结果
        return preIntArrays[right + 1] - preIntArrays[left]
    }

}

/**
 * leetCode 304. 二维区域和检索 - 矩阵不可变(middle)
 * 给定一个二维矩阵 matrix，以下类型的多个请求：
 *
 * 计算其子矩形范围内元素的总和，该子矩阵的 左上角 为 (row1, col1) ，右下角 为 (row2, col2) 。
 * 实现 NumMatrix 类：
 *
 * NumMatrix(int[][] matrix) 给定整数矩阵 matrix 进行初始化
 * int sumRegion(int row1, int col1, int row2, int col2) 返回 左上角 (row1, col1) 、右下角 (row2, col2) 所描述的子矩阵的元素 总和 。
 *
 *
 * 提示:
 * 1.使用好前缀和,便可以做到一次 for 循环解决
 * 2.利用好二维数组,提前计算好不同区间的二维数组的矩形元素之和
 */
class NumMatrix(matrix: Array<IntArray>) {

    private val preMatrixArrays = Array(matrix.size + 1) { IntArray(matrix[0].size + 1) }

    init {
        val m = matrix.size
        val n = matrix[0].size
        for (x in 1..m) {
            for (y in 1..n) {
                //计算每个[0,0,x,y]的矩阵元素之和
                preMatrixArrays[x][y] =
                    preMatrixArrays[x - 1][y] + preMatrixArrays[x][y - 1] + matrix[x - 1][y - 1] - preMatrixArrays[x - 1][y - 1]
            }
        }
    }

    fun sumRegion(x1: Int, y1: Int, x2: Int, y2: Int): Int {
        //每个矩阵元素之和,都可以通过相邻的 4 个矩阵[0,0,x,y]运算得出
        return preMatrixArrays[x2 + 1][y2 + 1] - preMatrixArrays[x1][y2 + 1] - preMatrixArrays[x2 + 1][y1] + preMatrixArrays[x1][y1]
    }

}