package com.fang.leetcode.weeklyContest;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author letian
 * @version 1.0
 * <a href="https://leetcode.cn/problems/delivering-boxes-from-storage-to-ports/description/">...</a>
 */
public class December05 {
    public static void main(String[] args) {
        System.out.println((long) Integer.MAX_VALUE * 2);

    }

    // 你有一辆货运卡车，你需要用这一辆车把一些箱子从仓库运送到码头。这辆卡车每次运输有 箱子数目的限制 和 总重量的限制 。

    // 给你一个箱子数组 boxes 和三个整数 portsCount, maxBoxes 和 maxWeight ，其中 boxes[i] = [portsi, weighti] 。

    // portsi 表示第 i 个箱子需要送达的码头， weightsi 是第 i 个箱子的重量。
    // portsCount 是码头的数目。
    // maxBoxes 和 maxWeight 分别是卡车每趟运输箱子数目和重量的限制。
    // 箱子需要按照 数组顺序 运输，同时每次运输需要遵循以下步骤：

    // 卡车从 boxes 队列中按顺序取出若干个箱子，但不能违反 maxBoxes 和 maxWeight 限制。
    // 对于在卡车上的箱子，我们需要 按顺序 处理它们，卡车会通过 一趟行程 将最前面的箱子送到目的地码头并卸货。
    // 如果卡车已经在对应的码头，那么不需要额外行程，箱子也会立马被卸货。
    // 卡车上所有箱子都被卸货后，卡车需要 一趟行程回到仓库，从箱子队列里再取出一些箱子。
    // 卡车在将所有箱子运输并卸货后，最后必须回到仓库。
    // 请你返回将所有箱子送到相应码头的 最少行程 次数。
    public int boxDelivering(int[][] boxes, int portsCount, int maxBoxes, int maxWeight) {
        int n = boxes.length;
        // 前缀和数组
        long[] sum = new long[n + 1];
        // 前面变换了多少次港口的前缀和
        int[] negs = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + boxes[i - 1][1];
            // 第一个是1,其余的是0
            // 这个表示从i位置到0位置总共变换了多少次码头
            // 0到0位置是0次变换
            // 0到1位置是1次变换，这都是规定好的
            // 后面就考察当时的位置了
            negs[i] = negs[i - 1] + (i == 1 ? 1 : boxes[i - 2][0] == boxes[i - 1][0] ? 0 : 1);
        }
        // 输入：boxes = [[1,1],[2,1],[1,1]], portsCount = 2, maxBoxes = 3, maxWeight = 3
        // 输出：4
        // 解释：最优策略如下：
        // - 卡车将所有箱子装上车，到达码头 1 ，然后去码头 2 ，然后再回到码头 1 ，最后回到仓库，总共需要 4 趟行程。
        // 所以总行程数为 4 。
        // 注意到第一个和第三个箱子不能同时被卸货，因为箱子需要按顺序处理（也就是第二个箱子需要先被送到码头 2 ，然后才能处理第三个箱子）。
        Deque<Integer> dp = new ArrayDeque<>();
        dp.offerLast(0);
        // 从0到i位置的货物最少需要多少路程
        // 就是ans数组下标为i的含义
        int[] ans = new int[n + 1];
        // 这个地方就开始考虑单调队列怎么进行下去了
        for (int i = 1; i <= n; i++) {
            // 首先去掉队头的不符合卡车限额的下标
            while (!dp.isEmpty() && (i - dp.peekFirst() > maxBoxes || sum[i] - sum[dp.peekFirst()] > maxWeight)) {
                dp.pollFirst();
            }
            //
            if (!dp.isEmpty()) {
                // 这个就是转移方程
                // ans[j] = min(neg[j] - neg[i + 1] + 2 + ans[i])(for i < j);
                // 提取和i无关的即ans[j] = neg[j] + 2 + min(ans[i] - neg[i + 1])
                // 我们只要维护ans[i] - neg[i + 1]的O(1)转移就可以了
                ans[i] = negs[i] + 2 + ans[dp.peekFirst()] - negs[dp.peekFirst() + 1];
            }
            if (i != n) {
                // 其次是我们只要满足卡车限额的ans[i] - neg[i + 1]的值就可以了
                // 所以我们从队尾开始往前遍历，弹出无用的数据，我比你小更能满足卡车的要求。而且数值比你还小
                // 那么前面的你就毫无可能成为最优的那一段了
                while (!dp.isEmpty() && ans[i] - negs[i + 1] < ans[dp.peekLast()] - negs[dp.peekLast() + 1]) {
                    dp.pollLast();
                }
            }
            dp.offerLast(i);
        }
        return ans[n];

    }

}
