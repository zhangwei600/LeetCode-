package com.fang.leetcode;


import java.util.*;

/**
 * @author letian
 * @version 1.0
 */
@SuppressWarnings("all")
public class Test {
    static class ListNode {
        ListNode next;
        int val;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.next = next;
            this.val = val;
        }
    }

    public static void main(String[] args) {
        int[] arr = {7, 1, 5, 3, 6, 4};
        System.out.println(maxProfit2(arr));

    }

    public static int countOdds(int low, int high) {
        // 决定high到low之间的奇数
        // 1，high奇数，low奇数
        if (high == low && high % 2 == 0) {
            return 0;
        } else if (high == low) {
            return 1;
        }
        int count = 0;
        int sub = high - low - 1;// 获取high与low之间的数字个数


        if (sub % 2 == 0) { //如果之间的数字是偶数个，证明high和low的奇偶性相反
            count = sub / 2 + 1;

        } else {
            if (low % 2 == 0) {//如果low是偶数，且low与high之间的数字个数为奇数，就获得如下的个数
                count = (sub / 2) + 1;//加1是因为中间的数字开头是奇数，加2是因为
            } else {
                count = (sub / 2) + 2;
            }
        }
        return count;

    }

    public static double average(int[] salary) {
        int max = salary[0];
        int min = salary[0];
        int len = salary.length;
        double sum = 0;// 注意精度，不要使用int来定义
        for (int i = 1; i < len; i++) {
            if (salary[i] > max) {
                max = salary[i];
            }
        }
        for (int i = 1; i < len; i++) {
            if (min < salary[i]) {
                min = salary[i];
            }
        }
        for (int i = 0; i < len; i++) {
            sum += salary[i];
        }
        return (sum - max - min) / (len - 2);

    }

    public static String toUpper(String s) {
        return s.toLowerCase();
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        HashMap<Integer, Integer> node1Hs = new HashMap<>();
        HashMap<Integer, Integer> node2Hs = new HashMap<>();
        ArrayList<ListNode> listNodes = new ArrayList<>();
        int n1 = 0;
        int n2 = 0;
        boolean flag = false;
        int sum;
        int val;
        int i;

        // 头节点是个位
        while (l1 != null) {
            node1Hs.put(n1++, l1.val);
            l1 = l1.next;
        }
        while (l1 != null) {
            node2Hs.put(n2++, l2.val);
            l2 = l2.next;
        }

        // 把索引下面的东西提取出来然后开启创建链，两个单位数字相加最多进一位
        // 索引
        for (i = 0; i <= Math.min(n1, n2); i++) {
            sum = 0;
            if (flag) {
                sum++;
            }
            sum = node1Hs.get(i) + node2Hs.get(i);
            val = sum % 10;
            if (i == 0) {
                listNodes.add(new ListNode(val));
            }
            listNodes.add(new ListNode(val, listNodes.get(i - 1)));
            // 小于10进位标志置为false,否则置为true
            // 怎么往前推
            if (sum >= 10) {
                flag = true;
            } else {
                flag = false;
            }
        }
        if (node1Hs.size() > node2Hs.size()) {
            if (flag) {
                // 如果之前的flag没有重置的话
                listNodes.add(new ListNode(node1Hs.get(++i) + 1, listNodes.get(i - 1)));
                flag = false;
            } else {
                listNodes.add(new ListNode(node1Hs.get(++i), listNodes.get(i - 1)));
            }
        } else {
            if (flag) {
                // 如果之前的flag没有重置的话
                listNodes.add(new ListNode(node2Hs.get(++i) + 1, listNodes.get(i - 1)));
                flag = false;
            } else {
                listNodes.add(new ListNode(node2Hs.get(++i), listNodes.get(i - 1)));
            }
        }

        return listNodes.get(listNodes.size() - 1);

    }

    public static ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ArrayList<ListNode> nodes = new ArrayList<>();
        boolean flag = false;
        int index = 0;
        int sum = 0;
        while (!(l1 == null || l2 == null)) {
            sum = 0;
            if (flag) {
                // 值相加后进行判断是不是要进位
                sum = l1.val + l2.val + 1;
            } else {
                sum = l1.val + l2.val;
            }
            flag = false;
            if (sum >= 10) {
                flag = true;
            }
            // 这里才需要取模
            sum %= 10;
            if (index == 0) {
                nodes.add(new ListNode(sum));
            } else {
                nodes.add(new ListNode(sum, nodes.get(index - 1)));
            }
            l1 = l1.next;
            l2 = l2.next;
            index++;
        }
        while (l1 != null) {
            sum = 0;
            if (flag) {
                sum = l1.val + 1;
            } else {
                sum = l1.val;
            }
            flag = false; //进完位之后重新置为false
            if (sum >= 10) {
                flag = true;
            }
            sum %= 10;
            nodes.add(new ListNode(sum, nodes.get(index - 1)));
            index++;
            l1 = l1.next;
        }
        while (l2 != null) {
            sum = 0;
            if (flag) {
                sum = l2.val + 1;
            } else {
                sum = l2.val;
            }
            flag = false; //进完位之后重新置为false
            if (sum >= 10) {
                flag = true;
            }
            sum %= 10;
            nodes.add(new ListNode(sum, nodes.get(index - 1)));
            index++;
            l2 = l2.next;
        }
        if (flag) {
            nodes.add(new ListNode(1, nodes.get(index - 1)));
        }
        // 将最后一个节点的next置位null
        nodes.get(nodes.size() - 1).next = null;
        if (nodes.size() != 1) {
            for (int i = 0; i < nodes.size() - 1; i++) {
                nodes.get(i).next = nodes.get(i + 1);
            }
        }

        return nodes.get(0);

    }

    public static ListNode addTwoNumbers3(ListNode l1, ListNode l2) {
        boolean flag = false;
        int sum = 0;
        ListNode last = null; // 存放最后一个节点使用
        ListNode addNew = null; // 如果需要添加新的节点，就用这个保存
        // 存放两个头的位置
        ListNode head1 = l1;
        ListNode head2 = l2;
        int position = -1;
        while (!(l1 == null || l2 == null)) {
            sum = 0;
            if (flag) {
                // 值相加后进行判断是不是要进位
                sum = l1.val + l2.val + 1;
            } else {
                sum = l1.val + l2.val;
            }
            flag = false;
            if (sum >= 10) {
                flag = true;
            }
            // 这里才需要取模
            sum %= 10;
            // 将原来的链表重新利用了
            l1.val = sum;
            l1.val = sum;
            // 如果两个链表一样长就要思考后面的两个循环可能根本就不会进入，最后的结果就是last为null，抛出空指针异常
            if (l1.next == null || l2.next == null) {
                last = l1.next;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        while (l1 != null) {
            sum = 0;
            if (flag) {
                sum = l1.val + 1;
            } else {
                sum = l1.val;
            }
            flag = false; //进完位之后重新置为false
            if (sum >= 10) {
                flag = true;
            }
            sum %= 10;
            l1.val = sum;
            if (l1.next == null) {
                last = l1;
            }
            l1 = l1.next;
            // 标志位看进入了哪个循环
            position = 1;
        }
        while (l2 != null) {
            sum = 0;
            if (flag) {
                sum = l2.val + 1;
            } else {
                sum = l2.val;
            }
            flag = false; //进完位之后重新置为false
            if (sum >= 10) {
                flag = true;
            }
            sum %= 10;
            l2.val = sum;
            // 在快到头的时候设一个变量保存一下
            if (l2.next == null) {
                last = l2;
            }
            l2 = l2.next;
            position = 2;
        }
        if (flag) {
            addNew = new ListNode(1);
            last.next = addNew;
        }
        // 如果进入循环1就返回头节点1
        if (position == 1 || position == -1) {
            return head1;
        }
        // 否则就返回头节点2
        return head2;
    }

    public int[] intersect(int[] nums1, int[] nums2) {
        int[] re = new int[0];
        // int len1 = nums1.length;
        // int len2 = nums2.length;
        // 标识两个数组下标的指针
        int position1 = 0;
        int position2 = 0;
        // int less = Math.min(nums1.length, nums2.length);
        ArrayList<Integer> res = new ArrayList<>();
        // 排序后的数组怎么快速找到这些相同的元素
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        // 找出重复的元素
        // 让短的数组去找，进行匹配
        while (position1 < nums1.length && position2 < nums2.length) {
            if (nums1[position1] < nums2[position2]) {
                position1++;
            } else if (nums1[position1] == nums2[position2]) {
                res.add(nums1[position1]);
                position1++;
                position2++;
            } else {
                position1++;
            }
        }
        if (nums1.length <= nums2.length) {

//            for (; position1 < nums1.length; position1++) {
//                for (; position2 < nums2.length; position2++) {
//                    if (nums1[position1] == nums2[position2]) {
//                        res.add(nums1[position1]);
//                        position2++;
//                        break;
//                    }
//                }
//            }
        } else {
            for (; position2 < nums2.length; position2++) {
                for (; position1 < nums1.length; position1++) {
                    if (nums2[position2] == nums1[position1]) {
                        res.add(nums2[position2]);
                        position1++; // 只需要这个指针进行移动
                        break;
                    }
                }
            }
        }
        if (res.size() == 0) {
            return re;
        }
        int[] target = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            target[i] = res.get(i);
        }
        return target;


    }

    public static int maxProfit(int[] prices) {
        // 决定什么时候买，然后决定什么时候卖
        int sell = prices.length - 1;
        int buy = 0;
        // 这个就相当于你选定一个日子买了股票之后，后面的最高价与其进行比较
        // 所以可以对买进的日子后面的数据进行排序，只要最大值，别的不管，不需要整体排序
        int partialMax = prices[prices.length - 1];//后面数组的最大值,初始化为price的最后一个值
        int partialMin = prices[0];
        // buy < sell - 1这个边界条件你要考虑到，因为buy < sell 可能会造成max和min相等指向同一个元素
        for (; buy < sell; ) {
            // 这么写的唯一错误就是在穿透交织的时候改变了原来的最大最小值，如果不改变就啥事都没有
            // 针对的是具有偶数个元素的数组才会出现穿透
            if (prices.length % 2 == 0 && buy == prices.length / 2) {
                break;
            }
            // 后面部分的最大值可以一直更新
            partialMax = Math.max(partialMax, prices[--sell]);
            partialMin = Math.min(partialMin, prices[++buy]);

        }
        return partialMax - partialMin > 0 ? partialMax - partialMin : 0;
    }

    public static int maxProfit2(int[] prices) {
        int maxIndex = -1;
        int max = 0;
        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            // 如果i的索引超过了我们之前找到的最大值，我们就重新寻找这个最大值，除此之外不要改变这个值
            if (i >= maxIndex) {
                max = 0;
                // 将buy的后面的最大值以及索引给找出来
                for (int j = i + 1; j < prices.length; j++) {
                    // 在每次从新寻找后面的最大值的时候，还需要进行max的重置
                    if (max != Math.max(max, prices[j])) {
                        maxIndex = j;
                    }
                    max = Math.max(max, prices[j]);
                }
            }
            maxProfit = Math.max(maxProfit, max - prices[i]); // 每存在一个buy的日期就算一个最大利润
        }
        return maxProfit;
    }

    public List<List<Integer>> generate(int numRows) {

        List<List<Integer>> res = new ArrayList<>();
        // 前两行杨辉三角的出口
        if (numRows == 1) {
            ArrayList<Integer> row1 = new ArrayList<>();
            row1.add(1);
            res.add(row1);
            return res;
        }
        if (numRows == 2) {
            ArrayList<Integer> row1 = new ArrayList<>();
            ArrayList<Integer> row2 = new ArrayList<>();
            row1.add(1);
            row2.add(1);
            row2.add(1);
            res.add(row1);
            res.add(row2);
            return res;
        }
        ArrayList<Integer> row1 = new ArrayList<>();
        ArrayList<Integer> row2 = new ArrayList<>();
        row1.add(1);
        row2.add(1);
        row2.add(1);
        res.add(row1);
        res.add(row2);
        // 针对有多少行进行的外层循环
        for (int i = 2; i < numRows; i++) {
            ArrayList<Integer> integers = new ArrayList<>();
            // 创造每一行
            for (int j = 0; j < i; j++) {
                if (j == 1 || j == i - 1) {
                    integers.add(1);
                } else {
                    integers.add(res.get(i - 1).get(j - 1) + res.get(i - 1).get(j));
                }
            }
            res.add(integers);
        }
        return res;
    }

}

