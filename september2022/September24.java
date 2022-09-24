package com.fang.leetcode.september2022;

import java.util.*;

/**
 * @author letian
 * @version 1.0
 *
 */
public class September24 {
    public static void main(String[] args) {

    }

    /**
     * 你的国家有无数个湖泊，所有湖泊一开始都是空的。当第n个湖泊下雨前是空的，那么它就会装满水。
     * 如果第 n个湖泊下雨前是满的，这个湖泊会发生洪水。你的目标是避免任意一个湖泊发生洪水
     * 给你一个整数数组rains，其中：
     * rains[i] > 0表示第 i天时，第 rains[i]个湖泊会下雨。
     * rains[i] == 0表示第 i天没有湖泊会下雨，你可以选择一个湖泊并抽干这个湖泊的水。
     * 请返回一个数组ans，满足：
     * ans.length == rains.length
     * 如果rains[i] > 0 ，那么ans[i] == -1。
     * 如果rains[i] == 0，ans[i]是你第i天选择抽干的湖泊。
     * 如果有多种可行解，请返回它们中的 任意一个。如果没办法阻止洪水，请返回一个长度为0的数组。
     * 请注意，如果你选择抽干一个装满水的湖泊，它会变成一个空的湖泊。但如果你选择抽干一个空的湖泊，那么将无事发生。
     * 链接：<a href="https://leetcode.cn/problems/avoid-flood-in-the-city">...</a>
     * @param rains 哪一天的标号为rains[i]的湖泊会下雨
     * @return 根据题目要求返回的数组
     */
    // 避免洪水泛滥的题目
    public int[] avoidFlood(int[] rains) {
        int n = rains.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        // map里面记录的是上一次湖泊的降雨日期
        // 第一次降雨啥也不管
        Map<Integer, Integer> map = new HashMap<>();
        // 晴天的集合
        TreeSet<Integer> tree = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            if (rains[i] == 0) {
                // 这里是添加晴天的数据
                // 使用有序表是方便进行二分的查找
                tree.add(i);
                // 先默认这个晴天处理1号湖泊
                // 后面根据数据情况进行修改
                ans[i] = 1;
                continue;
            }
            if (!map.containsKey(rains[i])) {
                map.put(rains[i], i);
            } else {
                // 看看有没有在两次降雨之间的晴天，如果有那一天就来抽这个湖泊的水
                // 如果没有直接返回空数组，没办法阻止洪水
                Integer low = tree.higher(map.get(rains[i]));
                if (low == null) {
                    return new int[0];
                } else {
                    ans[low] = rains[i];
                    // 更新上一次这个湖泊的第一次降雨时间
                    // 上一次降雨的水已经抽掉了，在第i天又重新下雨填满了
                    map.put(rains[i], i);
                    // 将这个晴天去掉
                    tree.remove(low);
                }
            }
        }
        return ans;

    }

    /**
     *
     * @param arr 数组
     * @param m 连续1的长度为m的序列
     * @return 最后的操作能够满足这个的区间
     * 给你一个数组 arr ，该数组表示一个从 1 到 n 的数字排列。有一个长度为 n 的二进制字符串，该字符串上的所有位最初都设置为 0 。
     * 在从 1 到 n 的每个步骤 i 中（假设二进制字符串和 arr 都是从 1 开始索引的情况下），二进制字符串上位于位置 arr[i] 的位将会设为 1 。
     * 给你一个整数 m ，请你找出二进制字符串上存在长度为 m 的一组 1 的最后步骤。一组 1 是一个连续的、由 1 组成的子串，且左右两边不再有可以延伸的 1 。
     * 返回存在长度 恰好 为 m 的 一组 1的最后步骤。如果不存在这样的步骤，请返回 -1
     * 链接：<a href="https://leetcode.cn/problems/find-latest-group-of-size-m">...</a>
     */
    public int findLatestStep(int[] arr, int m) {
        // 键是起点，value是长度这一段的长度
        TreeMap<Integer, Integer> map = new TreeMap<>();
        // count是长度为m的连续1的段有几个
        int count = 0;
        int res = -1;
        for (int i = 0; i < arr.length; i++) {
            int v = arr[i];
            int start = v;
            // 看看有没有前面的是不是1
            int nextLen = map.getOrDefault(v + 1, 0);
            int len = nextLen + 1;
            if (nextLen == m) {
                count--;
            }
            // 右边的端点清除
            map.remove(v + 1);
            Integer preStart = map.floorKey(v - 1);
            if (preStart != null && preStart + map.get(preStart) == v) {
                start = preStart;
                int preLen = map.remove(start);
                // 少了一段长度为m的连续序列
                if (preLen == m) {
                    count--;
                }
                len += preLen;
            }
            map.put(start, len);
            if (len == m) {
                count++;
            }
            // 存在长度为m的序列的时候可以计入res
            if (count > 0) {
                res = i + 1;
            }
        }
        return res;

    }
}
