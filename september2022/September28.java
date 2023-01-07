package com.fang.leetcode.september2022;

/**
 * @author letian
 * @version 1.0
 */
public class September28 {
    public static void main(String[] args) {

    }
    public int getKthMagicNumber(int k) {
        int[] res = new int[k];
        // 数组里面第一个数字是1
        res[0] = 1;
        // 一开始三个因数的指针都指向数组第一个数字1
        int p3 = 0;
        int p5 = 0;
        int p7 = 0;
        // 除了3和5和7以外，不会有其他的素因子
        // 所以一定可以分解为这几个数字的积，且只能分解为这几个数的积
        // 每个因数至少需要出现一次
        // 所以从小到大依次是
        // 我来解读一下吧，力扣又是一道不说人话的题，就是说，一个数，
        // 他只能由3，5，7的乘法运算得到(1是例外)，让你按从小到大顺序求出第k个数是多少。
        // 所以思路呼之欲出，先是1*3 1*5 1*7 再是 3*3 3*5 3*7 然后5*3 5*5 5*7
        for (int i = 1; i < k; i++) {
            // 每一个数取其中小的那个
            // 所以数组第二个数是3，然后是5然后是7
            // 一个管着3的倍数
            // 另两个分别管着5的倍数和7的倍数
            // res[p3]是最近的三的倍数，其他的同理
            int temp = Math.min(res[p3] * 3, Math.min(res[p5] * 5, res[p7] * 7));
            // 如果这个数是其中某一个素因数的倍数
            // 需要将对应的指针元素加1
            if (temp % 3 == 0) {
                p3++;
            }
            if (temp % 5 == 0) {
                p5++;
            }
            if (temp % 7 == 0) {
                p7++;
            }
            res[i] = temp;
        }
        return res[k - 1];

    }
}
