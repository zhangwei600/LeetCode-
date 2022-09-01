package com.fang.leetcode.september2022;

import java.util.Arrays;

/**
 * @author letian
 * @version 1.0
 * 一颗树多加了一条边，请找出这条边
 * 题目链接https://leetcode.cn/problems/redundant-connection-ii/
 * 两个想法，一个是有入度为2的边，那么要删除的边一定在造成入度为2的两条边的其中一条
 * 另一种就是图中有环，这个环是指向根节点的
 */
public class september01 {
    public static void main(String[] args) {

    }

    int[] fa;
    int n;


    public int[] findRedundantDirectedConnection(int[][] edges) {
        //
        n = edges.length;
        fa = new int[n + 1];
        int[] res = null;
        int[] inDegree = new int[n + 1];
        for (int[] edge : edges) {

            if (++inDegree[edge[1]] == 2) {
                res = edge;
            }
        }
        // 入度为2的点
        if (res != null) {
            // 入度为2的两条边，必须去掉其中的一条
            // 能形成一棵树的话就直接返回这条边
            if (check(edges, res)) {
                return res;
            } else {
                // 否则的就返回另一条边
                for (int[] edge : edges) {
                    // 也就是使得这个点的入度从0变为1的那条边
                    // 所以我们再次通过从前往后的遍历得到这个边
                    if (edge[1] == res[1]) {
                        return edge;
                    }
                }
            }
        }

        // 初始化并查集
        for (int i = 0; i <= n; i++) {
            fa[i] = i;
        }

        // 如果没有入度为2的点，那么一定有一个环在图中
        for (int[] edge : edges) {
            if (find(edge[0]) == find(edge[1])) {
                // 也就是返回首次造成成环的那条边
                return edge;
            } else {
                // 合并元素
                union(edge[0], edge[1]);
            }
        }
        return null;
    }


    // 检查删除这条边以后能不能形成一棵树
    boolean check(int[][] edges, int[] res) {
        // 初始化并查集
        for (int i = 0; i <= n; i++) {
            fa[i] = i;
        }
        for (int[] edge : edges) {
            if (Arrays.equals(edge, res)) {
                continue;
            }
            if (find(edge[0]) == find(edge[1])) {
                return false;
            }
            union(edge[0], edge[1]);
        }
        return true;
    }

    int find(int i) {
        if (fa[i] != i) {
            fa[i] = find(fa[i]);
        }
        return fa[i];
    }


    // 并查集合并元素
    void union(int i, int j) {
        int faI = find(i);
        int faJ = find(j);
        fa[faI] = faJ;
    }
}
