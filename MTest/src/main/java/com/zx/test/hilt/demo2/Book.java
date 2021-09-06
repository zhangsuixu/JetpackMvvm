package com.zx.test.hilt.demo2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * ZhangSuiXu
 * 2021/9/6
 */
@Entity(tableName = "book")
public class Book {

    @PrimaryKey(autoGenerate = true)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
