package com.miaosha.miaoshaproduct.redisson;

import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public interface DistributedLocker {
    /**
     * 锁名称前缀
     */
    String LOCK_KEY_PREFIX_KEY = "distributed:keys:";



    /**
     * 可重入锁
     *
     * @param key
     *          锁名称
     * @return
     *          锁对象
     */
    RLock lock(String key);

    /**
     * 公平锁
     *
     * @param key
     *          锁名称
     * @return
     *          锁对象
     */
    RLock fairLock(String key);

    /**
     * 读写锁
     *
     * @param key
     *          锁名称
     * @return
     *          锁对象
     */
    RReadWriteLock readWriteLock(String key);





    /**
     * 在锁内执行业务
     *
     * @param key
     *          锁名称
     * @param supplier
     *          具体业务逻辑
     * @param <T>
     * @return
     *          操作结果
     */
    <T> T lock(String key, Supplier<T> supplier);

    /**
     * 可重入锁(设置锁的持有时间)
     *
     * @param key
     *          锁名称
     * @param leaseTime
     *          上锁后, leaseTime 后自动解锁
     * @param unit
     *          单位
     * @param supplier
     *          业务执行结果
     * @return
     *          锁对象
     * @param <T>
     */
    <T> T lock(String key, int leaseTime, TimeUnit unit, Supplier<T> supplier);



    /**
     * 尝试获取锁
     *
     * @param key
     *          锁名称
     * @param supplier
     *          具体业务逻辑
     * @return
     *          业务执行结果
     * @param <T>
     */
    <T> T tryLock(String key, Supplier<T> supplier);

    /**
     * 尝试获取锁(为加锁等待 waitTime unit时间)
     *
     * @param key
     *          锁名称
     * @param waitTime
     *          获取锁最长等待时间
     * @param unit
     *          单位
     * @param supplier
     *          具体业务逻辑
     * @return
     *          业务执行结果
     * @param <T>
     * @throws InterruptedException ex
     */
    <T> T tryLock(String key, long waitTime, TimeUnit unit, Supplier<T> supplier) 
            throws InterruptedException;

    /**
     * 尝试获取锁(为加锁等待 waitTime unit时间，并在加锁成功 leaseTime unit 后自动解开)
     *
     * @param key
     *          锁名称
     * @param waitTime
     *          获取锁最长等待时间
     * @param leaseTime
     *          上锁后, leaseTime 后自动解锁
     * @param unit
     *          单位
     * @param supplier
     *          具体业务逻辑
     * @return
     *          业务执行结果
     * @param <T>
     * @throws InterruptedException ex
     */
    <T> T trysLock(String key, long waitTime, int leaseTime, TimeUnit unit, Supplier<T> supplier) 
            throws InterruptedException;



    /**
     * 解锁
     *
     * @param lock
     *          锁对象
     */
    default void unlock(RLock lock) {
        if (lock != null && lock.isLocked()) {
            lock.unlock();
        }
    }
}
