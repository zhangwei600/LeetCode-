package com.fang.leetcode.twenty_twenty_three.january;

import java.util.PriorityQueue;

/**
 * @author letian
 * @version 1.0
 * 2023-1-6的一道经典困难题
 * <a href="https://leetcode.cn/problems/shu-ju-liu-zhong-de-zhong-wei-shu-lcof/description/">...</a>
 */
public class Sixth {
    public static void main(String[] args) {

    }

    // 存放左半部分的优先队列
    // 需要是大根堆，随时取得左半部分最大的数字
    PriorityQueue<Integer> l;

    // 存放右半部分数据的优先队列
    // 需要是小堆，需要随时取得右半部分最小的数字
    PriorityQueue<Integer> r;

    /**
     * initialize your data structure here.
     */
    public Sixth() {
        // l为大堆，r为小堆
        l = new PriorityQueue<>((o1, o2) -> (o2 - o1));
        r = new PriorityQueue<>();
    }

    public void addNum(int num) {
        // l存放左半部分的数字
        // r存放右半部分的数字
        // 保证奇数的时候l比r多一个
        if (l.size() == r.size()) {
            if (l.isEmpty()) {
                // 左右都是空 直接加到l优先队列中去
                l.offer(num);
            } else if (num <= r.peek()) {
                // 不应该放到右边的队列中，可以直接放到左边的优先队列中
                l.offer(num);
            } else {
                l.offer(r.poll());
                r.offer(num);
            }
        } else {
            // 这个时候两个堆的大小不一样，一定是l堆比r堆多一个
            // 这是上面那个if保证的
            if (!l.isEmpty() && num >= l.peek()) {
                r.offer(num);
            } else {
                r.offer(l.poll());
                l.offer(num);
            }
        }
    }

    @SuppressWarnings("all")
    public double findMedian() {
        if (l.size() == r.size()) {

            return (double) (l.peek() + r.peek()) / 2;
        } else {
            return (double) l.peek();
        }

    }


}
