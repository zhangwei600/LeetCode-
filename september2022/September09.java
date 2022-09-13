package com.fang.leetcode.september2022;

import java.util.Arrays;

/**
 * @author letian
 * @version 1.0
 * 九月九日的题解
 * 两道假设答案，然后进行二分查找的题目,第一题就是两球之间的最小磁力
 * <a href="https://leetcode.cn/problems/magnetic-force-between-two-balls/">...</a>
 */
public class September09 {
    public static void main(String[] args) {
        September09 temp = new September09();
        System.out.println(temp.maxDistance(new int[]{1, 2, 3}, 1));
    }

    public int maxDistance(int[] position, int m) {

        // 排序数组
        Arrays.sort(position);
        int len = position.length;
        int l = 1;
        int r = position[len - 1] - position[0];
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (check(position, mid) < m) {
                r = mid - 1;
            } else if (check(position, mid) == m && check(position, mid + 1) > m) {
                return mid;
            } else {
                l = mid + 1;
            }
        }
        return r;
    }

    // 检查在最短间距再mid的情况下最多放置几个球
    // 如果超过题目给的要求也就是m个球，我们就可以尝试缩小一下范围
    public int check(int[] position, int mid) {
        int pre = position[0];
        int index = 1;
        int ans = 1;
        while (index < position.length) {
            if (position[index] - pre >= mid) {
                //
                ans++;
                pre = position[index];
            }
            index++;
        }
        return ans;
    }
}
