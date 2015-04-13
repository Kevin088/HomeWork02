package com.kevin.cn;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.*;
import com.kevin.cn.helper.DateHelper;
import com.kevin.cn.helper.MySQLiteOpenHelper;
import com.kevin.cn.myview.ExpenseActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClick(View v){
        switch (v.getId()){

            case R.id.extView_listchartview:
                Intent intent=new Intent();
                intent.setClass(this,ChartActivity.class);
                startActivity(intent);
                break;
            case R.id.button_income:
                Intent intent1=new Intent();
                intent1.setClass(this,IncomeActivity.class);
                startActivity(intent1);
                break;
            case R.id.button_out:
                Intent intent2=new Intent();
                intent2.setClass(this,ExpenseActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }


}
