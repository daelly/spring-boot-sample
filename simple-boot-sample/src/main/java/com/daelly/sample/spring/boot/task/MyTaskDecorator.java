package com.daelly.sample.spring.boot.task;

import com.alibaba.ttl.TtlRunnable;
import org.springframework.core.task.TaskDecorator;

public class MyTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        return TtlRunnable.get(runnable);
    }
}
