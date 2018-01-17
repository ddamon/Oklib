package com.oklib.widget.imageloader.glide.listener;
interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
