package com.example.user.softkeyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText et;
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText) findViewById(R.id.et);
        pwd = (EditText) findViewById(R.id.pwd);


        PopwinSoftkeyboard.getInstance(this).initEditText(et,false);
        PopwinSoftkeyboard.getInstance(this).initEditText(pwd,true);

    }
}
