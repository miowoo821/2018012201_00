package com.example.student.a2018012201_00;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void click1(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        //新建一個檔案，放在getFilesDir裡面，叫做student
        InputStream is = getResources().openRawResource(R.raw.school);
        //getResources抓出來的是InputStream類型，所以要用一個InputStream裝他
        try {
            OutputStream os = new FileOutputStream(dbFile);//弄一個寫入的物件，目標是dbFile這個檔案物件
            int r;
            while ((r = is.read()) != -1)//當讀的到資料的時候就繼續(讀不到會=-1)
            {
                os.write(r);
            }
            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void click2(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        //SQLiteDatabase.openDatabase的第三個參數flag，設定 SQLiteDatabase.OPEN_READWRITE可以做讀寫?
        Cursor c = db.rawQuery("Select * from students", null);
        // Cursor物件，可以處理db給的資料，
        //新增一個 Cursor將db物件查詢出來的資料丟進去

        c.moveToFirst();//moveToFirst,游標移動到第一筆資料
        Log.d("DB", c.getString(1) + "," + c.getInt(2));//把這筆資料的第1、2欄(第0欄是_id)抓到LOG秀出來
        c.moveToNext();//moveToFirst,游標移動到下一筆資料
        Log.d("DB", c.getString(1) + "," + c.getInt(2));
    }
}
