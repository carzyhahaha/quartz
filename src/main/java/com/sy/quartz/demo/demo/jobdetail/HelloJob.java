package com.sy.quartz.demo.demo.jobdetail;

import org.quartz.*;

public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("吃屎啦你");
    }
}
