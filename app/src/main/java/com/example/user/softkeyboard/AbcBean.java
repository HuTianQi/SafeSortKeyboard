package com.example.user.softkeyboard;

import android.view.View;

/**
 * Created by user on 17-6-26.
 */

public class AbcBean {
    private Integer mId;
    private View mView;
    private String mValue;


    public AbcBean(Integer id,View view,String value){
        setmId(id);
        setmView(view.findViewById(id));
        setmValue(value);
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public View getmView() {
        return mView;
    }

    public void setmView(View mView) {
        this.mView = mView;
        mView.setTag(this);
    }

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }

}
