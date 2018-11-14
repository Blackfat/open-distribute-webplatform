package com.blackfat.common.algorithm;

/**
 * @author wangfeiyang
 * @desc   二叉查找树
 * @create 2018/11/14-9:26
 */
public class BinarySearchTree {

    private Node tree;

    private static class Node{
        private int data;

        private Node left;

        private Node right;

        public Node(int value){
            data = value;
        }

        boolean isLeaf() {
            return left == null ? right == null : false;
        }

    }

    /**
     * 查找
     * @param data
     * @return
     */
    public Node find(int data){
        Node p = tree;
        while(p != null){
            if(data < p.data){
                 p = p.left;
            }else if(data > p.data){
                 p= p.right;
            }else{
                return p;
            }
        }
        return null;
    }


    /**
     * 插入
     * @param data
     */
    public void insert(int data){
        if(tree == null){
            tree = new Node(data);
            return;
        }
        Node p = tree;
        while(p != null){
            if(data > p.data){
                if(p.right == null){
                    p.right = new Node(data);
                    return;
                }
                p = p.right;
            }else{
                if(p.left == null){
                    p.left = new Node(data);
                    return;
                }
                p = p.left;
            }
        }

    }



    /**
     * 删除
     * @param data
     */
    public void delete(int data){
      Node p = tree;   // 指向要删除的节点
      Node pp = null; // p的父节点
      while(p != null && p.data != data){
          pp = p;
          if(data > p.data){
             p = p.right;
          }else{
              p = p.left;
          }
      }
      if(p == null){
          return;
      }

      // 要删除的节点有2个子节点
      if(p.left != null && p.right != null){
        Node minp = p.right;// 右子树的最小节点
        Node minpp = p;
        while(minp.left != null){
           minpp = minp;
           minp = minp.left;
        }
        p.data = minp.data;
        p = minp;
        pp = minpp;
      }

      // 删除节点是叶子节点或者仅有一个节点
       Node child;
      if(p.left != null){
          child = p.left;
      }else if(p.right != null){
          child = p.right;
      }else child = null;


      // 删除节点是根节点
        if (pp == null) tree = child; // 删除的是根节点
        else if (pp.left == p) pp.left = child;
        else pp.right = child;


    }

    /**
     * 获取二叉树的深度
     * @param node
     * @return
     */
    public int getDep(Node node){
        int level = 0;
        int last = 0;//层队尾的下标
        int front = -1;//层队头下标
        int index = -1;// 数组元素下标
        Node[] queue = new Node[100];
        if(node == null){
            return 0;
        }
        queue[++index] = node;
        Node p;
        while(front < index){
            p = queue[++front];
            if(p.left!=null){
                queue[++index]=p.left;
            }
            if(p.right!=null){
                queue[++index]=p.right;
            }
            if(front == last){
                level++;
                last = index;
            }

        }
        return level;

    }




}
