package cn.nsfz.clsl.hk.concurrent;

import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;

public class LockEx implements Runnable{

	//������
	private Lock myLock;
	 //= new ReentrantLock(); 
	
	public LockEx(Lock myLock){
		this.myLock = myLock;
	}
	
	public void run() { 
        //��ȡ�� 
        this.myLock.lock(); 
        
        System.out.print("do sth");
        //�ͷ���
        this.myLock.unlock(); 
	} 
	
}
