package com.seky.leetcode;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: wangfei
 * @create: 2020/9/14 16:35
 * @description:
 */
public class TestMain {
    
    ReentrantLock lock = new ReentrantLock();
    Condition addCondition = lock.newCondition();
    Condition removeCondition = lock.newCondition();
    int index = 0;
    Integer[] queue = new Integer[10];
    
    public void addElement(Integer element){
        try {
            lock.lock();
            if(index == queue.length){
                removeCondition.signalAll();
                removeCondition.signal();
                addCondition.await();
            }
            queue[index] = element;
            System.out.println("添加元素：" + index + " " + element);
            index++;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    
    public void removeElement(){
        try {
            lock.lock();
            if(index == 0){
                addCondition.signal();
                removeCondition.await();
            }
            Integer element = queue[--index];
            System.out.println("删除元素：" + index + " " + element);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    
    
    @Test
    public void testReadWriteLock() throws InterruptedException {
        new Thread(() -> {
            for(int i = 1; i <= 100; i++){
                addElement(i);
            }
        }).start();
    
        new Thread(() -> {
            for(int i = 1; i <= 100; i++){
                removeElement();
            }
        }).start();
        Thread.sleep(1000 * 50);
        System.out.println("结束！！！！！！！！！！！！！！！！！！！");
    }
    
    
    @Test
    public void testCondition() throws Exception {
        
    }
    
    
    @Test
    public void testStream() throws Exception {
        ReentrantLock lock = new ReentrantLock(false);
        try {
            lock.lock();
            System.out.println(lock.getHoldCount());
            System.out.println("同步队列：" + lock.getQueueLength());
            lock.lock();
            System.out.println(lock.getHoldCount());
            System.out.println("同步队列：" + lock.getQueueLength());
            lock.lock();
            System.out.println(lock.getHoldCount());
            System.out.println("同步队列：" + lock.getQueueLength());
            System.out.println("其他同步业务操作");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        
        
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        
        writeLock.lock();
        System.out.println("写锁：" + readWriteLock.getWriteHoldCount());
        writeLock.unlock();
        
        
        readLock.lock();
        System.out.println("读锁：" + readWriteLock.getReadLockCount());
        readLock.unlock();
        
        
        readWriteLock.getReadLockCount();
        readWriteLock.getReadHoldCount();
    }
    
    @Test
    public void testSql() throws IOException {
        List<String> list = Arrays.asList("aa", "ab", "ac", "bb", "cc", "dd", "hh", "ww");
        String sql = "INSERT INTO single_table(key1, key2, key3, key_part1, key_part2, key_part3, common_field) VALUES('%s', %s, '%s', '%s', '%s', '%s', '%s');";
        
        BufferedWriter writer = new BufferedWriter(new FileWriter("D:/测试数据.sql"));
        for (int i = 21; i <= 200000; i++) {
            int key1 = (int) (list.size() * Math.random());
            int key3 = (int) (list.size() * Math.random());
            int key_part1 = (int) (list.size() * Math.random());
            int key_part2 = (int) (list.size() * Math.random());
            
            String str = String.format(sql, list.get(key1), i, list.get(key3), list.get(key_part1), list.get(key_part2), list.get(key_part2) + i, "内容字段" + i);
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
        
        for (int i = 1; i <= count; i++) {
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
        for (int i = 1; i <= 20; i++) {
            executorService.execute(() -> {
                try {
                    System.out.println("执行线程开始！！！" + Thread.currentThread().getName());
                    downLatch.await();
                    System.out.println("执行线程结束！！！" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
            });
        }
        for (int i = 1; i <= 5; i++) {
            downLatch.countDown();
        }
        executorService.shutdown();
        System.out.println("执行完毕。。。。。。。。。。。。。。。。。。。。");
    }
    
    
    @Test
    public void test() {
        int[] nums = new int[]{1, 3, 5, 6};
        int target = 5;
        System.out.println(searchInsert1(nums, target));
        System.out.println(searchIndex2(nums, target, 0, nums.length));
        System.out.println("===================================");
    }
    
    /**
     * 二分查找(用循环)
     *
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert1(int[] nums, int target) {
        int head = 0;
        int tail = nums.length - 1;
        while (head < tail) {
            int index = (head + tail) / 2;
            if (nums[index] > target) {
                tail = index;
            }
            if (nums[index] < target) {
                head = index;
            }
            if (nums[index] == target) {
                return index;
            }
        }
        return 0;
    }
    
    /**
     * 递归算法
     *
     * @param numbers
     * @param target
     * @param begin
     * @param end
     * @return
     */
    public int searchIndex2(int[] numbers, int target, int begin, int end) {
        int index = (begin + end) / 2;
        if (numbers[index] == target) {
            return index;
        }
        if (target > numbers[index]) {
            begin = index;
        } else if (target < numbers[index]) {
            end = index;
        }
        return searchIndex2(numbers, target, begin, end);
    }
    
    
    public static void main(String[] args) throws Exception {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.put(10, 100);
        treeMap.put(3, 3);
        treeMap.put(1, 1);
        treeMap.put(2, 2);
        treeMap.put(4, 4);
    
        System.out.println(treeMap);
        Map.Entry<Integer, Integer> firstEntry = treeMap.firstEntry();
        SortedMap<Integer, Integer> subTreeMap = treeMap.tailMap(4);
        System.out.println(subTreeMap);
        
        System.out.println("====================");
        System.out.println(treeMap.floorEntry(1));
        System.out.println(treeMap.lowerEntry(1));
        System.out.println(treeMap.ceilingEntry(10));
        System.out.println(treeMap.higherEntry(10));
    }
    
    @Test
    public void phantomReference(){
        ThreadLocal<Integer> local1 = new ThreadLocal<>();
        
        //local1.set(10);
        Integer integer = local1.get();
        System.out.println(integer);
        local1.remove();
    }
    
    private static final ThreadLocal<DateFormat> df = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
}
