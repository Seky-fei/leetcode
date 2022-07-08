package com.seky.leetcode;

import java.util.Arrays;
import java.util.List;

/**
 * @author: wf
 * @create: 2021/8/26 8:51
 * @description:
 */
public final class AbstractTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd", "cc");
        
        String sql = "INSERT INTO single_table(key1, key2, key3, key_part1, key_part2, key_part3, common_field) VALUES('%s', %s, '%s', '%s', '%s', '%s', '%s');";
        for(int i = 1; i <= 3000; i++){
            int ran1 = (int) (Math.random() * list.size());
            int ran2 = (int) (Math.random() * list.size());
            int ran3 = (int) (Math.random() * list.size());
            int ran4 = (int) (Math.random() * list.size());
            int ran5 = (int) (Math.random() * list.size());
            
            String tt = String.format(sql, list.get(ran1), i, list.get(ran2), list.get(ran3), list.get(ran4), list.get(ran5), "文本类容"+i);
            System.out.println(tt);
        }
    }
}
