package com.zx.test.room.demo1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * ZhangSuiXu
 * 2021/9/7
 *
 * @PrimaryKey 定义主键
 * @ColumnInfo name= ”age“  设置数据库中存储的字段名为age    typeAffinity = ColumnInfo.TEXT 设置存储类型。 不设置时按public String name进行默认设置
 */
@Entity(tableName = "student")
public class Student {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    public String name;

    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.TEXT)
    public String age;

    public Student(int id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    /**
     * 1.  Room只能识别和使用单一构造器,其他构造器需要@Ignore进行忽略
     * 2.  @Ignore可以用于字段，Room不会持久化被@Ignore标记的字段数据
     */
    @Ignore
    public Student(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
