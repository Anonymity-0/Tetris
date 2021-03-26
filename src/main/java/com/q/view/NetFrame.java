package com.q.view;

import com.q.controller.GameControl;

import javax.swing.*;
import java.awt.*;

/**
 * @author anonymity-0
 * @date 2020/12/15 - 12:28
 */
public class NetFrame extends JFrame{
    private final JButton connect;
    private  OnlinePanel onlinePanel;
    private static final  int NET_COUNT = 400;
    public NetFrame(OnlinePanel onlinePanel) {
        this.onlinePanel =onlinePanel;
        this.setResizable(false);
        this.setSize(900,600);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(onlinePanel);
        onlinePanel.setOpaque(false);
        onlinePanel.getMyDto().setDoubleMode(true);
        connect = new JButton("连接");
        this.add(connect);
        connect.addActionListener(e -> {
            onlinePanel.getMyDto().setOnline(true);
            if (!checkConnect()){
                onlinePanel.getMyDto().setOnline(false);
                JOptionPane.showMessageDialog(onlinePanel,"连接失败请重试","错误",JOptionPane.ERROR_MESSAGE);
            }
        });
        this.setVisible(true);
    }

    private boolean checkConnect() {
        int count = 0;
        while (!onlinePanel.getOtherDto().isOnline()&&count< NET_COUNT){
            try {
                Thread.sleep(20);
                count++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (count==NET_COUNT){
            return false;
        }
        GameControl.localControl.start();
        connect.setEnabled(false);
        onlinePanel.repaint();
        this.repaint();
        return true;
    }



}
