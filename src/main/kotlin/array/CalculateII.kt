package array


/**
 * leetcode 227. 基本计算器 II(middle)
 * https://leetcode.cn/problems/basic-calculator-ii/description/
 * 输入：s = "3+2*2"
 * 输出：7
 */
class CalculateII {

    fun calculate(s: String): Int {
        var pre = 0
        var ans = 0
        var sign = '+'
        var index = 0
        while (index < s.length) {
            when (s[index]) {
                ' ' -> {
                }

                '-', '+', '/', '*' -> {
                    sign = s[index]
                }

                else -> {
                    //digit
                    var temp = s[index] - '0'
                    while (index + 1 < s.length && s[index + 1].isDigit()) {
                        temp = 10 * temp + (s[++index] - '0')
                    }
                    when (sign) {
                        '+' -> {
                            ans += pre
                            pre = temp
                        }
                        '-' -> {
                            ans += pre
                            pre = -temp
                        }
                        '*' -> {
                            pre *= temp
                        }
                        '/' -> {
                            pre /= temp
                        }
                    }
                }
            }
            index++
        }
        ans += pre
        return ans
    }

}