package com.cegeka.project.infrastructure.debounce;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import static java.time.Instant.now;

@Component
@Log4j2
public class Debouncer {

    private final Map<Object, ScheduledFuture> futureMap;
    private final TaskScheduler taskScheduler;

    public Debouncer(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
        this.futureMap = new ConcurrentHashMap<>();
    }

    public void debounce(Object key, Runnable runnable, long delay, boolean interruptWhenRunning) {
        ScheduledFuture previousFuture = futureMap.put(key, taskScheduler.schedule(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                log.error(() -> "Exception in debounced method: " + key, e);
                throw e;
            } finally {
                futureMap.remove(key);
            }
        }, now().plusMillis(delay)));
        if (previousFuture != null) {
            previousFuture.cancel(interruptWhenRunning);
        }
    }

    public void debounce(Object key, Runnable runnable, long delay) {
        debounce(key, runnable, delay, false);
    }
}
