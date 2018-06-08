package com.example.mark.iotexperiment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private int syear;
    private int smonth;
    private int sday;
    private int shour;
    private int sminute;
    private int eyear;
    private int emonth;
    private int eday;
    private int ehour;
    private int eminute;
    Button gogogo;
    Button writetosql;
    Button showimage;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //各种初始化
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);//startyear
        DatePicker datePicker2 = (DatePicker) findViewById(R.id.datePicker2);//endyear
        Button gogogo = (Button) findViewById(R.id.Goto);
        Button writetosql = (Button) findViewById(R.id.writetosql);
        Button showimage = (Button) findViewById(R.id.showimage);
        Button spectempera = (Button)findViewById(R.id.spectempera);
//        Button GoToPie = (Button)findViewById(R.id.GoToPie);
        final TextView text = (TextView) findViewById(R.id.test);
//        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);

        //利用Calendar定义年月日，年月日为int类型
        Calendar s = Calendar.getInstance();
        syear = s.get(Calendar.YEAR);
        smonth = s.get(Calendar.MONTH);
        shour = s.get(Calendar.HOUR);
        sday = s.get(Calendar.DAY_OF_MONTH);
        sminute = s.get(Calendar.MINUTE);

        Calendar e = Calendar.getInstance();
        eyear = e.get(Calendar.YEAR);
        emonth = e.get(Calendar.MONTH);
        ehour = e.get(Calendar.HOUR);
        eday = e.get(Calendar.DAY_OF_MONTH);
        eminute = e.get(Calendar.MINUTE);

        //截取Starttime的时间信息
        datePicker.init(syear, smonth, sday, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                MainActivity.this.syear = year;
                MainActivity.this.smonth = month + 1;
                MainActivity.this.sday = day;
                showDate(year, month + 1, day, shour, sminute);
                String temp = String.valueOf("0" + smonth);
//                text.setText(temp);
            }
        });

        //截取endtime的时间信息
        datePicker2.init(eyear, emonth, eday, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                MainActivity.this.eyear = year;
                MainActivity.this.emonth = month + 1;
                MainActivity.this.eday = day;
                showDate(year, month + 1, day, ehour, eminute);
            }
        });

        //进入测试Activity
        gogogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = String.valueOf(syear);
                if (smonth >= 10) {
                    s = s + String.valueOf(smonth);
                } else {
                    s = s + String.valueOf("0" + smonth);
                }

                if (sday < 10) {
                    s = s + String.valueOf("0" + sday);
                } else {
                    s = s + String.valueOf(sday);
                }

                String e = String.valueOf(eyear);
                if (emonth >= 10) {
                    e = e + String.valueOf(emonth);
                } else {
                    e = e + String.valueOf("0" + emonth);
                }

                if (eday < 10) {
                    e = e + String.valueOf("0" + eday);
                } else {
                    e = e + String.valueOf(eday);
                }
//                text.setText(e + s);

                //将处理好的年月日打包进bundle中，之后通过Intent传值给WriteToSql的Activity
                Bundle mybundle = new Bundle();
                mybundle.putString("Date3", s + e);

                Intent myintent = new Intent();
                myintent.setClass(MainActivity.this, PieActivity.class);
                myintent.putExtras(mybundle);
                MainActivity.this.startActivity(myintent);

            }
        });

        //进入写入数据库的Activity
        writetosql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //给月份做处理，<10月的前面需要加0，保证和数据库中的数据对齐
                String s = String.valueOf(syear);
                if (smonth >= 10) {
                    s = s + String.valueOf(smonth);
                } else {
                    s = s + String.valueOf("0" + smonth);
                }

                if (sday < 10) {
                    s = s + String.valueOf("0" + sday);
                } else {
                    s = s + String.valueOf(sday);
                }

                String e = String.valueOf(eyear);
                if (emonth >= 10) {
                    e = e + String.valueOf(emonth);
                } else {
                    e = e + String.valueOf("0" + emonth);
                }

                if (eday < 10) {
                    e = e + String.valueOf("0" + eday);
                } else {
                    e = e + String.valueOf(eday);
                }

//                text.setText(e + s);

                //将处理好的年月日打包进bundle中，之后通过Intent传值给WriteToSql的Activity
                Bundle mybundle = new Bundle();
                mybundle.putString("Date2", s + e);
                System.out.println(s + e);

                Intent myintent = new Intent();
                myintent.setClass(MainActivity.this, WriteToSqlActivity.class);
                myintent.putExtras(mybundle);
                MainActivity.this.startActivity(myintent);
                MainActivity.this.finish();
            }
        });

        //进入MPAndroidchart类
        showimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //给月份做处理，<10月的前面需要加0，保证和数据库中的数据对齐
                String s = String.valueOf(syear);
                if (smonth >= 10) {
                    s = s + String.valueOf(smonth);
                } else {
                    s = s + String.valueOf("0" + smonth);
                }

                if (sday < 10) {
                    s = s + String.valueOf("0" + sday);
                } else {
                    s = s + String.valueOf(sday);
                }

                String e = String.valueOf(eyear);
                if (emonth >= 10) {
                    e = e + String.valueOf(emonth);
                } else {
                    e = e + String.valueOf("0" + emonth);
                }

                if (eday < 10) {
                    e = e + String.valueOf("0" + eday);
                } else {
                    e = e + String.valueOf(eday);
                }
//                text.setText(e + s);

                //将处理好的年月日打包进bundle中，之后通过Intent传值给WriteToSql的Activity
                Bundle mybundle = new Bundle();
                mybundle.putString("Date2", s + e);

                Intent myintent = new Intent();
                myintent.setClass(MainActivity.this, MPAndrActivity.class);
                myintent.putExtras(mybundle);
                MainActivity.this.startActivity(myintent);
            }
        });

        //进入实验5部分
        spectempera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //给月份做处理，<10月的前面需要加0，保证和数据库中的数据对齐
                String s = String.valueOf(syear);
                if (smonth >= 10) {
                    s = s + String.valueOf(smonth);
                } else {
                    s = s + String.valueOf("0" + smonth);
                }

                if (sday < 10) {
                    s = s + String.valueOf("0" + sday);
                } else {
                    s = s + String.valueOf(sday);
                }

                String e = String.valueOf(eyear);
                if (emonth >= 10) {
                    e = e + String.valueOf(emonth);
                } else {
                    e = e + String.valueOf("0" + emonth);
                }

                if (eday < 10) {
                    e = e + String.valueOf("0" + eday);
                } else {
                    e = e + String.valueOf(eday);
                }
//                text.setText(e + s);

                //将处理好的年月日打包进bundle中，之后通过Intent传值给WriteToSql的Activity
                Bundle mybundle = new Bundle();
                mybundle.putString("Date2", s + e);

                Intent myintent = new Intent();
                myintent.setClass(MainActivity.this, SpecTemper.class);
                myintent.putExtras(mybundle);
                MainActivity.this.startActivity(myintent);
            }
        });


//        GoToPie.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent myintent = new Intent();
//                myintent.setClass(MainActivity.this, PieActivity.class);
//                MainActivity.this.startActivity(myintent);
//            }
//        });


//        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                MainActivity.this.hour = hourOfDay;
//                MainActivity.this.minute = minute;
//                showDate(year, month, day, hour, minute);
//            }
//        });
    }

    //将数据显示在EditTest里
    private void showDate(int year, int month, int day, int hour, int minute) {
        EditText show = (EditText) findViewById(R.id.show);
        show.setText("您所选的时间是：" + year + "年" + month + "月" + day + "日");
    }
}
