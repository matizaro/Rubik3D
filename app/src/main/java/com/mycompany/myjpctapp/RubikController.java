package com.mycompany.myjpctapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Interact2D;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Msi on 2015-12-24.
 */
public class RubikController {

    private int[] corners = {
            0, 2, 6, 8, 18, 20, 24, 26
    };
    Object3D[] slabs;

    private World world;
    RubikCube rubikCube;


    boolean released=true;
    int slabIndex=0;
    private List<Rect> boundsList;
    private int maxX = 1280, maxY=720, topY = 186*maxY/768, topX = 503*maxX/1158, botY = 578*maxY/768, botX = 895*maxX/1158,
            firstX =topX + ((botX - topX) / 3), secondX = topX + 2 * ((botX - topX) / 3),
            firstY =topY + ((botY - topY) / 3), secondY = topY + 2 * ((botY - topY) / 3);


    public RubikController(Context context, World world){
        create(context,world);
        rubikCube = new RubikCube(context, world);
    }
    public RubikController(Context context, World world, LoadRubikStateContainer resumeObject){
        create(context,world);
        rubikCube = new RubikCube(context, world, resumeObject);
    }
    private void create(Context context, World world){
        this.world=world;

        slabs = new Object3D[8];

        for(int a=0 ;a<slabs.length; a++){
            slabs[a]= Primitives.getPlane(1, 0.05f);
            slabs[a].build();
            world.addObject(slabs[a]);
        }
    }
    public void setMax(int maX, int maY){
        maxX = maX; maxY = maY;
    }
    public void setCubeBounds(Camera camera, FrameBuffer fb){
        Object3D[] cube = rubikCube.getCube();
        for(int i = 0 ; i <  corners.length;i++){
            Object3D cor = cube[corners[i]];
            float sc = cor.getScale();
            cor.setScale(1.65f);
            SimpleVector sv = Interact2D.projectCenter3D2D(camera, fb, cor);
            cor.setScale(sc);
            SimpleVector sv2 = Interact2D.reproject2D3D(camera, fb, (int) sv.x, (int) sv.y);
            slabs[i].clearTranslation();
            slabs[i].translate(sv2.x, sv2.y, -11);
//            System.out.println(sv3.x + " " + sv3.y);

        }
        int maxX=0, maxY=0, minX=Integer.MAX_VALUE, minY=Integer.MAX_VALUE;
        for(int i=0;i<slabs.length;i++){
            SimpleVector sv4 = Interact2D.projectCenter3D2D(camera, fb, slabs[i]);
            if(sv4.x<minX)
                minX=(int)sv4.x;
            if(sv4.y<minY)
                minY=(int)sv4.y;
            if(sv4.x>maxX)
                maxX=(int)sv4.x;
            if(sv4.y>maxY)
                maxY=(int)sv4.y;
        }
        setCube2dBounds(minY, minX, maxY, maxX);

        for(int i = 0 ; i <  slabs.length;i++)
            world.removeObject(slabs[i]);
    }
    private void setCube2dBounds(int tX, int tY, int bX, int bY){
        topX=tX; topY = tY; botX = bX; botY = bY;
        firstX =topX + ((botX - topX) / 3); secondX = topX + 2 * ((botX - topX) / 3);
        firstY =topY + ((botY - topY) / 3); secondY = topY + 2 * ((botY - topY) / 3);
        initList();
    }
    private void initList(){

        boundsList = new ArrayList<>();
        boundsList.add(0, new Rect(0,0,topY,maxX));
        boundsList.add(1, new Rect(topY,0,botY,topX));
        boundsList.add(2, new Rect(botY,0,maxY,maxX));
        boundsList.add(3, new Rect(topY,botX,maxY,maxX));

        boundsList.add(4, new Rect(topY,topX,firstY,firstX));
        boundsList.add(5, new Rect(firstY,topX,secondY,firstX));
        boundsList.add(6, new Rect(secondY,topX,botY,firstX));

        boundsList.add(7, new Rect(topY,firstX,firstY,secondX));
        boundsList.add(8, new Rect(firstY,firstX,secondY,secondX));
        boundsList.add(9, new Rect(secondY,firstX,botY,secondX));

        boundsList.add(10, new Rect(topY,secondX,firstY,botX));
        boundsList.add(11, new Rect(firstY, secondX, secondY, botX));
        boundsList.add(12, new Rect(secondY, secondX, botY, botX));
    }
    public void onTouch(MotionEvent e) {
        if(boundsList!=null){
            int result = checkBounds((int)e.getX(), (int)e.getY());
            switch (result){
                case -1:
                    return;
                case 0:
                case 1:
                case 2:
                case 3:
                    rubikCube.rotateCube(result);
                    break;
                default:
                    rubikCube.stopCube();
                    slabIndex = result;
                    break;
            }
        }
    }
    private int checkBounds(int x, int y){
        for(int i = 0 ; i < boundsList.size() ;i++){
            if(boundsList.get(i).contains(x, y))
                return i;
        }
        return -1;
    }
    public void onMove(MotionEvent e) {
        int result = checkBounds((int)e.getX(), (int)e.getY());
        if(slabIndex != result){
            if(released){
                released=false;
                switch(slabIndex){
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        return;

                    case 4:
                        switch (result){
                            case 7:
                            case 10:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.LEFT));
                                break;
                            case 5:
                            case 6:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.TOP) + 6);
                                break;
                        }
                        break;
                    case 7:
                        switch (result){
                            case 4:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.LEFT) + 6);
                                break;
                            case 10:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.LEFT));
                                break;
                            case 8:
                            case 9:
                                SideHolder sideHolder = rubikCube.getSideHolder();
                                rubikCube.rotateCenter(sideHolder.getSide(SideHolder.TOP), sideHolder.getSide(SideHolder.BOTTOM) + 6, 0);
                                break;
                        }
                        break;
                    case 10:
                        switch (result){
                            case 4:
                            case 7:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.LEFT) + 6);
                                break;
                            case 11:
                            case 12:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.BOTTOM));
                                break;
                        }
                        break;
                    case 11:
                        switch (result){
                            case 10:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.BOTTOM) + 6);
                                break;
                            case 12:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.BOTTOM));
                                break;
                            case 8:
                            case 5:
                                SideHolder sideHolder = rubikCube.getSideHolder();
                                rubikCube.rotateCenter(sideHolder.getSide(SideHolder.LEFT), sideHolder.getSide(SideHolder.RIGHT) + 6, 3);
                                break;
                        }
                        break;
                    case 12:
                        switch (result){
                            case 10:
                            case 11:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.BOTTOM) + 6);
                                break;
                            case 6:
                            case 9:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.RIGHT));
                                break;
                        }
                        break;
                    case 9:
                        switch (result){
                            case 6:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.RIGHT));
                                break;
                            case 12:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.RIGHT) + 6);
                                break;
                            case 7:
                            case 8:
                                SideHolder sideHolder = rubikCube.getSideHolder();
                                rubikCube.rotateCenter(sideHolder.getSide(SideHolder.TOP) + 6, sideHolder.getSide(SideHolder.BOTTOM), 2);
                                break;
                        }
                        break;
                    case 6:
                        switch (result){
                            case 4:
                            case 5:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.TOP));
                                break;
                            case 9:
                            case 12:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.RIGHT) + 6);
                                break;
                        }
                        break;
                    case 5:
                        switch (result){
                            case 4:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.TOP));
                                break;
                            case 6:
                                rubikCube.rotateWall(rubikCube.getSideHolder().getSide(SideHolder.TOP)+6);
                                break;
                            case 8:
                            case 11:
                                SideHolder sideHolder = rubikCube.getSideHolder();
                                rubikCube.rotateCenter(sideHolder.getSide(SideHolder.LEFT) + 6, sideHolder.getSide(SideHolder.RIGHT), 1);
                                break;
                        }
                        break;
                    case 8:
                        SideHolder sideHolder = rubikCube.getSideHolder();
                        switch (result){
                            case 5:
                                rubikCube.rotateCenter(sideHolder.getSide(SideHolder.LEFT), sideHolder.getSide(SideHolder.RIGHT) + 6, 3);
                                break;
                            case 7:
                                rubikCube.rotateCenter(sideHolder.getSide(SideHolder.TOP) + 6, sideHolder.getSide(SideHolder.BOTTOM), 2);
                                break;
                            case 9:
                                rubikCube.rotateCenter(sideHolder.getSide(SideHolder.TOP), sideHolder.getSide(SideHolder.BOTTOM) + 6, 0);
                                break;
                            case 11:
                                rubikCube.rotateCenter(sideHolder.getSide(SideHolder.LEFT) + 6, sideHolder.getSide(SideHolder.RIGHT), 1);
                                break;

                        }
                }
                setMovesText();
            }
        }
    }

    private void setMovesText() {
        int moves = Integer.valueOf(StaticThings.moves.getText().toString());
        String movesText = String.valueOf((moves < 9 ? "0" : "") + Integer.valueOf(moves + 1));
        StaticThings.setMovesText(movesText);
    }

    public void saveState(SharedPreferences mPrefs){rubikCube.saveState(mPrefs);}
    public void onReleaseTouch(MotionEvent e) {
        released=true;
    }

    public void rotateCube(int ccw) {
        this.rubikCube.rotateCube(ccw);
    }

    public void reset() {
        this.rubikCube.reset();
    }

    public int[] getTempTable() {
        return this.rubikCube.getTempTable();
    }

    public SimpleVector getCenter() {
        return this.rubikCube.getCenter();
    }

    public void setPosition() {
        this.rubikCube.setPosition();
    }
}
