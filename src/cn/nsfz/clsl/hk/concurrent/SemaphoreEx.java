package cn.nsfz.clsl.hk.concurrent;

import java.util.concurrent.Semaphore;

public class SemaphoreEx implements Runnable{
	
	private Semaphore sp;	
	private int need = 1;
	
	SemaphoreEx(Semaphore sp,int need){
		this.sp = sp;
		this.need = need;
	}
	
	public void run() { 
	     try { 
             //从此信号量获取给定数目的许可 
    	 	 sp.acquire(need); 
             System.out.println("成功获取了" + need + "个许可！"); 
	     } catch (InterruptedException e) { 
	         e.printStackTrace(); 
	     } finally { 
	         //释放给定数目的许可，将其返回到信号量。 
	    	 sp.release(need); 
	         System.out.println("释放了" + need + "个许可！"); 
	     } 
	 } 
}
