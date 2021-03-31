package com.blackfat.common.algorithm;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author wangfeiyang
 * @desc    图
 * @create 2018/12/3-15:25
 */
public class Graph {

    private int v;   // 顶点的个数
    private LinkedList<Integer>  adj[]; // 邻接表

    public Graph(int v){
        this.v = v;
        this.adj = new LinkedList[v];
        for(int i= 0 ; i< v ; i++){
            adj[i] = new LinkedList<>();
        }
    }

    // 无向图一条边存两次
    public void addEdge(int s, int t){
        adj[s].add(t);
        adj[t].add(s);
    }


    /**
     * 广度优先BFS 优先一般是解决最短路径问题
     * 广度优先搜索会根据离起点的距离，按照从近到远的顺序对各节点进行搜索
     * @param s
     * @param t
     */
    public void bfs(int s, int t) {
        if (s == t) return;
        boolean[] visited = new boolean[v];
        visited[s]=true;  // 已经被访问的节点
        Queue<Integer> queue = new LinkedList<>(); //已经被访问、但相连的顶点还没有被访问的顶点
        queue.add(s);
        int[] prev = new int[v]; // 搜索路径
        for (int i = 0; i < v; ++i) {
            prev[i] = -1;
        }
        while (queue.size() != 0) {
            int w = queue.poll();
            for (int i = 0; i < adj[w].size(); ++i) {
                int q = adj[w].get(i);
                if (!visited[q]) {
                    prev[q] = w;
                    if (q == t) {
                        print(prev, s, t);
                        return;
                    }
                    visited[q] = true;
                    queue.add(q);
                }
            }
        }
    }

    private void print(int[] prev, int s, int t) { // 递归打印 s->t 的路径
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }



    boolean found = false; // 全局变量或者类成员变量

    /**
     * 深度遍历DFS 优先一般是解决连通性问题
     * 深度优先搜索会沿着一条路径不断往下搜索直到不能再继续为止，然后再折返，开始搜索下一条路径。
     * @param s
     * @param t
     */
    public void dfs(int s, int t) {
        found = false;
        boolean[] visited = new boolean[v];
        visited[s]=true;  // 已经被访问的节点
        int[] prev = new int[v];
        for (int i = 0; i < v; ++i) {
            prev[i] = -1;
        }
        recurDfs(s, t, visited, prev);
        print(prev, s, t);
    }

    private void recurDfs(int w, int t, boolean[] visited, int[] prev) {
        if (found == true) return;
        visited[w] = true;
        if (w == t) {
            found = true;
            return;
        }
        for (int i = 0; i < adj[w].size(); ++i) {
            int q = adj[w].get(i);
            if (!visited[q]) {
                prev[q] = w;
                recurDfs(q, t, visited, prev);
            }
        }
    }




}
