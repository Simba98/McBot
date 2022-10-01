package cn.evolvefield.mods.botapi.sdk.model.event;

import cn.evolvefield.mods.botapi.sdk.listener.EnableListener;
import cn.evolvefield.mods.botapi.sdk.listener.Listener;
import cn.evolvefield.mods.botapi.sdk.util.ListenerUtils;
import cn.evolvefield.mods.botapi.sdk.util.json.util.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/14 16:58
 * Version: 1.0
 */
@SuppressWarnings("unused")
public class EventDispatchers implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(EventDispatchers.class);

    //存储监听器对象
    protected List<Listener> listenerList = new ArrayList<>();

    //缓存类型与监听器的关系
    protected Map<Class<? extends Event>, List<Listener>> cache = new ConcurrentHashMap<>();

    //线程池 用于并发执行队列中的任务
    protected ExecutorService service;

    protected BlockingQueue<String> queue;

    private Listener<String> messageListener;

    public EventDispatchers(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void addListener(Listener listener) {
        listenerList.add(listener);
    }

    public void start() {
        start(1);
    }

    public void start(Integer threadCount) {
        if (threadCount <= 0) {
            threadCount = 1;
        }
        service = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            service.submit(this);
        }
    }

    public void stop(){
        service.shutdown();
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.runTask();
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        }
    }

    /**
     * 执行任务
     */
    protected void runTask() {
        String message = this.getTask();//获取消息
        if (message == null) {
            return;
        }
        Class<? extends Event> messageType = ListenerUtils.getMessageType(message);//获取消息对应的实体类型
        if (messageType == null) {
            return;
        }
        if (this.messageListener != null){
            this.messageListener.onMessage(message);
        }
        log.debug("接收到上报消息：{}", messageType);
        log.debug("接收到上报消息内容：{}", message);
        Event bean = GsonUtil.strToJavaBean(message, messageType);//将消息反序列化为对象
        List<Listener> executeListener = (executeListener = cache.get(messageType)) == null ?
                getMethod(messageType) : executeListener;//检查缓存

        for (Listener listener : executeListener) {
            if (listener.valider(bean)) {
                listener.onMessage(bean);//调用监听方法
            }
        }
        if (executeListener != null) {
            cache.put(messageType, executeListener);
        }
    }

    /**
     * 从队列中获取任务
     *
     * @return
     */
    protected String getTask() {
        try {
            return this.queue.take();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 获取能处理消息类型的处理器
     *
     * @param messageType
     * @return
     */
    protected List<Listener> getMethod(Class messageType) {
        List<Listener> listeners = new ArrayList<>();
        for (Listener listener : listenerList) {
            try {
                try {
                    listener.getClass().getMethod("onMessage", messageType);//判断是否支持该类型
                } catch (NoSuchMethodException e) {
                    continue;//不支持则跳过
                }
                if(listener instanceof EnableListener){
                    EnableListener enableListener = (EnableListener)listener;
                    if (!enableListener.enable()) {//检测是否开启该插件
                        continue;
                    }
                }
                listeners.add(listener);//开启后添加入当前类型的插件
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listeners;
    }

    public List<Listener> getListenerList() {
        return listenerList;
    }

    /**
     * 清除类型缓存
     */
    public void cleanCache() {
        cache.clear();
    }

    public void setMessageListener(Listener<String> messageListener) {
        this.messageListener = messageListener;
    }

    public Listener<String> getMessageListener() {
        return messageListener;
    }
}
