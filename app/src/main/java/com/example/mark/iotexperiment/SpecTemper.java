package com.example.mark.iotexperiment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

import java.util.LinkedList;


/**
 * @author Mark栋
 * @version v1.0
 * @since jdk 1.6
 */
public class SpecTemper extends AppCompatActivity {

    SQLiteDatabase db;
    private LineChart chart;
    String Date2 = null;
    String startY;
    String endY;
    ListView listView;
    TextView test;
    EditText max, min;
    int maxT = 50, minT = 0;
    //    int SpecPosition[];
    LinkedList<Integer> SpecPosition = new LinkedList<Integer>();
    private int[] colors = new int[]{0x30FF0000, 0x300000FF};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spec_temper);

        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "/test1.db3", null);//①

        test = (TextView) findViewById(R.id.test);
        max = (EditText) findViewById(R.id.max);
        min = (EditText) findViewById(R.id.min);
        Button select = (Button) findViewById(R.id.select);
        listView = (ListView) findViewById(R.id.show);
        Intent myintent = this.getIntent();
        Bundle mybundle = myintent.getExtras();
        Date2 = mybundle.getString("Date2");
        if (Date2 == null) {
            test.setText("空");
        }


        assert select != null;
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String startyears = ((EditText) findViewById(R.id.title)).getText().toString();
//                String endyears = ((EditText) findViewById(R.id.content)).getText().toString();
//                int startyear = Integer.valueOf(startyears).intValue();
//                int endyear = Integer.valueOf(endyears).intValue();
//                test.setText(startyears);
                String SmaxT = max.getText().toString();
                String SminT = min.getText().toString();
                if (SmaxT.equals("") || SminT.equals("")) {
                    test.setText("默认温度Max=50，Min=0");
                } else {
                    maxT = Integer.valueOf(SmaxT);
                    minT = Integer.valueOf(SminT);
                    test.setText("默认温度Max=" + SmaxT + "，Min=" + SminT);
                }
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
    }

    private void inflateList(Cursor cursor) {

        int i = 0;
        int j = 0;
//        a.size();
        while (cursor.moveToNext()) {
            if (cursor.getInt(2) < maxT && cursor.getInt(2) > minT) {
                SpecPosition.add(i);
                j++;
            }
            i++;
        }
//        test.setText(String.valueOf(SpecPosition));
        //实例化对象
        AltColorAdapt adapter = new AltColorAdapt(SpecTemper.this, R.layout.line, cursor,
                new String[]{"news_title", "news_content"}, new int[]{R.id.my_title, R.id.my_content}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        {

        }

        listView.setAdapter(adapter);
    }

    public class AltColorAdapt extends SimpleCursorAdapter {

        public AltColorAdapt(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            int paint = 0;
            for (int i = 0; i < SpecPosition.size(); i++) {
                if (position == SpecPosition.get(i)) {
                    paint = 1;
                }
            }

            if (paint == 1) {
                view.setBackgroundColor(colors[1]);
            } else {
                view.setBackgroundColor(colors[0]);
            }

            return view;

        }
    }

}
