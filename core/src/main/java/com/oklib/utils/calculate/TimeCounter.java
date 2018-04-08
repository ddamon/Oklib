package com.oklib.utils.calculate;

import com.oklib.utils.Logger.Logger;

/**
 * Time Counter.
 */
public class TimeCounter {

    private long t;

    public TimeCounter() {
        start();
    }

    /**
     * Count start.
     */
    public long start() {
        t = System.currentTimeMillis();
        return t;
    }

    /**
     * Get duration and restart.
     */
    public long durationRestart() {
        long now = System.currentTimeMillis();
        long d = now - t;
        t = now;
        return d;
    }

    /**
     * Get duration.
     */
    public long duration() {
        return System.currentTimeMillis() - t;
    }

    /**
     * Print duration.
     */
    public void printDuration(String tag) {
        Logger.i(tag + " :  " + duration());
    }

    /**
     * Print duration.
     */
    public void printDurationRestart(String tag) {
        Logger.i(tag + " :  " + durationRestart());
    }
}