package com.example.tema2android.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tema2android.Model.User;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM users")
    Flowable<List<User>> getAllUsers();

    @Query("SELECT * FROM users WHERE id = :userId")
    Flowable<User> getUserById(int userId);

    @Query("SELECT * FROM users WHERE name LIKE :name LIMIT 1")
    User findByName(String name);

    @Insert
    void insertAll(User... users);

    @Update
    void updateUser(User... users);

    @Delete
    void delete(User user);

    @Query("DELETE FROM users")
    void deleteAllUsers();
}

