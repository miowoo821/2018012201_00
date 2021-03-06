package com.example.student.a2018012201_00;

import android.content.ContentValues;
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
//        c.moveToNext();//moveToFirst,游標移動到下一筆資料
//        Log.d("DB", c.getString(1) + "," + c.getInt(2));
        while (c.moveToNext())
        {
            Log.d("DB", c.getString(1) + "," + c.getInt(2));
        }
        db.close();
    }
    public void click3(View v)//篩選方法1
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        String strSql = "Select * from students where _id=?";
        Cursor c = db.rawQuery(strSql, new String[] {"2"});
        //Cursorj物件可以搜尋低一個引數裡的SQL語法裡面的問號，並用第二個引數的陣列依序填入SQL與法理的問號，有幾個物號陣列就有幾個值
        c.moveToFirst();
        Log.d("DB", c.getString(1) + "," + c.getInt(2));
        db.close();
    }
    public void click4(View v)//讀取方法2
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.query("students", new String[] {"_id", "name", "score"}, null, null, null, null, null);
       //SQLiteDatabase.query的
        //第一個引數：選定這個資料庫物件的特定資料表
        //第二個引數：這個資料表的欄位
        //第三個引數：欲篩選欄位
        //第四個引數：欲篩選的關鍵字
        //第五個引數：
        //第六個引數：
        //第七個引數：排序方法
        c.moveToFirst();
        Log.d("DB", c.getString(1) + "," + c.getInt(2));
        db.close();
    }
    public void click5(View v)//篩選方法2
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.query("students", new String[] {"_id", "name", "score"}, "_id=?", new String[] {"2"}, null, null, null);
        c.moveToFirst();
        Log.d("DB", c.getString(1) + "," + c.getInt(2));
        db.close();
    }

    public void click6(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        db.execSQL("Insert into students( name, score) values('VVV',99)");
        //完整欄位是_id, name, score，但_id已設定會自動新增，所以不打也行

        db.close();
    }
    public void click7(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues cv = new ContentValues();
        cv.put("_id", 5);//_id已經設定是會自動新增，沒有這一行也沒關係
        cv.put("name", "Mary");//先決定要輸入的欄位以及決定輸入的值
        cv.put("score", 92);//先決定要輸入的欄位以及決定輸入的值
        db.insert("students", null, cv);
        //接著再決定這個值是要做新增還是做更新用，此處是做新增(修改)
        //insert第一個參數是要輸入的資料表，第二個參數是，第三個參數是要新增甚麼
        db.close();
    }
    public void click8(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues cv = new ContentValues();
        cv.put("score", 85);//先決定要輸入的欄位以及決定輸入的值
        db.update("students", cv, "_id=?", new String[] {"2"});
        //接著再決定這個值是要做新增還是做更新用，此處是做更新(修改)
        //update第一個參數是要輸入的資料表，第二個參數是修改甚麼，第三個參數是要修改哪一欄，第四個參數是修改哪一筆
        db.close();
    }
    public void click9(View v)
    {
        File dbFile = new File(getFilesDir(), "student.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        db.delete("students", "_id=?", new String[] {"2"});
        //delete第一個參數是要刪除資料所在的資料表，第二個參數是要刪除資料的欄位，第三個參數是要刪除該欄位符合第三個參數的資料
        db.close();
    }
}
