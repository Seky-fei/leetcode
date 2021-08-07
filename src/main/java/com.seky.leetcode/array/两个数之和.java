package com.seky.leetcode.array;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author: wf
 * @create: 2021/7/21 10:34
 * @description: 
 * 给定一个数组numbers[] 和 目标数target, 找出numbers[i] + numbers[j] = target的元素位置
 * (备注: 假设正确答案只有一个)
 * 
 */
public class 两个数之和 {
    
    @Test
    public void testMain(){
        int[] numbers = new int[]{1, 2, 3, 4, 5, 6, -10};
        int target = 10;
        System.out.println(Arrays.toString(findMethod1(numbers, target)));
        System.out.println(findMethod2(numbers, target));
        System.out.println(findMethod3(numbers, target));
        System.out.println(findMethod4(numbers, target));
    }
    
    /**
     * 暴力解法, 
     *      时间复杂度 O(n^2)
     *      空间复杂度 O(1)
     * @param numbers
     * @param target
     * @return
     */
    public int[] findMethod1(int[] numbers, int target){
        int[] result = new int[2];
        if(numbers.length < 2){
            return result;
        }
        for(int le = 0; le < numbers.length; le++){
            for(int ri = le + 1; ri < numbers.length; ri++){
                if(numbers[le] + numbers[ri] == target){
                    result[0] = le;
                    result[1] = ri;
                    return result;
                }
            }
        }
        return result;
    }
    
    /**
     * 时间复杂度 O(2n)
     * 空间复杂度O(n)
     * 
     * @param numbers
     * @param target
     * @return  
     *  为什么使用两个for循环? 习惯的正向思维, x + y = target ===> x = target -y 
     *    习惯的正向思维是 map里存numbers的值和对应的index下标一一对应, 在遍历数组从前往后找
     * 
     */
    public List<Integer> findMethod2(int[] numbers, int target){
        List<Integer> indexs = new ArrayList<>(2);
        if(numbers == null || numbers.length < 2){
            return indexs;
        }
        Map<Integer, Integer> map = new HashMap<>(numbers.length);
        for(int i = 0; i < numbers.length; i++){
            map.put(numbers[i], i);
        }
        for(int i = 0; i < numbers.length; i++){
            Integer integer = map.get(target - numbers[i]);
            if(integer != null){
                indexs.add(i);
                indexs.add(integer);
                return indexs;
            }
        }
        return indexs;
    }
    
    /**
     * 时间复杂度 O(n) 最坏的情况下,遍历一遍数组
     * 空间复杂度O(n)
     * @param numbers
     * @param target
     * @return
     *  逆向思维: x + y = target ===> x = target -y
     *  正向思维:把numbers的value和index一一对应的存入map, 在遍历从前往后找
     *  逆向思维:把x 和 y 对应的下标错开存, 即key=x对应的value=y对应的下标index, 这样就可以做到从后往前找
     *  (最坏的情况: 有一个值在最右边,需要把数组都遍历一遍)
     */
    public List<Integer> findMethod3(int[] numbers, int target){
        List<Integer> list = new ArrayList<>(2);
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < numbers.length; i++){
            if(map.containsKey(numbers[i])){
                list.add(map.get(numbers[i]));
                list.add(i);
                break;
            }
            map.put(target - numbers[i], i);
        }
        return list;
    }
    
    /**
     *   时间复杂度 O(n/2) 最坏的情况下遍历 n/2
     *   空间复杂度O(n)
     * 
     * @param numbers
     * @param target
     * @return
     *  在方法三的基础上再优化: 方法三是单从前往后找,最坏的情况下回遍历一遍数组,时间复杂度是 O(2)
     *  
     *  在优化: 利用收尾下标[ll, rr]从两头寻找
     * 
     */
    public List<Integer> findMethod4(int[] numbers, int target){
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < numbers.length; i++){
            //从左边找
            int ll = i;
            if(map.containsKey(numbers[ll])){
                list.add(map.get(numbers[ll]));
                list.add(ll);
                break;
            }
            map.put(target - numbers[ll], ll);
            
            //从右边找
            int rr = numbers.length - 1 - ll;
            if(map.containsKey(numbers[rr])){
                list.add(map.get(numbers[rr]));
                list.add(rr);
                break;
            }
            map.put(target - numbers[rr], rr);
        }
        return list;
    }
}
