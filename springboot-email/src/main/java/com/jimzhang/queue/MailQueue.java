package com.jimzhang.queue;

import com.jimzhang.model.Email;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author jimzhang
 * @version V1.0.0
 * @description 邮件队列
 * @home <>https://segmentfault.com/u/itzhangjm</>
 * @date 2017-11-01 13:39
 */
public class MailQueue {
    /**
     * 队列大小
     */
    static final int QUEUE_MAX_SIZE = 1000;

    /**
     * LinkedBlockingQueue 为一个阻塞队列是线程安全的，同时具有先进先出等特性，是作为生产者消费者的首选。
     * 默认最大Integer.MAX_VALUE
     * 与它对应的是非阻塞队列 ConcurrentLinkedQueue
     */
    static BlockingQueue<Email> blockingQueue = new LinkedBlockingQueue<Email>(QUEUE_MAX_SIZE);

    /**
     * 私有的默认构造子，保证外界无法直接实例化
     */
    private MailQueue() {
    };

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * <p>
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static MailQueue queue = new MailQueue();
    }

    /**
     * 单例队列
     * @return
     */
    public static MailQueue getMailQueue() {
        return SingletonHolder.queue;
    }

    /**
     * 生产入队
     * @param mail
     * @throws InterruptedException
     */
    public void produce(Email mail) throws InterruptedException {
        // put方法在队列满的时候会阻塞直到有队列成员被消费
        blockingQueue.put(mail);
    }

    /**
     * 消费出队
     * @return
     * @throws InterruptedException
     */
    public Email consume() throws InterruptedException {
        // take方法在队列空的时候会阻塞，直到有队列成员被放进来
        return blockingQueue.take();
    }

    /**
     * 获取队列大小
     * @return
     */
    public int size() {
        return blockingQueue.size();
    }
}
