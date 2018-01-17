package com.oklib.widget.imageloader.glide.listener;

/**
 * Created by Damon.Han on 2018/1/17 0017.
 *
 * @author Damon
 */

public interface ProgressLoadListener {

    void update(int bytesRead, int contentLength);

    void onException();

    void onResourceReady();
}
