package com.q.util;

/**
 * @author anonymity-0
 * @date 2020/12/14 - 0:38
 */
public class GameFunction {

    public static long getSleepTimeByLevel(int level){
        long sleep = (-40*level+740);
        sleep =sleep<100?100:sleep;
        return sleep;
    }
}
