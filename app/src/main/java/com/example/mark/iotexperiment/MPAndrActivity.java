package com.example.mark.iotexperiment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

public class MPAndrActivity extends AppCompatActivity {

    SQLiteDatabase db;
    private LineChart chart;
    String Date2 = null;
    String startY;
    String endY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpandr);

        TextView test = (TextView) findViewById(R.id.test);
        TextView test2 = (TextView) findViewById(R.id.test2);
        chart = (LineChart) findViewById(R.id.chart);
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "/test1.db3", null);//①

        Intent myintent = this.getIntent();
        Bundle mybundle = myintent.getExtras();
        Date2 = mybundle.getString("Date2");
        if (Date2 == null) {
            test.setText("空");
        }
        startY = Date2.substring(0, 8) + "00";
        endY = Date2.substring(8, 16) + "00";
//        System.out.println(Date2);
//        else
//            test.setText("选择的年份为： " +
//                    ""+Date2);

//        test.setText(startY);
//        test2.setText(endY);
        Cursor cursor = db.rawQuery("select * from news_inf where news_title>" + startY + " and " + "news_title<" + endY + " order by news_content desc", null);

        //数据处理部分
        StringBuffer SelectTime = new StringBuffer();
        StringBuffer SelectTempera = new StringBuffer();
        if (cursor.getCount() == 0) {
            SelectTime.append("0");
        } else {
            while (cursor.moveToNext()) {
                SelectTime.append(cursor.getInt(1));
                SelectTempera.append(cursor.getInt(2));
                SelectTime.append(" ");
                SelectTempera.append(" ");
            }
        }

//        test2.setText(SelectTime);
        String use_time = SelectTime.toString();
        String use_tempera = SelectTempera.toString();
        System.out.println(use_time);
        System.out.println(use_tempera);

        LineData mLineData = makeLineData(use_time, use_tempera);
        setChartStyle(chart, mLineData, Color.WHITE);

    }

    private void setChartStyle(LineChart mLineChart, LineData lineData,
                               int color) {
        // 是否在折线图上添加边框
        mLineChart.setDrawBorders(false);

        // X轴配置
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setLabelsToSkip(0); //设置坐标相隔多少，参数是int类型
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 让x轴在下面
        xAxis.resetLabelsToSkip();
        xAxis.setTextColor(Color.RED);

        //y轴配置
        YAxis yAxis = mLineChart.getAxisLeft();
        //yAxis.setStartAtZero(false); // 设置Y轴坐标是否从0开始
        yAxis.setTextColor(Color.BLUE);
//        yAxis.setShowOnlyMinMax(true);
        yAxis.setInverted(false); // Y轴坐标反转,默认false,下小上大，，，
        yAxis.setSpaceTop(10); // Y轴坐标距顶有多少距离，，
        yAxis.setSpaceBottom(10); // Y轴坐标距底有多少距离，，
        yAxis.setShowOnlyMinMax(false); // 为true的话 Y轴坐标只显示最大值和最小值
        yAxis.setLabelCount(20, false);//参数一Y轴坐标的个数，参数二是否不均匀分布，，默认 false即均匀分布
        mLineChart.setDescription("---林诗栋---");// 数据描述

        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        mLineChart.setNoDataTextDescription("如果传给MPAndroidChart的数据为空，那么你将看到这段文字。");

        mLineChart.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴

        // 是否绘制背景颜色。
        // 如果mLineChart.setDrawGridBackground(false)，
        // 那么mLineChart.setGridBackgroundColor(Color.CYAN)将失效;
        mLineChart.setDrawGridBackground(false);
        mLineChart.setGridBackgroundColor(Color.CYAN);

        // 隐藏纵横网格线
        mLineChart.getXAxis().setDrawGridLines(false);
        mLineChart.getAxisLeft().setDrawGridLines(false);
        mLineChart.getAxisRight().setDrawGridLines(false);
        // 触摸
        mLineChart.setTouchEnabled(true);

        // 拖拽
        mLineChart.setDragEnabled(true);

        // 缩放
        mLineChart.setScaleEnabled(true);

        mLineChart.setPinchZoom(false);

        // 设置背景
        mLineChart.setBackgroundColor(color);

        // 设置x,y轴的数据
        mLineChart.setData(lineData);

        // 设置比例图标示，就是那个一组y的value的
        Legend mLegend = mLineChart.getLegend();

        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(15.0f);// 字体
        mLegend.setTextColor(Color.GRAY);// 颜色

        // 沿x轴动画，时间2000毫秒。
        mLineChart.animateX(2000);
    }


    private LineData makeLineData(String time, String tempera) {
        String[] use_ti = time.split(" ");
        String[] use_te = tempera.split(" ");

        ArrayList<String> x = new ArrayList<String>();
        for (int i = 0; i < use_ti.length; i++) {
            String ti = use_ti[i];
            x.add(ti);
        }

        //y轴显示的数据
        ArrayList<Entry> y = new ArrayList<Entry>();

        //ArrayList<Entry> y = new ArrayList<Entry>();
        for (int i = 0; i < use_te.length; i++) {
            //Log.i("---LK---",use_data[i]);
            int val = Integer.valueOf(use_te[i]);
            //float val = (float)(val1);
            //Log.i("---LK---val",val+"");
            Entry entry = new Entry(val, i);
            y.add(entry);
        }


        //y轴数据集
        LineDataSet mLineDataSet = new LineDataSet(y, "X:温度（华氏度）Y:时间（年月日）");

        // 用y轴的集合来设置参数
        // 线宽
        mLineDataSet.setLineWidth(2.0f);
        // 显示的圆形大小
        mLineDataSet.setCircleSize(0.0f);
        // 折线的颜色
        mLineDataSet.setColor(Color.GREEN);
        // 圆球的颜色
        mLineDataSet.setCircleColor(Color.MAGENTA);
        //折线图上文字的大小
        mLineDataSet.setValueTextSize(10.0f);
        // 设置mLineDataSet.setDrawHighlightIndicators(false)后，
        // Highlight的十字交叉的纵横线将不会显示，
        // 同时，mLineDataSet.setHighLightColor(Color.CYAN)失效。
        mLineDataSet.setDrawHighlightIndicators(true);
        // 按击后，十字交叉线的颜色
        mLineDataSet.setHighLightColor(Color.CYAN);
        // mLineDataSet.setDrawCircleHole(true);

        // 改变折线样式，用曲线。
        // mLineDataSet.setDrawCubic(true);
        // 默认是直线
        // 曲线的平滑度，值越大越平滑。
        // mLineDataSet.setCubicIntensity(0.2f);

        // 填充曲线下方的区域，红色，半透明。
        // mLineDataSet.setDrawFilled(true);
        // mLineDataSet.setFillAlpha(128);
        // mLineDataSet.setFillColor(Color.RED);

        // 填充折线上数据点、圆球里面包裹的中心空白处的颜色。
        mLineDataSet.setCircleColorHole(Color.YELLOW);

        // 设置折线上显示数据的格式。如果不设置，将默认显示float数据格式。
        mLineDataSet.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                int n = (int) v;
                String s = "y:" + n;
                return s;
            }
        });


        ArrayList<LineDataSet> mLineDataSets = new ArrayList<LineDataSet>();
        mLineDataSets.add(mLineDataSet);


        LineData mLineData = new LineData(x, mLineDataSets);

        return mLineData;

    }
}
