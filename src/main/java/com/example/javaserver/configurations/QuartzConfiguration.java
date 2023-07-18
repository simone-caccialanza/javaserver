package com.example.javaserver.configurations;

import com.example.javaserver.jobs.AutoWiringSpringBeanJobFactory;
import com.example.javaserver.jobs.TemperatureJob;
import org.quartz.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
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
        return DataSourceBuilder.create().url("jdbc:h2:mem:spring-quartz;INIT=RUNSCRIPT FROM 'classpath:/org/quartz/impl/jdbcjobstore/tables_h2.sql'") // Replace with your H2 database URL
                .driverClassName("org.h2.Driver")
                .username("sa")
                .password("").build();
    }

    @Bean
    public JobDetailFactoryBean myJobDetail() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(TemperatureJob.class);
        jobDetailFactoryBean.setName("Temperature1");
        jobDetailFactoryBean.setGroup("Temperature1");
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean myJobTrigger(JobDetail jobDetail) {
        SimpleTriggerFactoryBean triggerFactoryBean = new SimpleTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(jobDetail);
        triggerFactoryBean.setRepeatInterval(5000); // Run every 5 seconds
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
        schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
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
