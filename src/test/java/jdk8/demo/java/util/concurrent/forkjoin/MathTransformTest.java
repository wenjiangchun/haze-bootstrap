package jdk8.demo.java.util.concurrent.forkjoin;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.*;

/**
 * 该demo用来测试使用fork-join并行计算数组内元素
 * <p>
 *     在jdk8中 ForkJoinPool提供一个默认公共池,可以不用显式使用ForkJoinPool构造方法创建一个池
 * </p>
 * Created by sofar on 16-1-24.
 */
public class MathTransformTest {

    @Test
    public void testCompute() throws Exception {
        System.out.println("当前处理器个数:" + Runtime.getRuntime().availableProcessors());
        long longTime = System.currentTimeMillis();
        //ForkJoinPool pool = new ForkJoinPool();
        ForkJoinPool pool = ForkJoinPool.commonPool();
        System.out.println("当前并行任务数量:" + pool.getParallelism());
        double[] nums = new double[100000000];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
        }

        /*for (int i = 0; i < nums.length; i++) {
            nums[i] = Math.cos(nums[i]);
        }*/
        //new MathTransform(nums, 0, nums.length).fork();
        pool.invoke(new MathTransform(nums, 0, nums.length));
        System.out.println("功耗时=" + (System.currentTimeMillis() - longTime)/60);
        for (int i = 0; i < 10000; i++) {
            System.out.printf("%.4f", nums[i]);
            System.out.print(",");
            System.out.print(i + ";");
        }
    }
}