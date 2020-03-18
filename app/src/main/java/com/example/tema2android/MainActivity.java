package com.example.tema2android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tema2android.Database.UserRepository;
import com.example.tema2android.Local.UserDataSource;
import com.example.tema2android.Local.UserDatabase;
import com.example.tema2android.Model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity {

    private List<User> userList = new ArrayList<>();

    private Button addButton;
    private Button removeButton;
    private EditText nameEditText;
    private EditText markEditText;

    private CompositeDisposable compositeDisposable;
    private UsersAdapter adapter;
    private UserRepository userRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.rvUsers);
        adapter = new UsersAdapter(userList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        compositeDisposable = new CompositeDisposable();


        addButton = findViewById(R.id.addButton);
        removeButton = findViewById(R.id.removeButton);
        nameEditText = findViewById(R.id.nameTextView);
        markEditText = findViewById(R.id.markTextView);


        UserDatabase userDatabase = UserDatabase.getInstance(this);
        userRepository = UserRepository.getInstance(UserDataSource.getInstance(userDatabase.userDao()));
        loadData();

        addButton.setOnClickListener(v -> {
            Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                    String name = nameEditText.getText().toString();
                    Integer mark = Integer.parseInt(markEditText.getText().toString());

                    if (nameIsValid(name) && markIsValid(mark)) {
                        User user = new User(name, mark);
                        userRepository.insertAll(user);

                        nameEditText.getText().clear();
                        markEditText.getText().clear();

                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);

                        emitter.onComplete();
                    } else {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer() {
                        @Override
                        public void accept(Object o) throws Exception {
                            Toast.makeText(MainActivity.this, "User added", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            loadData();
                        }
                    });
        });

        removeButton.setOnClickListener(v -> {
            Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                    String name = nameEditText.getText().toString();
                    if (!name.isEmpty()) {
                        User user = userRepository.findByName(name);
                        if (user != null) {
                            userRepository.delete(user);

                            nameEditText.getText().clear();

                            InputMethodManager inputManager = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);

                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                            emitter.onComplete();
                        } else {
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer() {
                        @Override
                        public void accept(Object o) throws Exception {
                            Toast.makeText(MainActivity.this, "User deleted", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            loadData();
                        }
                    });
        });
    }

    private Boolean nameIsValid(String name) {
        return (name != null && !name.isEmpty() && name.matches("[a-zA-Z]+"));

    }

    private Boolean markIsValid(Integer mark) {
        return (mark >= 1 && mark <= 10);
    }

    private void loadData() {
        Disposable disposable = userRepository.getAllUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onGetAllUsersSuccess, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, throwable.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }


    private void onGetAllUsersSuccess(List<User> users) {
        this.userList.clear();
        this.userList.addAll(users);
        adapter.notifyDataSetChanged();
    }


}

