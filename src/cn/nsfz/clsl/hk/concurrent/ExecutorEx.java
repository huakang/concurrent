package cn.nsfz.clsl.hk.concurrent;

import java.util.concurrent.Executors; 
import java.util.concurrent.ExecutorService; 

public class ExecutorEx {

    /** 
	* Java线程：线程池- 
	*/ 
	public static void main(String[] args) { 
	    //创建一个可重用固定线程数的线程池 
	    ExecutorService pool = Executors.newFixedThreadPool(2); 
        
        //适用于非固定大小的较小线程
        //Executors.newCachedThreadPool(); //http://blog.csdn.net/cutesource/article/details/6061229
        
        //单个线程
        //Executors.newSingleThreadExecutor();
        
        //创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。
        //Executors.newScheduledThreadPool(2);
        
        //创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口 
        Thread t1 = new MyThread(); 
        Thread t2 = new MyThread(); 
        Thread t3 = new MyThread(); 
        Thread t4 = new MyThread(); 
        Thread t5 = new MyThread(); 
        //将线程放入池中进行执行 
        pool.execute(t1); 
        pool.execute(t2); 
        pool.execute(t3); 
        pool.execute(t4); 
        pool.execute(t5); 
        //关闭线程池 
        pool.shutdown(); 
    } 

	static class MyThread extends Thread{ 
	        @Override 
	        public void run() { 
	                System.out.println(Thread.currentThread().getName()+"正在执行。。。"); 
	        } 
	}
	
}
