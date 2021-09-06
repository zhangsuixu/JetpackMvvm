package com.zx.test.hilt.demo2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * ZhangSuiXu
 * 2021/9/6
 */
@Dao
public interface BookDao {

    @Insert
    void insert(Book book);

    @Query("SELECT * FROM book")
    List<Book> queryBookAll();
}
