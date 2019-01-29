package com.mycompany.myjpctapp;

import com.threed.jpct.Matrix;

/**
 * Created by Welcome on 2015-09-16.
 */
public class LoadRubikStateContainer {
    public long startTime, pauseTime;
    public int[] tempTable, resetTempTable;
    public float[][] cube, resetCube;
    public int rotationLevel, rotationSide;
    public String moves;
}