package com.seky.leetcode.entry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wf
 * @create: 2021/12/16 11:00
 * @description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Long id;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 班级
     */
    private Long classId;
    
    /**
     * 总分
     */
    private double score;
    
    public void say(String temp){
        System.out.println("执行了say()方法, 入参 temp = " + temp);
    }

    public static void main(String[] args) {
        Student student = new Student();
        student.say("1111");
    }
    
/*    public Student(Long id, Integer age, Long classId, double score) {
        this.id = id;
        this.age = age;
        this.classId = classId;
        this.score = score;
    }
    
    
    
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student(1L, 20, 1L, 50.3));
        list.add(new Student(2L, 20, 2L, 100.1));
        list.add(new Student(3L, 21, 2L, 16.1));
        list.add(new Student(4L, 22, 1L, 50.1));
        list.add(new Student(5L, 22, 3L, 30.1));
        list.add(new Student(6L, 21, 3L, 30.1));
    
        Double collect1 = list.stream().collect(Collectors.averagingInt(f -> f.getAge()));
        Double collect2 = list.stream().collect(Collectors.averagingInt(f -> f.getAge() * 2));
        System.out.println(collect1 + "  " + collect2);
    
        list.stream().collect(Collectors.averagingDouble(f -> f.getScore() * f.getScore()));
    
    
        Map<Long, Double> collect = list.stream().collect(Collectors.collectingAndThen(Collectors.groupingBy(f -> f.getClassId()), map -> {
            Map<Long, Double> result = new HashMap<>();
            map.forEach((k, v) -> {
                double sum = v.stream().mapToDouble(hh -> hh.getScore()).sum();
                System.out.println(sum);
                result.put(k, sum);
            });
            return result;
        }));
        System.out.println(collect);
        System.out.println("=======================================================");
    
        Map<Integer, Student> collect3 = list.stream().collect(Collectors.toMap(f -> f.getAge(), f -> f, (k1, k2) -> k1.getId() >= k2.getId() ? k1 : k2));
        System.out.println(collect3);
        TreeMap<Integer, Student> collect4 = list.stream().collect(Collectors.toMap(f -> f.getAge(), f -> f, (k1, k2) -> k1.getId() >= k2.getId() ? k1 : k2, TreeMap::new));
        System.out.println(collect4);
    
    }*/
}
