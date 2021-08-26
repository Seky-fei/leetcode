package com.seky.leetcode;

import java.security.PublicKey;
import java.util.*;

/**
 * @author: wangfei
 * @create: 2020/9/14 16:35
 * @description:
 */
public class TestMain {
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(12, 1);
        map.put(21, 2);
        map.put(32, 3);
        map.put(40, 4);
        
        //操作
        map.put(3, 33);
        Iterator<Integer> iterator = map.keySet().iterator();
        while (iterator.hasNext())
            System.out.print(iterator.next() + ", ");
    
        System.out.println();
        System.out.println("1111111111111111111111111111111111111111111111");
        
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>(15, 0.75f, true);
        //LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>(15, 0.75f, true);
        linkedHashMap.put(11, 1);
        linkedHashMap.put(21, 2);
        linkedHashMap.put(31, 3);
        linkedHashMap.put(41, 4);
        linkedHashMap.put(51, 5);
        linkedHashMap.put(61, 6);
        linkedHashMap.put(71, 7);
        
        //操作
        linkedHashMap.get(21);
        Iterator<Integer> itera = linkedHashMap.keySet().iterator();
        while (itera.hasNext())
            System.out.print(itera.next() + ", ");
        
        System.out.println();
        System.out.println("1111111111111111111111111111111111111111111111111");
        
        LinkedHashMap<Integer, Integer> myMap = new MyLinkedHashMap<>();
        myMap.put(11, 1);
        myMap.put(21, 2);
        myMap.put(31, 3);
        myMap.put(41, 4);
        myMap.put(51, 5);
        myMap.put(61, 6);
        myMap.put(71, 7);
        Iterator<Integer> myIte = myMap.keySet().iterator();
        while (myIte.hasNext())
            System.out.print(myIte.next() + ", ");
        
    }
}
