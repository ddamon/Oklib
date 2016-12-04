package com.dunkeng.utils.network;

/**
 * Created by Tamic on 2016-08-02.
 */
public abstract class DownLoadCallBack {

    public void onStart(){}

    public void onCancel(){}

    public void onCompleted(){}


    /** Note : the Fun run not MainThred
     * @param e
     */
    abstract public void onError(MThrowable e);

    public void onProgress(long fileSizeDownloaded){}

    /**  Note : the Fun run UIThred
     * @param path
     * @param name
     * @param fileSize
     */
    abstract public void onSucess(String path, String name, long fileSize);
}
