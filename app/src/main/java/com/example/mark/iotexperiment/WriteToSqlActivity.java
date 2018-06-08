package com.example.mark.iotexperiment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WriteToSqlActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Button bn = null;
    Button showbutton = null;
    Button showtime = null;
    Button showtemperature = null;
    Button backtomain;
    Button testbutton;
    ListView listView;
    TextView test, test2;
    private Context context;
    String temp = null;
    BufferedReader bufferedReader2 = null;
    FileReader fileReader2 = null;
    Button selectbutton;
    String Date2 = null;
    String startY;
    String endY;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    //这两句用于下拉列表框
    private ArrayAdapter<String> adapter2;
    private static final String[] m = {"请选择","升序", "降序"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_to_sql);

        //各种数据的初始化
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "/test1.db3", null);//①
        listView = (ListView) findViewById(R.id.show);
        bn = (Button) findViewById(R.id.ok);
        showbutton = (Button) findViewById(R.id.showbutton);
        showtime = (Button) findViewById(R.id.showtime);
        showtemperature = (Button) findViewById(R.id.showtemperature);
        selectbutton = (Button) findViewById(R.id.selectbutton);
        backtomain = (Button) findViewById(R.id.backtomain);
        test2 = (TextView) findViewById(R.id.test2);
        testbutton = (Button) findViewById(R.id.testbutton);

        //接收来自MainActivity的数据，进行了一个非空判断，其实非空判断可以省略
        Intent myintent = this.getIntent();
        Bundle mybundle = myintent.getExtras();
        Date2 = mybundle.getString("Date2");
        if (Date2 == null) {
            test2.setText("空");
        }
//        System.out.println(Date2);
        else
            test2.setText(Date2);

        //这部分代码源自CSDN，定义下拉列表框，用于选择排序是逆序还是顺序
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner = (Spinner) findViewById(R.id.spinner);
        //将可选内容与ArrayAdapter连接起来
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m);
        //设置下拉列表的风格
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spinner.setAdapter(adapter2);
        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        //设置默认值
        spinner.setVisibility(View.VISIBLE);


        //assets方法，网上看的，用于给存放文件，但是不好操作，先不使用
//        File file = new File(this.getFilesDir(), filename);

//        File file1 = new File(getFilesDir() +"/shumei.txt");
//        try {
//            InputStream is = getAssets().open("tat");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //在此处做清明节这几天新使用的数据的初始化
        //给文件设置路径
        final File file2 = new File(Environment.getExternalStorageDirectory().getPath() + "/tat");
//        FileReader fileReader1;

        //测试用--测试每个语句的路径是什么
        System.out.println(Environment.getExternalStorageDirectory().getPath());
        System.out.println(this.getAssets());
        System.out.println(getFilesDir());
        System.out.println(Environment.getExternalStorageDirectory());

        test = (TextView) findViewById(R.id.test);//test用来测试文件读取是否能用

        //开启BufferReader读取文件中数据
        try {
//            FileReader fileReader1 = new FileReader(file1);
//            BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
            FileReader fileReader2 = new FileReader(file2);
            bufferedReader2 = new BufferedReader(fileReader2);
//            temp = bufferedReader2.readLine();
//            test.setText(temp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            test.setText("文件没写入" + e.toString());

        } catch (IOException e) {//此处总是错的原因是
//            e.printStackTrace();
            test.setText("逐行读出错了");
        }

//        try {
//            while ((temp = bufferedReader2.readLine()) != null) {
//                String time = null;
//                String temperature = null;
//                time = temp.substring(0, 12);
//                temperature = temp.substring(12);
//                test.setText(temp + temperature);
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //按下bn按钮（建立数据库），如果数据库没被建立，就创建新的数据库执行的是catch部分的代码，若数据库已经建立完毕执行的是try部分的代码
        //按下bn按钮（建立数据库），如果数据库没被建立，就创建新的数据库执行的是catch部分的代码，若数据库已经建立完毕执行的是try部分的代码


        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = null;
                String content = null;
                test.setText("数据库正在建立，请稍等...");
//                String title = ((EditText) findViewById(R.id.title)).getText().toString();
//                String content = ((EditText) findViewById(R.id.content)).getText().toString();
                try {
                    try {
                        while ((temp = bufferedReader2.readLine()) != null) {
                            String time = null;
                            String temperature = null;
                            time = temp.substring(0, 10);
                            temperature = temp.substring(12);
//                            test.setText(temp + temperature);
                            content = time + temperature;
//                            test.setText(time + temperature + "haha");
                            insertData(db, time, temperature);
                        }
                        test.setText("数据库建立完毕");
//                        bufferedReader2.close();
//                        fileReader2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        test.setText("压根没循环");
                    }

                } catch (SQLiteException se) {
                    //test.setText("catch");
                    db.execSQL("create table news_inf(_id integer primary key autoincrement,news_title int(50),news_content int(50))");
                    //insertData(db, title, content);
                    Cursor cursor = db.rawQuery("select * from news_inf", null);
                    inflateList(cursor);
                }
            }
        });

        //显示数据库中所有的时间和温度
        showbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = db.rawQuery("select * from news_inf", null);
                inflateList(cursor);
            }
        });

        //只显示时间
        showtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = db.rawQuery("select * from news_inf", null);
                inflateList2(cursor);
//                test.setText("heihei");
            }
        });

        //只显示温度
        showtemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = db.rawQuery("select * from news_inf", null);
                inflateList3(cursor);
            }
        });

        //按照选择的时间查询
        selectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String startyears = ((EditText) findViewById(R.id.title)).getText().toString();
//                String endyears = ((EditText) findViewById(R.id.content)).getText().toString();
//                int startyear = Integer.valueOf(startyears).intValue();
//                int endyear = Integer.valueOf(endyears).intValue();
//                test.setText(startyears);
                startY = Date2.substring(0, 8) + "00";
                endY = Date2.substring(8, 16) + "00";
                int IstartY = Integer.valueOf(startY).intValue();
                int IendY = Integer.valueOf(endY).intValue();
//                System.out.println(IstartY);
//                System.out.println(IendY);
//                Cursor cursor = db.rawQuery("select * from news_inf where news_title like '" + startyears + "%'", null);
                Cursor cursor = db.rawQuery("select * from news_inf where news_title>" + IstartY + " and " + "news_title<" + IendY, null);
//                Cursor cursor = db.query(db, new String[]{tab_field02}, tab_field02 + " like '%" + startyears + "%'", null, null, null, null);
                inflateList(cursor);
            }
        });

        //回到主函数
        backtomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent();
                myintent.setClass(WriteToSqlActivity.this, MainActivity.class);
                WriteToSqlActivity.this.startActivity(myintent);
                WriteToSqlActivity.this.finish();
            }
        });

        //显示主函数传过来的开始和结束时间
        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startY = Date2.substring(0, 8);
                endY = Date2.substring(8, 16);
                test.setText(startY);
                test2.setText(endY);
            }
        });
    }


    private void insertData(SQLiteDatabase db, String title, String content) {
        db.execSQL("insert into news_inf values(null, ? , ?)", new String[]{title, content});
    }

    private void inflateList(Cursor cursor) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(WriteToSqlActivity.this, R.layout.line, cursor,
                new String[]{"news_title", "news_content"}, new int[]{R.id.my_title, R.id.my_content}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
    }

    private void inflateList2(Cursor cursor) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(WriteToSqlActivity.this, R.layout.line, cursor,
                new String[]{"news_title"}, new int[]{R.id.my_title}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
    }

    private void inflateList3(Cursor cursor) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(WriteToSqlActivity.this, R.layout.line, cursor,
                new String[]{"news_content"}, new int[]{R.id.my_content}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
    }

    public void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }



    /*选择查询的是升序还是降序
    若数据库没建立，则必须先注释该类，数据库已经生产才能使用该类*/
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            test2.setText(m[position]);
            if(m[position] == "请选择"){
                test.setText("请选择升序或者是降序");
            }
            if (m[position] == "升序") {
                startY = Date2.substring(0, 8) + "00";
                endY = Date2.substring(8, 16) + "00";
                int IstartY = Integer.valueOf(startY).intValue();
                int IendY = Integer.valueOf(endY).intValue();
                Cursor cursor = db.rawQuery("select * from news_inf where news_title>" + IstartY + " and " + "news_title<" + IendY + " order by news_content", null);
                inflateList(cursor);
            }
            if (m[position] == "降序") {
                startY = Date2.substring(0, 8) + "00";
                endY = Date2.substring(8, 16) + "00";
                int IstartY = Integer.valueOf(startY).intValue();
                int IendY = Integer.valueOf(endY).intValue();
                Cursor cursor = db.rawQuery("select * from news_inf where news_title>" + IstartY + " and " + "news_title<" + IendY + " order by news_content desc", null);
                inflateList(cursor);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            test.setText("请选择升序或降序");
        }
    }
}
