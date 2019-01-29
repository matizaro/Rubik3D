package com.mycompany.myjpctapp;

import com.threed.jpct.Object3D;

import java.util.Arrays;

/**
 * Created by Welcome on 2015-09-09.
 */
public class WallRotate extends AbstractRotate {

    int direction;
    private int[][] rotationIndexes = {
            {0 ,1 ,2 ,3 ,4 ,5 ,6 ,7 ,8 }, //ORANGE
            {18,19,20,21,22,23,24,25,26}, //RED
            {0 ,5 ,8 ,9 ,12,17,18,23,26}, //WHITE
            {2 ,3 ,6 ,11,14,15,20,21,24}, //YELLOW
            {0 ,1 ,2 ,15,16,17,18,19,20}, //GREEN
            {6 ,7 ,8 ,9 ,10,11,24,25,26}, //BLUE
    };
    private int[][][] rotationPermutations = {
            {{0,2}  ,{1,3}  ,{2,6}  ,{3,7}  ,{6,8}  ,{7,5}  ,{8,0}  ,{5,1}  },
            {{20,18},{19,23},{18,26},{23,25},{26,24},{25,21},{24,20},{21,19}},
            {{0,8}  ,{5,9}  ,{8,26} ,{9,23} ,{26,18},{23,17},{18,0} ,{17,5} },
            {{2,20} ,{15,21},{20,24},{21,11},{24,6} ,{11,3} ,{6,2}  ,{3,15} },
            {{2,0}  ,{1,17} ,{0,18} ,{17,19},{18,20},{19,15},{20,2} ,{15,1} },
            {{6,24} ,{11,25},{24,26},{25,9} ,{26,8} ,{9,7}  ,{8,6}  ,{7,11} }
    };
//    private final int numberOfRotations = 6;
    public WallRotate(RubikCube cube, int speed) {
        super(cube, speed);
    }

    @Override
    protected void rotateMethod(int side) {
        wallRotation(angle-prevAngle);
    }

    private void wallRotation(float alpha){
        Object3D[] cube = this.cube.getCube();
        float a = ((float)Math.pow(-1, side))*(float)((5.5 - side)/Math.abs(side - 5.5))*alpha;
        if(side%6 == 0 || side%6 == 1 ) {
            for (int i = 0; i < rotationIndexes[side%6].length; i++)
                cube[rotationIndexes[side%6][i]].rotateY(a);
        }else if (side%6 == 2 || side%6 == 3) {
            for (int i = 0; i < rotationIndexes[side%6].length; i++)
                cube[rotationIndexes[side%6][i]].rotateZ(a);
        }else if(side%6 == 4 || side%6 == 5) {
            for (int i = 0; i < rotationIndexes[side%6].length; i++)
                cube[rotationIndexes[side%6][i]].rotateX(a);
        }
//        else if(side == 6 || side == 7 ) {
//            for (int i = 0; i < rotationIndexes[side-6].length; i++)
//                cube[rotationIndexes[side-6][i]].rotateY(((float)Math.pow(-1, side))*(-alpha));
//        }else if (side == 8 || side == 9) {
//            for (int i = 0; i < rotationIndexes[side-6].length; i++)
//                cube[rotationIndexes[side-6][i]].rotateZ(((float)Math.pow(-1, side))*(-alpha));
//        }else if(side == 10 || side == 11) {
//            for (int i = 0; i < rotationIndexes[side-6].length; i++)
//                cube[rotationIndexes[side-6][i]].rotateX(((float)Math.pow(-1, side))*(-alpha));
//        }
    }

    @Override
    protected void rotateEndMethod() {
        wallRotation(endAngle-prevAngle);
        Object3D[] newRubikArray = Arrays.copyOf(cube.getCube(), cube.getCube().length);
        int[] newTempTable = Arrays.copyOf(cube.getTempTable(), cube.getTempTable().length);
        if(side>5){
            for (int i = 0; i < rotationPermutations[side-6].length; i++){
                newRubikArray[rotationPermutations[side-6][i][0]]=cube.getCube()[rotationPermutations[side-6][i][1]];
                newTempTable[rotationPermutations[side-6][i][0]]=cube.getTempTable()[rotationPermutations[side-6][i][1]];
            }
        }else{
            for (int i = 0; i < rotationPermutations[side].length; i++){
                newRubikArray[rotationPermutations[side][i][1]]=cube.getCube()[rotationPermutations[side][i][0]];
                newTempTable[rotationPermutations[side][i][1]]=cube.getTempTable()[rotationPermutations[side][i][0]];
            }
        }
        cube.setTempTable(newTempTable);
        cube.setCube(newRubikArray);
//        for(int i = 0 ; i < cube.getTempTable().length;i++){
//            System.out.print(cube.getTempTable()[i] + " ");
//        }
//        System.out.println("");
    }


}
