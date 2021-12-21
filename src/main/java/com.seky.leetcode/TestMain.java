package com.seky.leetcode;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author: wangfei
 * @create: 2020/9/14 16:35
 * @description:
 */
public class TestMain {
    
    @Test
    public void testStream(){
        List<Integer> list = Arrays.asList(1, 2, 3, -1, -6, 10);
        Map<Boolean, List<Integer>> map = list.stream().collect(Collectors.partitioningBy(f -> f >= 0));
        System.out.println(map);
        System.out.println("===============");
        Map<Boolean, Set<Integer>> map1 = list.stream().collect(Collectors.partitioningBy(f -> f >= 0, Collectors.toSet()));
        System.out.println(map1);
        
        
    }
    
    @Test
    public void testSql() throws IOException {
        List<String> list = Arrays.asList("aa", "ab", "ac", "bb", "cc", "dd", "hh", "ww");
        String sql = "INSERT INTO single_table(key1, key2, key3, key_part1, key_part2, key_part3, common_field) VALUES('%s', %s, '%s', '%s', '%s', '%s', '%s');";
    
        BufferedWriter writer = new BufferedWriter(new FileWriter("D:/测试数据.sql"));
        for(int i = 21; i <= 200000; i++){
            int key1 = (int) (list.size() * Math.random());
            int key3 = (int) (list.size() * Math.random());
            int key_part1 = (int) (list.size() * Math.random());
            int key_part2 = (int) (list.size() * Math.random());
            
            String str = String.format(sql, list.get(key1), i, list.get(key3), list.get(key_part1), list.get(key_part2), list.get(key_part2)+ i, "内容字段"+i);
            writer.write(str);
            writer.newLine();
            writer.flush();
            System.out.println(str);
        }
        
    }
    
    @Test
    public void testJUC() throws InterruptedException {
        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        AtomicInteger atomicInt = new AtomicInteger();
        List<Thread> list = new ArrayList<>();
        
        for(int i = 1; i <= count; i++){
            Thread thread = new Thread(() -> {
                atomicInt.incrementAndGet();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                atomicInt.incrementAndGet();
                countDownLatch.countDown();
            });
            list.add(thread);
        }
        list.stream().forEach(f -> f.start());
        System.out.println("等待所有线程执行完！！！！");
        countDownLatch.await();
        System.out.println("线程执行完毕!!!!!!!   " + atomicInt.get());
    }
    
    @Test
    public void testSemaphore() throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(3);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for(int i = 1; i <= 20; i++){
            executorService.execute(() ->{
                try {
                    System.out.println("执行线程开始！！！" + Thread.currentThread().getName());
                    downLatch.await();
                    System.out.println("执行线程结束！！！" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
            });
        }
        for(int i = 1; i <= 5; i++){
            downLatch.countDown();
        }
        executorService.shutdown();
        System.out.println("执行完毕。。。。。。。。。。。。。。。。。。。。");
    }
    
    
    @Test
    public void test(){
        int[] nums = new int[]{1,3,5,6};
        int target = 5;
        System.out.println(searchInsert1(nums, target));
        System.out.println(searchIndex2(nums, target, 0, nums.length));
        System.out.println("===================================");
    }

    /**
     * 二分查找(用循环)
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert1(int[] nums, int target){
        int head = 0;
        int tail = nums.length - 1;
        while (head < tail){
            int index = (head + tail)/2;
            if(nums[index] > target){
                tail = index;
            }
            if(nums[index] < target){
                head = index;
            }
            if(nums[index] == target){
                return index;
            }
        }
        return 0;
    }

    /**
     * 递归算法
     * @param numbers
     * @param target
     * @param begin
     * @param end
     * @return
     */
    public int searchIndex2(int[] numbers, int target, int begin, int end){
        int index = (begin + end)/2;
        if(numbers[index] == target){
            return index;
        }
        if(target > numbers[index]){
            begin = index;
        }else if(target < numbers[index]){
            end = index;
        }
        return searchIndex2(numbers, target, begin, end);
    }


}
