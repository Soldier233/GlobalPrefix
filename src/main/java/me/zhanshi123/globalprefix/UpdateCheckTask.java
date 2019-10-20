package me.zhanshi123.globalprefix;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateCheckTask extends BukkitRunnable {
    @Override
    public void run() {
        try {
            URL url = new URL("http://service.zhanshi123.me/update/index.php?name=GlobalPrefix");
            InputStream in = url.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String json = br.readLine();
            Update update = new Gson().fromJson(json, Update.class);
            if (!String.valueOf(update.getVersion()).equalsIgnoreCase(GlobalPrefix.getInstance().getDescription().getVersion())){
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        Bukkit.getConsoleSender().sendMessage("§6§lGlobalPrefix §7>>> §a插件有新版本"+update.getVersion()+"了，更新内容为: §7"+update.getMessage()+" §a赶快去下载吧!");
                    }
                }.runTask(GlobalPrefix.getInstance());
            }
            new BukkitRunnable(){
                @Override
                public void run() {
                    Bukkit.getConsoleSender().sendMessage("§6§lGlobalPrefix §7>>> §a插件你目前使用的是最新版本");
                }
            }.runTask(GlobalPrefix.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
