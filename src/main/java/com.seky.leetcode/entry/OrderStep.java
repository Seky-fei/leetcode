package com.seky.leetcode.entry;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wf
 * @create: 2022/4/11 13:47
 * @description:
 */
@Data
public class OrderStep {
    private long orderId;
    private String desc;
    
    
    /**
     * 生成模拟订单数据
     */
    public static List<OrderStep> buildOrders() {
        List<OrderStep> orderList = new ArrayList<>();
        
        OrderStep orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);
        
        orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);
        
        orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("推送");
        orderList.add(orderDemo);
        
        orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);
        
        orderDemo = new OrderStep();
        orderDemo.setOrderId(2L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);
        
        orderDemo = new OrderStep();
        orderDemo.setOrderId(2L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);
    
        orderDemo.setOrderId(2L);
        orderDemo.setDesc("推送");
        orderList.add(orderDemo);
        
        orderDemo = new OrderStep();
        orderDemo.setOrderId(2L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);
        
/*        orderDemo = new OrderStep();
        orderDemo.setOrderId(3L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);
        
        orderDemo = new OrderStep();
        orderDemo.setOrderId(3L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);
    
        orderDemo = new OrderStep();
        orderDemo.setOrderId(3L);
        orderDemo.setDesc("推送");
        orderList.add(orderDemo);
        
        orderDemo = new OrderStep();
        orderDemo.setOrderId(3L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);*/
        return orderList;
    }
}
