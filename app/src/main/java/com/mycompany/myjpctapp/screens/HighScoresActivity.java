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
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mycompany.myjpctapp.HSTable;
import com.mycompany.myjpctapp.R;

import java.util.ArrayList;
import java.util.List;


public class HighScoresActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);


        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/BebasNeue.otf");
        Typeface tf2 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface tf3 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Regular.ttf");

        TextView[][] tab = new TextView[5][2];
        tab[0][0] = (TextView) findViewById(R.id.hsTw11); tab[0][1] = (TextView) findViewById(R.id.hsTw12);
        tab[1][0] = (TextView) findViewById(R.id.hsTw21); tab[1][1] = (TextView) findViewById(R.id.hsTw22);
        tab[2][0] = (TextView) findViewById(R.id.hsTw31); tab[2][1] = (TextView) findViewById(R.id.hsTw32);
        tab[3][0] = (TextView) findViewById(R.id.hsTw41); tab[3][1] = (TextView) findViewById(R.id.hsTw42);
        tab[4][0] = (TextView) findViewById(R.id.hsTw51); tab[4][1] = (TextView) findViewById(R.id.hsTw52);

        ((TextView)findViewById(R.id.textView7)).setTypeface(tf2);
        ((TextView)findViewById(R.id.textView8)).setTypeface(tf2);
        ((TextView)findViewById(R.id.textView9)).setTypeface(tf2);

        ((TextView) findViewById(R.id.textViewnumber0)).setTypeface(tf2);
        ((TextView) findViewById(R.id.textViewnumber1)).setTypeface(tf2);
        ((TextView) findViewById(R.id.textViewnumber2)).setTypeface(tf2);
        ((TextView) findViewById(R.id.textViewnumber3)).setTypeface(tf2);
        ((TextView) findViewById(R.id.textViewnumber4)).setTypeface(tf2);
        ((TextView) findViewById(R.id.textViewnumber5)).setTypeface(tf2);

        for(int i=0;i<tab.length;i++)
            for(int j=0;j<tab[i].length;j++)
                tab[i][j].setTypeface(tf3);

        ((Button)findViewById(R.id.backMenuButton2)).setTypeface(tf);

        Button b = (Button) findViewById(R.id.backMenuButton2);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences mPrefs = getSharedPreferences("HIGHSCORELIST", Context.MODE_PRIVATE);

        if(mPrefs==null)
            return;
        Gson gson = new Gson();
        int length = mPrefs.getInt("hs_length", -1);

        for(int i=0; i<length; i++){
            HSTable t = gson.fromJson(mPrefs.getString("hsTable_"+i, ""), HSTable.class);
            tab[i][0].setText( t.getStringTime() ); tab[i][1].setText( t.moves );
        }
        for(int i=length ;i<5; i++){
            tab[i][0].setText(""); tab[i][1].setText("");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_high_scores, menu);

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
