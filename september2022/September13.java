package com.fang.leetcode.september2022;

import java.util.Arrays;

/**
 * @author letian
 * @version 1.0
 * 演示线段树
 */
public class September13 {
    public static void main(String[] args) {

    }

}

class SegmentTree {
    public static void main(String[] args) {
        int[] temp = {1, 3, 4, 2, 5, 36, 24};
        SegmentTree tree = new SegmentTree(temp);
        System.out.println(Arrays.toString(tree.sum));
        tree.add(1, 3, 2, 1, temp.length, 1);
        System.out.println(tree.query(1, 4, 1, temp.length, 1));
    }

    int nMax;
    // 复制原来的数组
    int[] arr;
    // 和数组
    int[] sum;
    // 懒标记数组
    int[] lazy;
    int[] change;
    // 是否需要更新的布尔值，如果为true就需要更新
    boolean[] update;



    public SegmentTree(int[] original) {
        // 加一是为了0位置放弃不使用，方便后面找左右孩子的时候可以
        // 更方便的使用位运算来加速，左右孩子分别是2*i以及2*i+1
        nMax = original.length + 1;
        // 数组开4n长度
        nMax <<= 2;
        arr = new int[nMax];
        sum = new int[nMax];
        lazy = new int[nMax];
        change = new int[nMax];
        // 是否有更新标记
        update = new boolean[nMax];
        System.arraycopy(original, 0, arr, 1, original.length);
        build(arr, sum, 1, original.length, 1);
    }

    // 汇总更新的函数
    public void pushUp(int rt) {
        sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
    }

    // 这个用来建立sum数组，根据原来的数组来建立这个东西
    // rt是l-r范围上的的和应该在sum数组中的哪一个位置
    public void build(int[] arr, int[] sum, int l, int r, int rt) {
        if (l == r) {
            sum[rt] = arr[l];
            return;
        }
        int mid = (l + r) >> 1;
        build(arr, sum, l, mid, rt << 1);
        build(arr, sum, mid + 1, r, rt << 1 | 1);
        // 两边的数据都弄好了之后，向上汇总信息
        pushUp(rt);
    }

    // 下发任务，rt代表这个区间的和在sum数组的位置
    // lNum代表左孩子的元素个数，rNum代表右边孩子的元素个数
    public void pushDown(int rt, int lNum, int rNum) {
        // 先检查是否有懒更新操作
        if (update[rt]) {
            // 向下传递更新的标记
            update[rt << 1] = true;
            update[rt << 1 | 1] = true;
            // 左右孩子的需要更新的值改成这个位置上的值
            change[rt << 1] = change[rt];
            change[rt << 1 | 1] = change[rt];
            lazy[rt << 1] = 0;
            lazy[rt << 1 | 1] = 0;
            sum[rt << 1] = lNum * change[rt];
            sum[rt << 1 | 1] = rNum * change[rt];
            // 完成下发任务后，需要将这个更新标识清除
            update[rt] = false;
        }
        // 然后检查是否有懒更新任务
        // 下发懒标记任务
        if (lazy[rt] != 0) {
            lazy[rt << 1] += lazy[rt];
            lazy[rt << 1 | 1] += lazy[rt];
            sum[rt << 1] += lNum * lazy[rt];
            sum[rt << 1 | 1] += rNum * lazy[rt];
            // 懒标记清空
            lazy[rt] = 0;
        }
    }

    // l到r范围上加上一个数字c，都是封闭的区间
    // L-R范围是表达的范围，主要看任务范围是否完全包括这个表达的范围
    public void add(int l, int r, int c, int L, int R, int rt) {
        // 完全包住的话
        // lazy标记更新，然后懒住
        if (l <= L && r >= R) {
            lazy[rt] += c;
            sum[rt] += (R - L + 1) * c;
            return;
        }
        // 如果没有完全包住这个范围的话，那么就将当前的任务下发
        int mid = (L + R) >> 1;
        pushDown(rt, mid - L + 1, R - mid);
        // 左边的区间是不是需要这个任务
        // 也就是说我们要更新的范围和这个区间有没有交集
        if (l <= mid) {
            add(l, r, c, L, mid, rt << 1);
        }
        // 右边的区间需不需要这个任务

        if (r > mid) {
            add(l, r, c, mid + 1, R, rt << 1 | 1);
        }
        // 处理好这些问题将sum数组更新
        pushUp(rt);
    }

    public void update(int l, int r, int c, int L, int R, int rt) {
        // 区间能够完全包住
        if (l <= L && r >= R) {
            update[rt] = true;
            // 要更新为哪一个值
            change[rt] = c;
            sum[rt] = (R - L + 1) * c;
            lazy[rt] = 0;
            return;
        }
        int mid = (L + R) >> 1;
        pushDown(rt, mid - L + 1, R - mid);
        if (l <= mid) {
            update(l, r, c, L, mid, rt << 1);
        }
        if (r > mid) {
            update(l, r, c, mid + 1, R, rt << 1 | 1);
        }
        pushUp(rt);
    }

    // 查询范围l-r，实际范围L-R
    public long query(int l, int r, int L, int R, int rt) {
        if (l <= L && r >= R) {
            return sum[rt];
        }
        int mid = (L + R) >> 1;
        pushDown(rt, mid - L + 1, R - mid);
        long ans = 0L;
        // 左边是否有重叠，有的话就进行查询
        if (l <= mid) {
            ans += query(l, r, L, mid, rt << 1);
        }
        if (r > mid) {
            ans += query(l, r, mid, R, rt << 1 | 1);
        }
        return ans;
    }


}
