package com.q.dao;

import com.q.entity.GameAct;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * @author anonymity-0
 * @date 2020/11/26 - 20:34
 */
public class GameDao {
    private  GameDto dto;
    private final Random random= new Random();
    private static final  int MAX_TYPE =7;

    private static final int LEVEL_UP =20;
    public GameDao(GameDto gameDto) {
        dto =gameDto;
    }

    public void keyUp() {
        synchronized (this.dto){
            this.dto.getGameAct().round(dto.getGameMap());
        }
    }

    public void keyFunDown(){
        synchronized (dto) {
            while (keyDown()) {
            }
        }
    }

    public void pause(){
        synchronized (dto){
            this.dto.setPause(!this.dto.isPause());
        }
    }
    public boolean keyDown() {
        if (!dto.isStart()){
            return false;
        }
        synchronized (dto){
            if (this.dto.getGameAct().move(0, 1,dto.getGameMap())){
                return true;
            }
            boolean[][] gameMap = this.dto.getGameMap();
            Point[] actPoints = this.dto.getGameAct().getActPoints();
            for (Point actPoint : actPoints) {
                //将方块加到地图里面
                gameMap[actPoint.x][actPoint.y] = true;
            }
            int exp = this.plusExp();
            this.plusPoint(exp);
            //生成下一个方块
            int next = random.nextInt(MAX_TYPE);
            this.dto.getGameAct().init(dto.getNext());
            this.dto.setNext(next);
            if (this.checkLose()){
                this.dto.setStart(false);
            }
            return false;
        }
    }

    private boolean checkLose() {
        synchronized (dto){
            Point[] actPoints = this.dto.getGameAct().getActPoints();
            boolean[][] gameMap = this.dto.getGameMap();
            for (Point actPoint : actPoints) {
                if (gameMap[actPoint.x][actPoint.y]) {
                    return true;
                }
            }
            return false;
        }
    }

    private void plusPoint(int plusExp) {
        synchronized (dto){
            if (plusExp==0) {
                return;
            }
            int level = this.dto.getLevel();
            int removeLine = this.dto.getRemoveLine();
            if (removeLine%LEVEL_UP+plusExp>=LEVEL_UP){
                this.dto.setLevel(++level);
            }
            this.dto.setRemoveLine(removeLine+plusExp);
            int nowScore = this.dto.getNowScore();
            switch (plusExp){
                case 1: nowScore+=10;break;
                case 2: nowScore+=30;break;
                case 3: nowScore+=50;break;
                case 4: nowScore+=80;break;
                default:
            }
            this.dto.setNowScore(nowScore);
        }
    }


    public int plusExp() {
        synchronized (dto) {
            boolean[][] gameMap = this.dto.getGameMap();
            int exp = 0;
            for (int y = 0; y < gameMap[0].length; y++) {
                if (canRemove(y, gameMap)) {
                    this.removeLine(y, gameMap);
                    exp++;
                }
            }
            return exp;
        }
    }

    private void removeLine(int row, boolean[][] gameMap) {
        for (int x = 0;x< gameMap.length;x++){
            for (int y =row ; y>0;y--){
                gameMap[x][y]=gameMap[x][y-1];
            }
            gameMap[x][0]=false;
        }
    }

    private boolean canRemove(int y, boolean[][] gameMap){
        for (boolean[] booleans : gameMap) {
            if (!booleans[y]) {
                return false;
            }
        }

        return true;
    }
    public void keyLeft() {
        synchronized (dto) {
            this.dto.getGameAct().move(-1, 0, dto.getGameMap());
        }
    }
    public void keyRight() {
        synchronized (dto) {
            this.dto.getGameAct().move(1, 0, dto.getGameMap());
        }
    }

    public void setRecodeDisk(List<Player> players){
        synchronized (dto){
            this.dto.setDiskRecode(players);
        }
    }

    public void startGame() {
        synchronized (dto){
        this.dto.setNext(random.nextInt(MAX_TYPE));
        GameAct act = new GameAct(random.nextInt(MAX_TYPE));
        dto.setStart(true);
        dto.setGameAct(act);
        dto.dtoInit();
        }
    }
    public void mainAction(){
        this.keyDown();
    }
}
