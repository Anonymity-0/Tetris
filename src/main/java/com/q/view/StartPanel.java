
package com.q.view;

import com.q.controller.GameControl;

import javax.swing.*;
import java.awt.*;


/**
 * @author anonymity-0
 * @date 2020/12/10 - 23:29
 */

import com.q.controller.GameControl;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    public StartPanel() {
        this.setSize(900,600);
        this.setLayout(new GridBagLayout());
        this.requestFocus();
        this.setVisible(true);
        int [] num = new int[10];
        int NUMBER = 2019;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Img.IMG_START,0,0,null);
    }
}

