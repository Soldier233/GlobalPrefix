package me.zhanshi123.globalprefix;

import me.zhanshi123.globalprefix.cacher.Cacher;
import me.zhanshi123.globalprefix.commands.Commands;
import me.zhanshi123.globalprefix.metrics.Metrics;
import me.zhanshi123.globalprefix.placholder.PlaceholderHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class GlobalPrefix extends JavaPlugin {
    private static GlobalPrefix instance;

    public static GlobalPrefix getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        new ConfigManager();
        Plugin jdbc = Bukkit.getPluginManager().getPlugin("JdbcConnectionBridge");
        if (jdbc == null) {
            Bukkit.getConsoleSender().sendMessage("§6§lGlobalPrefix §7>>> §c无法找到JdbcConnectionBridge插件，请检查是否安装，数据库无法连接，停止加载");
            setEnabled(false);
            return;
        }
        new Database();
        new Cacher();
        new Commands();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), instance);
        Plugin papi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        if (papi != null) {
            new PlaceholderHook().register();
        }
        new Metrics(instance);
        Bukkit.getConsoleSender().sendMessage("§6§lGlobalPrefix §7>>> §a即将开始检查版本");
        new UpdateCheckTask().runTaskAsynchronously(instance);
        Bukkit.getConsoleSender().sendMessage("§6§lGlobalPrefix §7>>> §a插件加载完成!");
    }

    @Override
    public void onDisable() {
        Database.getInstance().close();
    }

    public String getPlayerName(Player player) {
        if (ConfigManager.getInstance().isUUID()) {
            return player.getUniqueId().toString();
        } else {
            return player.getName();
        }
    }
}
