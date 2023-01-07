package com.fang.leetcode.October2022;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author letian
 * @version 1.0
 */
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        System.out.print("请输入信息");
        String[] s = br.readLine().split(",");
        int[] arr = new int[s.length];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(s[i]);
        }
        List<List<Integer>> l = process1(arr);
        for (List<Integer> integers : l) {
            bw.write(integers.toString());
            bw.write("\n");
        }
        br.close();
        bw.close();
    }

    public static List<List<Integer>> process1(int[] arr) {
        //
        List<List<Integer>> ans = new ArrayList<>();
        LinkedList<Integer> ele = new LinkedList<>();
        f1(arr, 0, ele, ans);
        return ans;
    }

    static void f1(int[] arr, int index, LinkedList<Integer> ele, List<List<Integer>> ans) {
        if (index == arr.length) {
            ans.add(new LinkedList<>(ele));
        }
        for (int i = index; i < arr.length; i++) {
            swap(arr, index, i);
            ele.add(arr[index]);
            f1(arr, index + 1, ele, ans);
            ele.removeLast();
            swap(arr, index, i);
        }
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

    }
    public static List<List<Integer>> process(int[] arr) {
        List<List<Integer>> ans = new ArrayList<>();
        LinkedList<Integer> ele = new LinkedList<>();
        f(ans, ele, 0, arr);
        return ans;
    }

     static void f(List<List<Integer>> ans, LinkedList<Integer> ele, int index, int[] arr) {
        if (index == arr.length) {
            ans.add(new LinkedList<>(ele));
            return;
        }
        f(ans, ele, index + 1, arr);
        ele.add(arr[index]);
        f(ans, ele, index + 1, arr);
        ele.removeLast();
    }

}
