package com.mycompany.myjpctapp;

import com.threed.jpct.Object3D;

/**
 * Created by Welcome on 2015-09-09.
 */
public class CubeRotate extends AbstractRotate {


    public CubeRotate(RubikCube cube, int speed) {
        super(cube, speed);
    }

    @Override
    protected void rotateMethod(int side) {
        cubeRotation(angle-prevAngle,side);
    }
    private void cubeRotation(float alpha, int side){
        Object3D cp = this.cube.getCenterpoint();
        if(side==SideHolder.LEFT){
            cp.rotateY(-alpha);
        }else if(side==SideHolder.TOP){
            cp.rotateX(-alpha);
        }else if(side==SideHolder.RIGHT){
            cp.rotateY(alpha);
        }else if(side==SideHolder.BOTTOM){
            cp.rotateX(alpha);
        }else if(side==SideHolder.CW){
            cp.rotateZ(-alpha);
        }else if(side==SideHolder.CCW){
            cp.rotateZ(alpha);
        }
    }
    @Override
    protected void rotateEndMethod() {
        cubeRotation(endAngle-prevAngle, side);
        cube.getSideHolder().swapWall(side);
    }
}
