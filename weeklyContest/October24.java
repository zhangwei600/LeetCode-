package com.fang.leetcode.weeklyContest;

import java.util.Arrays;

/**
 * @author letian
 * @version 1.0
 * 10月24日的周赛
 */
public class October24 {

    public static void main(String[] args) {
        October24 o = new October24();
        int i = o.subarrayGCD(new int[]{9, 3, 1, 2, 6, 3}, 3);
        System.out.println(i);
    }

    // 给你一个整数数组nums和一个整数k ，请你统计并返回 nums的子数组中元素的最大公因数等于 k的子数组数目。
    // 子数组 是数组中一个连续的非空序列。
    // 数组的最大公因数是能整除数组中所有元素的最大整数。
    // 来源：力扣（LeetCode）
    // 链接：https://leetcode.cn/problems/number-of-subarrays-with-gcd-equal-to-k
    // 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
    // 输入：nums = [9,3,1,2,6,3], k = 3
    // 输出：4
    // 解释：nums 的子数组中，以 3 作为最大公因数的子数组如下：
    //- [9,3,1,2,6,3]
    //- [9,3,1,2,6,3]
    //- [9,3,1,2,6,3]
    //- [9,3,1,2,6,3]
    public int subarrayGCD(int[] nums, int k) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int g = nums[i];
            for (int j = i; j < nums.length; j++) {
                g = gcb(g, nums[j]);
                if (g % k != 0) {
                    break;
                }
                if (g == k) {
                    ans++;
                }
            }
        }
        return ans;
    }

    // 计算最大公因数
    int gcb(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcb(b, a % b);
    }

    // 给你两个下标从 0开始的数组nums 和cost，分别包含n个正整数。
    //
    //你可以执行下面操作 任意次：
    //
    //将nums中 任意元素增加或者减小 1。
    //对第 i个元素执行一次操作的开销是cost[i]。
    //
    //请你返回使 nums中所有元素 相等的 最少总开销。
    //
    //来源：力扣（LeetCode）
    //链接：https://leetcode.cn/problems/minimum-cost-to-make-array-equal
    //著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
    public long minCost(int[] nums, int[] cost) {
        // 中位数贪心
        int n = nums.length;
        long ans = 0;
        int[][] data = new int[n][2];
        long sumCost = 0L;
        for (int i = 0; i < n; i++) {
            data[i][0] = nums[i];
            data[i][1] = cost[i];
            sumCost += cost[i];
        }
        // 按照第一维数据进行排序
        // 将cost看作数字出现的次数
        Arrays.sort(data, (o1, o2) -> (o1[0] - o2[0]));
        long s = 0L;
        for (int i = 0; i < n; i++) {
            s += data[i][1];
            if (s > sumCost / 2) {
                for (int j = 0; j < n; j++) {
                    ans += Math.abs(data[j][0] - data[i][0]) * (long) data[j][1];
                }
                break;
            }
        }
        return ans;
    }

    //    给你两个正整数数组nums 和target，两个数组长度相等。
//
//    在一次操作中，你可以选择两个 不同的下标i 和j，其中0 <= i, j < nums.length，并且：
//
//    令nums[i] = nums[i] + 2且
//    令nums[j] = nums[j] - 2。
//    如果两个数组中每个元素出现的频率相等，我们称两个数组是 相似的。
//
//    请你返回将 nums变得与 target相似的最少操作次数。测试数据保证 nums一定能变得与 target相似。
//
//    来源：力扣（LeetCode）
//    链接：https://leetcode.cn/problems/minimum-number-of-operations-to-make-arrays-similar
//    著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
    // 输入：nums = [8,12,6], target = [2,14,10]
    // 输出：2
    // 解释：可以用两步操作将 nums 变得与 target 相似：
    // - 选择 i = 0 和 j = 2 ，nums = [10,12,4] 。
    // - 选择 i = 1 和 j = 2 ，nums = [10,14,2] 。
    // 2 次操作是最少需要的操作次数。
    public long makeSimilar(int[] nums, int[] target) {
        // 一个加2一个减去2
        Arrays.sort(nums);
        Arrays.sort(target);
        // 贪心的匹配
        // 0代表偶数，1代表奇数
        // 偶数找最近的偶数，奇数找最近的奇数
        int[] flag = new int[2];
        long ans = 0L;
        for (int i : nums) {
            int p = i & 1;
            // 如果当前位置上的数字与i的奇偶性不符合，那么就往后指针移动进行查找
            // 直到找到为止
            while ((target[flag[p]] & 1) != p) {
                flag[p]++;
            }
            // 用完了需要自增
            ans += Math.abs(target[flag[p]++] - i);
        }
        // 一次操作可以消除4个差异，一个数字加上2一个数字减去2
        // 所以最后返回的就是差异除以4
        return ans / 4;
    }
}
