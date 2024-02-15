package char

import java.util.*
import kotlin.math.max


class StringCodeModel {

    /**
     * leetCode 20. 有效的括号
     * 要求:给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     * 有效:必须是连续的配对符号,例如:s = "()[]{}"或者“(([]){})” 输出 true;s = "(]"或者“((”输出 false
     */
    fun isValid(s: String): Boolean {
        if (s.isEmpty() || s.length % 2 != 0) {
            return false
        }
        val linkList = LinkedList<Char>()
        s.forEach { c ->
            if (linkList.isEmpty() && (c == ')' || c == '}' || c == ']')) {
                return false
            }
            val last = linkList.peekLast()
            if ((c == '(' || c == '{' || c == '[')) {
                linkList.offer(c)
            } else {
                if (
                    last == '(' && c != ')'
                    || last == '{' && c != '}'
                    || last == '[' && c != ']'
                ) {
                    return false
                } else if (linkList.isNotEmpty()) {
                    linkList.removeLast()
                }
            }
        }
        return linkList.isEmpty()
    }

    /**
     * 242. 有效的字母异位词
     *给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     *
     * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
     * 示例 1:
     * 输入: s = "anagram", t = "nagaram"
     * 输出: true
     * 示例 2:
     * 输入: s = "rat", t = "car"
     * 输出: false
     */
    fun isAnagram(s: String, t: String): Boolean {
        if (s.length != t.length) {
            return false
        }
        val map = mutableMapOf<Char, Int?>()
        s.forEach { c ->
            map[c]?.let {
                map[c] = it + 1
            } ?: run {
                map[c] = 1
            }
        }
        t.forEach { c ->
            map[c]?.let {
                val value = it - 1
                if (value <= 0) {
                    map.remove(c)
                } else {
                    map[c] = value
                }
            }
        }
        return map.isEmpty()
    }

    /**
     * 387. 字符串中的第一个唯一字符
     * 给定一个字符串 s ，找到 它的第一个不重复的字符，并返回它的索引 。如果不存在，则返回 -1
     */
    fun firstUniqChar(s: String): Int {
        val map = mutableMapOf<Char, Boolean>()
        s.forEach { c ->
            // 如果map里面没有这个char，那么 map[c] = true
            //反之，map里面有这个char，char数量就大于 1 了，那么 map[c] = false
            map[c] = !map.contains(c)
        }
        s.forEachIndexed { index, c ->
            //找到第一个 true
            if (true == map[c]) {
                return index
            }
        }
        return -1
    }

    /**
     * leetCod 394. 字符串解码
     * 给定一个经过编码的字符串，返回它解码后的字符串。
     * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
     * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
     * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
     * 说明：
     * "3[a2[c]]" 输出"accaccacc"，3[a]2[bc]输出aaabcbc
     */
    fun decodeString(s: String): String {
        val result = dfsDecode(s, 0)
        return if (result.contains("_")) result.split("_")[1] else result
    }

    /**
     * position 是序列号，说明当前应该是哪里开始提前 char
     * 方便递归
     */
    private fun dfsDecode(s: String, position: Int): String {
        val append = StringBuilder()
        var multi = 0
        var index = position
        while (index < s.length) {
            when (val charData = s[index]) {
                in '0'..'9' -> {
                    /**
                     * 针对'15'这种多位数的k
                     * 例如 150,早 for 循环拆解下就是 (0+1) -> (1*10+5)->(15 *10+0) = 150
                     */
                    multi = multi * 10 + charData.toString().toInt()
                }

                '[' -> {
                    val tmp: String = dfsDecode(s, index + 1)
                    if (tmp.contains("_")) {
                        index = Integer.parseInt(tmp.split("_")[0])
                        while (multi > 0) {
                            append.append(tmp.split("_")[1])
                            multi--
                        }
                    }
                }

                ']' -> {
                    println("return ${"${index}_$append"}")
                    return "${index}_$append"
                }

                else -> {
                    append.append(charData)
                }
            }
            index++
        }
        return append.toString()
    }

    /**
     * leeCode 205. 同构字符串(easy)
     * 给定两个长度一致的字符串 s 和 t ，判断它们是否是同构的。
     * 如果 s 中的字符可以按某种映射关系替换得到 t ，那么这两个字符串是同构的。
     * 每个出现的字符都应当映射到另一个字符，同时不改变字符的顺序。不同字符不能映射到同一个字符上，相同字符只能映射到同一个字符上，字符可以映射到自己本身。
     *
     * 提示: s 和 t 由任意有效的 ASCII 字符组成(就是只有 数字 和 字母,它们的长度都不会为0并且一致)
     *
     * 示例1:
     * 输入：s = "egg", t = "add"
     * 输出：true
     * 示例 2：
     * 输入：s = "foo", t = "bar"
     * 输出：false
     * 示例 3：
     * 输入：s = "paper", t = "title"
     * 输出：true
     */
    fun isIsomorphic(s: String, t: String): Boolean {
        val sMap = mutableMapOf<Char, Char>()
        for (position in s.indices) {
            val sc = sMap[s[position]]
            if (sc == null && !sMap.values.contains(t[position])) {
                sMap[s[position]] = t[position]
            } else if (sc != t[position]) {
                return false
            }
        }
        return true
    }

    /**
     * leetCode 409. 最长回文串(easy)
     * “回文串”是一个正读和反读都一样的字符串，比如“level”或者“noon”等等就是回文串
     * 现在给定一个包含大写字母和小写字母的字符串 s(最大长度为 2000，最小长度为 0),返回通过 这些字母 构造成的 最长的回文串
     * 在构造过程中，请注意 区分大小写 。比如 "Aa" 不能当做一个回文字符串
     * 示例 1:
     * 输入:s = "abccccdd"
     * 输出:7
     * 解释: 我们可以构造的最长的回文串是"dccaccd", 它的长度是 7
     *
     * 示例 2:
     * 输入:s = "a"
     * 输出:1
     *
     * 示例 3：
     * 输入:s = "aaaaaccc"
     * 输出:7
     */
    fun longestPalindrome(s: String): Int {
        var maxLength = 0
        val map = mutableMapOf<Char, Int>()
        s.forEach { c ->
            map[c]?.let { count ->
                map[c] = count + 1
            } ?: run {
                map[c] = 1
            }
        }
        var hasOdd = false
        map.forEach { (_, count) ->
            if ((count and 1) == 0) {
                //偶数
                maxLength += count
            } else {
                //奇数
                hasOdd = true
                maxLength += (count - 1)
            }
        }
        if (hasOdd && ((maxLength and 1) == 0)) {
            maxLength += 1
        }
        return maxLength
    }

    /**
     * leetCode 516. 最长回文子串(middle)
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     * 如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
     *
     * 示例 1：
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba" 同样是符合题意的答案
     *
     * 示例 2：
     * 输入：s = "cbbd"
     * 输出："bb"
     */
    fun longestPalindromeChild(s: String): String {
        if (s.isNullOrEmpty()) {
            return ""
        }
        if (s.length == 1) {
            return s
        }
        var result = ""
        for (i in 0 until s.length) {
            val result1 = palindrome(s, i, i)
            val result2 = palindrome(s, i, i + 1)
            result = if (result.length > result1.length) result else result1
            result = if (result.length > result2.length) result else result2
        }
        return result
    }

    private fun palindrome(s: String, left: Int, right: Int): String {
        var start = left
        var end = right
        while (start >= 0 && end < s.length && s[start] == s[end]) {
            start--
            end++
        }
        return s.substring(start + 1, end)
    }

    /**
     * leetCode 392. 判断子序列
     *
     * 定字符串 s 和 t ，判断 s 是否为 t 的子序列。
     * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。
     * （例如，"ace"是"abcde"的一个子序列，而"aec"不是）
     * 进阶：
     * 如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码？
     *
     * 示例 1：
     *
     * 输入：s = "abc", t = "ahbgdc"
     * 输出：true
     * 示例 2：
     *
     * 输入：s = "axc", t = "ahbgdc"
     * 输出：false
     *
     * 提示：s 是短字符串，t 是长字符串
     */
    fun isSubsequence(s: String, t: String): Boolean {
        if (s.isEmpty()) {
            return true
        }
        var sIndex = 0
        var tPosition = 0
        //一个循环,双指针,最多循环次数 t.length
        while (tPosition < t.length) {
            if (s[sIndex] == t[tPosition]) {
                // 若已经遍历完 s ，则提前返回 true
                if (++sIndex == s.length) {
                    return true
                }
            }
            tPosition++
        }
        return false
    }

    /**
     * leetCode 151. 反转字符串中的单词(middle)
     *
     * 给你一个英文字符串 s ，请你反转字符串中 单词 的顺序
     * 单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开
     * 返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串
     * 注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格
     * 返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格
     * 示例 1：
     * 输入：s = "the sky is blue"
     * 输出："blue is sky the"
     * 示例 2：
     *
     * 输入：s = "  hello world  "
     * 输出："world hello"
     * 解释：反转后的字符串中不能存在前导空格和尾随空格
     * 示例 3：
     *
     * 输入：s = "a good   example"
     * 输出："example good a"
     * 解释：如果两个单词间有多余的空格，反转后的字符串需要将单词间的空格减少到仅有一个
     */
    fun reverseWords(s: String): String {
        val target = s.trim()
        val builder = java.lang.StringBuilder()
        var endIndex = target.length - 1
        var position = endIndex
        while (position >= 0) {
            //遍历提取最后一个单词，遇到空格就结束了
            while (position >= 0 && target[position] != ' ') {
                position--
            }
            //拼接后面的单词 + ' '
            builder.append(target.subSequence(position + 1, endIndex + 1)).append(' ')
            //跳过空格字符
            while (position >= 0 && target[position] == ' ') {
                position--
            }
            //调整字符串裁剪的结束边界
            endIndex = position
        }
        return builder.toString().trim()
    }

    /**
     * leetCode 3.无重复字符的最长子串(middle)
     * 给定一个字符串 s (英文、数字和空格符组成)，请你找出其中不含有重复字符的 最长子串 的长度。
     *示例 1:
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     */
    fun lengthOfLongestSubstring(s: String): Int {
        val map = mutableMapOf<Char, Int>()
        //使用双指针,这里浮动指针
        var index = -1
        var result = 0
        for (position in s.indices) {
            if (map.containsKey(s[position])) {
                //发现重复 char 了 ,更新 index
                index = max(index, map[s[position]]!!)
            }
            //更新 map 的 char 对应的下标
            map[s[position]] = position
            //计算没有重复的 char 个数
            result = max(result, position - index)
        }
        return result
    }

    /**
     * leetCod 415. 字符串相加(easy)
     *
     * 给定两个字符串形式的非负整数 num1 和num2 (num1 和num2 都只包含数字 0-9)
     * 计算它们的和并同样以字符串形式返回
     * 你不能使用任何內建的用于处理大整数的库（比如 BigInteger）， 也不能直接将输入的字符串转换为整数形式
     *
     * 示例 1：
     * 输入：num1 = "11", num2 = "123"
     * 输出："134"
     *
     * 示例 2：
     * 输入：num1 = "456", num2 = "77"
     * 输出："533"
     *
     * 示例 3：
     * 输入：num1 = "0", num2 = "0"
     * 输出："0"
     */
    fun addStrings(num1: String, num2: String): String {
        val builder = java.lang.StringBuilder()
        var indexOne: Int = num1.length - 1
        var indexTwo: Int = num2.length - 1
        //是否需要加1
        var carry = 0
        while (indexOne >= 0 || indexTwo >= 0) {
            //计算出单个字符的ASCII值
            val addOne = if (indexOne >= 0) num1[indexOne] - '0' else 0
            val addTwo = if (indexTwo >= 0) num2[indexTwo] - '0' else 0
            //得出新的ASCII值
            val result = addOne + addTwo + carry
            //如果整数运算结果大于10，则加 1
            carry = result / 10
            //result % 10 得出整数运算结果的个位上的ASCII值
            builder.append(result % 10)
            indexOne--
            indexTwo--
        }
        if (carry == 1) {
            builder.append(1)
        }
        //反转输出
        return builder.reverse().toString()
    }

    /**
     * leetCod 466. 统计重复个数(hard)
     *
     * 定义 str = [s, n] 表示 str 由 n 个字符串 s 连接构成。
     * 例如，str == ["abc", 3] =="abcabcabc" 。
     * 如果可以从 s2 中删除某些字符使其变为 s1，则称字符串 s1 可以从字符串 s2 获得。
     * 例如，根据定义，s1 = "abc" 可以从 s2 = "abdbec" 获得，仅需要删除加粗且用斜体标识的字符。
     * 现在给你两个字符串 s1 和 s2 和两个整数 n1 和 n2 。由此构造得到两个字符串，其中 str1 = [s1, n1]、str2 = [s2, n2] 。
     * 请你找出一个最大整数 m ，以满足 str = [str2, m] 可以从 str1 获得。
     *
     * 示例 1：
     * 输入：s1 = "acb", n1 = 4, s2 = "ab", n2 = 2
     * 输出：2
     *
     * 示例 2：
     * 输入：s1 = "acb", n1 = 1, s2 = "acb", n2 = 1
     * 输出：1
     *
     * 提示：动态规划
     */
    fun getMaxRepetitions(s1: String, n1: Int, s2: String, n2: Int): Int {
        //str2.length = s2.length * n2 , str1.length = s1.length * n1
        //而 str2.length 又满足  str1.(删除非重复字符后).length / m(未知数)
        //用于记录字符的重复次数
        val repeatCountArray = IntArray(s2.length)
        //开始统计出s1和 s2的重复字符的个数
        repeat(s2.length) { index ->
            var count = index
            repeat(s1.length) { position ->
                if (s1[position] == s2[count]) {
                    //count + 1 并且通过取余防止越界
                    count = (count + 1) % s2.length
                    //更新该位置的字符重复次数
                    repeatCountArray[index]++
                }
            }
        }
        //现在开始统计最终匹配的总字符量
        //ans是s1和s2的重复字符的数量,就是str1.(删除非重复字符后).length
        var ans = 0
        var position = 0
        repeat(n1) {
            //因为 str1.length = s1.length * n1
            //所以可以统计出s1和s2的重复字符的数量
            val add = repeatCountArray[position]
            position = (position + add) % s2.length
            ans += add
        }
        // str1.(删除非重复字符后).length / str2.length = m
        return ans / s2.length / n2
    }


    /**
     * leetCod 383. 赎金信(easy)
     * 给你两个字符串：ransomNote 和 magazine ，他们都是小写英文字符串
     * 现在要判断 ransomNote 能不能由 magazine 里面的字符构成
     * 如果可以，返回 true ；否则返回 false
     * magazine 中的每个字符只能在 ransomNote 中使用一次
     *
     * 示例 1：
     * 输入：ransomNote = "a", magazine = "b"
     * 输出：false
     * 示例 2：
     * 输入：ransomNote = "aa", magazine = "ab"
     * 输出：false
     * 示例 3：
     * 输入：ransomNote = "aa", magazine = "aab"
     * 输出：true
     *
     */
    fun canConstruct(ransomNote: String, magazine: String): Boolean {
        if (magazine.length < ransomNote.length) {
            //magazine比ransomNote短,必然无法组成ransomNote
            return false
        }
        val check = IntArray(26)
        for (char in magazine) {
            check[char - 'a'] += 1
        }
        for (char in ransomNote) {
            check[char - 'a'] -= 1
            if (check[char - 'a'] < 0) {
                //只要没有小于 0 的情况，必然是可以匹配上的
                return false
            }
        }
        return true
    }

    /**
     * leetCod 2696. 删除子串后的字符串最小长度(easy)
     * 给你一个仅由 大写 英文字符组成的字符串 s 。
     * 你可以对此字符串执行一些操作，在每一步操作中，你可以从 s 中删除 任一个 "AB" 或 "CD" 子字符串。
     * 通过执行操作，删除所有 "AB" 和 "CD" 子串，返回可获得的最终字符串的 最小 可能长度。
     * 注意，删除子串后，重新连接出的字符串可能会产生新的 "AB" 或 "CD" 子串。
     *
     *
     * 示例 1：
     * 输入：s = "ABFCACDB"
     * 输出：2
     * 解释：你可以执行下述操作：
     * - 从 "ABFCACDB" 中删除子串 "AB"，得到 s = "FCACDB" 。
     * - 从 "FCACDB" 中删除子串 "CD"，得到 s = "FCAB" 。
     * - 从 "FCAB" 中删除子串 "AB"，得到 s = "FC" 。
     * 最终字符串的长度为 2 。
     * 可以证明 2 是可获得的最小长度。
     * 示例 2：
     * 输入：s = "ACBBD"
     * 输出：5
     * 解释：无法执行操作，字符串长度不变。
     *
     */
    fun minLength(s: String): Int {
        //每次都只删除AB或者CD
        if (s.length < 2) {
            return s.length
        }
        val deque = java.util.ArrayDeque<Char>()
        s.forEach {
            if (deque.isNotEmpty() && (it == 'B' && deque.peek() == 'A' || it == 'D' && deque.peek() == 'C')) {
                //出栈
                deque.pop()
            } else {
                //入栈
                deque.push(it)
            }
        }
        return deque.size
    }


    /**
     * leetCod 2645. 构造有效字符串的最少插入数(middle)
     * 给你一个字符串 word ，其由"a"、"b"、"c" 这三个字符随机组成
     * 你可以向其中任何位置插入 "a"、"b" 或 "c" 任意次，返回使 word 有效 需要插入的最少字母数。
     * 如果字符串可以由 "abc" 串联多次得到，则认为该字符串 有效 。
     *
     * 示例 1：
     * 输入：word = "b"
     * 输出：2
     * 解释：在 "b" 之前插入 "a" ，在 "b" 之后插入 "c" 可以得到有效字符串 "abc" 。
     * 示例 2：
     * 输入：word = "aaa"
     * 输出：6
     * 解释：在每个 "a" 之后依次插入 "b" 和 "c" 可以得到有效字符串 "abcabcabc" 。
     * 示例 3：
     * 输入：word = "abc"
     * 输出：0
     * 解释：word 已经是有效字符串，不需要进行修改。
     *
     * 提示:模拟题，使用规律推理
     * 需要插入的字符数量为 t x 3(abc个数) - s.length,想法子算出 t
     * 参考一下 caa ,abb 这些插入数据，进行推导
     */
    fun addMinimum(s: String): Int {
        if ("abc" == s) {
            //已经是有效字符串
            return 0
        }
        if (s.length == 1) {
            //word由"a"、"b"、"c" 这三个字符随机组成
            return 2
        }
        var ans = 1
        for (i in 1..<s.length) {
            //前一个<=后一个,说明不是连续的 abc 了
            //需要插入字符了
            if (s[i] <= s[i - 1]) {
                ans++
            }
        }
        return ans * 3 - s.length
    }

    fun countWords(words1: Array<String>, words2: Array<String>): Int {
        val check = mutableMapOf<String, Int>()
        val check2 = mutableMapOf<String, Int>()
        for (item in words1) {
            check[item] = (check[item] ?: 0) + 1
        }
        for (item in words2) {
            check2[item] = (check2[item] ?: 0) + 1
        }
        var count = 0
        check.forEach { (t, u) ->
            if (u == 1 && check2[t] != null && check2[t] == 1) {
                count++
            }
        }
        return count
    }

    /**
     * leetCod 2744. 最大字符串配对数目(easy)
     *
     * 给你一个下标从 0 开始的数组 words(只有小写字母) ，数组中包含 互不相同 的字符串,并且长度固定为 2
     * 如果字符串 words[i] 与字符串 words[j] 满足以下条件，我们称它们可以匹配：
     * 1. 字符串 words[i] 等于 words[j] 的反转字符串。
     * 2. 0 <= i < j < words.length
     * 请你返回数组 words 中的 最大 匹配数目，注意，每个字符串最多匹配一次
     *
     * 示例 1：
     * 输入：words = ["cd","ac","dc","ca","zz"]
     * 输出：2
     * 解释：在此示例中，我们可以通过以下方式匹配 2 对字符串：
     * - 我们将第 0 个字符串与第 2 个字符串匹配，因为 word[0] 的反转字符串是 "dc" 并且等于 words[2]。
     * - 我们将第 1 个字符串与第 3 个字符串匹配，因为 word[1] 的反转字符串是 "ca" 并且等于 words[3]。
     * 可以证明最多匹配数目是 2
     * 示例 2：
     * 输入：words = ["ab","ba","cc"]
     * 输出：1
     * 解释：在此示例中，我们可以通过以下方式匹配 1 对字符串：
     * - 我们将第 0 个字符串与第 1 个字符串匹配，因为 words[1] 的反转字符串 "ab" 与 words[0] 相等。
     * 可以证明最多匹配数目是 1 。
     * 示例 3：
     *
     * 输入：words = ["aa","ab"]
     * 输出：0
     * 解释：这个例子中，无法匹配任何字符串
     *
     */
    fun maximumNumberOfStringPairs(words: Array<String>): Int {
        val nums = Array(26) {
            BooleanArray(26)
        }
        var ans = 0
        words.forEach { s ->
            //s都是长度为 2 的小写字母字符串
            //因此可以用减去'a'定位二维数组
            val x = s[0] - 'a'
            val y = s[1] - 'a'
            if (nums[x][y]) {
                ans++
            } else {
                nums[x][y] = true
            }
        }
        return ans
    }

    /**
     * leetCod 345. 反转字符串中的元音字母(easy)
     * 给你一个字符串 s（1 <= s.length <= 3 * 105,只有英文字母） ，仅反转字符串中的所有元音字母，并返回结果字符串。
     * 元音字母包括 'a'、'e'、'i'、'o'、'u'，且可能以大小写两种形式出现不止一次。
     *
     * 示例 1：
     * 输入：s = "hello"
     * 输出："holle"
     * 示例 2：
     * 输入：s = "leetcode"
     * 输出："leotcede"
     *
     * 提示：单调栈
     */
    fun reverseVowels(s: String): String {
        val res = CharArray(s.length)
        Arrays.fill(res, '\u0000')
        val checkString = "aeiouAEIOU"
        val checkArray = CharArray(checkString.length)
        checkString.forEachIndexed { index, c ->
            checkArray[index] = c
        }
        val stack = Stack<Char>()
        s.forEachIndexed { index, c ->
            if (c in checkArray) {
                stack.push(c)
            } else {
                res[index] = c
            }
        }
        repeat(res.size) {
            if (res[it] == '\u0000') {
                res[it] = stack.pop()
            }
        }
        return String(res)
    }

    /**
     * leetCod 443. 压缩字符串(middle)
     * 给你一个字符数组 chars(1 <= chars.length <= 2000,可以是大小写字母,数字,特殊符号)
     * 请使用下述算法压缩：从一个空字符串 s 开始。对于 chars 中的每组 连续重复字符 ：
     * 如果这一组长度为 1 ，则将字符追加到 s 中。
     * 否则，需要向 s 追加字符，后跟这一组的长度。
     * 压缩后得到的字符串 s 不应该直接返回 ，需要转储到字符数组 chars 中。需要注意的是，如果组长度为 10 或 10 以上，则在 chars 数组中会被拆分为多个字符。
     * 请在 修改完输入数组后 ，返回该数组的新长度。
     * 你必须设计并实现一个只使用常量额外空间的算法来解决此问题。
     *
     * 示例 1：
     * 输入：chars = ["a","a","b","b","c","c","c"]
     * 输出：返回 6 ，输入数组的前 6 个字符应该是：["a","2","b","2","c","3"]
     * 解释："aa" 被 "a2" 替代。"bb" 被 "b2" 替代。"ccc" 被 "c3" 替代。
     * 示例 2：
     * 输入：chars = ["a"]
     * 输出：返回 1 ，输入数组的前 1 个字符应该是：["a"]
     * 解释：唯一的组是“a”，它保持未压缩，因为它是一个字符。
     * 示例 3：
     * 输入：chars = ["a","b","b","b","b","b","b","b","b","b","b","b","b"]
     * 输出：返回 4 ，输入数组的前 4 个字符应该是：["a","b","1","2"]。
     * 解释：由于字符 "a" 不重复，所以不会被压缩。"bbbbbbbbbbbb" 被 “b12” 替代
     *
     * 提示：双指针
     */
    fun compress(chars: CharArray): Int {
        if (chars.isEmpty()) {
            return 0
        }
        if (chars.size == 1) {
            return 1
        }
        val ans = StringBuilder()
        val length = chars.size
        var index = 0
        while (index < length) {
            var position = index
            while (position < length && chars[position] == chars[index]) {
                position++
            }
            position--
            if (position == index) {
                //说明是不同 char
                ans.append(chars[index])
                index++
            } else {
                //说明是连续相同 char
                ans.append(chars[index])
                ans.append(position - index + 1)
                index = position + 1
            }
        }
        Arrays.fill(chars, ' ')
        println("ans is $ans")
        ans.forEachIndexed { i, c ->
            chars[i] = c
        }
        return ans.length
    }

    /**
     * leetCode 468. 验证IP地址(middle)
     * 给定一个字符串 queryIP（queryIP 仅由英文字母，数字，字符 '.' 和 ':' 组成）
     * 如果是有效的 IPv4 地址，返回 "IPv4" ；如果是有效的 IPv6 地址，返回 "IPv6" ；如果不是上述类型的 IP 地址，返回 "Neither"
     *
     * 有效的IPv4地址 是 “x1.x2.x3.x4” 形式的IP地址。 其中 0 <= xi <= 255 且 xi 不能包含 前导零。
     * 例如: “192.168.1.1” 、 “192.168.1.0” 为有效IPv4地址
     * “192.168.01.1” 为无效IPv4地址; “192.168.1.00” 、 “192.168@1.1” 为无效IPv4地址。
     *
     * 一个有效的IPv6地址 是一个格式为“x1:x2:x3:x4:x5:x6:x7:x8” 的IP地址，其中:
     *
     * 1 <= xi.length <= 4
     * xi 是一个 十六进制字符串 ，可以包含数字、小写英文字母( 'a' 到 'f' )和大写英文字母( 'A' 到 'F' )。
     * 在 xi 中允许前导零。
     * 例如 "2001:0db8:85a3:0000:0000:8a2e:0370:7334" 和 "2001:db8:85a3:0:0:8A2E:0370:7334" 是有效的 IPv6 地址
     * 而 "2001:0db8:85a3::8A2E:037j:7334" 和 "02001:0db8:85a3:0000:0000:8a2e:0370:7334" 是无效的 IPv6 地址
     *
     * 示例 1：
     * 输入：queryIP = "172.16.254.1"
     * 输出："IPv4"
     * 解释：有效的 IPv4 地址，返回 "IPv4"
     * 示例 2：
     * 输入：queryIP = "2001:0db8:85a3:0:0:8A2E:0370:7334"
     * 输出："IPv6"
     * 解释：有效的 IPv6 地址，返回 "IPv6"
     * 示例 3：
     * 输入：queryIP = "256.256.256.256"
     * 输出："Neither"
     * 解释：既不是 IPv4 地址，又不是 IPv6 地址
     *
     */
    fun validIPAddress(queryIP: String): String {
        val splitV4 = queryIP.split(".")
        if (splitV4.size == 4) {
            var ans = true
            for (i in 0..<4) {
                val ip = splitV4[i]
                if (ip.length > 3 || ip.isEmpty()) {
                    ans = false
                    break
                }
                if (ip.startsWith("0") && ip.length > 1) {
                    ans = false
                    break
                }
                val address = ip.toIntOrNull()
                if (address == null || address < 0 || address > 255) {
                    ans = false
                    break
                }
            }
            return if (ans) "IPv4" else "Neither"
        }
        val splitV6 = queryIP.split(":")
        if (splitV6.size == 8) {
            var ans = true
            for (i in 0..<8) {
                val ip = splitV6[i]
                if (ip.length > 4 || ip.isEmpty()) {
                    ans = false
                    break
                }
                run {
                    ip.forEach { c ->
                        if (c !in 'a'..'f' && c !in '0'..'9' && c !in 'A'..'F') {
                            ans = false
                            return@run
                        }
                    }
                }
                if (!ans) {
                    break
                }
            }
            return if (ans) "IPv6" else "Neither"
        }
        return "Neither"
    }


    /**
     * leetCode 481. 神奇字符串(middle)
     *
     * 神奇字符串 s 仅由 '1' 和 '2' 组成，并需要遵守下面的规则：
     *
     * 神奇字符串 s 的神奇之处在于，串联字符串中 '1' 和 '2' 的连续出现次数可以生成该字符串
     * s 的前几个元素是 s = "122",并且可以推出后面的元素，一个长度为 19 的神奇字符串:1221121221221121122
     * 给你一个整数 n ，使用 122 作为开头，构建一组长度为 n 的神奇字符串，返回该神奇字符串中的数字 1 的数量
     *
     * 示例 1：
     * 输入：n = 6
     * 输出：3
     * 解释：n 是 6 ，构建出来的神奇字符串是 “122112”，它包含三个 1，因此返回 3 。
     * 示例 2：
     * 输入：n = 1
     * 输出：n 是 1，神奇字符串是 122 开头，所以返回 1
     *
     */
    fun magicalString(n: Int): Int {
        if (n == 0) {
            return 0
        }
        var dimple = 2
        if (n <= (dimple + 1)) {
            return 1
        }
        val builder = StringBuilder("122")
        var ans = 1
        while (builder.length < n) {
            val last = builder.last()
            val check = builder[dimple] == '2'
            val append = if (last == '1') '2' else '1'
            builder.append(append)
            if (append == '1') {
                ans++
            }
            if (check && builder.length < n) {
                builder.append(append)
                if (append == '1') {
                    ans++
                }
            }
            dimple++
        }
        return ans
    }
}

fun main(args: Array<String>) {
    val item = StringCodeModel()
//    print(item.isValid("{}]"))
//    println((",false"))
//    print(item.isValid("(([]){})"))
//    println((",true"))
//    print(item.isValid("([)]"))
//    println((",false"))
//    print(item.isValid("(("))
//    println((",false"))
//    print(item.isValid("){"))
//    println((",false"))
//    print(item.isValid("(["))
//    println((",false"))

    //println(item.decodeString("3[a2[c]]") + ",ans is :accaccacc" + ",you are ${item.decodeString("3[a2[c]]") == "accaccacc"}")

//    println(item.isIsomorphic("paper", "title"))
//    println(item.longestPalindrome("ccd"))
//    println(item.reverseWords("the sky is blue"))
//    println(item.longestPalindromeChild("ccd"))
//    println(item.compress(charArrayOf('a', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b')))
//    println(item.validIPAddress("172.16.254.1"))
//    println(item.validIPAddress("2001:0db8:85a3:0:0:8A2E:0370:7334"))
//    println(item.validIPAddress("256.256.256.256"))
    println(item.magicalString(6))
    println(item.magicalString(1))
    println(item.magicalString(4))
}


