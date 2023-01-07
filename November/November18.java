package com.fang.leetcode.November;

import java.util.Arrays;

/**
 * @author letian
 * @version 1.0
 * 子序列的宽度之和
 * 一个序列的 宽度 定义为该序列中最大元素和最小元素的差值。
 * 给你一个整数数组 nums ，返回 nums 的所有非空 子序列 的 宽度之和 。由于答案可能非常大，请返回对 109 + 7 取余 后的结果。
 * <a href="https://leetcode.cn/problems/sum-of-subsequence-widths/description/">...</a>
 */
public class November18 {

    // 对mod进行取模运算
    private final static int MOD = (int) (1e9 + 7);


    public static void main(String[] args) {
        System.out.println(sumSubseqWidths(new int[]{1, 2, 2}));
    }


    // 题解的链接如下
    // https://leetcode.cn/problems/sum-of-subsequence-widths/solutions/1977682/by-endlesscheng-upd1/
    public static int sumSubseqWidths(int[] nums) {
        int n = nums.length;
        int[] pow = new int[n];
        Arrays.sort(nums);
        pow[0] = 1;
        for (int i = 1; i < n; i++) {
            pow[i] = pow[i - 1] * 2 % MOD;
        }
        long ans = 0L;
        // 针对每一个nums[i]计算它的贡献度，也就是以这个数为最大值和最小值的子序列能有多少个
        // 作为最大值的时候就加到ans上，如果是最小值就从ans中减去，最后得到相应的结果
        for (int i = 0; i < n; i++) {
            // 因为减法可能会造成负数，所以我们最后返回的时候需要进行处理
            ans += (long) (pow[i] - pow[n - i - 1]) * nums[i];
        }
        // 在这里对mod取余之后再加上mod防止负数的产生
        return (int) ((ans % MOD + MOD) % MOD);
    }
}
