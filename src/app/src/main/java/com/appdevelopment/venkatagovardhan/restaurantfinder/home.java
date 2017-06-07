package com.appdevelopment.venkatagovardhan.restaurantfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Venkata Govardhan on 3/11/2016.
 */
public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }

    public void Logout(View v){
        Intent redirect = new Intent(home.this, MainActivity.class);
        startActivity(redirect);
        Toast temp = Toast.makeText(home.this , "Logged out successfully" , Toast.LENGTH_SHORT);
        temp.show();

    }
    public void places(View v){
        Intent redirect = new Intent(home.this, GooglePlacesActivity.class);
        startActivity(redirect);


    }
    public void foodtype(View v){
        Intent redirect = new Intent(home.this, type.class);
        startActivity(redirect);


    }
    public void bmi(View v){
        Intent redirect = new Intent(home.this, BMI.class);
        startActivity(redirect);


    }
    public void dietoverview(View v){
        Intent redirect = new Intent(home.this, CalorieCounterOverviewActivity.class);
        startActivity(redirect);


    }
    public void dietplan(View v){
        Intent redirect = new Intent(home.this, CalorieCounterMainActivity.class);
        startActivity(redirect);


    }
    public void cal(View v){
        Intent redirect = new Intent(home.this, calorieinfo.class);
        startActivity(redirect);


    }
}    