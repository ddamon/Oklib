package com.oklib.widget.imageloader.glide.listener;

/**
 * Created by Damon.Han on 2018/1/17 0017.
 *
 * @author Damon
 */

public interface ProgressLoadListener {

    void update(long bytesRead, long contentLength);

    void onException(Exception e);

    void onResourceReady();
}
