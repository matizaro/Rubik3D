package com.mycompany.myjpctapp.screens;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mycompany.myjpctapp.R;


public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        final MainMenuActivity p = this;

        Button start = (Button) findViewById(R.id.selectGameButton);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/BebasNeue.otf");
        start.setTypeface(tf);
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(p, SelectGameActivity.class);
                startActivity(intent);
            }
        });

        Button highScores = (Button) findViewById(R.id.highscoresButton);
        highScores.setTypeface(tf);

        highScores.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(p, HighScoresActivity.class);
                startActivity(intent);
            }
        });

        Button credits = (Button) findViewById(R.id.creditsButton);
        credits.setTypeface(tf);

//        credits.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(p, SettingsActivity.class);
//                startActivity(intent);
//
//            }
//        });

        Button settings = (Button) findViewById(R.id.settingsButton);
        settings.setTypeface(tf);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(p, SettingsActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
