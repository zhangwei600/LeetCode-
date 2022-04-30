package com.fang.leetcode.queue;

import java.util.Scanner;

/**
 * @author letian
 * @version 1.0
 * 数组模拟队列
 */
public class ArrayQueue {
    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue();
        arrayQueue.addData(2);
        arrayQueue.addData(4);
        arrayQueue.addData(4);
        arrayQueue.addData(4);
        System.out.println(arrayQueue.pop());
        arrayQueue.list();
        Scanner scanner = new Scanner(System.in);
        System.out.print("请添加数据:");
        arrayQueue.addData(Integer.parseInt(scanner.nextLine()));
        scanner.close();
    }
    /**
     * 头指针front
     * 尾指针rear
     * 数组data存放数据，也是线性列表
     */
    private int front;
    private int rear;
    private final static int MAX_SIZE = 4;
    private int[] data;

    public ArrayQueue() {
        this.data = new int[MAX_SIZE];
    }

    public void addData(int num) {
        if (isFull()) {
            System.out.println("队列满，不能再添加数据");
            return;
        }
        data[rear++] = num;//队列尾部添加数据，然后自增
    }

    public boolean isFull() {
        return rear == MAX_SIZE;
    }

    public boolean empty() {
        return rear == front;
    }

    public int pop() {
        if (empty()) {
            // 直接抛出运行异常
            throw new RuntimeException("队列为空,不能取得数据");
        }
        int res = data[front++];
        return res;
    }

    public void list() {
        if (empty()) {
            System.out.println("队列空");
        }
        for (int i = front; i < rear; i++) {
            // 格式化输出字符串的第二种格式
            System.out.printf("data[%d]=%d%n", i, data[i]);
        }
    }

    public int peek() {
        if (empty()) {
            throw new RuntimeException("队列为空");
        }
        return data[front];
    }
}
