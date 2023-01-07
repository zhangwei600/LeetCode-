package com.fang.leetcode.November;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author letian
 * @version 1.0
 */
public class November11 {

    static int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    public static void main(String[] args) {

        November11 s = new November11();
        int i = s.shortestPathAllKeys(new String[]{"@.a..", "###.#", "b.A.B"});
        System.out.println(i);
        String s1 = String.valueOf(10);
        System.out.println(s1);
    }



    public int shortestPathAllKeys(String[] grid) {
        // 记住一点
        // 那就是广度优先便利第一次遇到的状态，就是出发点到这个点的最小距离
        // 不会有更小的，所以我们记录一下这个距离，后面可能还需要用到
        // 更类似于最短路的迪氏算法的意思
        int n = grid.length;
        int m = grid[0].length();
        // 初始化start位置，也就是出发点
        int[] start = {1, 1, 1};
        Queue<int[]> q1 = new LinkedList<>();
        int target = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char c = grid[i].charAt(j);
                if (c == '@') {
                    start = new int[]{i, j, 0};
                } else if (c <= 'f' && c >= 'a') {
                    // 就是全部相等的值
                    target |= 1 << (c - 'a');
                }
            }
        }
        // 同一个状态下不应该重复访问一个位置
        // 这里面存放的是到各个状态的最小距离
        int[][][] seen = new int[n][m][target + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Arrays.fill(seen[i][j], -1);
            }
        }
        seen[start[0]][start[1]][0] = 0;
        q1.offer(start);
        while (!q1.isEmpty()) {
            int[] cur = q1.poll();
            if (cur[2] == target) {
                return seen[cur[0]][cur[1]][target];
            }
            for (int[] d : dirs) {
                int x = cur[0] + d[0];
                int y = cur[1] + d[1];
                int state = cur[2];
                if (x >= 0 && x < n && y >= 0 && y < m && grid[x].charAt(y) != '#') {
                    char c = grid[x].charAt(y);
                    if (c <= 'f' && c >= 'a') {
                        // 获得了这把钥匙
                        state |= 1 << (c - 'a');
                        if (seen[x][y][state] == -1) {
                            seen[x][y][state] = seen[cur[0]][cur[1]][cur[2]] + 1;
                            q1.offer(new int[]{x, y, state});
                        }
                    } else if (c <= 'F' && c >= 'A') {
                        // 有这把钥匙
                        if (((state & (1 << (c - 'A'))) != 0 && seen[x][y][state] == -1)) {
                            seen[x][y][state] = seen[cur[0]][cur[1]][cur[2]] + 1;
                            q1.offer(new int[]{x, y, state});
                        }
                    } else {
                        // 证明这个位置是.不需要改变状态
                        if (seen[x][y][state] == -1) {
                            seen[x][y][state] = seen[cur[0]][cur[1]][cur[2]] + 1;
                            q1.offer(new int[]{x, y, state});
                        }
                    }
                }
            }
        }
        // 广度优先便利结束，都找不到这个状态的话，直接返回-1，表示此状态不可到达
        return -1;
    }
}
