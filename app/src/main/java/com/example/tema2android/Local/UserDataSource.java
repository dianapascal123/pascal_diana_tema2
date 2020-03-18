package com.example.tema2android.Local;

import com.example.tema2android.Database.IUserDataSource;
import com.example.tema2android.Model.User;

import java.util.List;

import io.reactivex.Flowable;

public class UserDataSource implements IUserDataSource {
    private UserDAO userDAO;
    private static UserDataSource mInstance;

    private UserDataSource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public static UserDataSource getInstance(UserDAO userDAO){
        if(mInstance == null){
            mInstance = new UserDataSource(userDAO);
        }
        return mInstance;
    }

    @Override
    public Flowable<User> getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    public Flowable<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User findByName(String first) {
        return userDAO.findByName(first);
    }

    @Override
    public void insertAll(User... users) {
        userDAO.insertAll(users);
    }

    @Override
    public void updateUser(User... users) {
        userDAO.updateUser(users);
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    public void deleteAllUsers() {
        userDAO.deleteAllUsers();
    }
}
