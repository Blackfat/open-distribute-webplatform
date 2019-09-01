package com.blackfat.common.algorithm;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/9/19-9:54
 */
public class Solution {

    public static int Fibonacci1(int n) {
        return (int)((Math.pow((1 + Math.sqrt(5)) / 2, n) - Math.pow((1 - Math.sqrt(5)) / 2, n)) /  Math.sqrt(5));
    }


    public static int pow( int x, int n){
        int r = 1;
        int v = x;
        while(n > 0){
           if(n % 2 == 1){
               r = r * v;
               n = n - 1;
           }
           v = v * v ;
           n = n / 2;
        }
        return r;
    }






    public static int Fibonacci(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        //底
        int[][] base = {{1,1},
                {1,0}};
        //求底为base矩阵的n-2次幂
        int[][] res = matrixPower(base, n - 2);
        //根据[f(n),f(n-1)] = [1,1] * {[1,1],[1,0]}^(n-2)，f(n)就是
        //1*res[0][0] + 1*res[1][0]
        return res[0][0] + res[1][0];
    }

    //矩阵乘法
    public static int[][] multiMatrix(int[][] m1,int[][] m2) {
        //参数判断什么的就不给了，如果矩阵是n*m和m*p,那结果是n*p
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }
    /*
     * 矩阵的快速幂：
     * 1.假如不是矩阵，叫你求m^n,如何做到O(logn)？答案就是整数的快速幂：
     * 假如不会溢出，如10^75,把75用用二进制表示：1001011,那么对应的就是：
     * 10^75 = 10^64*10^8*10^2*10
     * 2.把整数换成矩阵，是一样的
     */
    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        //先把res设为单位矩阵
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        } //单位矩阵乘任意矩阵都为原来的矩阵
        //用来保存每次的平方
        int[][] tmp = m;
        //p每循环一次右移一位
        for ( ; p != 0; p >>= 1) {
            //如果该位不为零，应该乘
            if ((p&1) != 0) {
                res = multiMatrix(res, tmp);
            }
            //每次保存一下平方的结果
            tmp = multiMatrix(tmp, tmp);
        }
        return res;
    }


    public static void main(String[] args) {
        System.out.println(Fibonacci(10));
        System.out.println(Fibonacci1(10));
        System.out.println(pow(2, 4));
    }

}

