package me.zhanshi123.globalprefix.cacher;

import me.zhanshi123.globalprefix.Database;

import java.util.HashMap;

public class Cacher {
    private static Cacher instance;

    public static Cacher getInstance() {
        return instance;
    }

    public Cacher() {
        instance = this;
    }

    private HashMap<String, PlayerData> data = new HashMap<>();

    public HashMap<String, PlayerData> getData() {
        return data;
    }

    public void setData(HashMap<String, PlayerData> data) {
        this.data = data;
    }

    public void add(String player) {
        if (data.containsKey(player)) {
            data.remove(player);
        }
        data.put(player, Database.getInstance().getData(player));
    }

    public PlayerData get(String player) {
        return data.get(player);
    }

    public void remove(String player) {
        data.remove(player);
    }
}
