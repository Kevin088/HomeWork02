package com.kevin.cn;

import android.app.Activity;
import android.os.Bundle;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        arcView= (ArcView) findViewById(R.id.arcView);
        db=new MySQLiteOpenHelper(this);
        List<Map<String,Object>> totalData_In=db.selectList("select name_id,money,datetime from tb_income_expenses_detail join " +
                "tb_income_expenses_name on tb_income_expenses_name._id=" +
                "tb_income_expenses_detail.name_id where _type=?",new String[]{"1"});
        List<Map<String,Object>> totalData_Out=db.selectList("select name_id,money,datetime from tb_income_expenses_detail join " +
                "tb_income_expenses_name on tb_income_expenses_name._id=" +
                "tb_income_expenses_detail.name_id where _type=?",new String[]{"0"});

        Map<Integer,Integer> dataIn=dateChange(totalData_In);
        Map<Integer,Integer> dataOut=dateChange(totalData_Out);
        arcView.setData_in(dataIn);
        arcView.setData_out(dataOut);



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
            if (dateTime.length()==9){
                min=dateTime.compareTo("2015-4-00");
                max=dateTime.compareTo("2015-5-00");
            }
            if (dateTime.length()==10){
                min=dateTime.compareTo("2015-04-00");
                max=dateTime.compareTo("2015-05-00");
            }
            if(min>0&&max<0){
              Integer value=map1.put(Integer.parseInt (map.get("name_id").toString()), Integer.parseInt(map.get("money").toString()));
               // Integer value=map1.put(1, 1);
                ;
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