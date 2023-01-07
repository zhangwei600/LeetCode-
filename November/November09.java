package com.fang.leetcode.November;


/**
 * @author letian
 * @version 1.0
 * 11月9日的每日一题,最大加号标志
 * <a href="https://leetcode.cn/problems/largest-plus-sign/">...</a>
 */
@SuppressWarnings("all")
public class November09 {
    public static void main(String[] args) {
        November09 s = new November09();
        int i = s.orderOfLargestPlusSign(10, new int[][]{{1, 1}});
        System.out.println(i);
    }

    public int orderOfLargestPlusSign(int n, int[][] mines) {
        // 图形，里面是1的位置是不能成为最大加号标志的一部分的
        int[][] g = new int[n][n];
        for (int[] m : mines) {
            g[m[0]][m[1]] = 1;
        }
        // 以i,j位置为中心，往外最多延伸的的臂长，包括四个方向，下面的四个循环得到的就是瓶颈
        // 也就是最多延伸的臂长
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = n;
            }
        }
        int ans = 0;
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            // 往左能延伸多长的多少个连续个数的1
            for (int j = 0; j < n; j++) {
                if (g[i][j] == 1) {
                    cnt = 0;
                } else {
                    cnt++;
                }
                dp[i][j] = Math.min(dp[i][j], cnt);
            }
            // 注意在进入循环的时候将计数变量置为0
            cnt = 0;
            // 往右能延伸多长的多少个连续个数的1
            for (int j = n - 1; j >= 0; j--) {
                if (g[i][j] == 1) {
                    cnt = 0;
                } else {
                    cnt++;
                }
                dp[i][j] = Math.min(dp[i][j], cnt);
            }
        }
        for (int i = 0; i < n; i++) {
            cnt = 0;
            for (int j = 0; j < n; j++) {
                if (g[j][i] == 1) {
                    cnt = 0;
                } else {
                    cnt++;
                }
                dp[j][i] = Math.min(dp[j][i], cnt);
            }
            cnt = 0;
            for (int j = n - 1; j >= 0; j--) {
                if (g[j][i] == 1) {
                    cnt = 0;
                } else {
                    cnt++;
                }
                dp[j][i] = Math.min(dp[j][i], cnt);
                ans = Math.max(dp[j][i], ans);
            }
        }
        return ans;
    }


}
