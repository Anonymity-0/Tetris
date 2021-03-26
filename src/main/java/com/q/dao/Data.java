package com.q.dao;

import java.util.List;

/**
 * @author anonymity-0
 * @date 2020/12/7 - 19:58
 */
public interface Data {
    /**
     * 读取数据
     * @return
     */
    List<Player> loadData();

    /**
     * 存储数
     * @param p
     */
    void saveData(Player p);
}
