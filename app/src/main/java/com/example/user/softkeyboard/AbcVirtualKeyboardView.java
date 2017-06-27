package com.example.user.softkeyboard;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 数字虚拟键盘
 */
public class AbcVirtualKeyboardView extends RelativeLayout implements View.OnClickListener {

    private final TextView num_view;
    private final ImageView img_back;
    Context context;

    private RelativeLayout layoutBack;
    private EditText textAmount;
    private Animation  enterAnim ;
    private Animation exitAnim ;
    private HashMap<Integer,AbcBean> viewMap=new HashMap<Integer,AbcBean>();
    private Boolean isUpCase=false;

    public AbcVirtualKeyboardView(Context context) {
        this(context, null);
    }

    public AbcVirtualKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        View view = View.inflate(context, R.layout.layout_virtual_keyboard_abc, null);


        layoutBack = (RelativeLayout) view.findViewById(R.id.layoutBack);
        num_view = (TextView) view.findViewById(R.id.num_view);
        img_back= (ImageView) view.findViewById(R.id.img_back);

        setupView(view);

        addView(view);      //必须要，不然不显示控件
    }

    private void setupView(View view) {

        view.findViewById(R.id.imgDelete).setOnClickListener(this);

        viewMap.put(R.id.btn_a,new AbcBean(R.id.btn_a,view,"a"));
        viewMap.put(R.id.btn_b,new AbcBean(R.id.btn_b,view,"b"));
        viewMap.put(R.id.btn_c,new AbcBean(R.id.btn_c,view,"c"));
        viewMap.put(R.id.btn_d,new AbcBean(R.id.btn_d,view,"d"));
        viewMap.put(R.id.btn_e,new AbcBean(R.id.btn_e,view,"e"));
        viewMap.put(R.id.btn_f,new AbcBean(R.id.btn_f,view,"f"));
        viewMap.put(R.id.btn_g,new AbcBean(R.id.btn_g,view,"g"));
        viewMap.put(R.id.btn_h,new AbcBean(R.id.btn_h,view,"h"));
        viewMap.put(R.id.btn_i,new AbcBean(R.id.btn_i,view,"i"));
        viewMap.put(R.id.btn_j,new AbcBean(R.id.btn_j,view,"j"));
        viewMap.put(R.id.btn_k,new AbcBean(R.id.btn_k,view,"k"));
        viewMap.put(R.id.btn_l,new AbcBean(R.id.btn_l,view,"l"));
        viewMap.put(R.id.btn_m,new AbcBean(R.id.btn_m,view,"m"));
        viewMap.put(R.id.btn_n,new AbcBean(R.id.btn_n,view,"n"));
        viewMap.put(R.id.btn_o,new AbcBean(R.id.btn_o,view,"o"));
        viewMap.put(R.id.btn_p,new AbcBean(R.id.btn_p,view,"p"));
        viewMap.put(R.id.btn_q,new AbcBean(R.id.btn_q,view,"q"));
        viewMap.put(R.id.btn_r,new AbcBean(R.id.btn_r,view,"r"));
        viewMap.put(R.id.btn_s,new AbcBean(R.id.btn_s,view,"s"));
        viewMap.put(R.id.btn_t,new AbcBean(R.id.btn_t,view,"t"));
        viewMap.put(R.id.btn_u,new AbcBean(R.id.btn_u,view,"u"));
        viewMap.put(R.id.btn_v,new AbcBean(R.id.btn_v,view,"v"));
        viewMap.put(R.id.btn_w,new AbcBean(R.id.btn_w,view,"w"));
        viewMap.put(R.id.btn_x,new AbcBean(R.id.btn_x,view,"x"));
        viewMap.put(R.id.btn_y,new AbcBean(R.id.btn_y,view,"y"));
        viewMap.put(R.id.btn_z,new AbcBean(R.id.btn_z,view,"z"));

        for (Map.Entry<Integer, AbcBean> entry : viewMap.entrySet()) {
            AbcBean abcBean = entry.getValue();
            abcBean.getmView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AbcBean bean = (AbcBean) v.getTag();
                        editWords(bean);
                    }
                });
            }
    }

    private void editWords(AbcBean bean) {
       if (textAmount!=null){
           String s = textAmount.getText().toString().trim();
           textAmount.setText(s+(isUpCase? bean.getmValue().toUpperCase():bean.getmValue().toLowerCase()));
           textAmount.setSelection( textAmount.getText().toString().trim().length());
       }
    }


    public ImageView getImgBack() {
        return img_back;
    }

    public TextView getNum_view(){return num_view;}





    public void initView(EditText editText) {

        textAmount = editText;

        enterAnim = AnimationUtils.loadAnimation(context, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(context, R.anim.push_bottom_out);


    }

    public void disMiss(){
        startAnimation(exitAnim);
        setFocusable(false);
        setFocusableInTouchMode(false);
        setVisibility(View.GONE);
    }

    public void show(){
        startAnimation(enterAnim);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setVisibility(View.VISIBLE);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgDelete:
                String amount = textAmount.getText().toString().trim();
                if (amount.length() > 0) {
                    amount = amount.substring(0, amount.length() - 1);
                    textAmount.setText(amount);

                    Editable ea = textAmount.getText();
                    textAmount.setSelection(ea.length());
                }
                break;

        }

    }
}
