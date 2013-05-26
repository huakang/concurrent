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
             //�Ӵ��ź�����ȡ������Ŀ����� 
    	 	 sp.acquire(need); 
             System.out.println("�ɹ���ȡ��" + need + "����ɣ�"); 
	     } catch (InterruptedException e) { 
	         e.printStackTrace(); 
	     } finally { 
	         //�ͷŸ�����Ŀ����ɣ����䷵�ص��ź����� 
	    	 sp.release(need); 
	         System.out.println("�ͷ���" + need + "����ɣ�"); 
	     } 
	 } 
}
