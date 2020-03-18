package com.example.tema2android.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "mark")
    public Integer mark;

    @Ignore
    public User(String name, Integer mark) {
        this.name = name;
        this.mark = mark;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int uid) {
        this.id = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }
}
