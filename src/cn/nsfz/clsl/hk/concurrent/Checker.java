package cn.nsfz.clsl.hk;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Checker {
    public static final int EXPIRE_MIN = 5;
    
    public static final int REMOVE_LIMIT_PER_CHECK = 10;
    
    private final ReadWriteLock rwlock = new ReentrantReadWriteLock();
    
    private final Lock lock = new ReentrantLock();

    private final LinkedList<Timer> timeLine = new LinkedList<Timer>();
    
    private final Map<String,Timer> exists = new HashMap<String,Timer>();
    
    public Timer check(String key) throws Exception{
        //当前时间
        long current = System.currentTimeMillis();
        //过期时间
        long expires = current - EXPIRE_MIN*60*1000;
        //读锁加锁
        rwlock.readLock().lock();
        //移除过期的值
        for(int i=0;i<REMOVE_LIMIT_PER_CHECK;){
            Timer expiredtimer = timeLine.peek();
            if(expiredtimer==null||expiredtimer.getTime()>expires){
                break;
            }
            lock.lock();
            if(expiredtimer==exists.get(expiredtimer.getKey())){
                exists.remove(expiredtimer.getKey());
            }
            if(expiredtimer==timeLine.peek()){
                timeLine.pop();
                i++;
            }            
            lock.unlock();
        }
        //读锁解锁
        rwlock.readLock().unlock();
        //写锁加锁
        rwlock.writeLock().lock();
        //如果有重复值，抛出异常
        if(exists.get(key)!=null&&exists.get(key).getTime()>expires){
            //写锁解锁
            rwlock.writeLock().unlock();
            throw new Exception();
        }else{
            //检测没有重复值，将该值塞进
            Timer timer = new Timer(key,current);
            timeLine.offer(timer);
            exists.put(key, timer);
            //写锁解锁
            rwlock.writeLock().unlock();
            return timer;
        }
    }
    
    public Timer remove(Timer timer){
        //读锁加锁
        rwlock.readLock().lock();
        lock.lock();
        //检测到该timer仍存在，则移除该timer
        Timer existTimer = exists.get(timer.getKey());
        if(timer==existTimer){
            exists.remove(timer.getKey());
            timeLine.remove(timer);
        }
        lock.unlock();
        //读锁解锁
        rwlock.readLock().unlock();
        return existTimer;
    }
    
    private static class Timer{
        private String key;
        private long time;
        
        Timer(String key,long time){
            this.key = key;
            this.time = time;
        }

        public String getKey() {
            return key;
        }

        public long getTime() {
            return time;
        }
               
    }
    
}
