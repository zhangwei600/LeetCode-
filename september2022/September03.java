package com.fang.leetcode.september2022;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author letian
 * @version 1.0
 * 九月三日的leetcode题解
 * 第一题的链接是：<a href="https://leetcode.cn/problems/0JzXQB/">...</a>
 * 第二题的链接是：<a href="https://leetcode.cn/problems/maximum-length-of-pair-chain/">...</a>
 */
public class September03 {
    static int m, n;
    // 从一段序列中剔除一段数据
    // 保证这个序列按照原来的相对顺序剔除这个区间的数据后，形成一个单调不减的子序列
    // 问你这个区间有多少个，区间的左右取值范围在[1,m+1]之间，其中m是原来的序列中的最大值
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        m = sc.nextInt();
        n = sc.nextInt();
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = sc.nextInt();
        }
        sc.close();
        // 单调不下降的东西，数组中的最大值是m
        // 子序列的个数有多少是不是
        long ans = 0L;
        // 外层循环确定左边的边界
        // 内层循环查找符合要求的最小的右边界
        for (int i = 1; i <= m; i++) {
            // 右边的边界一开始设置成最大
            // 二分查找右边界，左边的去
            int l = i;
            int r = m + 1;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (check(i, mid, data)) {
                    if (!check(i, mid - 1, data)) {
                        ans += m + 1 - mid;
                        break;
                    }
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
        }
        System.out.print(ans);
    }

    // 将一段数据从这里面排除出去
    public static boolean check(int l, int r, int[] data) {
        int pre = Integer.MIN_VALUE;
        for (int i : data) {
            if ((i > 0 && i < l) || (i > r && i < m + 1)) {
                if (pre > i) {
                    return false;
                }
                pre = i;
            }
        }
        return true;
    }


    // 会议安排的贪心策略，给你一段会议每一个有一个开始时间和结束时间，问你如何安排这些会议
    // 能够安排尽量多的会议
    public int findLongestChain(int[][] pairs) {
        // 第二个数从小到大进行排序，第一个数从小到大排序
        Arrays.sort(pairs, (o1, o2) -> (
                o1[1] != o2[1] ? o1[1] - o2[1] : o1[0] - o2[0]
        ));
        int ans = 1;
        int pre = pairs[0][1];
        // 直接暴力循环破解
        for (int i = 1; i < pairs.length; i++) {
            if (pairs[i][0] > pre) {
                ans++;
                pre = pairs[i][1];
            }
        }
        return ans;
    }
}
