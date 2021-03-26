package com.q.view;
import javax.swing.*;
import java.awt.*;

/**
 * @author anonymity-0
 * @date 2020/11/23 - 13:08
 */
public class MainFrame extends JFrame {
   public MainFrame(GamePanel gamePanel){
      this.setSize(900,600);
      this.setTitle("俄罗斯方块");
      //设置居中
      Toolkit toolkit =Toolkit.getDefaultToolkit();
      Dimension screenSize = toolkit.getScreenSize();
      int height = (screenSize.height-this.getHeight())/2;
      int width = (screenSize.width-this.getWidth())/2;
      this.setLocation(width,height);
      this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      this.setResizable(false);
      this.setContentPane(gamePanel);
      this.requestFocus();
      this.setVisible(true);
   }

}
