package com.dunkeng.tools.model;

/**
 * Created by Damon.Han on 2017/3/29 0029.
 *
 * @author Damon
 */

public class ToolObj {
    public ToolObj() {
    }

    public ToolObj(String title, int drawable) {
        this.title = title;
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String title;
    public int drawable;
}
