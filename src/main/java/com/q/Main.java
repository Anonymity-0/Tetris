package com.q;

import com.q.view.StartGame;

import java.awt.*;

/**
 * @author anonymity-0
 * @date 2020/11/26 - 20:59
 */
public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(StartGame::new);
    }
}
