package com.kevin.cn.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Foreign;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2015/4/12.
 */
@Table(name="tb_income_expenses_detail")
public class Detail {
    @Id(column = "_id")
    private long id;
    @Column(column = "datetime",defaultValue = "0000-00-00")
    private String dateTime;
    @Column(column = "money",defaultValue = "0")
    private int money;
    @Foreign(column = "name_id",foreign = "_id")
    private Category category;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
