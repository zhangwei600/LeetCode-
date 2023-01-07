package com.fang.leetcode.weeklyContest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author letian
 * @version 1.0
 * 12月11日的周赛最后一题
 */
public class December11 {
    public static void main(String[] args) {

    }
    int[] fa, size;
    private final static int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};


    // 这个题目还可以是使用优先队列进行离线查询，结果是一样的
    // 只需要进行一次bfs最后，速度比并查集更快
    public int[] maxPoints01(int[][] grid, int[] queries) {
        int n = grid.length;
        int m = grid[0].length;
        int k = queries.length;
        int nm = n * m;
        // 并查集和代表元的伴随数据
        this.fa = new int[nm];
        this.size = new int[nm];
        for (int i = 0; i < nm; i++) {
            fa[i] = i;
            size[i] = 1;
        }
        int[][] data = new int[nm][3];
        Integer[] id = new Integer[k];
        // 从0到k-1，代表queries的下标
        for (int i = 0; i < k; i++) {
            id[i] = i;
        }
        // 记录grid的数据，并保存原始的下标信息
        // 方便后面使用并查集的时候能够快速的找到下标信息，方便将周围的满足要求
        // 的连通块联合起来，这个时候也就是使用并查集的代表元的伴随数据size得到连通块的大小
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                data[m * i + j] = new int[]{grid[i][j], i, j};
            }
        }
        // 按照grid的数字进行从小到大的排序
        Arrays.sort(data, Comparator.comparingInt(o -> o[0]));
        // 指定排序规则，对id数组排序，规则是query数组中数字大的，对应的下标也拍到后面去
        Arrays.sort(id, Comparator.comparingInt(o -> queries[o]));
        int j = 0;
        int[] ans = new int[k];
        for (int i : id) {
            for (; j < nm && data[j][0] < queries[i]; j++) {
                for (int[] d : dirs) {
                    int x = data[j][1] + d[0];
                    int y = data[j][2] + d[1];
                    // 如果相邻的格子满足要求，那么就可以合并
                    if (x >= 0 && x < n && y >= 0 && y < m && grid[x][y] < queries[i]) {
                        merge(m * x + y, m * data[j][1] + data[j][2]);
                    }
                }
            }
            // 答案就是左上角的连通块
            // 前提是这个查询的大小要大于grid左上角的值
            ans[i] = queries[i] > grid[0][0] ? size[find(0)] : 0;
        }
        return ans;
    }

    int find(int x) {
        if (fa[x] != x) {
            fa[x] = find(fa[x]);
        }
        return fa[x];
    }

    void merge(int a, int b) {
        int f1 = find(a);
        int f2 = find(b);
        if (f1 != f2) {
            // 代表元的父亲改到f2去
            // 得到应该有的结果
            fa[f1] = f2;
            size[f2] += size[f1];
        }
    }


    // 使用优先队列进行离线查询得到的结果
    public int[] maxPoints02(int[][] grid, int[] queries) {
        int k = queries.length;
        int n = grid.length, m = grid[0].length;
        Integer[] id = new Integer[k];
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            id[i] = i;
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
        Arrays.sort(id, Comparator.comparingInt(o -> queries[o]));
        pq.offer(new int[]{0, 0, grid[0][0]});
        grid[0][0] = 0;
        int cnt = 0;
        for (int i : id) {
            while (!pq.isEmpty() && pq.peek()[2] < queries[i]) {
                cnt++;
                int[] cur = pq.poll();
                for (int[] d : dirs) {
                    int x = cur[0] + d[0];
                    int y = cur[1] + d[1];
                    if (x < n && x >= 0 && y < m && y >= 0 && grid[x][y] != 0) {
                        pq.offer(new int[]{x, y, grid[x][y]});
                        grid[x][y] = 0;
                    }
                }
            }
            ans[i] = cnt;
        }
        return ans;
    }
}
