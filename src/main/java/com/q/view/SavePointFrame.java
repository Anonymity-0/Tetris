package com.q.view;

import com.q.controller.GameControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author anonymity-0
 * @date 2020/12/13 - 23:07
 */
public class SavePointFrame extends JFrame {
    private JButton btnOk =null;
    private JLabel point = null;
    private JTextField name =null;
    private JLabel errorMsg =null;
    private GameControl gameControl =null;
    public SavePointFrame(GameControl gameControl) {
        this.gameControl =gameControl;
        this.setTitle("保存记录");
        this.setSize(300,150);
        Toolkit toolkit =Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int height = (screenSize.height-this.getHeight())/2;
        int width = (screenSize.width-this.getWidth())/2;
        this.setLocation(width,height);

        this.setLayout(new BorderLayout());
        this.createButton();
        this.createAction();
        this.setResizable(false);

        this.requestFocus();
    }

    private void createAction() {
        this.btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name==null||"".equals(name)){
                    errorMsg.setText("用户名错误");
                }
                else {
                    setVisible(false);
                    gameControl.savePoint(name.getText());
                }
            }
        });
    }

    public void showWindow(int score){
        this.point.setText("您的得分："+score);
        this.setVisible(true);
    }

    private void createButton(){
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.point =new JLabel("您的得分：");
        north.add(point);
        this.errorMsg =new JLabel();
        this.errorMsg.setForeground(Color.red);
        north.add(this.errorMsg);
        this.add(north,BorderLayout.NORTH);

        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        center.add(new JLabel("您的名字："));
        this.name = new JTextField(10);
        center.add(name);
        this.add(center,BorderLayout.CENTER);

        btnOk =new JButton("确定");
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        south.add(btnOk);
        this.add(south,BorderLayout.SOUTH);

    }

}
