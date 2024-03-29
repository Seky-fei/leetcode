package com.seky.leetcode.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: wf
 * @create: 2021/8/26 10:57
 * @description:
 */
public class MyLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
    private static final Integer MAXIMUM_CAPACITY = 5;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    
    public MyLinkedHashMap() {
        super(MAXIMUM_CAPACITY, DEFAULT_LOAD_FACTOR, true);
    }
    
    /**
     * 当map中元素个数大于指定容量后，删除对头节点
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        if (this.size() > MAXIMUM_CAPACITY) {
            return true;
        }
        return false;
    }
}
