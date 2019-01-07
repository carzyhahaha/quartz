package com.sy.quartz.demo.demo.jobdetail;

import com.sy.quartz.demo.demo.Config;
import org.quartz.*;

import javax.naming.Context;

public class HelloJob implements Job {

    String key1;
    String key2;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        JobDataMap map = jobExecutionContext.getJobDetail().getJobDataMap();
        System.out.println(key1 +" " + key2);
    }


    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }
}
