package com.fang.leetcode.september2022;

/**
 * @author letian
 * @version 1.0
 * 演示旋转数组查询一个数，这个数组原来是升序排列的
 * 但是其中可能有重复值，因此我们二分的时候有些细节
 */
public class September17 {
    public static void main(String[] args) {

    }

    public boolean search(int[] arr, int target) {
        int l = 0;
        int r = arr.length - 1;
        while (l <= r) {
            int mid = (l + r) >> 1;
            if (arr[mid] == target) {
                return true;
            }
            if (arr[mid] == arr[l] && arr[mid] == arr[r]) {
                // 无法判断那一边是有序的
                l++;
                r--;
            } else if (arr[mid] >= arr[l]) {
                // 左半部分有序
                if (arr[mid] > target && arr[l] <= target) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {
                // 右半部分有序
                if (arr[mid] < target && arr[r] >= target) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }

        }
        return false;
    }
}
