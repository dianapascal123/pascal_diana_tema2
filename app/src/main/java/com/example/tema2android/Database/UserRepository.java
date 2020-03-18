package com.example.tema2android.Database;

import com.example.tema2android.Model.User;

import java.util.List;

import io.reactivex.Flowable;

public class UserRepository implements IUserDataSource {
    private IUserDataSource mLocalDataSource;
    private static UserRepository mInstance;

    public UserRepository(IUserDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static UserRepository getInstance(IUserDataSource mLocalDataSource) {
        if (mInstance == null) {
            mInstance = new UserRepository(mLocalDataSource);
        }
        return mInstance;
    }

    @Override
    public Flowable<User> getUserById(int userId) {
        return mLocalDataSource.getUserById(userId);
    }

    @Override
    public Flowable<List<User>> getAllUsers() {
        return mLocalDataSource.getAllUsers();
    }

    @Override
    public User findByName(String first) {
        return mLocalDataSource.findByName(first);
    }

    @Override
    public void insertAll(User... users) {
        mLocalDataSource.insertAll(users);
    }

    @Override
    public void updateUser(User... users) {
        mLocalDataSource.updateUser(users);
    }

    @Override
    public void delete(User user) {
        mLocalDataSource.delete(user);
    }

    @Override
    public void deleteAllUsers() {
        mLocalDataSource.deleteAllUsers();
    }

}
