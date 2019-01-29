package com.mycompany.myjpctapp;

import com.threed.jpct.Object3D;

/**
 * Created by Welcome on 2015-09-09.
 */
public abstract class AbstractRotate {
    RubikCube cube;
    protected boolean isBusy;
    protected float angle, prevAngle, endAngle = (float) Math.PI / 2;
    protected int side;
    protected long startTime;
    protected boolean fasten;
    protected long fastenTime;
    int rotatingTime = 300;

    public AbstractRotate(RubikCube cube, int speed) {
        this.cube = cube;
        rotatingTime = speed;
        isBusy = false;
    }

    public void rotate(long time, int side) {
        if (isBusy) {
            rotateEndMethod();
        }

        fasten = false;
        isBusy = true;
        this.side = side;
        startTime = time;
        prevAngle = 0;
        angle = 0;
    }

    public void rotateEnd() {
        if(isBusy){
            fasten = false;
            isBusy = false;
            rotateEndMethod();
        }
//        cube.printState();
    }

    public void animate(long time){
        if(isBusy)
            rotateM(time, this.side);
    }
    private void rotateM(long time, int side){
        float tempAngle;
        if(fasten)
            tempAngle = (float) (Math.PI/((float)(2*fastenTime))*(time-this.startTime) );
        else
            tempAngle = (float) (Math.PI/((float)(2*rotatingTime))*(time-this.startTime) );
        if(tempAngle > (Math.PI/2)) {
            rotateEnd();
        }else{
            angle = tempAngle;
            rotateMethod(side);
            prevAngle = angle;
        }
    }
    protected abstract void rotateMethod(int side);
    protected abstract void rotateEndMethod();


    public boolean isBusy(){return isBusy;}
}
