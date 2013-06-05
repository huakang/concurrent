package cn.nsfz.clsl.hk.uuid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Checker {
    public static final int EXPIRE_MIN = 5;
    
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final List<Timer> timeLine = new LinkedList<Timer>();
    
    private final Map<String,Timer> exists = new HashMap<String,Timer>();
    
    public Timer check(String key) throws Exception{
        //当前时间
        long current = System.currentTimeMillis();
        //过期时间
        long expires = current - EXPIRE_MIN*60*1000;
        //读锁加锁
        lock.readLock().lock();
        //移除过期的值
        List<Timer> expiredtimers = new ArrayList<Timer>();
        for(Timer timer:timeLine){
            if(timer.getTime()<=expires){
                expiredtimers.add(timer);
            }else{
                break;
            }
        }
        for(Timer timer:expiredtimers){
            exists.remove(timer.getKey());
            timeLine.remove(timer);
        }
        //读锁解锁
        lock.readLock().unlock();
        //写锁加锁
        lock.writeLock().lock();
        //如果有重复值，抛出异常
        if(exists.get(key)!=null&&exists.get(key).getTime()>expires){
            //写锁解锁
            lock.writeLock().unlock();
            throw new Exception();
        }else{
            //检测没有重复值，将该值塞进    
            Timer timer = new Timer(key,current);
            timeLine.add(timer);
            exists.put(key, timer);
            //写锁解锁
            lock.writeLock().unlock();
            return timer;
        }        
    }
    
    public void remove(Timer timer){
        //读锁加锁
        lock.readLock().lock();
        Timer existTimer = exists.get(timer.getKey());
        //检测到该timer仍存在，则移除该timer
        if(existTimer!=null&&timer==existTimer){
            exists.remove(timer.getKey());
            timeLine.remove(timer);
        }        
        //读锁解锁
        lock.readLock().unlock();
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
