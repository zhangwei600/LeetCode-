package com.fang.leetcode.september2022;

import java.util.Arrays;

/**
 * @author letian
 * @version 1.0
 * 二分查找的两道题
 */
@SuppressWarnings("all")
public class September25 {
    public static void main(String[] args) {

    }
    //给你四个整数：n 、a 、b 、c ，请你设计一个算法来找出第 n 个丑数
    //丑数是可以被 a 或 b 或 c 整除的 正整数 。
    // 输入：n = 3, a = 2, b = 3, c = 5
    //输出：4
    //解释：丑数序列为 2, 3, 4, 5, 6, 8, 9, 10... 其中第 3 个是 4。
    // https://leetcode.cn/problems/ugly-number-iii/
    public int nthUglyNumber(int n, int a, int b, int c) {
        long A =  a;
        long B =  b;
        long C =  c;
        // 下边界
        long l = Math.min(a, Math.min(b, c));
        // 二分的上边界直接做成全局最大值
        long h = 2000000001;
        // 四个最大公倍数
        long com_AB = A * B / gcb(A, B);
        long com_BC = B * C / gcb(B, C);
        long com_AC = A * C / gcb(A, C);
        // 最大公倍数的计算
        // 只能使用AB和AC吗
        long com = com_AB * com_AC / gcb(com_AB, com_AC);
        while (l <= h) {
            long mid = l +((h - l) >> 1);
            // 找丑数
            long count = search(mid, A, B, C, com_AB, com_BC, com_AC, com);
            if (count > n) {
                // 丑数太多，缩小右边界
                h = mid - 1;
            } else if (count < n) {
                // 丑数太少，扩大左边界
                l = mid + 1;
            } else {
                long flag = Math.min(mid % a, Math.min(mid % b, mid % c));
                // 返回最近离mid最近的丑数
                return (int) (mid - flag);
            }
        }
        return 0;
    }

    // 最大公因数
    long gcb(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcb(b, a % b);
    }

    public long search(long mid, long A, long B, long C, long AB, long BC, long AC, long ABC) {
        long all = mid / A + mid / B + mid / C;
        long two = mid / AB + mid / AC + mid / BC;
        long three = mid / ABC;
        // 容斥原理计算从1到mid有几个丑数
        return all - two + three;
    }

    static int mod = (int) (1e9 + 7);

    // 销售价值减少的颜色球
    // 输入：inventory = [2,5], orders = 4
    //输出：14
    //解释：卖 1 个第一种颜色的球（价值为 2 )，卖 3 个第二种颜色的球（价值为 5 + 4 + 3）。
    //最大总和为 2 + 5 + 4 + 3 = 14 。
    //
    //来源：力扣（LeetCode）
    //链接：https://leetcode.cn/problems/sell-diminishing-valued-colored-balls

    public int maxProfit(int[] inventory, int orders) {
        Arrays.sort(inventory);
        int l = 0;
        int n = inventory.length;
        int r = inventory[n - 1];
        int edge = 0;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            int index = search(inventory, mid);
            // 大于mid才行
            long sum = 0L;
            for (int i = index; i < n; i++) {
                // 需要的数据
                sum += inventory[i] - mid;
            }
            if (sum >= orders) {
                edge = mid;
                l = mid + 1;
            } else {
                // 这个是开的最低价格太大
                // 卖得太少
                r = mid - 1;
            }
        }
        // 上面出来的edge是数组里面的卖过东西后的最小值
        // 同时意味着数组里面不会有edge+1的数值
        long sum = 0L;
        edge += 1;
        long res = 0;
        // 经过下面一个for循环处理后，里面最大的价格就是edge，也就是上一个二分查找
        // 得到的edge + 1；这个是一个边界
        for (long num : inventory) {
            if (num > edge) {
                sum += num - edge;
                long size = num - edge;
                // 等差数列求和，首项加上尾项乘以项数然后除以2
                res += (edge + 1 + num) * size / 2;
                res %= mod;
            }
        }
        res += (orders - sum) * edge % mod;
        res %= mod;
        return (int) res;
    }


    // 找比limit大的数字
    int search(int[] inventory, int limit) {
        int l = 0;
        int r = inventory.length - 1;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            if (inventory[mid] <= limit) {
                l = mid + 1;
            } else {
                if (mid == 0 || inventory[mid - 1] <= limit) {
                    return mid;
                }
                r = mid - 1;
            }
        }
        // 找不到比limit更大的数字
        return Integer.MAX_VALUE;
    }
}
