package com.blackfat.common.concurrent;

import com.blackfat.common.utils.number.RandomUtil;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/8/4-15:28
 */
public class ForkTest {

    public static void main(String[] args) {
        // 创建随机数组成的数组:
        long[] array = new long[400000];
        fillRandom(array);
        sumArray(array);
        ForkJoinPool fjp = new ForkJoinPool(4); // 最大并发数4
        ForkJoinTask<Long> task = new SumTask(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = fjp.invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
    }

    public static void sumArray(long[] array) {
        long startTime = System.currentTimeMillis();
        long result = 0;
        if (array != null && array.length > 0) {
            for (long i : array) {
                result = result + i;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Serial sum: " + result + " in " + (endTime - startTime) + " ms.");
    }

    public static void fillRandom(long[] array) {

        if (array != null && array.length > 0) {
            for (int i = 0; i < array.length; i++) {
                array[i] = RandomUtil.nextInt(1000);
            }
        }
    }
}

class SumTask extends RecursiveTask<Long> {

    static final int THRESHOLD = 10000;
    long[] array;
    int start;
    int end;

    SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            // 如果任务足够小,直接计算:
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//            }
//            System.out.println(String.format("compute %d~%d = %d", start, end, sum));
            return sum;
        }
        // 任务太大,一分为二:
        int middle = (end + start) / 2;
//        System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
        SumTask subtask1 = new SumTask(this.array, start, middle);
        SumTask subtask2 = new SumTask(this.array, middle, end);
        invokeAll(subtask1, subtask2);
        Long subresult1 = subtask1.join();
        Long subresult2 = subtask2.join();
        Long result = subresult1 + subresult2;
//        System.out.println("result = " + subresult1 + " + " + subresult2 + " ==> " + result);
        return result;
    }
}
