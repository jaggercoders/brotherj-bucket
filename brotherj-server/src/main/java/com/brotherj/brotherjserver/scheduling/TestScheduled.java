package com.brotherj.brotherjserver.scheduling;

import com.brotherj.brotherjutil.scheduling.DistributeScheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * description：
 *
 * @author wangjie
 */
@Component
public class TestScheduled {

//    @Scheduled(cron = "0 0/1 * * * ? ")
//    public void scheduleTaskUsingCronExpression() {
//
//        long now = System.currentTimeMillis() / 1000;
//        System.out.println(
//                "schedule tasks using cron jobs - " + now);
//    }

//    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
//    public void scheduleFixedRateWithInitialDelayTask() {
//
//        long now = System.currentTimeMillis() / 1000;
//        System.out.println(
//                "Fixed rate task with one second initial delay - " + now);
//    }

    @DistributeScheduled(cron = "0 0/1 * * * ? ")
    public void scheduleTaskUsingCronExpression() {

        long now = System.currentTimeMillis() / 1000;
        System.out.println(
                "schedule tasks using cron jobs - " + now);
    }

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(1553850480015l), ZoneId.systemDefault());
        System.out.println(localDateTime.toString());
    }
}
