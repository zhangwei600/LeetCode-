package com.fang.leetcode.December;

/**
 * @author letian
 * @version 1.0
 */
public class December04 {
    int ans = Integer.MAX_VALUE;
    int target;
    public static void main(String[] args) {
        December04 s = new December04();
        // 主料的价格为3，10
        // 辅料的价格为2，5
        // 制作出价格最接近9的冰激淋的
        // 的价格是多少，返回。如果两个数字与9的差绝对值相等，返回价格较小的那个。
        int i = s.closestCost(new int[]{3, 10}, new int[]{2, 5}, 9);
        System.out.println(i);
    }
    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        // 只能选择一种主料，可以选多种配料或者不选择配料
        // 主料只可以选择一份，配料可以选择一份或者两份
        // 得到最接近target的成本
        this.target = target;
        for (int i : baseCosts) {
            process(i, toppingCosts, 0);
        }
        return this.ans;
    }

    // 三个参数，已经得到的冰激淋的价格sum,辅料价格表,以及index往后可以使用的辅料
    void process(int sum, int[] data, int index) {
        if (Math.abs(target - sum) < Math.abs(target - ans)) {
            ans = sum;
        }
        if (Math.abs(target - sum) == Math.abs(target - ans) && sum < ans) {
            ans = sum;
        }
        if (sum >= target || index >= data.length) {
            return;
        }
        // 辅料可以不选择，但是最多只能选择两份
        process(sum + data[index], data, index + 1);
        process(sum + 2 * data[index], data, index + 1);
        process(sum, data, index + 1);
    }
}
