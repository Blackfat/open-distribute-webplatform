package com.blackfat.common.algorithm;

import java.util.Stack;

/**
 * @author wangfeiyang
 * @desc 二叉搜索树
 * @create 2018/11/1-9:42
 */
/*
*
*  A binary search tree is a
* sorted binary tree, where value of a node is greater than or equal to its
* left the child and less than or equal to its right child.
* */
public class BinaryTree {

    private static class Node{
        private int data;

        private Node left;

        private Node right;

        public Node(int value){
            data = value;
            left = right = null;
        }

        boolean isLeaf() {
            return left == null ? right == null : false;
        }

    }

    // 根节点
    private Node root;

    public BinaryTree(){
        root = null;
    }

    public Node getRoot(){
        return root;
    }

    // 检查二叉树是否为空
    public boolean isEmpty(){
        return root == null;
    }


    public void clear(){
        root = null;
    }

    public int size(){
         Node current = root;
         int size = 0;
        Stack<Node> stack = new Stack<Node>();
        while(current != null || !stack.isEmpty()){
            if(current != null){
                stack.push(current);
                current = current.left;
            }else{
                size ++;
                stack.pop();
                current = current.right;
            }
        }
        return size;
    }

    // 先序遍历
    private void preOrder(Node node){
        if(node == null){
            return;
        }
        System.out.printf("%s ", node.data);
        preOrder(node.left);
        preOrder(node.right);
    }



    private void preOrderWithoutRecursion(Node node){
        if(node == null){
            return;
        }
        Stack<Node> stack = new Stack<Node>();
        stack.push(node);

        while(!stack.isEmpty()){

            Node current = stack.pop();

            System.out.printf("%s ", current.data);

            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }

        }
    }

    // 中序
    public void inOrder(Node node){
        if(node == null){
            return;
        }

        inOrder(node.left);
        System.out.printf("%s ", node.data);

        inOrder(node.right);
    }



    public void inOderWithoutRecursion(Node node){
        if(node == null){
            return;
        }
        Stack<Node> stack = new Stack<Node>();
        Node current = node;
        while (!stack.isEmpty() || current != null) {
            if(current != null){
                stack.push(current);
                current = current.left;
            }
            else{
               Node inNode = stack.pop();
                System.out.printf("%s ", inNode.data);
                current = inNode.right;

            }

        }


    }


    // 后序
    public void postOrder(Node node){
        if(node == null){
            return;
        }

        postOrder(node.left);
        postOrder(node.right);
        System.out.printf("%s ", node.data);
    }


    public void postOrderWithoutRecurion(Node node){
        if(node == null){
            return;
        }
        Stack<Node> stack = new Stack<Node>();
        stack.push(node);
        while(!stack.isEmpty()){
            // 返回栈顶元素
            Node current = stack.peek();

            if(current.isLeaf()){
               Node leaf = stack.pop();
                System.out.printf("%s ", leaf.data);
            }else{
                if (current.right != null) {
                    stack.push(current.right);
                    current.right = null;
                }

                if (current.left != null) {
                    stack.push(current.left);
                    current.left = null;
                }
            }



        }

    }




}
