package com.seky.leetcode.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author: wf
 * @create: 2022/3/11 16:17
 * @description:  使用虚拟节点实现一致性Hash算法
 */
public class ConsistentHashShard {
    
    private static List<String> nodeIpList = Arrays.asList("198.162.122.12", "198.162.122.13", "198.162.122.14", "198.162.122.15", "198.162.122.16");
    /**
     * TreeMap实现一致性hash
     */
    private static TreeMap<Integer, String> NODES_MAP = new TreeMap<>();
    /**
     * 一个节点对应的虚拟节点
     */
    private static Integer VIRTUAL_NODE = 50;
    
    public ConsistentHashShard() {
        init();
    }
    
    /**
     * 初始化
     */
    private void init(){
        //每个真实机器节点都需要关联虚拟节点
        for (int i = 0; i < nodeIpList.size(); i++){
            String shardInfo = nodeIpList.get(i);
            for (int n = VIRTUAL_NODE; n > 0; n--){
                // 一个真实机器节点关联NODE_NUM个虚拟节点
                NODES_MAP.put(getHashCode("SHARD-" + i + "-NODE-" + n), shardInfo);
            }
        }
    }
    
    /**
     * 获取hash值,要求足够离散
     * @param str
     * @return
     */
    private int getHashCode(String str){
        return str.hashCode();
    }
    
    /**
     * key到hash环上的映射-不带虚拟节点（找第一个大于等于key的节点）
     * @param key
     * @return
     */
    public String getNodeServer(String key){
        int keyHash = getHashCode(key);
        Map.Entry<Integer, String> entry = NODES_MAP.ceilingEntry(keyHash);
        if(entry == null){
            entry = NODES_MAP.firstEntry();
        }
        return entry.getValue();
    }
    
    @Test
    public void testConsistentHash(){
        String[] keys = new String[]{"node1", "node2", "node3", "node421", "node512"};
        ConsistentHashShard shard = new ConsistentHashShard();
        for(String key : keys){
            String nodeServer = shard.getNodeServer(key);
            System.out.println(key + "  " + nodeServer);
        }
    }
}