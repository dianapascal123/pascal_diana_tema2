package com.example.tema2android.Local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tema2android.Model.User;

@Database(entities = User.class, version = 1,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDAO userDao();

    private static UserDatabase mInstance;
    public static UserDatabase getInstance(Context context){
        if(mInstance == null){
            mInstance = Room.databaseBuilder(context, UserDatabase.class, "UsersDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }
}
