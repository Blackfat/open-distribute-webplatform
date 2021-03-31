package com.blackfat.common.algorithm;

import java.util.Random;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/12/7-9:26
 */
public class SkipList {

    /**
     * 索引最大层数，包含原始链表那一层。
     */
    private static final int MAX_LEVEL = 16;

    /**
     * 当前索引总层数。
     */
    private int levelCount = 1;

    /**
     * 跳表头结点。
     * 类似链表里的带头链表，哨兵结点，本身不存储数据，下一跳指向各层索引的头结点。
     */
    private Node head;  // 带头链表

    private Random r;

    public SkipList() {
        head = new Node();
        r = new Random();
    }

    public Node find(int value) {
        Node p = head;
        // 从最上层开始查找，如果下一跳结点的值大于等于要查找的值，切换到下一层继续查找，直到第 0 层
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
        }
       // 第 0 层存储了所有结点，如果找到要查找的值，返回该结点，否则返回 null
        if (p.forwards[0] != null && p.forwards[0].data == value) {
            return p.forwards[0];
        } else {
            return null;
        }
    }

    /**
     * 往跳表插入一个数据，数据从小到大排序。
     */
    public void insert(int value) {
        int level = randomLevel();
        Node newNode = new Node();
        newNode.data = value;
        newNode.maxLevel = level;
        Node update[] = new Node[level];
        for (int i = 0; i < level; ++i) {
            update[i] = head;
        }

        Node p = head;
        for (int i = level - 1; i >= 0; --i) {
            // 找到第 i 层索引的插入位置，将插入位置前面的结点保存到 update 数组
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
            update[i] = p;
        }

        for (int i = 0; i < level; ++i) {
            // 更新各层的 forwards 结点
            newNode.forwards[i] = update[i].forwards[i];
            update[i].forwards[i] = newNode;
        }

        // 更新当前索引总层数
        if (levelCount < level) levelCount = level;
    }

    public void delete(int value) {
        Node[] update = new Node[levelCount];
        Node p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
            update[i] = p;
        }

        if (p.forwards[0] != null && p.forwards[0].data == value) {
            for (int i = levelCount - 1; i >= 0; --i) {
                if (update[i].forwards[i] != null && update[i].forwards[i].data == value) {
                    update[i].forwards[i] = update[i].forwards[i].forwards[i];
                }
            }
        }
    }

    private int randomLevel() {
        int level = 1;
        for (int i = 1; i < MAX_LEVEL; ++i) {
            if (r.nextInt() % 2 == 1) {
                level++;
            }
        }

        return level;
    }

    public void printAll() {
        Node p = head;
        while (p.forwards[0] != null) {
            System.out.print(p.forwards[0] + " ");
            p = p.forwards[0];
        }
        System.out.println();
    }

    public class Node {
        /**
         * 结点存储的整数值。
         */
        private int data = -1;
        /**
         * 保存当前结点的所有下一跳结点。
         * forwards[i] 表示当前结点在第 i 层索引的下一跳结点，i in [0, maxLevel-1]
         */
        private Node forwards[] = new Node[MAX_LEVEL];
        /**
         * 当前索引总层数。
         * 索引从 0 开始计数，到 maxLevel-1 为止。
         * 第 0 层为原始链表，从下往上依次建立索引，最上层为第 maxLevel-1 层索引。
         */
        private int maxLevel = 0;

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{ data: ");
            builder.append(data);
            builder.append("; levels: ");
            builder.append(maxLevel);
            builder.append(" }");

            return builder.toString();
        }
    }

}
