package com.example.javaserver.jobs;

import com.example.javaserver.services.TemperatureService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TemperatureJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(TemperatureJob.class);

    private TemperatureService temperatureService;

    @Value("${jobs.temperature-job.enabled}")
    private Boolean enabled;

    public TemperatureJob() {
        // Default constructor
    }

    @Autowired
    public void setTemperatureService(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (Boolean.TRUE.equals(enabled)) {
            logger.info("Temperature is: {}", temperatureService.getTemperature());
        } else {
            logger.debug("Temperature Job not enabled");
        }
    }
}
