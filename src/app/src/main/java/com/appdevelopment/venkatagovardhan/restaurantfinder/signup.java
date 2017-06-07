package com.appdevelopment.venkatagovardhan.restaurantfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Venkata Govardhan on 2/16/2016.
 */
public class signup extends AppCompatActivity{

   datatbase helper = new datatbase(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .7));

    }


    public void getinto(View v)
    {

        if(v.getId()== R.id.btn_signup)
        {
            EditText name = (EditText)findViewById(R.id.txt_fname);
            EditText lname = (EditText)findViewById(R.id.txt_lname);
            EditText uname = (EditText)findViewById(R.id.txt_uname);
            EditText pass1 = (EditText)findViewById(R.id.txt_pword);
            EditText pass2 = (EditText)findViewById(R.id.txt_pword2);

            String namestr = name.getText().toString();
            String lnamestr = lname.getText().toString();
            String unamestr = uname.getText().toString();
            String pass1str = pass1.getText().toString();
            String pass2str = pass2.getText().toString();
            if (namestr.isEmpty() && lnamestr.isEmpty() && unamestr.isEmpty() && pass1str.isEmpty() && pass2str.isEmpty())
            {
                Toast pass = Toast.makeText(signup.this , "Fill the required fields" , Toast.LENGTH_SHORT);
                pass.show();
            }

            else {

                if (!pass1str.equals(pass2str)) {
                    //popup msg
                    Toast pass = Toast.makeText(signup.this, "Passwords don't match!", Toast.LENGTH_SHORT);
                    pass.show();
                } else {
                    //insert the detailes in database
                    contact c = new contact();
                    c.setName(namestr);
                    c.setEmail(lnamestr);
                    c.setUname(unamestr);
                    c.setPass(pass1str);


                    helper.insertContact(c);
                    Intent redirect = new Intent(signup.this, home.class);
                    startActivity(redirect);
                }
            }

        }

    }
}
