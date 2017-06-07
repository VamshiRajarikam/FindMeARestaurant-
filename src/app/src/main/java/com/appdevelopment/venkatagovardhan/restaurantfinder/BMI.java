package com.appdevelopment.venkatagovardhan.restaurantfinder;

/**
 * Created by Venkata Govardhan on 4/6/2016.
 */



        import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class BMI extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        setListeners();
    };

    private Button button_cal;
    private Button button_share;
    private EditText fieldheight;
    private EditText fieldweight;
    private EditText fieldsex;
    private TextView result;
    private TextView suggest;

    private void findViews()
    {
        button_cal=(Button)findViewById(R.id.btn_cal);
        button_share=(Button)findViewById(R.id.btn_share);
        fieldheight=(EditText)findViewById(R.id.height);
        fieldweight=(EditText)findViewById(R.id.weight);
        fieldsex=(EditText)findViewById(R.id.sex);
        result=(TextView)findViewById(R.id.result);
        suggest=(TextView)findViewById(R.id.suggest);
    }
    private void setListeners()
    {
        button_cal.setOnClickListener(calcBMI);
        button_share.setOnClickListener(shareResult);

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // TODO Auto-generated method stub
        menu.add(0, 1, 1, R.string.about);
        menu.add(0, 2, 2, R.string.exit);
        return super.onCreateOptionsMenu(menu);
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(2==item.getItemId())	{
            finish();
        }
        else if(1==item.getItemId())
        {
            openOptionsDialog();
        }
        return super.onOptionsItemSelected(item);
    };

    private Button.OnClickListener calcBMI=new Button.OnClickListener()
    {
        public void onClick(View v)
        {
            DecimalFormat df=new DecimalFormat("0.00");
            double height=Double.parseDouble(fieldheight.getText().toString())/100;
            double weight=Double.parseDouble(fieldweight.getText().toString());
            String sex=fieldsex.getText().toString();
            double BMI =weight/(height*height);

            result.setText(getString(R.string.result)+df.format(BMI));

            if(sex.equals("Female"))
            {
                if(BMI>29)
                {
                    suggest.setText(R.string.advice_fat_female);
                }
                else if(BMI>24)
                {
                    suggest.setText(R.string.advice_heavy_female);
                }
                else if(BMI>18)
                {
                    suggest.setText(R.string.advice_ok_female);
                }
                else if(BMI<18)
                {
                    suggest.setText(R.string.advice_light_female);
                }
            }

            else if(sex.equals("Male"))
            {
                if(BMI>30)
                {
                    suggest.setText(R.string.advice_fat_male);
                }
                else if(BMI>25)
                {
                    suggest.setText(R.string.advice_heavy_male);
                }
                else if(BMI>20)
                {
                    suggest.setText(R.string.advice_ok_male);
                }
                else if(BMI<20)
                {
                    suggest.setText(R.string.advice_light_male);
                }
            }
        }
    };

    private Button.OnClickListener shareResult=new Button.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT,"BMI" );

            intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_result)
                    +result.getText()+getResources().getString(R.string.share_suggest)
                    +suggest.getText()
                    +getResources().getString(R.string.share_invite));
            startActivity(Intent.createChooser(intent,
                    getResources().getString(R.string.share_title)));
        }
    };

    private void openOptionsDialog()
    {
        Toast.makeText(BMI.this,R.string.about_msg,Toast.LENGTH_SHORT).show();
    }
    public void diet(View v){
        Intent redirect = new Intent(BMI.this, type.class);
        startActivity(redirect);


    }
    public void calorie(View v){
        Intent redirect = new Intent(BMI.this, CalorieCounterMainActivity.class);
        startActivity(redirect);


    }
}

