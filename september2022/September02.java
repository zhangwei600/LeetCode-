package com.fang.leetcode.september2022;
import java.util.*;


/**
 * @author letian
 * @version 1.0
 * 二叉树的同值路径问题，路径不一定从根节点开始，也不一定在叶节点结束
 * 这里考虑的是求出每一个节点为头的所定义的最长路径
 */

@SuppressWarnings("all")
public class September02 {

    // 主函数是求解一个美团的追击问题
    // 小美追小团，最多过多久能让小美追上小团
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        // 小美所在的位置
        int x = sc.nextInt();

        // 小团所在的位置
        int y = sc.nextInt();
        List<Integer>[] g = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            g[i] = new ArrayList<>();
        }

        // 建图然后遍历
        for (int i = 1; i < n; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            g[from].add(to);
            g[to].add(from);
        }
        sc.close();

        // 两次bfs得到小美和小团初始位置到达其他节点的最短距离
        // 挑选其中小美比小团晚到达的，且花费的步数最多的那个点，也就是小团能在被小美追上之前
        // 能花费的最长时间
        Queue<Integer> queue = new LinkedList<>();
        int[] dis1 = new int[n + 1];
        int[] dis2 = new int[n + 1];
        queue.offer(x);
        boolean[] seen = new boolean[n + 1];
        seen[x] = true;
        int dis = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                dis1[cur] = dis;
                for (int i : g[cur]) {
                    if (!seen[i]) {
                        queue.offer(i);
                        seen[i] = true;
                    }
                }
            }
            dis++;
        }
        queue.offer(y);
        seen = new boolean[n + 1];
        seen[y] = true;
        dis = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                dis2[cur] = dis;
                for (int i : g[cur]) {
                    if (!seen[i]) {
                        queue.offer(i);
                        seen[i] = true;
                    }
                }
            }
            dis++;
        }
        int res = 0;
        // 小美后于小团到达的节点中距离最大的
        // 就是答案
        for (int i = 1; i <= n; i++) {
            if (dis1[i] > dis2[i]) {
                res = Math.max(res, dis1[i]);
            }
        }
        System.out.print(res);

    }

    // 路径的问题，不必从根节点开始，也不必在叶节点结束
    // 给定一个二叉树的 root ，返回 最长的路径的长度 ，这个路径中的每个节点具有相同值 。 这条路径可以经过也可以不经过根节点。
    // 全局变量用来比较保存最大路径长度
    int ans;

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

    }
    public int longestUnivaluePath(TreeNode root) {
        ans = 0;
        dfs(root);
        return ans;
    }

    public int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = dfs(root.left);
        int right = dfs(root.right);
        // 一个节点的左右路径一开始默认左右两边都是0
        int left1 = 0;
        int right1 = 0;


        if (root.left != null && root.left.val == root.val) {
            left1 = left + 1;
        }


        if (root.right != null && root.right.val == root.val) {
            right1 = right + 1;
        }

        ans = Math.max(ans, left1 + right1);
        // 返回给父节点的时候不能同时经过左树和右树
        return Math.max(left1, right1);
    }


}
