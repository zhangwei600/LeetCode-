package com.fang.leetcode.september2022;

import java.io.*;
import java.util.*;

/**
 * @author letian
 * @version 1.0
 * 两个问题：找出一颗二叉树中结构完全相同的子树，类型相同的子树只返回一次。结果存在一个List中返回
 * 第二个就是分组问题，得到分组后的最大值，推导的公式表明让实力高的人去人数少的队伍能够提高平均得分
 * 两题的链接：<a href="https://leetcode.cn/problems/LMkFuT/">...</a>
 * <a href="https://leetcode.cn/problems/find-duplicate-subtrees/">...</a>
 */

public class September05 {

    static class TreeNode {
        TreeNode left;
        TreeNode right;
        int val;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        // 平均值尽量地大
        String[] temp = br.readLine().split(" ");
        // A组需要x人 B组需要y人
        int x = Integer.parseInt(temp[0]);
        int y = Integer.parseInt(temp[1]);
        // 实力值数组
        temp = br.readLine().split(" ");
        // 索引和实力值，两个坐标的含义
        int[][] data = new int[temp.length][];
        for (int i = 0; i < temp.length; i++) {
            data[i] = new int[]{i, Integer.parseInt(temp[i])};
        }
        char[] arr = new char[x + y];
        // 实力值从大到小，序号从小到大
        Arrays.sort(data, (o1, o2) -> (o2[1] != o1[1] ? o2[1] - o1[1] : o1[0] - o2[0]));
        if (x == y) {
            for (int i = 0; i < x; i++) {
                arr[i] = 'A';
            }
            for (int i = x; i < x + y; i++) {
                arr[i] = 'B';
            }

            // return;
        }
        // 实力高的去人数少的的队伍
        if (x < y) {
            for (int i = 0; i < x; i++) {
                arr[data[i][0]] = 'A';
            }
            for (int i = x; i < x + y; i++) {
                arr[data[i][0]] = 'B';
            }
        }
        if (x > y) {
            for (int i = 0; i < y; i++) {
                arr[data[i][0]] = 'B';
            }
            for (int i = y; i < x + y; i++) {
                arr[data[i][0]] = 'A';
            }
        }

        bw.write(new String(arr));
        br.close();
        bw.close();
    }

    Map<String, Integer> m = new HashMap<>();
    List<TreeNode> ans = new ArrayList<>();

    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        dfs(root);
        return ans;
    }


    // 深度优先遍历的时候求出每一个节点的序列化字符串，然后填入哈希表中，如果数量达到2就添加进入list中，并且返回
    String dfs(TreeNode root) {
        if (root == null) {
            return "#";
        }
        String s = root.val + "," + dfs(root.left) + "," + dfs(root.right);
        int n = m.getOrDefault(s, 0);
        if (n == 1) {
            ans.add(root);
        }
        m.put(s, n + 1);
        return s;
    }
}
