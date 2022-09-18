package com.fang.leetcode.september2022;

import java.util.*;

/**
 * @author letian
 * @version 1.0
 * 演示一个岛问题，在一片区域中，有些岛屿，现在你有一次机会使得一块水变成陆地
 * 问你能够使得岛屿连起来的数量最大是多少
 * 题目链接如下
 * <a href="https://leetcode.cn/problems/making-a-large-island/">...</a>
 */
public class September18 {
    public static void main(String[] args) {

    }
    int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    // n * n矩阵的边长
    int n;
    // 传进来的是一个n*n的矩阵，里面只有0和1，0代表水，1代表陆地
    public int largestIsland(int[][] grid) {

        // 这个表是记录各个编号对应的岛的陆地数量有多大
        Map<Integer, Integer> map = new HashMap<>();
        // 队列，也可以使用list都行
        Queue<int[]> queue = new LinkedList<>();
        // 标号从2开始
        int flag = 2;
        this.n = grid.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // flag是岛屿的标号
                    // 这个dfs目的是为了找到岛屿周围的水，然后将这些水的坐标入队列，同时标明这些水域是属于
                    // 哪一块岛的临近区域
                    // 最后统计岛屿的大小
                    dfs(grid, i, j, map, flag++, queue);
                }
            }
        }

        int ans = 0;
        // 矩阵里面没有岛屿或者全部是岛屿
        // 都会导致没有邻近的水
        if (queue.isEmpty()) {
            if (map.isEmpty()) {
                // 里面全部是水
                return 1;
            } else {
                return n * n;
            }
        }
        // 这个队列里面存放的是周围的水，数组的第一个位置是岛屿的标号
        while (!queue.isEmpty()) {
            flag = queue.peek()[0];
            // 首先是这个岛加上一块陆地，和原来进行比较
            // 这个是针对这个岛的周围的水最大能够产生多少陆地
            int count = 0;
            while (!queue.isEmpty() && flag == queue.peek()[0]) {
                // 针对每一个水走1步，看看能不能到一个岛
                // 如果能够到达一个岛的话，那么就将这两个岛的数量加起来，再加上多出来的一块陆地
                int[] water = queue.poll();
                Set<Integer> flags = new HashSet<>();
                int point = 0;
                for (int[] dir : dirs) {
                    int x = water[1] + dir[0];
                    int y = water[2] + dir[1];
                    // 越界，水走一步到了自己家
                    // 还有就是之前某一片岛屿区域被我们累加计算过，不再重复累加
                    if (x < 0 || x >= n || y < 0 || y >= n || grid[x][y] == flag || grid[x][y] == 0 || flags.contains(grid[x][y])) {
                        continue;
                    }
                    flags.add(grid[x][y]);
                    point += map.get(grid[x][y]);
                }
                count = Math.max(count, point);
            }
            ans = Math.max(ans, count + map.get(flag) + 1);
        }
        return ans;
    }


    public void dfs(int[][] grid, int i, int j, Map<Integer, Integer> map, int flag, Queue<int[]> queue) {
        if (i < 0 || i >= n || j < 0 || j >= n || (grid[i][j] != 0 && grid[i][j] != 1)) {
            return;
        }
        if (grid[i][j] == 0) {
            // flag表示几号岛
            // 这个队列里面添加的是岛屿周围的水
            queue.offer(new int[]{flag, i, j});
            return;
        }
        // 将这个岛屿改成标号flag，表示这个是i号岛
        grid[i][j] = flag;
        // 岛屿数量
        map.put(flag, map.getOrDefault(flag, 0) + 1);
        for (int[] dir : dirs) {
            int x = i + dir[0];
            int y = j + dir[1];
            dfs(grid, x, y, map, flag, queue);
        }
    }
}
