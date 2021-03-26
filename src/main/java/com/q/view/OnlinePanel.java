package com.q.view;

import com.q.dao.GameDto;

import javax.swing.*;
import java.awt.*;

/**
 * @author anonymity-0
 * @date 2020/11/23 - 23:35
 */
public class OnlinePanel extends GamePanel{
    private GameDto otherDto;
    private final GameDto myDto;
    private static final int ACT_SIZE =27;

    @Override
    public GameDto getMyDto() {
        return myDto;
    }

    public void setOtherDto(GameDto otherDto) {
        this.otherDto = otherDto;
    }

    public GameDto getOtherDto() {
        return otherDto;
    }

    public OnlinePanel(GameDto myDto,GameDto otherDto) {
        super(myDto);
        this.myDto = myDto;
        this.otherDto = otherDto;
        this.repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0,0,this.getWidth(),this.getHeight());
        //绘制游戏框
        repaint();
        g.drawImage(Img.IMG_DOUBLE_PANEL,0,0,null);
        if (myDto.isOnline()){
            paintScore(g);
            paintNext(g);
        }
        //绘制地图
        paintMap(g);
        if (this.myDto.isStart()) {
            paintCube(g);
        }
        if (this.otherDto!=null&&this.otherDto.isStart()&&this.myDto.isStart()){
            paintOtherPanel(g);
        }
        this.requestFocus();
    }

    private void paintScore(Graphics g) {
        Font font = new Font("微软雅黑", Font.BOLD, 26);
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString("我的得分",400,100);
        g.drawString(myDto.getNowScore()+"",450,140);
        g.drawString("对方得分",400,180);
        g.drawString(otherDto.getNowScore()+"",450,220);
    }

    @Override
    public void paintNext(Graphics g){
        Image myNext=  new ImageIcon("./image/next/"+myDto.getNext()+".png").getImage();
        Image otherNext=  new ImageIcon("./image/next/"+otherDto.getNext()+".png").getImage();
        g.drawString("myNext",400,260);
        g.drawImage(myNext,400,300,null);
        g.drawString("otherNext",400,400);
        g.drawImage(otherNext,400,420,null);
    }


    public void paintOtherPanel(Graphics g){
        g.drawImage(Img.IMG_GAMEBOX,600,30,null);
        int typeCode = this.otherDto.getGameAct().getTypeCode();
        typeCode = this.otherDto.isStart()?typeCode:7;
        Image img=  new ImageIcon("./image/cube/"+typeCode+".png").getImage();
        Point[] actPoints = this.otherDto.getGameAct().getActPoints();
        for (Point actPoint : actPoints) {
            g.drawImage(img, 570 + ViewConfig.ACT_X + actPoint.x * ACT_SIZE, ViewConfig.ACT_Y + actPoint.y * ACT_SIZE, null);
        }
        boolean[][] gameMap = this.otherDto.getGameMap();
        for (int x = 0; x < gameMap.length; x++) {
            for (int y = 0; y < gameMap[x].length; y++) {
                if (gameMap[x][y]){
                    if (this.otherDto.isStart()){
                        g.drawImage(Img.IMG_CUBE7,570+ViewConfig.ACT_X+x*ACT_SIZE,ViewConfig.ACT_Y+y*ACT_SIZE,null);
                    }
                    else {
                        g.drawImage(Img.IMG_CUBE8,570+ViewConfig.ACT_X+x*ACT_SIZE,ViewConfig.ACT_Y+y*ACT_SIZE,null);
                    }
                }
            }
        }
    }
}
