package com.q.controller;

import com.q.dao.*;
import com.q.view.*;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author anonymity-0
 * @date 2020/11/26 - 20:29
 */
public class GameControl {
    public static GameControl localControl;
    private Data dataDisk;
    private GamePanel gamePanel;
    private GameDao gameDao;
    private GameDto mygameDto;
    private GameDto otherDto;
    private static AudioClip backgroundMusic;
    private SavePointFrame savePointFrame;


    private NetFrame netFrame;

    static {
        try {
            URL bgm = new File("./audio/music.wav").toURL();
            backgroundMusic = Applet.newAudioClip(bgm);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public GameControl(OnlinePanel onlinePanel, GameDto myDto) {

        this.mygameDto = myDto;
        this.otherDto = onlinePanel.getOtherDto();
        this.gamePanel = onlinePanel;
        this.gameDao= new GameDao(myDto);
        this.savePointFrame = new SavePointFrame(this);
    }

    public void setNetFrame(NetFrame netFrame) {
        this.netFrame = netFrame;
    }


    public GameControl(GamePanel gamePanel, GameDto gameDto,GameDto otherDto) {
        //创建数据源
        this.mygameDto =gameDto;
        //创建逻辑层
        this.gamePanel =gamePanel;
        this.gameDao=new GameDao(gameDto);
        this.dataDisk =new DataDisk();
        //游戏界面
        gameDto.setDiskRecode(dataDisk.loadData());
        this.gameDao.setRecodeDisk(dataDisk.loadData());
        this.savePointFrame = new SavePointFrame(this);
    }

    /**
     * 游戏主线程
     */
    private Thread gameThread =null;

    public void keyUp() {
        this.gameDao.keyUp();
        this.gamePanel.updateUI();
    }

    public void keyDown() {
        this.gameDao.keyFunDown();
        this.gamePanel.updateUI();
    }

    public void keyLeft() {
        this.gameDao.keyLeft();
        this.gamePanel.updateUI();
    }

    public void keyRight() {
        this.gameDao.keyRight();
        this.gamePanel.updateUI();
    }

    public void start() {
        this.savePointFrame.setVisible(false);
        this.gameDao.startGame();
        //创建线程对象
        this.gameThread = new MainThread();
        //启动线程
        this.gameThread.start();
        //刷新
        this.gamePanel.updateUI();
    }

    public void pause() {
        this.gameDao.pause();
    }
    public void gameOver(){
        //单人模式
        if (!mygameDto.isDoubleMode()){
            this.savePointFrame.showWindow(this.mygameDto.getNowScore());
            this.gamePanel.buttonSwitch(true);
            this.savePointFrame.requestFocus();
            mygameDto.setStart(false);
            this.gamePanel.repaint();
        }
        //双人模式
        else {
            if (otherDto.isDoubleMode()&&!mygameDto.isOnline()){
                gameOver(0);
            }
            else {
                mygameDto.setOnline(false);
                mygameDto.setDoubleMode(false);
                int option = JOptionPane.showConfirmDialog(this.gamePanel,"是否继续","游戏结束",JOptionPane.YES_NO_OPTION);
                if (option==0){
                    mygameDto.dtoInit();
                    otherDto.dtoInit();
                    otherDto.setOnline(false);
                    this.netFrame.dispose();
                    new StartGame();
                }
                else if (option==1){
                    System.exit(0);
                }
            }

        }

    }
    public void gameOver(int mode){
        mygameDto.setStart(false);
        mygameDto.setOnline(false);
        mygameDto.setDoubleMode(false);
        mygameDto.dtoInit();
        int option = JOptionPane.showConfirmDialog(this.gamePanel,"是否继续进行游戏","游戏结束",JOptionPane.YES_NO_OPTION);
        if (option==1){
            System.exit(0);
        }
        else if (option==0){
            this.netFrame.dispose();
            StartGame startGame = new StartGame();
        }
    }

    public void checkLose(){
        int myScore = mygameDto.getNowScore();
        int otherScore= otherDto.getNowScore();
        if (myScore>otherScore){
            JOptionPane.showMessageDialog(this.gamePanel,"你赢了");
        }
        else {
            JOptionPane.showMessageDialog(this.gamePanel,"你输了");
        }
    }
    public void savePoint(String name) {
            Player player = new Player(name,this.mygameDto.getNowScore());
            this.dataDisk.saveData(player);
            this.mygameDto.setDiskRecode(dataDisk.loadData());
            this.gamePanel.repaint();
    }

    private class MainThread extends Thread{
        @Override
        public void run() {
            while (mygameDto.isStart()){
                gamePanel.repaint();
                try {
                    Thread.sleep(mygameDto.getSleepTime());
                    if (mygameDto.isPause()){
                        continue;
                    }
                    gameDao.mainAction();
                    gamePanel.updateUI();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            gameOver();
        }
    }
    public void playMusic() {// 背景音乐播放
        backgroundMusic.loop();
    }
    public void stopMusic(){
        backgroundMusic.stop();
    }
}
