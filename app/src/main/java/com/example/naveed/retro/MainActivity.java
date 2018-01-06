package com.example.naveed.retro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MTAG";
    EditText Name, Username, Password;
    Button Insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name= (EditText) findViewById(R.id.et1);
        Username= (EditText) findViewById(R.id.et2);
        Password= (EditText) findViewById(R.id.et3);
        Insert= (Button) findViewById(R.id.btn);

        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user= new User(
                        Name.getText().toString(),
                        Username.getText().toString(),
                        Password.getText().toString()
                );
                Login(user);

            }
        });



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebServices service = retrofit.create(WebServices.class);

        Call<List<User>> call = service.userRetrieve();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                for (User user : users) {
                    Log.d(TAG, "onResponse: " + user.getId());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "on failure : " + t);
            }
        });
    }

    public void Login (User user){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebServices service = retrofit.create(WebServices.class);

        Call<User> call = service.userLogin(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(MainActivity.this, "Successfull insert ID: " + response.body().getId() , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to insert", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: "+ t);
            }
        });


    }
}
