package com.q.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author anonymity-0
 * @date 2020/11/23 - 0:31
 */
public class KeyController extends KeyAdapter {
    private GameControl gameControl;
    public KeyController(GameControl control) {
        this.gameControl=control;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                this.gameControl.keyUp();
                break;
            case KeyEvent.VK_DOWN:
                this.gameControl.keyDown();
                break;
            case KeyEvent.VK_LEFT:
                this.gameControl.keyLeft();
                break;
            case KeyEvent.VK_RIGHT:
                this.gameControl.keyRight();
                break;
            default:
                break;
        }
    }
}
