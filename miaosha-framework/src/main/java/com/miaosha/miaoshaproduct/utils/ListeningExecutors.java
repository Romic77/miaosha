package com.miaosha.miaoshaproduct.utils;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class ListeningExecutors {

    private int queueCapacity = 2147483647;


    /**
     * 阻塞队列设置
     *
     * @param queueCapacity
     * @return
     */
    protected BlockingQueue<Runnable> createQueue(int queueCapacity) {
        return (BlockingQueue) (queueCapacity > 0 ? new LinkedBlockingQueue(queueCapacity) : new SynchronousQueue());
    }

    /**
     * @Author: chenqi
     * @Description: 创建Executor
     * @Date 17:27 2019-07-15
     */
    public ExecutorService createExecutor() {
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        int maximumPoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;

        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 5000, TimeUnit.MILLISECONDS
                , createQueue(queueCapacity), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    }


    /**
     * 创建guava公用线程池
     *
     * @return
     */
    @Bean
    public ListeningExecutorService createListeningExecutorService() {
        // 创建线程池
        ListeningExecutorService listeningExecutorService = MoreExecutors.
                listeningDecorator(createExecutor());

        return listeningExecutorService;
    }

}