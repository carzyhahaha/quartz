package com.sy.quartz.demo.demo;

import com.sy.quartz.demo.demo.jobdetail.HelloJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;
import org.quartz.impl.matchers.KeyMatcher;

import javax.net.ssl.KeyManager;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.DateBuilder.*;

import static org.quartz.JobKey.*;
import static org.quartz.impl.matchers.KeyMatcher.*;
import static org.quartz.impl.matchers.GroupMatcher.*;
import static org.quartz.impl.matchers.AndMatcher.*;
import static org.quartz.impl.matchers.OrMatcher.*;
import static org.quartz.impl.matchers.EverythingMatcher.*;

public class Demo {


    @Test
    public void test1() {
        try {

            HolidayCalendar calendar = new HolidayCalendar();

            calendar.addExcludedDate(new Date());

            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

            Scheduler sched = schedFact.getScheduler();

            sched.start();

            sched.getListenerManager().addTriggerListener(new MyTriggerListener());

            JobDetail job = newJob(HelloJob.class)
                    .withIdentity("myJob", "group1")
                    // 添加jobDataMap 具体的任务执行时数据
                    .usingJobData("key1", "hello2")
                    .usingJobData("key2", "world")
                    .build();

            // simpleTrigger
            Trigger trigger = newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .startNow()
                    //.startAt(new Date()) 设定开始时间
                    //.endAt(new Date()) 设定结束时间
                    //.forJob("jobName", "jobGroup")/forJob(Job job) 可跟制定任务绑定,
                    .withSchedule(simpleSchedule()
                            // 间隔多少时间执行, 多种单位时间选择
                            .withIntervalInSeconds(1)
                            //重复次数，可以是0、正整数，以及常量SimpleTrigger.REPEAT_INDEFINITELY(等同repeatForever)
                            .withRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY)
                            /** 失败/错过应对策略(考英语水平了)
                             * MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY
                             * MISFIRE_INSTRUCTION_FIRE_NOW
                             * MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT
                             * MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT
                             * MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT
                             * MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT
                             */
                            .withMisfireHandlingInstructionIgnoreMisfires())
                    .build();


            /** cronTrigger
             * Seconds
             * Minutes
             * Hours
             * Day-of-Month 当月第几天
             * Month
             * Day-of-Week 星期几
             * Year (optional field)
             * , 多选
             * - 连续
             * * 通配 该位置全部时间生效
             * / 指定增量 例如 在'秒'字段 '3/21' 表示从第3秒开始每21秒执行一次
             * (只有 3+21*n<60, 并不是 (3+21*n)%60) 的时间有效 '/21'等同0,21,44
             * ? 你不想写 星期几时候使用
             * L 最后一天 结合 - 可指定偏移量 倒数?
             * W 指定最近给定日期的工作日
             * #  指定本月的第几个工作日 或 本月第几个星期x
             */
            Trigger trigger1 = newTrigger()
                    .withIdentity("cronTrigger", "gropu1")
                    .withSchedule(cronSchedule("* * * * * ? *"))
                    .build();


            // Tell quartz to schedule the job using our trigger
//            sched.scheduleJob(job, trigger);
            sched.scheduleJob(job, trigger1);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

class MyTriggerListener implements TriggerListener {

    @Override
    public String getName() {
        return "myTriggerListener";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        System.out.println(trigger.getKey() + "- fired" );
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        System.out.println(trigger.getKey() + "- misFired" );
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        System.out.println(trigger.getKey() + "- complete" );
    }
}

