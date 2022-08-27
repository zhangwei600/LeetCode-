package com.fang.leetcode.august2022;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author letian
 * @version 1.0
 * 求得搜索二叉树的种类，范围为[1,n]
 * 题目的链接https://leetcode.cn/problems/unique-binary-search-trees-ii/
 */
public class August27 {
    public static void main(String[] args) {
        System.out.println(generateTrees(1));
        August27 august27 = new August27();
        System.out.println(august27.widthOfBinaryTree(new August27.TreeNode(10)));
    }

    // 每一层的层数以及对应的最大宽度
    Map<Integer, Integer> map = new HashMap<>();
    int ans;


    /**
     *
     * @param root 给你一个二叉树
     * @return 输出每一层左右两个端点之间的二叉树节点最大个数
     */
    public int widthOfBinaryTree(TreeNode root) {
        // u是节点编号
        dfs(root, 1, 0);
        return ans;
    }

    public void dfs(TreeNode root, int u, int depth) {
        if (root == null) return;
        // 深度优先遍历的过程中最先碰到的是最右边的节点
        if (!map.containsKey(depth)) map.put(depth, u);
        ans = Math.max(ans, u - map.get(depth) + 1);
        // 堆的摆放应该是2*i+1以及2*i-1
        dfs(root.left, u << 1, depth + 1);
        dfs(root.right, u << 1 | 1, depth + 1);
    }

    static class TreeNode {
        int val;

        TreeNode left;

        TreeNode right;


        TreeNode(int val) {
            this.val = val;
        }


    }

    /**
     * @param n 从1到n有多少二叉搜索树
     * @return 返回不同的二叉搜索树的列表
     */
    public static List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return null;
        }
        // 生成树所需要的范围
        return process(1, n);

    }

    public static List<TreeNode> process(int left, int right) {
        // 这个出口是这样设置的
        if (left > right) {
            List<TreeNode> temp = new ArrayList<>();
            temp.add(null);
            return temp;
        }
        List<TreeNode> ans = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            // 左边的给我生成相应的集合
            List<TreeNode> l = process(left, i - 1);
            // 右边的给我生成相应的集合
            List<TreeNode> r = process(i + 1, right);
            for (TreeNode node1 : l) {
                for (TreeNode node2 : r) {
                    TreeNode head = new TreeNode(i);
                    head.left = node1;
                    head.right = node2;
                    ans.add(head);
                }
            }
        }
        return ans;
    }

}
