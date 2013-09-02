package net.panda.backend.runners;

import net.panda.service.ScheduledService;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class InstanceRunner implements ScheduledService, Runnable {

    @Override
    public Runnable getTask() {
        return this;
    }

    /**
     * TODO Every 5 seconds (configurable)
     */
    @Override
    public Trigger getTrigger() {
        return new PeriodicTrigger(5, TimeUnit.SECONDS);
    }

    /**
     * Runs the instances
     */
    @Override
    public void run() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
