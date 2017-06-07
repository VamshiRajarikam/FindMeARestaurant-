package com.appdevelopment.venkatagovardhan.restaurantfinder;

/**
 * Created by Venkata Govardhan on 4/7/2016.
 */

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.androidplot.xy.XYPlot;

public class CalorieCounterOverviewActivity extends Activity{

    CalorieCounterDbAdapter db = new CalorieCounterDbAdapter(this);

    private XYPlot mySimpleXYPlot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);

        db.open();

/*
		// POGING EEN GRAFIEK TE MAKEN

        // Initialize our XYPlot reference:
        mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);

//         Create two arrays of y-values to plot:
        Number[] caloriesNumbers = db.getCalForGraph();
        Number[] protienNumbers = db.getProtForGraph();
        PixelUtils.init(CalorieCounterOverviewActivity.Context);

        String[] date = { "dag1", "dag2", "dag3" };

        int[] date2 = {1, 2, 3};

        Number[] date3 = {1, 2, 3};

        // Turn the above arrays into XYSeries:
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(caloriesNumbers), Arrays.asList(caloriesNumbers),          // SimpleXYSeries takes a List so turn our array into a List
                "Series1");
                 // Set the display title of the series

        // Same as abovwele, for series2
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(protienNumbers), SimpleXYSeries.ArrayFormat.XY_VALS_INTERLEAVED,
                "Series2");

        // Create a formatter to use for drawing a series using LineAndPointRenderer:
        LineAndPointFormatter series1Format = new LineAndPointFormatter(150,150,190,null);              // fill color (optional)

        // Add series1 to the xyplot:
        mySimpleXYPlot.addSeries(series1, series1Format);

        // Same as above, with series2:
        mySimpleXYPlot.addSeries(series2, new LineAndPointFormatter(150,150,190,null));


        // Reduce the number of range labels
        mySimpleXYPlot.setTicksPerRangeLabel(3);

        // By default, AndroidPlot displays developer guides to aid in laying out your plot.
        // To get rid of them call disableAllMarkup():


        // EINDE GRAFIEK POGING




        // Oude weergave kcal en eiwitten in kolommen

*/



        ListView listContent = (ListView)findViewById(R.id.contentlist);

        Cursor cursor = db.getOverviewDate();
        startManagingCursor(cursor);

        final String[] columns = { CalorieCounterDbAdapter.C_DATE, CalorieCounterDbAdapter.C_CAL, CalorieCounterDbAdapter.C_PROT};
        int[] to = new int[]{R.id.date, R.id.calories, R.id.protien};

        SimpleCursorAdapter cursorAdapter =
                new SimpleCursorAdapter(this, R.layout.row, cursor, columns, to);

        listContent.setAdapter(cursorAdapter);

        db.close();

    }
}