package com.example.user.softkeyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;


/**
 * Created by WHF on 2016-11-29.
 */


public class PopwinSoftkeyboard extends PopupWindow  {

    private Activity mContext;
    private EditText textAmount;
    private final View popView;
    private NumVirtualKeyboardView vkb_num_view;
    private AbcVirtualKeyboardView vkb_abc_view;
    private CharsVirtualKeyboardView vkb_char_view;

    public static PopwinSoftkeyboard getInstance(Activity context) {

        return new PopwinSoftkeyboard(context);
    }


    private PopwinSoftkeyboard(Activity context) {
        mContext = context;
        popView = LayoutInflater.from(mContext).inflate(R.layout.pop_softkeyboard, null);
        initPopWindow();
        setContentView(popView);
    }
    public  int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    private void initPopWindow() {
        Display display = mContext.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);

        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(dip2px(mContext,260f));
//        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        //设置动画
        setAnimationStyle(R.style.popwin_anim_style);

//        setBackgroundDrawable(new ColorDrawable(0));

        //设置popwindow如果点击外面区域，便关闭
        setTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        update();

    }



    public PopwinSoftkeyboard initEditText(EditText et,Boolean isPwd){

        this.textAmount = et;

        // 设置不调用系统键盘
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            textAmount.setInputType(InputType.TYPE_NULL);
        } else {
           mContext.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(textAmount, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (isPwd){
            textAmount.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else {
            textAmount.setInputType(InputType.TYPE_NULL);
        }
        initKeyboard();

        textAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(v);
            }
        });
        textAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    show(v);
                }
            }
        });
        return this;
    }

    private void show(View parent) {
        if (isShowing()){
            return;
        }else {
            WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
            lp.gravity = Gravity.BOTTOM;
            mContext.getWindow().setAttributes(lp);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        }


    }
    private void initKeyboard() {
        //初始化数字键盘
        vkb_num_view = (NumVirtualKeyboardView) popView.findViewById(R.id.vkb_num_view);
        vkb_num_view.initView(textAmount);
        vkb_num_view.getImgBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        vkb_num_view.getAbc_view().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vkb_num_view.disMiss();
                vkb_abc_view.show();

            }
        });

        //初始化特殊字符键盘
        vkb_char_view = (CharsVirtualKeyboardView) popView.findViewById(R.id.vkb_char_view);
        vkb_char_view.initView(textAmount);
        vkb_char_view.getImgBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        vkb_char_view.getAbc_view().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vkb_char_view.disMiss();
                vkb_abc_view.show();

            }
        });

        //初始化字母键盘
        vkb_abc_view = (AbcVirtualKeyboardView) popView.findViewById(R.id.vkb_abc_view);
        vkb_abc_view.initView(textAmount);
        vkb_abc_view.getImgBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        vkb_abc_view.getNum_view().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vkb_abc_view.disMiss();
                vkb_num_view.show();
            }
        });
        vkb_abc_view.getChar_view().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vkb_abc_view.disMiss();
                vkb_char_view.show();
            }
        });
    }



}
