package com.mycompany.myjpctapp.screens;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mycompany.myjpctapp.HSTable;
import com.mycompany.myjpctapp.R;
import com.mycompany.myjpctapp.StaticThings;
import com.threed.jpct.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class EndScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/BebasNeue.otf");
        Typeface tf2 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Regular.ttf");

        ((TextView) findViewById(R.id.textView)).setTypeface(tf2);
//        ((TextView) findViewById(R.id.endMovesText)).setTypeface(tf);
//        ((TextView) findViewById(R.id.finalTimeText)).setTypeface(tf);
        ((TextView) findViewById(R.id.textView4)).setTypeface(tf2);
        ((TextView) findViewById(R.id.endHSText)).setTypeface(tf2);
        ((Button) findViewById(R.id.endMenuButton)).setTypeface(tf);


        TextView timeText = (TextView) findViewById(R.id.finalTimeText);
        TextView movesText = (TextView) findViewById(R.id.endMovesText);
        TextView endHSText = (TextView) findViewById(R.id.endHSText);

        Button endButton = (Button) findViewById(R.id.endMenuButton);
        endButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


        List<HSTable> tableList = new ArrayList<>();
        SharedPreferences mPrefs = getSharedPreferences("HIGHSCORELIST", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        int length = mPrefs.getInt("hs_length", -1);
        HSTable newTable = new HSTable((long)this.getIntent().getExtras().get("TIME_LONG"),
                (String)this.getIntent().getExtras().get("MOVES"));

        for(int i=0; i<length; i++){
            HSTable t = gson.fromJson(mPrefs.getString("hsTable_"+i, ""), HSTable.class);
            tableList.add(t);
        }

        tableList.add(newTable);
        Collections.sort(tableList);

        for(int i=0; i<tableList.size(); i++){
            System.out.println(tableList.get(i).toS());
        }
        if(tableList.indexOf(newTable)<5){
            endHSText.setText("#"+Integer.toString(tableList.indexOf(newTable)+1));
        }

        timeText.setText(StaticThings.convetTime(newTable.getStringTime()));

        movesText.setText(StaticThings.convertMovesText(newTable.moves));

        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putInt("hs_length", (tableList.size() > 5 ? 5 : tableList.size()));

        for(int j=0;j<tableList.size() && j<5 ;j++){
            edit.putString("hsTable_"+j, gson.toJson(tableList.get(j)) );
        }
        edit.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_end_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
