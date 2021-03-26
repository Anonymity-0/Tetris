package com.q.socket;

import com.q.controller.GameControl;
import com.q.dao.GameDto;
import com.q.view.OnlinePanel;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author anonymity-0
 * @date 2020/12/14 - 19:38
 */
public class Server {



    public Server(OnlinePanel onlinePanel) {

        try {
                ServerSocket serverSocket = new ServerSocket(8765);
                System.out.println("waiting client....");
                Socket socket = serverSocket.accept();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                System.out.println("client connected.\n");
                while (true) {
                    //服务端先读后写
                    GameDto otherDto = (GameDto) ois.readObject();
                    onlinePanel.setOtherDto(otherDto);

                    GameDto myDto = new GameDto();
                    myDto = onlinePanel.getMyDto();

                    oos.writeObject(myDto);
                    oos.reset();
                    oos.flush();


                    if (!myDto.isDoubleMode()&&!otherDto.isStart()) {
                        myDto.setStart(false);
                        serverSocket.close();
                        return;
                    }
                    if (!otherDto.isOnline()&&!otherDto.isStart()&&!otherDto.isDoubleMode()){
                        JOptionPane.showMessageDialog(onlinePanel, "You Win!");
                        myDto.setStart(false);
                    }
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(onlinePanel,"连接出错","错误提示",JOptionPane.ERROR_MESSAGE);
                onlinePanel.getOtherDto().setStart(false);
                GameControl.localControl.gameOver(0);
            }
        }


}
