package com.example.tema2android.Database;

import com.example.tema2android.Model.User;

import java.util.List;

import io.reactivex.Flowable;

public interface IUserDataSource {
    Flowable<User> getUserById(int userId);
    Flowable<List<User>> getAllUsers();
    User findByName(String first);
    void insertAll(User... users);
    void updateUser(User... users);
    void delete(User user);
    void deleteAllUsers();
}
