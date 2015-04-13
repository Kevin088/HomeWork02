package com.kevin.cn.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2015/4/12.
 */
@Table(name="tb_income_expenses_name")
public class Category {
    @Id(column = "_id")
    private long id;
    @Column(column = "typename",defaultValue = " ")
    private String typeName;
    @Column(column = "_type",defaultValue = " ")
    private String typeNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(String typeNum) {
        this.typeNum = typeNum;
    }
}
