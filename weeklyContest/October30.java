package com.fang.leetcode.weeklyContest;

import java.util.*;

/**
 * @author letian
 * @version 1.0
 * 演示317场周赛的几个题目
 */
public class October30 {
    public static void main(String[] args) {

    }


    // 给你两个字符串数组 creators 和 ids ，和一个整数数组 views ，所有数组的长度都是 n 。平台上第 i 个视频者是creator[i] ，视频分配的 id 是 ids[i] ，且播放量为 views[i] 。
    // 视频创作者的 流行度 是该创作者的 所有 视频的播放量的 总和 。请找出流行度 最高 创作者以及该创作者播放量 最大 的视频的 id 。
    // 如果存在多个创作者流行度都最高，则需要找出所有符合条件的创作者。
    // 如果某个创作者存在多个播放量最高的视频，则只需要找出字典序最小的 id 。
    // 返回一个二维字符串数组 answer ，其中 answer[i] = [creatori, idi] 表示 creatori 的流行度 最高 且其最流行的视频 id 是 idi ，可以按任何顺序返回该结果。
    //来源：力扣（LeetCode）
    //链接：https://leetcode.cn/problems/most-popular-video-creator
    //著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处
    // 输入：creators = ["alice","bob","alice","chris"], ids = ["one","two","three","four"], views = [5,10,5,4]
    // 输出：[["alice","one"],["bob","two"]]
    // 解释：
    // alice 的流行度是 5 + 5 = 10 。
    // bob 的流行度是 10 。
    // chris 的流行度是 4 。
    // alice 和 bob 是流行度最高的创作者。
    // bob 播放量最高的视频 id 为 "two" 。
    // alice 播放量最高的视频 id 是 "one" 和 "three" 。由于 "one" 的字典序比 "three" 更小，所以结果中返回的 id 是 "one"
    static class Info {
        String id;
        int view;
        public Info(String id, int view) {
            this.id = id;
            this.view = view;
        }
    }
    public List<List<String>> mostPopularCreator(String[] creators, String[] ids, int[] views) {
        List<List<String>> ans = new ArrayList<>();
        Map<String, Integer> flag = new HashMap<>();
        Map<String, Info> mostSong = new HashMap<>();
        List<String> max = new ArrayList<>();
        int curMax = 0;
        for (int i = 0; i < creators.length; i++) {
            int cur = flag.getOrDefault(creators[i], 0) + views[i];
            if (cur > curMax) {
                curMax = cur;
                max = new ArrayList<>();
                max.add(creators[i]);
            } else if (curMax == cur) {
                max.add(creators[i]);
            }
            flag.put(creators[i], cur);
            if (!mostSong.containsKey(creators[i])) {
                mostSong.put(creators[i], new Info(ids[i], views[i]));
            } else {
                Info temp = mostSong.get(creators[i]);
                if (temp.view < views[i]) {
                    mostSong.put(creators[i], new Info(ids[i], views[i]));
                } else if (temp.view == views[i] && ids[i].compareTo(temp.id) < 0) {
                    mostSong.put(creators[i], new Info(ids[i], views[i]));
                }
            }
        }
        for (String s : max) {
            List<String> temp = new ArrayList<>();
            Info ele = mostSong.get(s);
            temp.add(s);
            temp.add(ele.id);
            ans.add(temp);
        }
        return ans;
    }


    // 美丽整数的最小增量
    // 给你两个正整数 n 和 target 。
    //如果某个整数每一位上的数字相加小于或等于 target ，则认为这个整数是一个 美丽整数 。
    // 找出并返回满足 n + x 是 美丽整数 的最小非负整数 x 。生成的输入保证总可以使 n 变成一个美丽整数。
    // 链接：https://leetcode.cn/problems/minimum-addition-to-make-integer-beautiful
    // 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
    // 输入：n = 16, target = 6
    // 输出：4
    // 解释：最初，n 是 16 ，且其每一位数字的和是 1 + 6 = 7 。在加 4 之后，n 变为 20 且每一位数字的和变成 2 + 0 = 2 。、
    // 可以证明无法加上一个小于 4 的非负整数使 n 变成一个美丽整数。


    // 返回num各个位置上的数字之和
    public int sub(long num) {
        int ans = 0;
        while (num != 0) {
            ans += num % 10;
            num /= 10;
        }
        return ans;
    }


    // 贪心，逐步进位，得到最小进位数字
    // 如16， 7。最小进位数字就是20，这个时候各个位置上的数字之和等于2小于等于target7
    // 即美丽数
    public long makeIntegerBeautiful(long n, int target) {
        int sum = sub(n);
        if (sum <= target) {
            return 0;
        }
        long temp = n;
        long i = 10L;
        while (sub(temp) > target) {
            // 去掉末尾的数字，也就是使得末尾的数字是0
            // 比如16，第一次就是把后面的6变成0
            temp /= i;
            // 然后将剩下的数字加上1，也就是进位，现在就是16 / 10 + 1 = 2
            temp++;
            // 再乘以10，得到最近的进位数是否满足需求，也就是通过sub函数验证一下各个位置上的
            // 数字之和是不是小于等于target的
            temp *= i;
            // 每进行一次循环，就多去掉一个末尾的数字，也就是末尾数字都是0
            // 所以i需要每次循环扩大10倍
            i *= 10;
        }
        // 最后得到最近的进位数字，也就是最小的进位数字，减去原来的数字n，就是增加最少的量
        // 使其变成一个美丽数字，即每一位上的数字之和小于等于target
        return temp - n;
    }

    // 树上的节点
    static class TreeNode {
        TreeNode left;
        TreeNode right;
        int val;

        public TreeNode(TreeNode left, TreeNode right, int val) {
            this.left = left;
            this.right = right;
            this.val = val;
        }

        public TreeNode(int val) {
            this.val = val;
        }
    }

    Map<TreeNode, Integer> heightMap;
    int[] res;
    public int[] treeQueries(TreeNode root, int[] queries) {
        this.heightMap = new HashMap<>();
        getHeight(root);
        return null;
    }

    int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int h = Math.max(getHeight(root.left), getHeight(root.right)) + 1;
        heightMap.put(root, h);
        return h;
    }


}
