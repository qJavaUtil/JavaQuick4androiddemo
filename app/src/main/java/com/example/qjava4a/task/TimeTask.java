package com.example.qjava4a.task;

import blxt.qjava.autovalue.inter.Component;


import java.util.Date;

@Component
public class TimeTask{

    int i = 0;

//    @Scheduled(cron="0/1 * * * * ?")
    public void taks1(){
        System.out.println("taks1: " + new Date() + " " +  i++);
    }

 //   @Scheduled(cron="0/5 * * * * ?")
    public void taks2(){
        System.out.println("taks2: " + new Date() + " " +  i++);
    }

}
