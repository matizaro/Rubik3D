package com.mycompany.myjpctapp;

import com.threed.jpct.Object3D;

/**
 * Created by Msi on 2015-12-24.
 */
public class RotationController {

    WallRotate wallRotation;
    CubeRotate cubeRotation;
    WallRotate centerRotation;
    CubeRotate cubeRotation2;

    private SideHolder sideHolder;
    Object3D centerpoint;

    public RotationController(RubikCube cube, int wallSpeed,int rotatingSpeed){
        sideHolder = cube.getSideHolder();
        centerpoint = cube.getCenterpoint();
        wallRotation = new WallRotate(cube, wallSpeed);
        cubeRotation = new CubeRotate(cube, rotatingSpeed);
        centerRotation = new WallRotate(cube, wallSpeed);
        cubeRotation2 = new CubeRotate(cube, wallSpeed);
    }
    public void clearRotation(){
        sideHolder.reset();
        centerpoint.clearRotation();
        centerpoint.rotateX(-(float)Math.PI / 2);
        centerpoint.rotateZ(-(float) Math.PI / 2);
    }
    public void stopCube(){
        long t = System.currentTimeMillis();
        wallRotation.rotateEnd();
        centerRotation.rotateEnd();
        cubeRotation.rotateEnd();
        cubeRotation2.rotateEnd();
    }

    public void rotateCenter(int side1, int side2, int side3){
        stopCube();
//        setMovesText();
        StaticThings.rubikTimer.start();
        long t = System.currentTimeMillis();
        wallRotation.rotate(t, side1);
        centerRotation.rotate(t, side2);
        cubeRotation2.rotate(t, side3);

    }
    public void rotateWall(int side){
        stopCube();
//        setMovesText();
        StaticThings.rubikTimer.start();
        wallRotation.rotate(System.currentTimeMillis(), side);

    }
    public void rotateWallnoTiming(int side){
        stopCube();
        wallRotation.rotate(System.currentTimeMillis(), side);

    }
    public void rotateCube(int side) {
        stopCube();
        cubeRotation.rotate(System.currentTimeMillis(), side);
    }
    public void setPosition(){
        long t = System.currentTimeMillis();
        wallRotation.animate(t);
        cubeRotation.animate(t);
        centerRotation.animate(t);
        cubeRotation2.animate(t);
    }
}
