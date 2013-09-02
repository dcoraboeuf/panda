package net.panda.backend.runners;

import net.panda.backend.dao.InstanceDao;
import net.panda.backend.dao.model.TInstance;
import net.panda.service.ScheduledService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Component
public class InstanceRunner implements ScheduledService, Runnable {

    private final Logger logger = LoggerFactory.getLogger(InstanceRunner.class);
    private final InstanceDao instanceDao;

    @Autowired
    public InstanceRunner(InstanceDao instanceDao) {
        this.instanceDao = instanceDao;
    }

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
        // Gets the list of instance to start
        Collection<TInstance> instances = instanceDao.findStarted();
        // Starts each instance
        // TODO JDK8 Parallel execution
        for (TInstance instance : instances) {
            logger.debug("[instance] Starting {} - {} - {}", instance.getId(), instance.getPipeline(), instance.getParameters());
            // TODO Starts an agent
        }
    }
}
