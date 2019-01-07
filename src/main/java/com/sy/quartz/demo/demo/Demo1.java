package com.sy.quartz.demo.demo;

import com.sy.quartz.demo.demo.jobdetail.HelloJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.DateBuilder.*;

@RunWith(JUnit4.class)
public class Demo1 {


    @Test
    public void test1() {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();


            JobDetail jd = newJob(HelloJob.class)
                    .withIdentity("helloJob", "testGroup")
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity("helloTrigger", "testGroup")
                    .startNow()
                    .withSchedule(simpleSchedule().withIntervalInSeconds(10)
                    .withRepeatCount(3))
                    .build();
            scheduler.scheduleJob(jd, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}
