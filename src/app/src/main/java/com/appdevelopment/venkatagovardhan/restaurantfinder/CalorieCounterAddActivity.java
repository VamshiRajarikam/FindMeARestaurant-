package com.appdevelopment.venkatagovardhan.restaurantfinder;

/**
 * Created by Venkata Govardhan on 4/7/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;

public class CalorieCounterAddActivity extends Activity {

    CalorieCounterDbAdapter db = new CalorieCounterDbAdapter(this);

    EditText textNutName;
    EditText textNutGram;
    EditText textNutCal;
    EditText textNutProt;

    DatePicker DatePicker;
    Date date;
    int year;
    int month;
    int day;
    String stringDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        // Button resources connect add button object and listener
        Button buttonAddNutValues = (Button) findViewById(R.id.buttonAddNutValues);
        buttonAddNutValues.setOnClickListener(buttonAddNutValuesListener);

        Button buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(buttonBackListener);
    }

    // Add to the table as you click on the 'Add button'
    private OnClickListener buttonAddNutValuesListener = new OnClickListener() {
        public void onClick(View v) {
            db.open();
            long id = 0;
            try {
                // Date of converting the date picker widget to a String
                DatePicker = (DatePicker) findViewById(R.id.datePicker);
                year = DatePicker.getYear();
                month = DatePicker.getMonth() + 1;
                day = DatePicker.getDayOfMonth();
                Date date = new Date((year - 1900), month, day);
                String stringDate = date.toString();
                long longDate = date.getTime();
                String stringDate2 = "" + (day) + (month);
                int intDate = Integer.parseInt(stringDate2);

                // Edittext fields verbinden aan hun resource
                textNutName = (EditText) findViewById(R.id.textNutName);
                textNutGram = (EditText) findViewById(R.id.textNutGram);
                textNutCal = (EditText) findViewById(R.id.textNutCal);
                textNutProt = (EditText) findViewById(R.id.textNutProt);

                // Check whether all fields are filled, if not then a Toast notification
                if (textNutName.getText().toString().equals("")
                        || textNutGram.getText().equals("")
                        || textNutCal.getText().equals("")
                        || textNutProt.getText().equals("")) {
                    CharSequence ToastFieldsEmpty = "\n" + "Fill in all fields";
                    Context context = getApplicationContext();
                    int duur = Toast.LENGTH_LONG;
                    Toast toast2 = Toast.makeText(context, ToastFieldsEmpty, duur);
                    toast2.show();
                } else {

                    // Call the "insertMultiple 'method from the DbAdapter to
                    db.insertMultiple(intDate,
                            textNutName.getText().toString(), Double
                                    .parseDouble(textNutGram.getText()
                                            .toString()), Double
                                    .parseDouble(textNutCal.getText()
                                            .toString()), Double
                                    .parseDouble(textNutProt.getText()
                                            .toString()));

                    // Toast message that indicates what and how much has been added
                    id = db.getAllEntries();
                    Context context = getApplicationContext();
                    CharSequence text = "The Food '"
                            + textNutName.getText()
                            + "' is successfully added\nTotal number of rows in the database is now: "
                            + id;
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    // The text fields empty again after adding to the
                    // database
                    textNutName.setText("");
                    textNutGram.setText("");
                    textNutCal.setText("");
                    textNutProt.setText("");
                }
            } catch (Exception ex) {
            }
            db.close();
        }

    };

    // Back to the Main Activity as the "back button" is pressed
    private OnClickListener buttonBackListener = new OnClickListener() {
        public void onClick(View v) {
            Intent myIntent = new Intent(CalorieCounterAddActivity.this,
                    CalorieCounterMainActivity.class);
            CalorieCounterAddActivity.this.startActivity(myIntent);
        }
    };

}