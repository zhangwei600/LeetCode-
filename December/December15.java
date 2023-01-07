package com.fang.leetcode.December;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * @author letian
 * @version 1.0
 * <a href="https://leetcode.cn/problems/minimum-deletions-to-make-character-frequencies-unique/description/">...</a>
 */
public class December15 {
    public static void main(String[] args) {
        December15 s = new December15();
        int i = s.minDeletions("aaabbbcc");
        System.out.println(i);
    }
    public int minDeletions(String s) {
        int[] cnt = new int[26];
        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }
        // 怎么才是最少的删除
        // 不就是弄出26个不同的数字来吗，或者说更少的数字
        // 最差就是全部删除，然后有没有别的呢
        Arrays.sort(cnt);
        int i = 24, ans = 0;
        TreeSet<Integer> seen = new TreeSet<>();
        seen.add(cnt[25]);
        while (i >= 0) {
            if (cnt[i] == 0) {
                break;
            }
            if (!seen.contains(cnt[i])) {
                seen.add(cnt[i]);
            } else {
                ans += cnt[i] - seen.first() + 1;
                if (seen.first() != 1) {
                    seen.add(seen.first() - 1);
                }
            }
            i--;
        }
        return ans;
    }
}
