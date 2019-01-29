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


public class SelectGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game);

        final SelectGameActivity p = this;

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/BebasNeue.otf");
        Button start = (Button) findViewById(R.id.newGameButton);

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(p, MainActivity.class);
                intent.putExtra("MODE", "NEW_GAME");
                startActivity(intent);
                finish();
            }
        });
        start.setTypeface(tf);

        Button resume = (Button) findViewById(R.id.resumeGameButton);

        if(getSharedPreferences("rubikVariables", MODE_PRIVATE).getBoolean("hasFinished", true))
            resume.setEnabled(false);

        resume.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(p, MainActivity.class);
                intent.putExtra("MODE", "RESUME_GAME");
                startActivity(intent);
                finish();
            }
        });
        resume.setTypeface(tf);

        Button back = (Button) findViewById(R.id.backSelectButton);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        back.setTypeface(tf);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_game, menu);
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
