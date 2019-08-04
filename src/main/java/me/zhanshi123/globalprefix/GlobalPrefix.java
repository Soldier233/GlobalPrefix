package me.zhanshi123.globalprefix;

import me.zhanshi123.globalprefix.cacher.Cacher;
import me.zhanshi123.globalprefix.commands.Commands;
import me.zhanshi123.globalprefix.metrics.Metrics;
import org.bukkit.Bukkit;
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
            Bukkit.getConsoleSender().sendMessage("§6§lGlobalPrefix §7>>> §c新版本的插件若需使用PlaceholderAPI,请按照帖子指示安装PlaceholderAPI拓展");
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
}
