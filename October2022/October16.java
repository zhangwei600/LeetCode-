package com.fang.leetcode.October2022;

import java.util.ArrayList;
import java.util.List;

/**
 * @author letian
 * @version 1.0
 * 演示一个周赛的最后一题目以及
 * 今天的每日一题，也就是判断二分图
 */
public class October16 {
    public static void main(String[] args) {

    }
    /**
     * 给定一组n人（编号为1, 2, ..., n），我们想把每个人分进任意大小的两组。每个人都可能不喜欢其他人，那么他们不应该属于同一组。
     * 给定整数 n和数组 dislikes，其中dislikes[i] = [ai, bi]，表示不允许将编号为 ai和bi的人归入同一组。
     * 当可以用这种方法将所有人分进两组时，返回 true；否则返回 false。
     */
    public boolean possibleBipartition(int n, int[][] dislikes) {
        List<Integer>[] g = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int[] d : dislikes) {
            g[d[0]].add(d[1]);
            g[d[1]].add(d[0]);
        }
        int[] flag = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            if (flag[i] == 0 && process(flag, i, 1, g)) {
                return false;
            }
        }
        return true;
    }

    boolean process(int[] flag, int index, int color, List<Integer>[] g) {
        // 染色
        flag[index] = color;
        for (int i : g[index]) {
            // 如果颜色相同就直接返回false
            // 代表没有办法将整张图染成二分颜色图
            if (flag[i] == color) {
                return true;
            }
            // 没有染色就染成相反的颜色
            if (flag[i] == 0 && process(flag, i, -color, g)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 这一题是今天的周赛最后一题
     * 给你一个整数数组 nums 和两个整数 minK 以及 maxK 。
     * nums 的定界子数组是满足下述条件的一个子数组：
     * 子数组中的 最小值 等于 minK 。
     * 子数组中的 最大值 等于 maxK 。
     * 返回定界子数组的数目。
     * 子数组是数组中的一个连续部分。
     */

    public long countSubarrays(int[] nums, int minK, int maxK) {
        // 窗口的左右指针
        int l = 0, r = 0;
        // 这个窗口中的数字的等于minK以及maxK的下标
        int maxP = -1, minP = -1;
        long ans = 0L;
        while (r < nums.length) {
            // 如果出现范围之外的数字，窗口直接重制，任何子数组也不会包括这个数字
            // 因此可以直接重置窗口，同时将数组的maxP以及minP指针进行重置
            if (nums[r] < minK || nums[r] > maxK) {
                // 越过这个点
                r++;
                l = r;
                maxP = -1;
                minP = -1;
                continue;
            }
            if (nums[r] == minK) {
                minP = r;
            }
            if (nums[r] == maxK) {
                maxP = r;
            }
            // 针对每一个窗口进行计算
            // 这个时候子数组的右端点是r
            // 也就是针对这个窗口，枚举右端点，看看右端点固定的情况下，有多少符合要求的定界子数组
            // 左端点只要能小于最近的两个端点值出现的下标即可，这些子数组都是满足题目意思的
            if (maxP != -1 && minP != -1) {
                ans += Math.min(minP, maxP) - l + 1;
            }
            // 别忘了窗口的右边指针移动
            r++;
        }
        return ans;
    }
}
