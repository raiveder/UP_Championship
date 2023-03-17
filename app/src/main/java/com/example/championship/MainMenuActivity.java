package com.example.championship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
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
public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    public static String Name;
    public static String Avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        InitializeComponent();

        setFeelings();
        setQuotes();
    }

    private void InitializeComponent() {

        new DownloadImages(findViewById(R.id.avatar)).execute(Avatar);

        findViewById(R.id.menu).setOnClickListener(this);
        findViewById(R.id.avatar).setOnClickListener(this);
        findViewById(R.id.profile).setOnClickListener(this);

        TextView tvGreeting = findViewById(R.id.tvGreeting);
        tvGreeting.setText("С возвращением, " + Name + "!");
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

                AdapterFeelings adapter = new AdapterFeelings(MainMenuActivity.this,
                        feelingsList);
                RecyclerView recyclerView = findViewById(R.id.rvFeelings);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<FeelingsLists> call, Throwable t) {

                Toast.makeText(MainMenuActivity.this, "Ошибка: " + t.getMessage(),
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

                AdapterQuotes adapterQuotes = new AdapterQuotes(MainMenuActivity.this,
                        response.body().getData());
                ListView lvQuotes = findViewById(R.id.lvQuotes);
                lvQuotes.setAdapter(adapterQuotes);
            }

            @Override
            public void onFailure(Call<QuotesLists> call, Throwable t) {

                Toast.makeText(MainMenuActivity.this, "Ошибка: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.menu:
                startActivity(new Intent(MainMenuActivity.this, MenuActivity.class));
                break;

            case R.id.avatar:
            case R.id.profile:
                startActivity(new Intent(MainMenuActivity.this,
                        ProfileActivity.class));
                break;
        }
    }
}