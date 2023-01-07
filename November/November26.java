package com.fang.leetcode.November;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author letian
 * @version 1.0
 * 882. 细分图中的可到达节点
 * <a href="https://leetcode.cn/problems/reachable-nodes-in-subdivided-graph/description/">...</a>
 */
@SuppressWarnings("all")
public class November26 {
    int n;
    public int reachableNodes(int[][] edges, int maxMoves, int n) {
        this.n = n;
        List<int[]>[] g = new List[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        // 建立邻接矩阵表
        // 其中存放的数组第一个元素代表接触的节点
        // 第二个距离代表两点之间的距离
        for (int[] e : edges) {
            g[e[0]].add(new int[]{e[1], e[2] + 1});
            g[e[1]].add(new int[]{e[0], e[2] + 1});
        }
        int[] dis = process(g);
        int ans = 0;
        // 能够在maxMoves步内能到达的，这个能到达的节点先算上
        for (int i = 0; i < n; i++) {
            if (dis[i] <= maxMoves) {
                // 如果能够从i到j花费的步数少于等于maxMoves的话
                // 就可以将ans加1
                ans++;
            }
        }
        for (int[] e : edges) {
            int p1 = Math.max(0, maxMoves - dis[e[0]]);
            int p2 = Math.max(0, maxMoves - dis[e[1]]);
            // 能到达这两个点，我们如何得到这中间有多少个点可以纳入
            ans += Math.min(p1 + p2, e[2]);

        }
        return ans;
    }


    // dijistra算法求单源最短路
    int[] process(List<int[]>[] g) {
        int[] dis = new int[n];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[0] = 0;
        // 按照距离的优先队列
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> (o1[1] - o2[1]));
        pq.offer(new int[]{0, 0});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            for (int[] temp : g[cur[0]]) {
                // 如果距离小就更新
                if (cur[1] + temp[1] < dis[temp[0]]) {
                    dis[temp[0]] = cur[1] + temp[1];
                    pq.offer(new int[]{temp[0], dis[temp[0]]});
                }
            }
        }
        return dis;
    }
}
