package com.fang.leetcode.weeklyContest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author letian
 * @version 1.0
 * 演示周赛的最后一题
 * 是一个非常好的题目
 * <a href="https://leetcode.cn/problems/divide-nodes-into-the-maximum-number-of-groups/description/">...</a>
 */
public class December04 {
    // 题意即图的每一个连通块都是二分图
    // 且对每一个连通块枚举其中每一个节点
    // 得到这个连通块最多能够分成几个部分
    // 对每一个连通块求解上面的字面量得到结果
    public static void main(String[] args) {

    }
    private List<Integer>[] g;
    // 染色标记
    private int[] color;
    // 每一轮的访问时间
    private int[] time;
    // 每一个连通块的所包含的节点集合
    List<Integer> node;
    int clock;
    public int magnificentSets(int n, int[][] edges) {
        this.g = new List[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        this.color = new int[n];
        this.time = new int[n];
        this.node = new ArrayList<>();
        // 建立图形
        for (int[] e : edges) {
            g[e[0] - 1].add(e[1] - 1);
            g[e[1] - 1].add(e[0] - 1);
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            // 如果已经染过色，那么证明这个节点之前已经被访问过
            // 属于某一个连通块,所以我们不再考虑
            if (color[i] != 0) {
                continue;
            }
            // 得到属于一个连通块的所有节点
            this.node = new ArrayList<>();
            // 如果这个连通块不是一个二分图，那么根本就无法完成分组
            // 对于这个连通块无法完成分组的话，那么对于整个图也不能完成分组
            // 所以我们直接返回-1
            if (!isBipartite(i, 1)) {
                return -1;
            }
            int temp = 0;
            // 对每一个连通块，我们暴力枚举其中每一个节点
            // 看看哪一个节点作为bfs的起点的时候能够得到最多的组
            for (int j : node) {
                temp = Math.max(temp, bfs(j));
            }
            ans += temp;
        }
        return ans;
    }

    private int bfs(int i) {
        // 当前时间自增
        // 并且标记这个i作为开始节点已经访问过
        time[i] = ++ clock;
        // clock++;
        Queue<Integer> q1 = new LinkedList<>();
        Queue<Integer> q2 = new LinkedList<>();
        int layers = 0;
        q1.offer(i);
        while (!q1.isEmpty()) {
            int cur = q1.poll();
            for (int neighbor : g[cur]) {
                // 如果neighbor节点的时间与当前时间不相等
                // 那么证明在这一轮的bfs中并没有访问到这个节点
                // 这样就避免了每次bfs中，都要开一个visit数组
                if (time[neighbor] != clock) {
                    time[neighbor] = clock;
                    q2.offer(neighbor);
                }
            }
            if (q1.isEmpty()) {
                layers++;
                q1 = q2;
                q2 = new LinkedList<>();
            }
        }
        return layers;
    }

    private boolean isBipartite(int i, int c) {
        color[i] = c;
        this.node.add(i);
        for (int neighbor : g[i]) {
            if (color[neighbor] == c || (color[neighbor] == 0 && !isBipartite(neighbor, -c))) {
                return false;
            }
        }
        return true;
    }
}
