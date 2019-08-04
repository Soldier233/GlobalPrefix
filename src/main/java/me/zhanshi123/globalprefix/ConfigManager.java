package me.zhanshi123.globalprefix;

import com.google.common.base.Charsets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ConfigManager {
    private static ConfigManager instance = null;

    public static ConfigManager getInstance() {
        return instance;
    }

    private GlobalPrefix plugin = GlobalPrefix.getInstance();
    private File f = null;
    private FileConfiguration config;

    public ConfigManager() {
        try {
            config = new YamlConfiguration();
            f = new File(plugin.getDataFolder(), "config.yml");
            if (!f.exists()) {
                plugin.saveResource("config.yml", false);
            }
            config.load(new BufferedReader(new InputStreamReader(new FileInputStream(f), Charsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        instance = this;
    }

    public String getName() {
        return config.getString("MySQL.name", "default");
    }

    public String getTable() {
        return config.getString("MySQL.table", "globalprefix");
    }

    public String getNoData() {
        return config.getString("NoData","-");
    }
}
