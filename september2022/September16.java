package com.fang.leetcode.september2022;

import java.util.*;

/**
 * @author letian
 * @version 1.0
 * 大楼轮廓天际线问题
 */
public class September16 {
    public static void main(String[] args) {

    }
    static class Node {
        int xIndex;
        boolean isAdd;
        int height;

        public Node(int xIndex, boolean isAdd, int height) {
            this.xIndex = xIndex;
            this.isAdd = isAdd;
            this.height = height;
        }
    }
    public List<List<Integer>> getSkyline(int[][] buildings) {
        int n = buildings.length;
        // 每一栋大楼都会产生一条高度的增加
        // 以及一条高度的减少
        Node[] data = new Node[n << 1];
        for (int i = 0; i < buildings.length; i++) {
            data[i << 1] = new Node(buildings[i][0], true, buildings[i][2]);
            data[i << 1 | 1] = new Node(buildings[i][1], false, buildings[i][2]);
        }
        // 这个有序表是为了存储每次添加或者减少高度时，所有的高度信息，key是高度
        // value是这个高度在遍历的时候所产生的次数
        TreeMap<Integer, Integer> maxTimes = new TreeMap<>();
        // 这个key是x轴的下标，value就是此时这个数轴上最高的高度
        TreeMap<Integer, Integer> indexMaxValue = new TreeMap<>();
        // 按照x数轴的坐标进行排序
        // 如果相同的的坐标，那么添加高度的放在前面
        Arrays.sort(data, (o1, o2) -> {
            if (o1.xIndex != o2.xIndex) {
                return o1.xIndex - o2.xIndex;
            }
            if (o1.isAdd != o2.isAdd) {
                return o1.isAdd ? -1 : 1;
            }
            return 0;
        });
        for (Node node : data) {
            if (node.isAdd) {
                maxTimes.put(node.height, maxTimes.getOrDefault(node.height, 0) + 1);
            } else {
                // 高度减少，如果这个高度只出现了一次，那么就删掉
                if (maxTimes.get(node.height) == 1) {
                    maxTimes.remove(node.height);
                } else {
                    // 不然的话，就减少次数
                    maxTimes.put(node.height, maxTimes.get(node.height) - 1);
                }
            }
            if (maxTimes.isEmpty()) {
                // 空的话，证明没有高度，也就是0
                indexMaxValue.put(node.xIndex, 0);
            } else {
                // 此时的最大值
                indexMaxValue.put(node.xIndex, maxTimes.lastKey());
            }
        }
        List<List<Integer>> ans = new ArrayList<>();
        // 初始化前面的高度为-1，保证第一个拐点顺利添加到res中
        int pre = -1;

        // 这个for循环就是输出轮廓线
        for (Map.Entry<Integer, Integer> entry : indexMaxValue.entrySet()) {

            int xIndex = entry.getKey();
            int height = entry.getValue();
            // 跳过高度相同的，也就是没有拐点的高度
            if (height == pre) {
                continue;
            }
            // 产生拐点的时候添加这个时候的index位置，以及此时的高度
            ans.add(Arrays.asList(xIndex, height));
            pre = height;
        }


        return ans;
    }
}
