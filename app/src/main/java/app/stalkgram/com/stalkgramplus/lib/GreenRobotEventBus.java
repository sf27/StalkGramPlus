package app.stalkgram.com.stalkgramplus.lib;

import app.stalkgram.com.stalkgramplus.lib.base.EventBus;

/**
 * Created by ykro.
 */
public class GreenRobotEventBus implements EventBus {
    org.greenrobot.eventbus.EventBus eventBus;

    public GreenRobotEventBus() {
        eventBus = org.greenrobot.eventbus.EventBus.getDefault();
    }

    public static GreenRobotEventBus getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    public void post(Object event) {
        eventBus.post(event);
    }

    private static class SingletonHolder {
        private static final GreenRobotEventBus INSTANCE = new GreenRobotEventBus();
    }
}
