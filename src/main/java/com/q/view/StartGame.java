package com.q.view;

import com.q.controller.GameControl;
import com.q.controller.KeyController;
import com.q.dao.GameDto;
import com.q.socket.Client;
import com.q.socket.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author anonymity-0
 * @date 2020/11/23 - 22:43
 */
public class StartGame extends JFrame implements ActionListener {
    GameDto myDto;
    GameDto otherDto;
    JButton button1;
    JButton button2;
    Server server;
    Client client;
    StartPanel mainPanel;
    private Thread gameMake;
    private static final String OPTION_SERVER= "Server";
    private static final String OPTION_CLIENT= "Client";
    public StartGame() {
        mainPanel = new StartPanel();
        this.setSize(900,600);
        this.setTitle("俄罗斯方块");
        this.setLayout(null);

        Toolkit toolkit =Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int height = (screenSize.height-this.getHeight())/2;
        int width = (screenSize.width-this.getWidth())/2;
        this.setLocation(width,height);
        this.add(mainPanel);
        mainPanel.setBounds(0,0,900,600);
        mainPanel.repaint();
        this.repaint();

        button1 =new JButton(Img.IMG_BUTTON_SINGLE);
        button1.setBounds(660,350,200,100);
        button1.setContentAreaFilled(false);
        button1.addActionListener(this);
        this.add(button1);


        button2=new JButton(Img.IMG_BUTTON_DOUBLE);
        button2.setBounds(660,450,200,100);
        button2.setContentAreaFilled(false);
        button2.addActionListener(this);
        this.add(button2);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        myDto= new GameDto();
        otherDto = new GameDto();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() ==button1){
            this.dispose();
            GamePanel gamePanel = new GamePanel(myDto);
            GameControl.localControl= new GameControl(gamePanel,myDto,null);
            gamePanel.setGameControl(GameControl.localControl);
            gamePanel.setPlayerControl(new KeyController(GameControl.localControl));
            new MainFrame(gamePanel);
        }
        else if (e.getSource() == button2){
            this.dispose();
            //新建一个gto存储他人的数据
            myDto= new GameDto();
            otherDto = new GameDto();
            this.myDto.setDoubleMode(true);
            //连接面板和游戏控制器
            OnlinePanel onlinePanel = new OnlinePanel(myDto, otherDto);
            GameControl.localControl= new GameControl(onlinePanel,myDto);
            onlinePanel.setGameControl(GameControl.localControl);
            onlinePanel.setPlayerControl(new KeyController(GameControl.localControl));
            Object[] possibleValues = { OPTION_SERVER, OPTION_CLIENT };
            String selectedValue = (String)JOptionPane.showInputDialog(null, "please choose your identity", "Prompt",
                    JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
            if (selectedValue == null) {
                new StartGame();
            }
            gameMake=new Thread(() -> {
                if (OPTION_SERVER.equals(selectedValue)){
                    server = new Server(onlinePanel);
                }
                else {
                    String host = (String)JOptionPane.showInputDialog(null, "please choose server Ip address", "Prompt",
                            JOptionPane.PLAIN_MESSAGE);
                    client = new Client(host,onlinePanel);
                }
            });
            gameMake.start();

            NetFrame netFrame = new NetFrame(onlinePanel);
            GameControl.localControl.setNetFrame(netFrame);
        }
    }
}
