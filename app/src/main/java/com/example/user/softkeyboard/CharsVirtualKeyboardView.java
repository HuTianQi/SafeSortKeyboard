package com.example.user.softkeyboard;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 数字虚拟键盘
 */
public class CharsVirtualKeyboardView extends RelativeLayout {

    private final TextView abc_view;
    private final ImageView img_back;
    Context context;

    //因为就6个输入框不会变了，用数组内存申请固定空间，比List省空间（自己认为）
    private GridView gridView;    //用GrideView布局键盘，其实并不是真正的键盘，只是模拟键盘的功能

    private ArrayList<Map<String, String>> valueList;    //有人可能有疑问，为何这里不用数组了？
    //因为要用Adapter中适配，用数组不能往adapter中填充

    private RelativeLayout layoutBack;
    private EditText textAmount;
    private Animation enterAnim;
    private Animation exitAnim;
    private String webInputId;
    private WebView mWebView;

    public CharsVirtualKeyboardView(Context context) {
        this(context, null);
    }

    public CharsVirtualKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        View view = View.inflate(context, R.layout.layout_virtual_keyboard_chars, null);

        valueList = new ArrayList<>();

        layoutBack = (RelativeLayout) view.findViewById(R.id.layoutBack);
        abc_view = (TextView) view.findViewById(R.id.abc_view);
        img_back= (ImageView) view.findViewById(R.id.img_back);

        gridView = (GridView) view.findViewById(R.id.gv_keybord);

        initValueList();

        setupView();

        addView(view);      //必须要，不然不显示控件
    }

    public ImageView getImgBack() {
        return img_back;
    }

    public TextView getAbc_view(){return abc_view;}

    public ArrayList<Map<String, String>> getValueList() {
        return valueList;
    }

    private void initValueList() {

        // 初始化按钮上应该显示的数字
        for (int i = 33; i <= 127; i++) {

            if ((i>=48 && i<=57)||(i>=65 && i<=90)||(i>=97 && i<=121)){
                continue;
            }

            Map<String, String> map = new HashMap<>();
            map.put("name", Character.toString((char) i));
            valueList.add(map);
        }
    }



    private void setupView() {

        CharKeyBoardAdapter charKeyBoardAdapter = new CharKeyBoardAdapter(context, valueList);
        gridView.setAdapter(charKeyBoardAdapter);
    }

    public void initView(EditText editText) {
        textAmount = editText;
        enterAnim = AnimationUtils.loadAnimation(context, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(context, R.anim.push_bottom_out);
        gridView.setOnItemClickListener(onItemClickListener);
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


    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            if (textAmount!=null) {
                String amount = textAmount.getText().toString().trim();
                amount = amount + valueList.get(position).get("name");

                textAmount.setText(amount);

                Editable ea = textAmount.getText();
                textAmount.setSelection(ea.length());
            }else if (mWebView!=null){
                AndroidBridge bridge = (AndroidBridge) mWebView.getTag();
                bridge.addInfoToJs(webInputId,valueList.get(position).get("name"));
            }

        }
    };

    public void initWebId(String inputId, WebView webView) {
        webInputId = inputId;
        mWebView = webView;
        enterAnim = AnimationUtils.loadAnimation(context, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(context, R.anim.push_bottom_out);

        gridView.setOnItemClickListener(onItemClickListener);
    }
}
