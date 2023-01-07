package com.fang.leetcode.November;

import java.util.ArrayList;
import java.util.List;

/**
 * @author letian
 * @version 1.0
 * 演示11-07的每日一题
 */
public class November07 {
    /**
     * 示例 2:
     * 输入: "(00011)"
     * 输出:  ["(0.001, 1)", "(0, 0.011)"]
     * 解释:
     * 0.0, 00, 0001 或 00.01 是不被允许的。
     * @param args 参数
     */
    public static void main(String[] args) {
//        /**
//         * 我们有一些二维坐标，如 "(1, 3)" 或 "(2, 0.5)"，然后我们移除所有逗号，小数点和空格，
//         * 得到一个字符串S。返回所有可能的原始字符串到一个列表中。
//         * 原始的坐标表示法不会存在多余的零，所以不会出现类似于"00", "0.0", "0.00", "1.0", "001", "00.01"或一些其他更小的数来表示坐标。
//         * 此外，一个小数点前至少存在一个数，所以也不会出现“.1”形式的数字。
//         * 最后返回的列表可以是任意顺序的。而且注意返回的两个数字中间（逗号之后）都有一个空格。
//         */
        November07 s = new November07();
        List<String> strings = s.ambiguousCoordinates("(12003)");
        for (String string : strings) {
            System.out.println(string);
        }
        System.out.println(Integer.MIN_VALUE);
    }

    public List<String> ambiguousCoordinates(String s) {
        ArrayList<String> ans = new ArrayList<>();
        s = s.substring(1, s.length() - 1);
        for (int i = 1; i < s.length(); i++) {
            String s1 = s.substring(0, i);
            List<String> p1 = process(s1);
            if (p1.isEmpty()) {
                continue;
            }
            String s2 = s.substring(i);
            List<String> p2 = process(s2);
            if (p2.isEmpty()) {
                continue;
            }
            for (String temp1 : p1) {
                for (String temp2 : p2) {
                    StringBuilder temp = new StringBuilder();
                    getString(temp1, temp2, temp);
                    ans.add(temp.toString());
                }
            }
        }

        return ans;
    }

    List<String> process(String s) {
        ArrayList<String> ans = new ArrayList<>();
        if (s.length() == 1) {
            ans.add(s);
            return ans;
        }
        // 单独作为一个整数，不添加小数点
        if (s.charAt(0) != '0') {
            ans.add(s);
        }

        // 在i位置后添加小数点，看看是不是符合要求
        for (int i = 1; i < s.length(); i++) {
            // 如果整数部分只有一个数字，那么整数部分开头是不是0都可以
            // 如果整数部分长度超过1，那么就开头就不能是0
            // 小数部分的最后一位不能是0，如1.0, 1,00, 1.010都是不合法的
            if ((s.charAt(0) != '0' || i == 1) && s.charAt(s.length() - 1) != '0') {
                ans.add(s.substring(0, i) + "." + s.substring(i));
            }
        }
        return ans;
    }

    void getString(String s1, String s2, StringBuilder temp) {
        temp.append('(');
        temp.append(s1);
        temp.append(", ");
        temp.append(s2);
        temp.append(')');
    }


}
