package cn.evolvefield.mods.botapi.model.event;

import cn.evolvefield.mods.botapi.listener.Listener;
import cn.evolvefield.mods.botapi.model.event.global.Message;
import cn.evolvefield.mods.botapi.util.ListenerUtils;
import com.alibaba.fastjson2.JSONObject;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.Event;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    protected List<Listener<Message>> listenerList = new ArrayList<>();

    //缓存类型与监听器的关系
    protected Map<Class<? extends Message>, List<Listener<Message>>> cache = new ConcurrentHashMap<>();

    //线程池 用于并发执行队列中的任务
    protected ExecutorService service;

    protected BlockingQueue<String> queue;

    private Listener<String> messageListener;

    public EventDispatchers( BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void addListener(Listener<Message> listener) {
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

    @Override
    public void run() {
        while (true) {
            try {
                this.runTask();
            } catch (Exception e) {
                log.warn(e.toString());
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
        Class<? extends Message> messageType = ListenerUtils.getMessageType(message);//获取消息对应的实体类型
        if (messageType == null) {
            return;
        }
        if (this.messageListener != null){
            this.messageListener.onMessage(message);
        }
        log.debug("接收到上报消息：{}", messageType);
        Message bean = JSONObject.parseObject(message, messageType);//将消息反序列化为对象
        List<Listener<Message>> executeListener = (executeListener = cache.get(messageType)) == null ?
                getMethod(messageType) : executeListener;//检查缓存

        for (Listener<Message> listener : executeListener) {
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
            log.error(e.toString());
        }
        return null;
    }

    /**
     * 获取能处理消息类型的处理器
     *
     * @param messageType
     * @return
     */
    protected List<Listener<Message>> getMethod(Class messageType) {
        List<Listener<Message>> listeners = new ArrayList<>();
        for (Listener<Message> listener : listenerList) {
            try {
                try {
                    listener.getClass().getMethod("onMessage", messageType);//判断是否支持该类型
                } catch (NoSuchMethodException e) {
                    continue;//不支持则跳过
                }
                listeners.add(listener);//开启后添加入当前类型的插件
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listeners;
    }

    public List<Listener<Message>> getListenerList() {
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
