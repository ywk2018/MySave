package com.example.myFileSave;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "SharedPreferences";
    private EditText mEditText;
    private FileOutputStream mFileOutputStream;
    private FileInputStream mFileInputStream;
    private Context mContext;
    private String mStr;
    private BufferedWriter mWriter;
    private BufferedReader mReader;
    private StringBuilder mBuilder;
    private Button btnEdit, btnPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.edt_text);
        mContext = this;

        btnEdit = findViewById(R.id.btn_share_preferences_edit);
        btnPref = findViewById(R.id.btn_share_preferences_pref);
        btnEdit.setOnClickListener(this);
        btnPref.setOnClickListener(this);

        String connect = load();
        if(!TextUtils.isEmpty(connect)) {
            mEditText.setText(connect);
            mEditText.setSelection(connect.length());
            Toast.makeText(mContext, "" + connect, Toast.LENGTH_SHORT).show();
        }
    }

    private String load() {
        try {
            mFileInputStream = mContext.openFileInput("ppp");
            mReader = new BufferedReader(new InputStreamReader(mFileInputStream));
            mBuilder = new StringBuilder();
            if ((mStr = mReader.readLine()) != null) {
                mBuilder.append(mStr);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mReader != null) {
                try {
                    mReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mBuilder.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            mFileOutputStream = mContext.openFileOutput("ppp", MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(mFileOutputStream);
            mWriter = new BufferedWriter(outputStreamWriter);
            mStr = mEditText.getText().toString();
            mWriter.write(mStr);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (mWriter != null) {
                try {
                    mWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_share_preferences_edit:
                SharedPreferences.Editor editor = getSharedPreferences("edit", MODE_PRIVATE).edit();
                editor.putString("name", "游文凯");
                editor.putInt("age", 26);
                editor.putBoolean("cool", true);
                editor.apply();
                Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_share_preferences_pref:
                SharedPreferences pref = getSharedPreferences("edit", MODE_PRIVATE);
                String name =pref.getString("name", "none");
                int age =pref.getInt("age", 0);
                Boolean cool = pref.getBoolean("cool", false);
                Log.d(TAG, "我的名字是 " + name);
                Log.d(TAG, "我的年龄是 " + age);
                Log.d(TAG, "帅不帅 " + cool);
                Toast.makeText(mContext, "取出成功", Toast.LENGTH_SHORT).show();

                break;

        }
    }
}
