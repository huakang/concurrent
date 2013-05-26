package cn.nsfz.clsl.hk.concurrent;

import java.util.concurrent.Executors; 
import java.util.concurrent.ExecutorService; 

public class ExecutorEx {

    /** 
	* Java�̣߳��̳߳�- 
	*/ 
	public static void main(String[] args) { 
	    //����һ�������ù̶��߳������̳߳� 
	    ExecutorService pool = Executors.newFixedThreadPool(2); 
        
        //�����ڷǹ̶���С�Ľ�С�߳�
        //Executors.newCachedThreadPool(); //http://blog.csdn.net/cutesource/article/details/6061229
        
        //�����߳�
        //Executors.newSingleThreadExecutor();
        
        //����һ����С���޵��̳߳ء����̳߳�֧�ֶ�ʱ�Լ�������ִ�����������
        //Executors.newScheduledThreadPool(2);
        
        //����ʵ����Runnable�ӿڶ���Thread����ȻҲʵ����Runnable�ӿ� 
        Thread t1 = new MyThread(); 
        Thread t2 = new MyThread(); 
        Thread t3 = new MyThread(); 
        Thread t4 = new MyThread(); 
        Thread t5 = new MyThread(); 
        //���̷߳�����н���ִ�� 
        pool.execute(t1); 
        pool.execute(t2); 
        pool.execute(t3); 
        pool.execute(t4); 
        pool.execute(t5); 
        //�ر��̳߳� 
        pool.shutdown(); 
    } 

	static class MyThread extends Thread{ 
	        @Override 
	        public void run() { 
	                System.out.println(Thread.currentThread().getName()+"����ִ�С�����"); 
	        } 
	}
	
}
