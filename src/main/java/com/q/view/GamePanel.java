package com.q.view;

import com.q.controller.GameControl;
import com.q.controller.KeyController;
import com.q.dao.GameDto;
import com.q.dao.Player;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author anonymity-0
 * @date 2020/11/23 - 23:35
 */
public class GamePanel extends JPanel{
    private GameDto myDto =null;
    private GameControl gameControl = null;
    private static int ACT_SIZE =27;
    private static int LEVEL_UP =20;
    private JButton stratBtn;
    private JButton pauseBtn;
    private JToggleButton jToggleButton;
    private static int RECODE_LENGTH = 3;
    public GamePanel(GameDto gameDto) {

        this.myDto=gameDto;
        if (!myDto.isDoubleMode()){
            initComponent();
        }
        this.setSize(900,600);
        this.repaint();
        this.requestFocus();
    }


    public void setGameControl(GameControl gameControl){
        this.gameControl =gameControl;
    }
    public void setPlayerControl(KeyController playerControl){
        this.addKeyListener(playerControl);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Img.IMG_SINGLE_PANEL,0,0,null);
        Font font = new Font("黑体", Font.BOLD, 35);
        g.setColor(Color.white);
        g.setFont(font);
        paintLevel(g);
        paintMap(g);
        paintScore(g);
        paintRecode(g);
        if (this.myDto.isStart()) {
             paintCube(g);
             paintNext(g);
        }
        if (myDto.isPause()){
            g.drawString("暂停",ViewConfig.PAUSE_X,ViewConfig.PAUSE_Y);
        }


        //返回焦点
        this.requestFocus();
    }
    private void paintScore(Graphics g) {
        g.drawString(String.valueOf(this.myDto.getNowScore()),760,140);
    }
    private void paintLevel(Graphics g){
        g.drawString(String.valueOf(this.myDto.getLevel()),520,400);
    }
    protected void paintCube(Graphics g){
        //打印方块
        int typeCode = this.myDto.getGameAct().getTypeCode();
        typeCode = this.myDto.isStart()?typeCode:7;
        Image img=  new ImageIcon("./image/cube/"+typeCode+".png").getImage();
        Point[] actPoints = this.myDto.getGameAct().getActPoints();
        for (int i=0;i<actPoints.length;i++){
            g.drawImage(img,ViewConfig.ACT_X+actPoints[i].x*ACT_SIZE,ViewConfig.ACT_Y+actPoints[i].y*ACT_SIZE,null);
        }

    }
    private void initComponent(){
        this.setLayout(null);
        stratBtn =new JButton(Img.IMG_BUTTON_START);
        stratBtn.setBounds(428,463,84,47);
        pauseBtn = new JButton(Img.IMG_BUTTON_PAUSE);
        pauseBtn.setBounds(543,465,85,50);
        this.add(stratBtn);
        this.stratBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameControl.localControl.start();
                stratBtn.setEnabled(false);
            }
        });
        this.add(pauseBtn);
        this.pauseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameControl.pause();
            }
        });
        jToggleButton = new JToggleButton("背景音乐");
        jToggleButton.setBackground(Color.black);
        jToggleButton.setBounds(430,520,100,20);
        jToggleButton.setFont(new Font("微软雅黑",Font.BOLD,15));
        jToggleButton.setForeground(Color.white);
        jToggleButton.setVisible(true);
        this.add(jToggleButton);
        jToggleButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (jToggleButton.isSelected()){
                    gameControl.playMusic();
                }
                else {
                    gameControl.stopMusic();
                }
            }
        });


    }
    protected void paintMap(Graphics g){
        //打印地图
        boolean[][] gameMap = this.myDto.getGameMap();
        for (int x = 0; x < gameMap.length; x++) {
            for (int y = 0; y < gameMap[x].length; y++) {
                if (gameMap[x][y]){
                    if (this.myDto.isStart()){
                        g.drawImage(Img.IMG_CUBE7,ViewConfig.ACT_X+x*ACT_SIZE,ViewConfig.ACT_Y+y*ACT_SIZE,null);
                    }
                    else {
                        g.drawImage(Img.IMG_CUBE8,ViewConfig.ACT_X+x*ACT_SIZE,ViewConfig.ACT_Y+y*ACT_SIZE,null);
                    }
                }
            }
        }

    }
    protected void paintNext(Graphics g){
        int next =myDto.getNext();
        Image img=  new ImageIcon("./image/next/"+next+".png").getImage();
        g.drawImage(img,490,120,null);
    }
    public GameDto getMyDto() {
        return myDto;
    }

    private void paintRecode(Graphics g){
        g.setFont(new Font("黑体", Font.BOLD, 25));
        List<Player> diskRecode = myDto.getDiskRecode();
        for (int i = 0; i < RECODE_LENGTH; i++) {
            if (diskRecode.size() <= i){
                g.drawString("no recode",660,i*30+400);
            }
            else {
                g.drawString(String.valueOf(diskRecode.get(i).getName()+"  "+diskRecode.get(i).getScore()),700,i*50+400);
            }
        }
    }

    public void buttonSwitch(boolean onOff){
        this.stratBtn.setEnabled(onOff);
    }

}
