package com.fang.leetcode.september2022;

/**
 * @author letian
 * @version 1.0
 * 演示一个数组去掉中间一部分，使得剩下的左右两部分拼起来能够形成一个单调不减少的数组
 * 题目的来链接如下
 */
public class September19 {
    public static void main(String[] args) {
        September19 temp = new September19();
        System.out.print(temp.findLengthOfShortestSubarray(new int[]{1, 2, 3, 10, 4, 2, 3, 5}));
    }

    public int findLengthOfShortestSubarray(int[] arr) {
        // 左半部分
        // 右半部分
        // 中间部分一定是要删除的
        // 左边部分和右边部分理论上的最长长度我们可以通过遍历得到
        // 但是我们不能直接把这个区间加起来，要保证左边区间的最大值，小于右边区间的
        // 最小值，因此我们可以使用二分查找或者双指针遍历
        // 双指针进行比较的时间复杂度可以达到O(N)，另一个可以达到O(NlogN)
        int l = 0;
        int n = arr.length;
        int r = n - 1;
        while (l < n - 1 && arr[l] <= arr[l + 1]) {
            l++;
        }
        // 数组本来就是单调递增的，所以我们直接返回0即可
        if (l == n - 1) {
            return 0;
        }
        // 右半部分
        while (r > 0 && arr[r] >= arr[r - 1]) {
            r--;
        }
        // 只留下左递增区间和右递增区间需要删掉的子数组长度
        // 给res设置一个上限
        int res = Math.min(r, n - l - 1);
        // 左右两部分的指针
        int i = 0;  // 左边的指针
        int j = r;  // 右边的指针
        while (i <= l && j <= n - 1) {
            if (arr[i] <= arr[j]) {
                // 这个时候是符合要求的数组，可以去掉中间的数组，左边和右边拼起来得到一个单调不减少的数组
                // 从i到j中间的区间给去掉
                res = Math.min(res, j - i);
                i++;
            } else {
                // 拼不出来一个符合要求的单调不减少的区间
                // 我们移动右边的指针，让这个右边的数据增大
                j++;
            }
        }
        //
        return res;
    }
}
