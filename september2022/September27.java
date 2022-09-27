package com.fang.leetcode.september2022;

import java.util.*;

/**
 * @author letian
 * @version 1.0
 */
public class September27 {


    public static void main(String[] args) {
        September27 temp = new September27();
        System.out.println(temp.longestESR(new int[]{10, 2, 1, 4, 3, 9, 6, 9, 9}));
        int[][] array = temp.getArray();
        for (int[] arr : array) {
            System.out.println(Arrays.toString(arr));
        }
        TreeSet<Integer> integers = new TreeSet<>();
        integers.add(10);
        integers.add(1);
        for (int i : integers) {
            System.out.println(i);
        }
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    List<TreeNode> ans;
    Map<String, Integer> map;
    // 寻找重复的子树
    // 为了判断是不是完全相同的结构，我们进行序列化，将二叉树转换为字符串
    // 然后进行比对，看看是不是会产生相同的字符串
    // 如果有相同的字符串，我们就可以将这个添加到list中，但是只需要添加一次
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        ans = new ArrayList<>();
        map = new HashMap<>();
        dfs(root);
        return ans;
    }

    // 序列化，得到一个字符串
    public String dfs(TreeNode root) {
        if (root == null) {
            return "#_";
        }
        String s = root.val + "_" + dfs(root.left) + dfs(root.right);
        int count = map.getOrDefault(s, 0) + 1;
        if (count == 2) {
            ans.add(root);
        }
        map.put(s, count);
        return s;
    }


    // 生成长度为为100的十个数组
    // 其中每一个数组都是满足nums[i] <= 16 && nums[i] >= 0 的
    // 这是为了测试下面的最长销售好天数的方法
    int[][] getArray() {
        int[][] ans = new int[10][];
        for (int i = 0; i < 10; i++) {
            ans[i] = new int[100];
            for (int j = 0; j < 100; j++) {
                ans[i][j] = (int) (Math.random() * 17);
            }
        }
        return ans;
    }

    /**
     * 给你一份销售数量表 sales，上面记录着某一位销售员每天成功推销的产品数目。
     *
     * 我们认为当销售员同一天推销的产品数目大于 8 个的时候，那么这一天就是「成功销售的一天」。
     *
     * 所谓「销售出色区间」，意味在这段时间内，「成功销售的天数」是严格 大于「未成功销售的天数」。
     *
     * 请你返回「销售出色区间」的最大长度。
     * @param sales 销售区间,其中nums[i] <= 16 && nums[i] >= 0 的
     * @return 最长销售出色区间的长度
     */
    public int longestESR(int[] sales) {
        // 长度
        int n = sales.length;
        int[] sum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            // 一个数字大于8前缀和就加一
            // 否则前缀和就减去1
            sum[i] = sum[i - 1] + (sales[i - 1] > 8 ? 1 : -1);
        }
        // key是前缀和，value是具有这个前缀和下标
        Map<Integer, List<Integer>> map = new HashMap<>();
        // 只要后面的数字大于前面的，就证明这一段里面的销售好的天数多于差的天数
        // 使用treeSet使得好天数是从小到大排序的
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i <= n; i++) {
            if (!map.containsKey(sum[i])) {
                map.put(sum[i], new ArrayList<>());
            }
            map.get(sum[i]).add(i);
            set.add(sum[i]);
        }
        // 从小到大遍历前缀和数组
        // 到目前为止的最小的下标
        int min = Integer.MAX_VALUE;


        // 需要返回的答案
        int ans = 0;
        for (int i : set) {
            // 遍历到的此时的i，都大于前面的已经遍历的前缀和
            // 我们只需要找到一个最小的下标
            // 使得长度最大就可以了
            for (int j : map.get(i)) {
                ans = Math.max(ans, j - min);
            }
            for (int j : map.get(i)) {
                min = Math.min(min, j);
            }
        }
        return ans;

    }
}


