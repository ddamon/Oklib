package com.dunkeng.base;

import com.dunkeng.data.repository.Repository;

/**
 * .
 */

public class CoreBaseRepository {
    @Override
    public Object clone() {
        Repository stu = null;
        try {
            stu = (Repository) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return stu;
    }
}
