package com.q.entity;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author anonymity-0
 * @date 2020/11/26 - 20:50
 */
public class GameAct implements Serializable {
    private Point[] actPoints = null;
    private static int MIN_X =0;
    private static int MAX_X =9;
    private static int MIN_Y =0;
    private static int MAX_Y =17;

    private int typeCode;
    /*
    不需要旋转的方块
     */
    private static final int TYPE_4 = 4;

    public int getTypeCode() {
        return typeCode;
    }

    private static List<Point[]> ACT_TYPE;

    static {
        ACT_TYPE =new ArrayList<Point[]>(7);
        ACT_TYPE.add(new Point[]{
                new Point(4,0),
                new Point(3,0),
                new Point(5,0),
                new Point(6,0),
        });
        ACT_TYPE.add(new Point[]{
                new Point(4,0),
                new Point(3,0),
                new Point(5,0),
                new Point(4,1),
        });
        ACT_TYPE.add(new Point[]{
                new Point(4,0),
                new Point(3,0),
                new Point(3,1),
                new Point(5,0),
        });
        ACT_TYPE.add(new Point[]{
                new Point(4,0),
                new Point(5,0),
                new Point(3,1),
                new Point(4,1),
        });
        ACT_TYPE.add(new Point[]{
                new Point(4,0),
                new Point(5,0),
                new Point(4,1),
                new Point(5,1),
        });
        ACT_TYPE.add(new Point[]{
                new Point(4,0),
                new Point(3,0),
                new Point(5,0),
                new Point(5,1),
        });
        ACT_TYPE.add(new Point[]{
                new Point(4,0),
                new Point(3,0),
                new Point(4,1),
                new Point(5,1),
        });

    }
    public GameAct(int typeCode) {
        this.typeCode =typeCode;
        this.init(typeCode);
    }

    public void init(int actCode) {
        //根据actCode的值刷新方块
        this.typeCode =actCode;
        Point [] points =ACT_TYPE.get(actCode);
        this.actPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            actPoints[i] = new Point(points[i].x,points[i].y);
        }
    }

    public Point[] getActPoints() {
        return actPoints;
    }

    public boolean move(int moveX,int moveY,boolean[][] map) {
        //移动处理
        for (int i =0;i<actPoints.length;i++){
            int x =actPoints[i].x+moveX;
            int y =actPoints[i].y+moveY;
            if (isOverZone(x,y,map)){
                return false;
            }
        }
        for (int i =0;i<actPoints.length;i++){
            int x =actPoints[i].x+moveX;
            int y =actPoints[i].y+moveY;
            actPoints[i].x+=moveX;
            actPoints[i].y+=moveY;
        }
        return true;
    }

    public void round(boolean[][]map){

        if (this.typeCode==TYPE_4){
            return;
        }
        for (int i = 1; i < actPoints.length; i++) {
            int x =actPoints[0].y+actPoints[0].x-actPoints[i].y;
            int y =actPoints[0].y-actPoints[0].x+actPoints[i].x;
        //判断是否可以旋转
            if (isOverZone(x,y,map)){
                return;
            }
        }
        for (int i = 0; i < actPoints.length; i++) {
            int x =actPoints[0].y+actPoints[0].x-actPoints[i].y;
            int y =actPoints[0].y-actPoints[0].x+actPoints[i].x;
            actPoints[i].x=x;
            actPoints[i].y=y;
        }
    }

    private boolean isOverZone(int x, int y, boolean[][]gameMap){
        boolean flag =x<MIN_X||x>MAX_X||y<MIN_Y||y>MAX_Y||gameMap[x][y];
        return flag;
    }
}
