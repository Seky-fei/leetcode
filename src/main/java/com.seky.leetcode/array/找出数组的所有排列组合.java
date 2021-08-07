package com.seky.leetcode.array;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: wf
 * @create: 2021/7/23 14:27
 * @description: 给定一个数组, 找出所有的排列组合
 */
public class 找出数组的所有排列组合 {
    
    @Test
    public void testMethod(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        System.out.println(getListArrange(list, 2, false));
        System.out.println(getListArrange(list, 2, true));
    }
    
    /**
     * 递归分治求一个数组所有的排列组合情况
     * @param list
     * @param number 排列组合中元素个数
     * @param isPerm true排列, false组合
     * @return
     */
    public List<List<Integer>> getListArrange(List<Integer> list, int number, boolean isPerm){
        List<List<Integer>> result = new ArrayList<>();
        //递归结束条件
        if(number == 1){
            result.addAll(list.stream().map(f -> Arrays.asList(f)).collect(Collectors.toList()));
            return result;
        }
        //递归分治: 排列+组合
        for(int i = 0; i <  list.size(); i++){
            int current = list.get(i);
            List<List<Integer>> listArrange = null;
            if(isPerm){
                //递归分: 排列
                listArrange = getListArrange(list.stream().filter(f -> f.intValue() != current).collect(Collectors.toList()), number - 1, isPerm);
            }else {
                //递归分: 组合
                listArrange = getListArrange(list.subList(i+1, list.size()), number - 1, isPerm);
            }
            //递归治
            for (List<Integer> arrange : listArrange) {
                List<Integer> subList = new ArrayList<>();
                subList.add(current);
                subList.addAll(arrange);
                result.add(subList);
            }
        }
        return result;
    }
}
