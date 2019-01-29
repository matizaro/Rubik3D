package com.mycompany.myjpctapp.screens;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mycompany.myjpctapp.R;
import com.mycompany.myjpctapp.StaticThings;


public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        EditText e1 = (EditText) findViewById(R.id.rotatingTImeText);

        e1.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        e1.setText(String.valueOf(StaticThings.wallSpeed));
        e1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    String number = ((EditText) v).getText().toString();
                    try {
                        int n = Integer.valueOf(number);

                        SharedPreferences mPrefs = getSharedPreferences("rubikVariables", MODE_PRIVATE);
                        SharedPreferences.Editor editor = mPrefs.edit();

                        StaticThings.rotatingSpeed = n;

                        editor.putInt("defaultRotatingWallSpeed", n);
                        editor.commit();

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
        EditText e2 = (EditText) findViewById(R.id.rotatingCubeTimeText);
        e2.setText(String.valueOf(StaticThings.rotatingSpeed));
        e2.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        e2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    String number = ((EditText) v).getText().toString();
                    try {
                        int n = Integer.valueOf(number);

                        SharedPreferences mPrefs = getSharedPreferences("rubikVariables", MODE_PRIVATE);
                        SharedPreferences.Editor editor = mPrefs.edit();

                        StaticThings.rotatingSpeed = n;

                        editor.putInt("defaultRotatingCubeSpeed", n);
                        editor.commit();

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        ((Button) findViewById(R.id.backMennuButton3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/BebasNeue.otf");
        Typeface tf2 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface tf3 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Regular.ttf");

        ((TextView)findViewById(R.id.textView10)).setTypeface(tf2);
        ((TextView)findViewById(R.id.textView5)).setTypeface(tf3);
        ((TextView)findViewById(R.id.textView6)).setTypeface(tf3);
        ((TextView)findViewById(R.id.textView11)).setTypeface(tf3);
        ((TextView)findViewById(R.id.textView12)).setTypeface(tf3);
        ((Button)findViewById(R.id.backMennuButton3)).setTypeface(tf);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
