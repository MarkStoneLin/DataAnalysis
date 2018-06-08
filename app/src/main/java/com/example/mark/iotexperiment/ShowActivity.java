package com.example.mark.iotexperiment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    Button button_back;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        EditText editText;
        editText = (EditText) findViewById(R.id.show2);
        button_back = (Button) findViewById(R.id.back);
        TextView test = (TextView)findViewById(R.id.test);

        Intent myintent = this.getIntent();
        Bundle mybundle = myintent.getExtras();
        String Date = mybundle.getString("Date");
        test.setText(Date);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent();
                myintent.setClass(ShowActivity.this, MainActivity.class);
                ShowActivity.this.startActivity(myintent);
                ShowActivity.this.finish();
            }
        });

    }


}
