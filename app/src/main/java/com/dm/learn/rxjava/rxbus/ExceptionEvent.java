package com.dm.learn.rxjava.rxbus;

import com.oklib.utils.rxbus.IEvent;

/**
 * Created by Damon.Han on 2019/2/21 0021.
 *
 * @author Damon
 */
public class ExceptionEvent implements IEvent {
    public ExceptionEvent(String text) {
        this.text = text;
    }

    public String text = "";
}
