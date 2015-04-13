package com.kevin.cn.myview;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.kevin.cn.R;
import com.kevin.cn.model.Category;
import com.kevin.cn.model.Detail;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.*;

/**
 * Created by Administrator on 2015/4/12.
 */
@ContentView(R.layout.activity_income)
public class ExpenseActivity extends Activity {
    @ViewInject(R.id.textView_datetime)
    private TextView textViewDatetime;
    @ViewInject(R.id.spinner_income_categrary)
    private Spinner spinner;
    @ViewInject(R.id.edittext_income_money)
    private EditText editTextMoney;
    @ViewInject(R.id.listView_income)
    private ListView listView_income;
    private List<Category> totadate;
    private DbUtils dbUtils;
    private List<Detail> details ;
    private Category category;
    //listView的数据源；
    List<Map<String,Object>> data_listView;
    SimpleAdapter adapter_listview;//listview的适配器；

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        init();
    }
    private void init(){
        dbUtils=DbUtils.create(this,
                "db_account_book.db",
                1,
                new DbUtils.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbUtils dbUtils, int i, int i1) {

                    }
                }
        );
        //初始化spinner
        selectFromDB(1);// 初始化数据源
        List<Map<String,Object>> list_spinner=dataChange(totadate);//转化数据源->List<Map<>>
        Log.i("xulu", list_spinner.size() + "");
        SimpleAdapter adapter=new SimpleAdapter(this,list_spinner,R.layout.item_spinner_income,
                new String[]{"typeName"},new int[]{R.id.textView_itemspinner_income});
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category=totadate.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //初始化ListVeiw
        selectFromDB(2);
        data_listView=dataChange2(details);
        adapter_listview=new SimpleAdapter(this,data_listView,R.layout.item_listview_income
                , new String[]{"dateTime","category","money"},new int[]{R.id.textView_listview_date,
                R.id.textView_listview_category,R.id.textView_listview_money});
        listView_income.setAdapter(adapter_listview);

    }
    @OnClick(R.id.textView_datetime)
    private void creatTime(View v){
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String month=monthOfYear + 1+"";
                if(month.length()==1)
                    month="0"+month;
                String day=dayOfMonth+"";
                if(day.length()==1)
                    day="0"+day;
                String datetime=year+"-" +month +"-" + day;

                textViewDatetime.setText(datetime);
            }
        }, calendar.get(Calendar.YEAR), calendar
                .get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }


    @OnClick({R.id.button_add_income,R.id.button_orderby_desc,R.id.button_orderby_esc})

    private void onClick(View v){
        switch (v.getId()){
            case R.id.button_add_income:
                Detail detail=new Detail();
                detail.setDateTime(textViewDatetime.getText().toString());
                String text = editTextMoney.getText().toString();
                if ("".equals(text)) {
                    Toast.makeText(this, "输入钱数", Toast.LENGTH_LONG).show();
                    return;
                }
                detail.setMoney(Integer.parseInt(text.toString()));
                detail.setCategory(category);
                try {
                    dbUtils.save(detail);
                    Toast.makeText(this,"插入成功",Toast.LENGTH_LONG).show();
                } catch (DbException e) {
                    e.printStackTrace();
                    Toast.makeText(this,"插入失败",Toast.LENGTH_LONG).show();
                }
                updateListView(2);
                break;
            case R.id.button_orderby_desc:
                updateListView(4);
                break;
            case R.id.button_orderby_esc:
                updateListView(3);
                break;
            default:
                break;
        }
    }
    //listview 刷新
    private void updateListView(int i){
        selectFromDB(i);
        data_listView.clear();
        data_listView.addAll(dataChange2(details));
        adapter_listview.notifyDataSetChanged();

    }
    //数据库查询
    private void selectFromDB(int i){
        try {
            switch (i) {
                case 1:
                    totadate = dbUtils.findAll(Selector.from(Category.class)
                                    .where("_type", "=", "0")
                    );
                    break;
                case 2:
                    details= dbUtils.findAll(Selector.from(Detail.class)
                            .orderBy("datetime",false));
                    break;
                case 3:
                    details= dbUtils.findAll(Selector.from(Detail.class)
                            .orderBy("money",false));
                    break;
                case 4:
                    details= dbUtils.findAll(Selector.from(Detail.class)
                            .orderBy("money",true));
                    break;
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    private List<Map<String,Object>> dataChange(List<Category> totadate){
        List<Map<String,Object>>list=new ArrayList<Map<String, Object>>();

        for (Category category: totadate) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", category.getId());
            map.put("typeName", category.getTypeName());
            map.put("typeNum", category.getTypeNum());
            list.add(map);
        }
        return list;
    }
    private List<Map<String,Object>> dataChange2(List<Detail> totadate){
        List<Map<String,Object>>list=new ArrayList<Map<String, Object>>();
        for (Detail detail : totadate) {
            if ("1".equals(detail.getCategory().getTypeNum()))
                continue;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id",detail.getId());
            map.put("dateTime",detail.getDateTime());
            map.put("money",detail.getMoney());
            map.put("category",detail.getCategory().getTypeName());
            list.add(map);
        }
        return list;
    }

}