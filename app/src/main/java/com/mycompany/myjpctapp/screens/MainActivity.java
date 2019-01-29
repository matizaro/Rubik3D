package com.mycompany.myjpctapp.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mycompany.myjpctapp.LoadRubikStateContainer;
import com.mycompany.myjpctapp.R;
import com.mycompany.myjpctapp.RubikController;
import com.mycompany.myjpctapp.RubikTimer;
import com.mycompany.myjpctapp.SideHolder;
import com.mycompany.myjpctapp.StaticThings;
import com.mycompany.myjpctapp.screens.EndScreenActivity;
import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

import java.util.Arrays;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class MainActivity extends Activity {
    private GLSurfaceView mGLView;
    private MyRenderer renderer = null;
    RubikController rubik;
    LoadRubikStateContainer resumeObject;
    String mode;
    boolean finished=false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create();
    }

    private void create(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        StaticThings.rubikTimer = new RubikTimer();

        SharedPreferences mPrefs = getSharedPreferences("rubikVariables", MODE_PRIVATE);
        StaticThings.wallSpeed = mPrefs.getInt("defaultRotatingWallSpeed", 300);
        StaticThings.rotatingSpeed = mPrefs.getInt("defaultRotatingCubeSpeed", 300);

        mode = getIntent().getStringExtra("MODE");

        StaticThings.rubikActivity = this;

        setContentView(R.layout.activity_main);

        mGLView = (GLSurfaceView) findViewById(R.id.cubeView);
        mGLView.setEGLContextClientVersion(2);
        renderer = new MyRenderer(this);
        mGLView.setRenderer(renderer);

        StaticThings.robotoBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        StaticThings.robotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        StaticThings.robotoLight =  Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        StaticThings.robotoThin = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");

        Typeface face= StaticThings.robotoThin;

//        TextView timeText = (TextView) findViewById(R.id.timeText);
//        timeText.setTypeface(face);

        StaticThings.timer = (TextView) findViewById(R.id.timeCounter);
        StaticThings.timer.setTypeface(face);

//        TextView movesText = (TextView) findViewById(R.id.movesText);
//        movesText.setTypeface(face);

        StaticThings.moves = (TextView) findViewById(R.id.movesCounter);
        StaticThings.moves.setTypeface(face);

        Button l = (Button) findViewById(R.id.rotateLeft);
        (l).setTypeface(face);
        l.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rubik.rotateCube(SideHolder.CCW);
            }
        });
        Button r = (Button) findViewById(R.id.rotateRight);
        (r).setTypeface(face);
        r.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rubik.rotateCube(SideHolder.CW);
            }
        });

        (findViewById(R.id.RotateRightLayout)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rubik.rotateCube(SideHolder.CW);
            }
        });
        (findViewById(R.id.RotateLeftLayout)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rubik.rotateCube(SideHolder.CCW);
            }
        });
        Button reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StaticThings.rubikTimer.reset();
                rubik.reset();
            }
        });
//        Button menu = (Button) findViewById(R.id.resumeMenu);
//        menu.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                StaticThings.rubikTimer.pause();
//                //rubik.saveState(getSharedPreferences("rubikVariables", MODE_PRIVATE));
//                finish();
//            }
//        });
    }
    public void onPause(){
        super.onPause();
        if(rubik!=null) {
            StaticThings.rubikTimer.pause();

            rubik.saveState(getSharedPreferences("rubikVariables", MODE_PRIVATE));
        }

    }
    protected void onResume() {
        super.onResume();

        SharedPreferences mPrefs = getSharedPreferences("rubikVariables", MODE_PRIVATE);
        if(mode.equals("RESUME_GAME")){
            if(mPrefs.getBoolean("isSaved", false)){
                resumeObject = new LoadRubikStateContainer();
                StaticThings.rubikTimer.startTime = mPrefs.getLong("startTime", 0);
                StaticThings.rubikTimer.pauseTime = mPrefs.getLong("pauseTime", 0);

                if(mPrefs.getBoolean("isPaused", false)){
                    StaticThings.rubikTimer.resume();

                System.out.println("Timers "+StaticThings.rubikTimer.startTime+" "+StaticThings.rubikTimer.pauseTime);
                }
                Gson gson = new Gson();

                String tt = mPrefs.getString("tempTable", "");
                resumeObject.tempTable = gson.fromJson(tt, int[].class);


                String rtt = mPrefs.getString("resetTempTable", "");
                resumeObject.resetTempTable = gson.fromJson(rtt, int[].class);

                resumeObject.cube = new float[resumeObject.resetTempTable.length][];
                resumeObject.resetCube = new float[resumeObject.resetTempTable.length][];

                for(int i=0;i<resumeObject.resetTempTable.length; i++){

                    String rm = mPrefs.getString("rotationMatrix_" + i, "");
                    resumeObject.cube[i]=gson.fromJson(rm, float[].class);

                    String rrm = mPrefs.getString("resetRotationMatrix_" + i, "");
                    resumeObject.resetCube[i] = gson.fromJson(rrm, float[].class);
                }
                resumeObject.moves = mPrefs.getString("moves", "0");

            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;

    public boolean onTouchEvent(MotionEvent e) {
        renderer.touchMethod(e);
        return true;
    }

    class MyRenderer implements GLSurfaceView.Renderer{

        private FrameBuffer fb = null;
        private World world;
        private Light sun = null;
        private RGBColor back = new RGBColor(58, 57, 63);
        Context context;
        private Camera cam;


        private float mPreviousX=0, dx=0,anglex=0;
        private float mPreviousY=0, dy=0, angley=0;

        public MyRenderer(Context context){
            init(context);
        }
        public MyRenderer(Context context, AttributeSet attrs) {
            init(context);
        }

        public MyRenderer(Context context, AttributeSet attrs, int defStyle) {
            init(context);
        }

        private void init(Context context) {
            this.context=context;

        }
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            world = new World();
            world.setAmbientLight(130, 130, 130);

            sun = new Light(world);
            sun.setIntensity(80, 80, 80);

            if(resumeObject!=null && mode.equals("RESUME_GAME"))
                rubik = new RubikController(context, world, resumeObject);
            else
                rubik = new RubikController(context, world);

            endTable = new int[rubik.getTempTable().length];
            for(int i=0;i<endTable.length; i++)
                endTable[i] = i;

            cam = world.getCamera();
            cam.moveCamera(Camera.CAMERA_MOVEOUT, 12);
            cam.lookAt(rubik.getCenter());

            SimpleVector sv = new SimpleVector();
            sv.set(rubik.getCenter());
            sv.y -= 0;
            sv.z -= 120;

            sun.setPosition(sv);

//            Light sun2 = new Light(world);
//            sun2.setIntensity(150, 150, 150);
//            SimpleVector sv2 = new SimpleVector();
//            sv2.set(rubik.getCenter());
//            sv.y += 0;
//            sv.z -= 0;
//            sun2.setPosition(sv);

        }
        int width, height;
        int[] endTable;
        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            this.width=width; this.height=height;
            fb = new FrameBuffer(width, height); // OpenGL ES 2.0 constructor
        }
        boolean areBoundsSet=false;
        @Override
        public void onDrawFrame(GL10 gl) {
            fb.clear(back);
            world.renderScene(fb);
            rubik.setPosition();
            world.draw(fb);
            fb.display();
            if(!areBoundsSet){
                rubik.setMax(height, width);
                rubik.setCubeBounds(cam, fb);
                areBoundsSet=true;
            }
            if(Arrays.equals(rubik.getTempTable(),endTable) && !finished){
                finished = true;

                endActivity();
            }
        }
        synchronized private void endActivity() {
            runOnUiThread(new Runnable() {
                public void run() {
                    StaticThings.rubikTimer.pause();
                    SharedPreferences.Editor edit = getSharedPreferences("rubikVariables", MODE_PRIVATE).edit();
                    edit.putBoolean("hasFinished", true);
                    edit.commit();
                    Intent intent = new Intent(StaticThings.rubikActivity, EndScreenActivity.class);

                    intent.putExtra("TIME_STRING", StaticThings.rubikTimer.getStringTime());
                    intent.putExtra("TIME_LONG", StaticThings.rubikTimer.getTime());
                    intent.putExtra("MOVES", StaticThings.moves.getText().toString());
                    StaticThings.rubikActivity.startActivity(intent);
                    finish();
                }
            });
        }
        public RubikController getCube(){
            return rubik;
        }
        public void touchMethod(MotionEvent e){
            float x = e.getX();
            float y = e.getY();

            switch (e.getAction()) {
                case MotionEvent.ACTION_MOVE:

                    rubik.onMove(e);
                    break;

                case MotionEvent.ACTION_DOWN:  // finger up event
                    System.out.println(x + " " + y);
                    if(rubik!=null)
                        rubik.onTouch(e);
//                rubik.printCenters(cam,fb);

                    //rubik.printCenters(cam,fb);
                    break;
                case MotionEvent.ACTION_UP:  // finger up event
                    //System.out.println(x+" "+y);
                    if(rubik!=null)
                        rubik.onReleaseTouch(e);

                    //rubik.printCenters(cam,fb);
                    break;
            }

            mPreviousX = x;
            mPreviousY = y;

        }
    }
}
