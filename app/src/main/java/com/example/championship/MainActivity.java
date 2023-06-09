package com.example.championship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeComponent();

        setFeelings();
        setQuotes();
    }

    private void InitializeComponent() {

        new DownloadImages(findViewById(R.id.imgAvatar), findViewById(R.id.pbWait)).
                execute(user.getAvatar());

        findViewById(R.id.imgMenu).setOnClickListener(this);
        findViewById(R.id.imgAvatar).setOnClickListener(this);
        findViewById(R.id.imgProfile).setOnClickListener(this);
        findViewById(R.id.imgListen).setOnClickListener(this);

        TextView tvGreeting = findViewById(R.id.tvGreeting);
        tvGreeting.setText("С возвращением, " + user.getNickName() + "!");
    }

    private void setFeelings() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<FeelingsLists> call = retrofitAPI.getFeelings();

        call.enqueue(new Callback<FeelingsLists>() {

            @Override
            public void onResponse(Call<FeelingsLists> call, Response<FeelingsLists> response) {

                List<Feelings> feelingsList = response.body().getData();
                Collections.sort(feelingsList);

                AdapterFeelings adapter = new AdapterFeelings(MainActivity.this,
                        feelingsList);
                RecyclerView recyclerView = findViewById(R.id.rvFeelings);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<FeelingsLists> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Ошибка: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setQuotes() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<QuotesLists> call = retrofitAPI.getQuotes();

        call.enqueue(new Callback<QuotesLists>() {

            @Override
            public void onResponse(Call<QuotesLists> call, Response<QuotesLists> response) {

                AdapterQuotes adapterQuotes = new AdapterQuotes(MainActivity.this,
                        response.body().getData());
                ListView lvQuotes = findViewById(R.id.lvQuotes);
                lvQuotes.setAdapter(adapterQuotes);
            }

            @Override
            public void onFailure(Call<QuotesLists> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Ошибка: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgMenu:
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
                break;

            case R.id.imgAvatar:
            case R.id.imgProfile:
                startActivity(new Intent(MainActivity.this,
                        ProfileActivity.class));
                break;

            case R.id.imgListen:
                startActivity(new Intent(MainActivity.this,
                        ListenActivity.class));
                break;
        }
    }
}