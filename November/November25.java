package com.fang.leetcode.November;

import java.util.TreeSet;

/**
 * @author letian
 * @version 1.0
 * 25日的每日一题
 * <a href="https://leetcode.cn/problems/expressive-words/description/">...</a>
 */
public class November25 {

    /**
     *有时候人们会用重复写一些字母来表示额外的感受，比如 "hello" -> "heeellooo", "hi" -> "hiii"。
     * 我们将相邻字母都相同的一串字符定义为相同字母组，例如："h", "eee", "ll", "ooo"。
     * 对于一个给定的字符串 S ，如果另一个单词能够通过将一些字母组扩张从而使其和 S 相同，我们将这个单词定义为可扩张的（stretchy）。
     * 扩张操作定义如下：选择一个字母组（包含字母 c ），然后往其中添加相同的字母 c 使其长度达到 3 或以上。
     * 例如，以 "hello" 为例，我们可以对字母组 "o" 扩张得到 "hellooo"，但是无法以同样的方法得到 "helloo" 因为字母组 "oo" 长度小于 3。
     * 此外，我们可以进行另一种扩张 "ll" -> "lllll" 以获得 "helllllooo"。如果 s = "helllllooo"，
     * 那么查询词 "hello" 是可扩张的，因为可以对它执行这两种扩张操作使得 query = "hello" -> "hellooo" -> "helllllooo" = s。
     * 输入一组查询单词，输出其中可扩张的单词数量
     */

    public static void main(String[] args) {
        November25 s = new November25();
        // 其中只有hello可以通过拓展得到字符串s，也就是"heeellooo"
        // 最后一个helo是因为s中的ll段长度只有2，小于3。
        // 字母组相等则不用考虑长度这件事
        int i = s.expressiveWords("heeellooo", new String[]{"hello", "hi", "helo"});
        System.out.println(i);
        TreeSet<Integer> set = new TreeSet<>();
        set.add(10);
        set.add(1);
        set.add(100);
        System.out.println(set.last());
    }


    public int expressiveWords(String s, String[] words) {
        int ans = 0;
        for (String w : words) {
            if (check(s, w)) {
                ans++;
            }
        }
        return ans;
    }

    private boolean check(String s, String w) {
        if (s.length() < w.length()) {
            return false;
        }
        int i1 = 0;
        int i2 = 0;
        while (i1 < s.length() && i2 < w.length()) {
            // 一个字母组的首字母必然相等
            // 因此我们拿出首字母，然后进行比较，不相等直接返回false
            // 否则，我们比较一下两个字母组的长度
            char c1 = s.charAt(i1++);
            char c2 = w.charAt(i2++);
            if (c1 != c2) {
                return false;
            }
            int cnt1 = 1;
            int cnt2 = 1;
            while (i1 < s.length() && s.charAt(i1) == c1) {
                i1++;
                cnt1++;
            }
            while (i2 < w.length() && w.charAt(i2) == c2) {
                i2++;
                cnt2++;
            }
            if ((cnt1 < cnt2) || (cnt1 > cnt2 && cnt1 < 3)) {
                return false;
            }
        }
        // 最后一定是两个字符串都读取完毕
        // s才能由字符串w扩张得到
        return i1 == s.length() && i2 == w.length();
    }

}
