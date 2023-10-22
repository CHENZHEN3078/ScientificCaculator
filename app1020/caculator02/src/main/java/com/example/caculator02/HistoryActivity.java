package com.example.caculator02;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.caculator02.entity.equation;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.text1).setOnClickListener(this);
        findViewById(R.id.text2).setOnClickListener(this);
        findViewById(R.id.text3).setOnClickListener(this);
        findViewById(R.id.text4).setOnClickListener(this);
        findViewById(R.id.text5).setOnClickListener(this);
        findViewById(R.id.text6).setOnClickListener(this);
        findViewById(R.id.text7).setOnClickListener(this);
        findViewById(R.id.text8).setOnClickListener(this);
        findViewById(R.id.text9).setOnClickListener(this);
        findViewById(R.id.text10).setOnClickListener(this);
    }

    private int num=0;
    public static String memory="";
    @SuppressLint("Range")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn1) {
            Cursor cursor = getContentResolver().query(EquationInfoContent.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    equation info = new equation();
                    //info.id = cursor.getInt(cursor.getColumnIndex(EquationInfoContent._ID));
                    info.str = cursor.getString(cursor.getColumnIndex(EquationInfoContent.EQUATION_STR));
                    info.ans = cursor.getDouble(cursor.getColumnIndex(EquationInfoContent.EQUATION_ANS));
                    Log.d("ning", info.toString());
                    TextView t;
                    if (num==0) t=(TextView)findViewById(R.id.text1);
                    else if (num==1)t=(TextView)findViewById(R.id.text2);
                    else if (num==2)t=(TextView)findViewById(R.id.text3);
                    else if (num==3)t=(TextView)findViewById(R.id.text4);
                    else if (num==4)t=(TextView)findViewById(R.id.text5);
                    else if (num==5)t=(TextView)findViewById(R.id.text6);
                    else if (num==6)t=(TextView)findViewById(R.id.text7);
                    else if (num==7)t=(TextView)findViewById(R.id.text8);
                    else if (num==8)t=(TextView)findViewById(R.id.text9);
                    else t=(TextView)findViewById(R.id.text10);
                    num++;
                    t.setText(info.toString());
                }
                num=0;
            }
        }else if (id== R.id.text1) {
            TextView a=(TextView)findViewById(R.id.text1);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text2) {
            TextView a=(TextView)findViewById(R.id.text2);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text3) {
            TextView a=(TextView)findViewById(R.id.text3);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text4) {
            TextView a=(TextView)findViewById(R.id.text4);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text5) {
            TextView a=(TextView)findViewById(R.id.text5);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text6) {
            TextView a=(TextView)findViewById(R.id.text6);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text7) {
            TextView a=(TextView)findViewById(R.id.text7);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text8) {
            TextView a=(TextView)findViewById(R.id.text8);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text9) {
            TextView a=(TextView)findViewById(R.id.text9);
            memory= (String) a.getText();
            finish();
        }else if (id== R.id.text10) {
            TextView a=(TextView)findViewById(R.id.text10);
            memory= (String) a.getText();
            finish();
        }
        }
    }