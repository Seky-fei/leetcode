package com.seky.leetcode.BFS.抽象成图论问题;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: wf
 * @create: 2021/11/1 19:34
 * @description:
 */
public class 员工的重要性 {
    @Test
    public void testMain(){
        //[[1, 5, [2, 3]], [2, 3, []], [3, 3, []]]
        List<Employee> list = new ArrayList<>();
    
        Employee employee1 = new Employee(1, 5, Arrays.asList(2, 3));
        Employee employee2 = new Employee(2, 3, Arrays.asList());
        Employee employee3 = new Employee(3, 3, Arrays.asList());
        
        list.add(employee1);
        list.add(employee2);
        list.add(employee3);
        
        System.out.println(getImportance(list, 1));
        System.out.println("=====================================");
        Map<Integer, Employee> map = list.stream().collect(Collectors.toMap(f -> f.id, f -> f));
        System.out.println(getImportByRecursion(map, 1));
    }
    
    /**
     * 员工和他所有下属的重要度之和 (BFS迭代)
     * @param list
     * @param id
     * @return
     */
    public int getImportance(List<Employee> list, int id){
        int total = 0;
        Map<Integer, Employee> map = list.stream().collect(Collectors.toMap(f -> f.id, f -> f));
        //BFS迭代
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(id);
        while(!queue.isEmpty()){
            Integer curId = queue.poll();
            Employee employee = map.get(curId);
            total += employee.importance;
            for(Integer im : employee.subordinates){
                queue.offer(im);
            }
        }
        return total;
    }
    
    /**
     * 员工和他所有下属的重要度之和 (递归方式)
     * @param map
     * @param id
     * @return
     */
    public int getImportByRecursion(Map<Integer, Employee> map, int id){
        Employee employee = map.get(id);
        if(employee.subordinates == null || employee.subordinates.size() == 0){
            return employee.importance;
        }
        int count = employee.importance;
        for(int emId : employee.subordinates){
            count += getImportByRecursion(map, emId);
        }
        return count;
    }
    
    
    class Employee{
        public int id;
        public int importance;
        public List<Integer> subordinates;
    
        public Employee(int id, int importance, List<Integer> subordinates) {
            this.id = id;
            this.importance = importance;
            this.subordinates = subordinates;
        }
    }
}
