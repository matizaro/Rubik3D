package com.mycompany.myjpctapp;

/**
 * Created by Welcome on 2015-09-06.
 */
public class SideHolder {

    private static int[][] neighbours = {
            {3,5,2,4}, //ORANGE
            {2,5,3,4}, //RED
            {0,5,1,4}, //WHITE
            {1,5,0,4}, //YELLOW
            {3,0,2,1}, //GREEN
            {3,1,2,0}  //BLUE
    };
    //LEFT, TOP, RIGHT, BOTTON
    private static int[][] rotationLevels = {
            {0,0,0,0},
            {0,2,0,2},
            {0,3,0,1},
            {0,1,0,3},
            {1,0,3,2},
            {3,2,1,0}
    };

    public static final int LEFT=0, TOP=1, RIGHT=2, BOTTOM=3;
    public static final int CW=7, CCW=5;


    private int currentRotationLevel = 0;
    private int currentRotationSide = 0;

    public void swapWall(int direction){
        switch(direction){
            case LEFT:
            case TOP:
            case RIGHT:
            case BOTTOM:
                int oldLevel=currentRotationLevel;
                int oldSide=currentRotationSide;
                currentRotationSide = neighbours[oldSide][(direction+oldLevel)%4];
                currentRotationLevel = (currentRotationLevel+(rotationLevels[oldSide][(direction+oldLevel)%4]))%4;
            break;
            case CW:
            case CCW:
                rotate(direction);
                break;
        }
    }
    public void reset(){
        currentRotationLevel = 0;
        currentRotationSide = 0;
    }
    public void setPosition(int rot, int side) {
        currentRotationLevel = rot;
        currentRotationSide = side;
    }

    public int getCurrentRotationLevel() {
        return currentRotationLevel;
    }

    public int getCurrentRotationSide() {
        return currentRotationSide;
    }

    public void rotate(int direction){
        currentRotationLevel=(direction+currentRotationLevel)%4;
    }
    public int getPosition(){
        return currentRotationSide;
    }
    public int getSide(int side){
        return neighbours[currentRotationSide][(currentRotationLevel+side)%4];
    }
}
