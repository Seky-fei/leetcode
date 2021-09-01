package com.seky.leetcode;

import lombok.Data;

/**
 * @author: wf
 * @create: 2021/8/27 15:36
 * @description:
 */
@Data
public class User {
    private Integer id;
    private String name;
    
    @Override
    public int hashCode(){
        int round = (int) (Math.random()*100);
        System.out.println("round = " + round);
        return round;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof User){
            User user = (User) obj;
            return id.equals(user.id) && name.equals(user.name);
        }
        return false;
    }
    
    public static void main(String[] args) {
        User user1 = new User();
        user1.setId(1);
        user1.setName("111");
    
        User user2 = new User();
        user2.setId(1);
        user2.setName("111");
        
        System.out.println(user1.hashCode() == user2.hashCode());
        System.out.println(user1.equals(user2));
    }
}



