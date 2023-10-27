package com.example.javaserver.configurations;

import com.example.javaserver.jobs.AutoWiringSpringBeanJobFactory;
import com.example.javaserver.jobs.TemperatureJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
public class QuartzConfiguration {

    @Bean
    @QuartzDataSource
    public DataSource quartzDataSource() {
        return DataSourceBuilder.create().url("jdbc:h2:~/javaserver;MODE=Oracle") // Replace with your H2 database URL
                .driverClassName("org.h2.Driver")
                .username("sa")
                .password("").build();
    }

    @Bean
    public JobDetailFactoryBean jobDetail(
            @Value("${jobs.temperature-job.name}") String jobName,
            @Value("${jobs.temperature-job.group}") String jobGroup
    ) {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(TemperatureJob.class);
        jobDetailFactoryBean.setName(jobName);
        jobDetailFactoryBean.setGroup(jobGroup);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean jobTrigger(
            JobDetail jobDetail,
            @Value("${jobs.temperature-job.repeat-interval}") int repeatInterval
    ) {
        SimpleTriggerFactoryBean triggerFactoryBean = new SimpleTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(jobDetail);
        triggerFactoryBean.setRepeatInterval(repeatInterval); // Run every 5 seconds
        triggerFactoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        return triggerFactoryBean;
    }

    @Bean
    public Scheduler scheduler(Trigger trigger, JobDetail job, SchedulerFactoryBean factory)
            throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        scheduler.clear();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
        return scheduler;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(Trigger trigger, JobDetail job, DataSource quartzDataSource) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setJobDetails(job);
        schedulerFactory.setTriggers(trigger);
        schedulerFactory.setDataSource(quartzDataSource);
        return schedulerFactory;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        return new AutoWiringSpringBeanJobFactory();
    }

}
