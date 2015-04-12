package com.kevin.cn;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.kevin.cn.helper.MySQLiteOpenHelper;
import com.kevin.cn.myview.ArcView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/10.
 */
public class ChartActivity extends Activity {

    ArcView arcView;
    MySQLiteOpenHelper db;
    Spinner spinner;
    String a;
    String b;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        arcView= (ArcView) findViewById(R.id.arcView);
        db=new MySQLiteOpenHelper(this);

        String[]arr=new String[]{"一月份","二月份","三月份","四月份","五月份","六月份","七月份","八月份","九月份","十月份","十一月份","十二月份"};
        spinner= (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arr);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                changeMonth(i);
                productArcView();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });









    }
    private void changeMonth(int i){
        if (i+1<10) {
            a = "0"+(i+1);
        }else{
            a=i+1+"";
        }
        if (i+2<10){
            b="0"+(i+2);
        }else {
            b=i+2+"";
        }

    }
    private void productArcView(){
        List<Map<String,Object>> totalData_In=db.selectList("select name_id,money,datetime from tb_income_expenses_detail join " +
                "tb_income_expenses_name on tb_income_expenses_name._id=" +
                "tb_income_expenses_detail.name_id where _type=?",new String[]{"1"});
        List<Map<String,Object>> totalData_Out=db.selectList("select name_id,money,datetime from tb_income_expenses_detail join " +
                "tb_income_expenses_name on tb_income_expenses_name._id=" +
                "tb_income_expenses_detail.name_id where _type=?", new String[]{"0"});

        Map<Integer,Integer> dataIn=dateChange(totalData_In);
        Map<Integer,Integer> dataOut=dateChange(totalData_Out);
        arcView.setData_in(dataIn);
        arcView.setData_out(dataOut);
        arcView.viewNotify();



    }
    private Map<Integer,Integer>dateChange(List<Map<String,Object>> list){
        if (list==null)
            return null;
        //默认计算统计四月份的数据
        int min=0;
        int max=0;
        Map<Integer,Integer> map1= new HashMap<Integer,Integer>();
        for (Map<String,Object> map:list){
            String dateTime=map.get("datetime").toString();
            if (dateTime.length()!=10){
                Toast.makeText(this,"日期格式错误",Toast.LENGTH_LONG).show();
                return null;
            }

            min=dateTime.compareTo("2015-"+a+"-00");
            max=dateTime.compareTo("2015-"+b+"-00");

            if(min>0&&max<0){
              Integer value=map1.put(Integer.parseInt (map.get("name_id").toString()), Integer.parseInt(map.get("money").toString()));


                if (value!=null){
                    Integer data=map1.get(Integer.parseInt(map.get("name_id").toString()));
                    value=value+data;
                    map1.put(Integer.parseInt(map.get("name_id").toString()), value);


                }
            }
        }
        int count=0;
        for (Map.Entry<Integer,Integer>entry:map1.entrySet()){
            count=count+entry.getValue();
        }
        Map<Integer,Integer> map2= new HashMap<Integer,Integer>();
        for (Map.Entry<Integer,Integer>entry:map1.entrySet()){
            int degree=entry.getValue()*360/count;
            map2.put(entry.getKey(),degree);
        }



        return map2;
    }


}