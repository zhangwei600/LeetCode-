package com.fang.leetcode.september2022;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author letian
 * @version 1.0
 * 今天周一，两道题分别是上周的周赛最后一题以及今天的困难题
 */
public class September26 {
    public static void main(String[] args) {

    }
    /**
     * 给你一棵 n个节点的树（连通无向无环的图），节点编号从0到n - 1且恰好有n - 1条边。
     * 给你一个长度为 n下标从 0开始的整数数组vals，分别表示每个节点的值。同时给你一个二维整数数组edges，其中edges[i] = [ai, bi]表示节点ai 和bi之间有一条无向边。
     * 一条 好路径需要满足以下条件：
     * 开始节点和结束节点的值 相同。
     * 开始节点和结束节点中间的所有节点值都 小于等于开始节点的值（也就是说开始节点的值应该是路径上所有节点的最大值）。
     * 请你返回不同好路径的数目。
     * 注意，一条路径和它反向的路径算作 同一路径。比方说，0 -> 1与1 -> 0视为同一条路径。单个节点也视为一条合法路径。
     */

    // 并查集，一开始代表元都是自己
    int[] fa;

    // 代表元的伴随数据项
    int[] size;
    // 封装的数据项
    // id是节点的编号
    // val是节点的值，建立这个类的目的是为了方便排序，也就是为了后面从节点值小的往大的合并
    static class Info {
        int id;
        int val;

        public Info(int id, int val) {
            this.id = id;
            this.val = val;
        }
    }
    public int numberOfGoodPaths(int[] vals, int[][] edges) {
        int n = vals.length;
        this.fa = new int[n];
        this.size = new int[n];
        Info[] data = new Info[n];
        List<Integer>[] g = new List[n];
        for (int i = 0; i < n; i++) {
            fa[i] = i;
            size[i] = 1;
            data[i] = new Info(i, vals[i]);
        }
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        // 建立图形
        for (int[] e : edges) {
            g[e[0]].add(e[1]);
            g[e[1]].add(e[0]);
        }
        int ans = n;
        Arrays.sort(data, (Comparator.comparingInt(o -> o.val)));
        for (int i = 0; i < n; i++) {
            int id = data[i].id;
            int fx = find(id);
            for (int neighbor : g[id]) {
                int fy = find(neighbor);
                // 已经合并过了或者这个邻居的值大于这个联通块的代表元的最大值
                if (fx == fy || vals[fy] > vals[fx]) {
                    continue;
                }
                // 如果最大值是相等的，根据惩罚远离，得到新增加的路径条数
                if (vals[fy] == vals[fx]) {
                    ans += size[fy] * size[fx];
                    // 如果两个联通块的最大值相等，我们就可以更新代表元对应的
                    // size伴随项，这个联通快的最大值数量应该加上与你合并的另一个联通块的代表元的数量
                    size[fx] += size[fy];
                }
                // 将小的联通块的代表元改成那个大的联通块的代表元
                // 做到集合的合并也就是，这个集合合并之后最后的结果就是代表元更新
                fa[fy] = fx;
            }
        }
        return ans;
    }

    // 并查集模版
    int find(int x) {
        if (fa[x] != x) {
            fa[x] = find(fa[x]);
        }
        return fa[x];
    }
}
