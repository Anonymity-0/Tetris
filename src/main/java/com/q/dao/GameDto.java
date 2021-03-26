package com.q.dao;

import com.q.entity.GameAct;
import com.q.util.GameFunction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author anonymity-0
 * @date 2020/11/26 - 20:46
 */
public class GameDto implements Serializable {
    private List<Player> dbRecode;
    private List<Player> diskRecode;
    private boolean[][] gameMap;
    private GameAct gameAct;
    private int next;
    private int level ;
    private  int nowScore ;
    private int nowPlayer;
    private int removeLine;
    private boolean start;
    private boolean pause;
    private long sleepTime;
    private boolean isOnline;
    private static final int RECODE_LENGTH =3 ;
    public boolean isDoubleMode() {
        return doubleMode;
    }

    public void setDoubleMode(boolean doubleMode) {
        this.doubleMode = doubleMode;
    }

    private boolean doubleMode;





    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public int getRemoveLine() {
        return removeLine;
    }

    public void setRemoveLine(int removeLine) {
        this.removeLine = removeLine;
    }

    public GameDto() {
        dtoInit();
    }

    public void dtoInit() {
        this.gameMap = new boolean[10][18];
        this.level=1;
        this.nowScore = 0;
        this.pause =false;
        this.sleepTime=GameFunction.getSleepTimeByLevel(level);
    }

    public List<Player> getDbRecode() {
        return dbRecode;
    }

    public void setDbRecode(List<Player> dbRecode) {
        this.dbRecode = dbRecode;
    }

    public List<Player> getDiskRecode() {
        return diskRecode;
    }

    public void setDiskRecode(List<Player> diskRecode) {
        if (diskRecode == null){
            diskRecode = new ArrayList<Player>();
        }
        while (diskRecode.size()<RECODE_LENGTH){
            diskRecode.add(new Player("No Data",0));

        }
        Collections.sort(diskRecode);
        this.diskRecode = diskRecode;
    }


    public boolean[][] getGameMap() {
        return gameMap;
    }

    public void setGameMap(boolean[][] gameMap) {
        this.gameMap = gameMap;
    }

    public GameAct getGameAct() {
        return gameAct;
    }

    public void setGameAct(GameAct gameAct) {
        this.gameAct = gameAct;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.sleepTime = GameFunction.getSleepTimeByLevel(level);
    }
    public int getNowScore() {
        return nowScore;
    }

    public void setNowScore(int nowScore) {
        this.nowScore = nowScore;
    }

    public int getNowPlayer() {
        return nowPlayer;
    }

    public void setNowPlayer(int nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }


    public long getSleepTime() {
        return sleepTime;
    }

    @Override
    public String toString() {
        return "GameDto{" +
                "dbRecode=" + dbRecode +
                ", diskRecode=" + diskRecode +
                ", gameMap=" + Arrays.toString(gameMap) +
                ", gameAct=" + gameAct +
                ", next=" + next +
                ", level=" + level +
                ", nowScore=" + nowScore +
                ", nowPlayer=" + nowPlayer +
                ", removeLine=" + removeLine +
                ", start=" + start +
                ", pause=" + pause +
                ", sleepTime=" + sleepTime +
                '}';
    }
}
