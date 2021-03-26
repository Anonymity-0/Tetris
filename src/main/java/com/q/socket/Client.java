package com.q.socket;

import com.q.controller.GameControl;
import com.q.dao.GameDto;
import com.q.view.OnlinePanel;
import com.q.view.StartGame;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author anonymity-0
 * @date 2020/12/14 - 19:40
 */
public class Client {
        private String host;
        public Client(String host,OnlinePanel onlinePanel) {
            this.host = "localhost";
            try {
                if (!"".equals(host)){
                    this.host =host;
                }
                Socket socket = new Socket(this.host, 8765);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                while (true) {
                    //客户端先写后读
                    GameDto gameDto = new GameDto();
                    gameDto = onlinePanel.getMyDto();
                    oos.writeObject(gameDto);
                    oos.reset();
                    oos.flush();

                    GameDto otherDto = (GameDto) ois.readObject();
                    onlinePanel.setOtherDto(otherDto);


                    if (!otherDto.isStart()&&!gameDto.isDoubleMode()){
                        gameDto.setStart(false);
                        socket.close();
                        return;
                    }
                    if (!otherDto.isOnline()&&!otherDto.isStart()&&!otherDto.isDoubleMode()){
                        JOptionPane.showMessageDialog(onlinePanel, "You Win!");
                        gameDto.setStart(false);
                    }
                }
            } catch (IOException  | ClassNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(onlinePanel,"连接出错","错误提示",JOptionPane.ERROR_MESSAGE);
                GameControl.localControl.gameOver(0);
            }
        }
}

