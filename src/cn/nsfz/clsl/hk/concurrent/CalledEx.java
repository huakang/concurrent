package cn.nsfz.clsl.hk.concurrent;

import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CalledEx {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //����һ���̳߳�
	    ExecutorService pool = Executors.newFixedThreadPool(2); 
        //���������з���ֵ������ 
        Callable<String> c1 = new MyCallable("A",3000); 
        Callable<String> c2 = new MyCallable("B",1000); 
        //ִ�����񲢻�ȡFuture���� 
        Future<String> f1 = pool.submit(c1); 
        Future<String> f2 = pool.submit(c2); 
        System.out.println("1");
        //List<Future<String>> list = new ArrayList<Future<String>>();
        Vector<String> list = new Vector<String>();
        list.add(f1.get());
        list.add(f2.get());
        System.out.println("2");
        //��Future�����ϻ�ȡ����ķ���ֵ�������������̨ 
        System.out.println(">>>"+list.get(0)); 
        System.out.println(">>>"+list.get(1)); 
        //�ر��̳߳�
        pool.shutdown();
	    } 


	static class MyCallable implements Callable<String>{ 
		private String oid; 
		private int sleep;

		MyCallable(String oid,int sleep) { 
			this.oid = oid; 
			this.sleep = sleep;
		} 

		@Override 
		public String call() throws Exception { 
			Thread.sleep(sleep);
			return oid+"���񷵻ص�����"; 
		} 
	}
}
