package com.example.myFileSave;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class MainActivity extends AppCompatActivity {


    private EditText mEditText;
    private FileOutputStream mFileOutputStream;
    private FileInputStream mFileInputStream;
    private Context mContext;
    private String mStr;
    private BufferedWriter mWriter;
    private BufferedReader mReader;
    private StringBuilder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.edt_text);
        mContext = this;

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
}
