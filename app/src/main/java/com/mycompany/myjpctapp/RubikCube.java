package com.mycompany.myjpctapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Display;

import com.google.gson.Gson;
import com.threed.jpct.Loader;
import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Msi on 2015-12-24.
 */
public class RubikCube {


    private final Context context;
    private final World world;

    private Object3D[] cube = null;
    private Object3D[] resetCube = null;
    Object3D centerpoint;

    private float[][] resetMatrix;
    private int[] resetTempTable;
    private int[] tempTable;

    RotationController rotationController;
    private SideHolder sideHolder;

    public RubikCube(Context context,World world){
        this.context=context;
        this.world=world;
        create();
        randomize();
        SharedPreferences.Editor edit = context.getSharedPreferences("rubikVariables", context.MODE_PRIVATE).edit();
        edit.putBoolean("hasFinished", false);
        edit.commit();
    }

    public RubikCube(Context context, World world, LoadRubikStateContainer resumeObject) {
        this.context=context;
        this.world=world;
        create();
        resume(resumeObject);

    }
    private void create(){

        centerpoint = new Object3D(0);
        centerpoint.build();
        world.addObject(centerpoint);

        resetCube = Loader.loadOBJ(context.getResources().openRawResource(R.raw.rc), context.getResources().openRawResource(R.raw.rcmtl), 1.0f);
        cube = Arrays.copyOf(resetCube, resetCube.length);
        resetMatrix = new float[cube.length][];
        for(int a=0;a<cube.length;a++){
            cube[a].build();
            cube[a].calcNormals();
            cube[a].setRotationPivot(new SimpleVector(0, 0, 0));
            world.addObject(cube[a]);
            centerpoint.addChild(cube[a]);
            resetMatrix[a]=cube[a].getRotationMatrix().getDump();
        }
        resetTempTable = new int[cube.length];

        for(int i = 0 ; i < resetTempTable.length ;i++)
            resetTempTable[i]=i;

        tempTable = Arrays.copyOf(resetTempTable, resetTempTable.length);

        sideHolder = new SideHolder();

        rotationController = new RotationController(this, StaticThings.wallSpeed, StaticThings.rotatingSpeed);
        rotationController.clearRotation();
    }
    private void resume(final LoadRubikStateContainer resumeObject){
        resetTempTable=resumeObject.resetTempTable;
        tempTable=resumeObject.tempTable;

        Object3D[] tab1 = Arrays.copyOf(resetCube, resetCube.length);

        for(int i=0;i<tab1.length;i++){
            tab1[i]=resetCube[resetTempTable[i]];
        }
        resetCube=tab1;

        Object3D[] tab2 = Arrays.copyOf(cube, cube.length);
        for(int i=0;i<tab2.length;i++){
            tab2[i]=cube[tempTable[i]];
        }
        cube = tab2;
        for(int i=0;i<cube.length; i++){
            Matrix m1 = new Matrix();
            m1.setDump(Arrays.copyOf(resumeObject.cube[i], 16));
            cube[i].setRotationMatrix(m1);
            resetMatrix[i]=Arrays.copyOf(resumeObject.resetCube[i], 16);
        }
        StaticThings.rubikActivity.runOnUiThread(new Runnable() {
            public void run() {
                StaticThings.setMovesText(resumeObject.moves);
            }
        });
    }

    public void saveState(SharedPreferences mPrefs){
        rotationController.stopCube();

        SharedPreferences.Editor edit = mPrefs.edit();

        edit.putBoolean("isRunning", StaticThings.rubikTimer.isRunning);
        edit.putBoolean("isPaused", StaticThings.rubikTimer.isPaused);
        edit.putLong("startTime", StaticThings.rubikTimer.startTime);
        edit.putLong("pauseTime", StaticThings.rubikTimer.pauseTime);
        System.out.println("Timers " + StaticThings.rubikTimer.startTime + " " + StaticThings.rubikTimer.pauseTime);

        Gson gson = new Gson();

        String tt = gson.toJson(tempTable);
        edit.putString("tempTable", tt);

        String rtt = gson.toJson(resetTempTable);
        edit.putString("resetTempTable", rtt);

        for(int i=0;i<cube.length; i++){
            String rm =  gson.toJson(cube[i].getRotationMatrix().getDump());
            edit.putString("rotationMatrix_" + i, rm);

            String rrm =  gson.toJson(this.resetMatrix[i]);
            edit.putString("resetRotationMatrix_"+i, rrm);
        }

        edit.putString("moves", StaticThings.moves.getText().toString());
        edit.putBoolean("isSaved", true);

        edit.commit();
    }
    public void randomize() {
        Random r = new Random();
//        int l = r.nextInt(50)+30;
        int l = 2;
        for(int i = 0 ; i < l ;i++){
            rotationController.rotateWallnoTiming(r.nextInt(12));
        }
        rotationController.stopCube();
        resetCube=Arrays.copyOf(cube, cube.length);
        resetTempTable=Arrays.copyOf(tempTable, tempTable.length);
        for(int i=0;i<cube.length;i++)
            resetMatrix[i] = Arrays.copyOf(resetCube[i].getRotationMatrix().getDump(),16);

    }
    public void rotateWall(int side){
        rotationController.rotateWall(side);

    }
    public void rotateCube(int side){
        rotationController.rotateCube(side);
    }

    public void rotateCenter(int side1, int side2, int side3){
        StaticThings.rubikTimer.start();
        rotationController.rotateCenter(side1, side2, side3);

    }

    public void stopCube(){
        rotationController.stopCube();
    }
    public void setPosition(){
        rotationController.setPosition();
    }
    public SideHolder getSideHolder() {
        return sideHolder;
    }
    public Object3D getCenterpoint() {
        return centerpoint;
    }
    public void setTempTable(int[] tempTable) {
        this.tempTable = tempTable;
    }
    public int[] getTempTable() {return tempTable; }
    public SimpleVector getCenter(){ return cube[13].getTransformedCenter();}
    public Object3D[] getCube(){return this.cube;}
    public void setCube(Object3D[] c){this.cube = c;}

    public void reset() {
        cube = Arrays.copyOf(resetCube, resetCube.length);

        tempTable = Arrays.copyOf(resetTempTable, resetTempTable.length);

        for(int i = 0 ; i < cube.length ;i++) {
            Matrix m = new Matrix();
            m.setDump(Arrays.copyOf(resetMatrix[i], 16));
            cube[i].setRotationMatrix(m);
        }
        sideHolder = new SideHolder();

        rotationController = new RotationController(this, StaticThings.wallSpeed, StaticThings.rotatingSpeed);
        rotationController.clearRotation();

        StaticThings.setMovesText("00");
    }
}
