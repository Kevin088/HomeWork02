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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    private Spinner spinner;
    private MySQLiteOpenHelper db;
    private List<Map<String,Object>> totaList;
    private SimpleAdapter adapter;
    private int position;
    private EditText editTextMoney;
//    private String dateTime;
//    DatePickerDialog datePickerDialog;
    private DatePicker datePicker;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();






    }
    private void init(){
        editTextMoney= (EditText) findViewById(R.id.editText_main_money);
        spinner= (Spinner) findViewById(R.id.spinner);
        datePicker= (DatePicker) findViewById(R.id.datePicker);
        db=new MySQLiteOpenHelper(this);
//        Calendar calendar = Calendar.getInstance();
//         datePickerDialog = new DatePickerDialog(
//                MainActivity.this, new OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view,
//                                  int year, int monthOfYear,
//                                  int dayOfMonth) {
//                dateTime=year+"-"
//                        +(monthOfYear + 1) +"-"
//                        + dayOfMonth;
//            }
//        }, calendar.get(Calendar.YEAR), calendar
//                .get(Calendar.MONTH), calendar
//                .get(Calendar.DAY_OF_MONTH));
//        datePickerDialog.setCancelable(true);



        totaList=new ArrayList<Map<String, Object>>();
        //查询数据库 name表
        totaList=db.selectList("select * from tb_income_expenses_name",null);
        adapter=new SimpleAdapter(this,totaList,android.R.layout.simple_list_item_1,
                    new String[]{"typename"},new int[]{android.R.id.text1});
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                Editable text = editTextMoney.getText();
                if (text.toString().length()<1||text.toString()==null||"".equals(text)){
                    Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
                    return;
                }



                //String datetime= DateHelper.getCurrentDate();

                String month=datePicker.getMonth()+1+"";
                if(month.length()==1)
                    month="0"+month;
                String day=datePicker.getDayOfMonth()+"";
                if(day.length()==1)
                    day="0"+day;
                String datetime=datePicker.getYear()+"-"+month+"-"+day;
                String money=text.toString();
                int name_id=Integer.parseInt((String) totaList.get(position).get("_id"));
               boolean flag= db.execData("insert into tb_income_expenses_detail (datetime ,money ,name_id)values(?,?,?)",
                            new Object[]{datetime,money,name_id});
                if (flag)
                    Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();

            break;
            case R.id.extView_listchartview:
                Intent intent=new Intent();
                intent.setClass(this,ChartActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}
