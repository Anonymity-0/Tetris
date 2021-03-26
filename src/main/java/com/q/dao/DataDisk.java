package com.q.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author anonymity-0
 * @date 2020/12/10 - 12:58
 */
public class DataDisk implements Data {
    private static final String FILE_PATH = "./save/recode.dat";
    private static final int RECODE_LENGTH = 3;
    @Override
    public List<Player> loadData() {
        ObjectInputStream ois = null;
        List<Player> players = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(FILE_PATH));
            players = (List<Player>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert ois != null;
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return players;
    }

    @Override
    public void saveData(Player p) {
        List<Player> players = this.loadData();
        ObjectOutputStream oos = null;
        for (int i = 0; i < players.size()&&i<RECODE_LENGTH; i++) {
            if (p.getScore() > players.get(i).getScore()) {
                players.remove(i);
                players.add(p);
                Collections.sort(players);
                break;
            }
            else if (players.size()<RECODE_LENGTH){
                players.add(p);
                Collections.sort(players);
                break;
            }
        }
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream(FILE_PATH)
            );
            oos.writeObject(players);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert oos != null;
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        Player w = new Player("w", 20010708);
        players.add(w);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream(FILE_PATH)
            );
            oos.writeObject(players);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
