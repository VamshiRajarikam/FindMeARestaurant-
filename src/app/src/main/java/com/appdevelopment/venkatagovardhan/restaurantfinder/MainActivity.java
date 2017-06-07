package com.appdevelopment.venkatagovardhan.restaurantfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contents_main);
    }
    public void login(View v){
        Intent redirect = new Intent(MainActivity.this, login.class);
        startActivity(redirect);

    }
    public void register(View v){
        Intent redirect = new Intent(MainActivity.this, signup.class);
        startActivity(redirect);

    }

}
