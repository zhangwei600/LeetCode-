package com.fang.leetcode.august2022;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author letian
 * @version 1.0
 * 8月26日这一天只弄清楚一个二分图的染色问题
 * 题目的链接https://leetcode.cn/problems/possible-bipartition/
 */
public class August26 {
    public boolean possibleBipartition(int n, int[][] dislikes) {
        if (dislikes == null || dislikes.length == 0) {
            return true;
        }

        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            graph.put(i, new HashSet<>());
        }
        // 每一个节点的颜色数组，1代表白色，-1代表黑色，0表示等待染色
        int[] colors = new int[n + 1];
        // 建立图
        for (int[] dislike : dislikes) {
            graph.get(dislike[0]).add(dislike[1]);
            graph.get(dislike[1]).add(dislike[0]);
        }
        for (int i = 1; i <= n; i++) {
            // 没遇到的直接染色成为1，在深度优先遍历时看是否有冲突
            if (colors[i] == 0 && !dfs(colors, graph, i, 1)) {
                return false;
            }
        }
        return true;
    }

    public boolean dfs(int[] colors, Map<Integer, Set<Integer>> graph, int index, int color) {
        for (int i : graph.get(index)) {
            if (colors[index] == colors[i]) {
                return false;
            }
            // 染色成为相反的颜色
            if (colors[i] == 0 && !dfs(colors, graph, i, -color)) {
                return false;
            }
        }
        return true;
    }
}
