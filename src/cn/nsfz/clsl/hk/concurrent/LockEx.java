package cn.nsfz.clsl.hk.concurrent;

import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;

public class LockEx implements Runnable{

	//锁对象
	private Lock myLock;
	 //= new ReentrantLock(); 
	
	public LockEx(Lock myLock){
		this.myLock = myLock;
	}
	
	public void run() { 
        //获取锁 
        this.myLock.lock(); 
        
        System.out.print("do sth");
        //释放锁
        this.myLock.unlock(); 
	} 
	
}
