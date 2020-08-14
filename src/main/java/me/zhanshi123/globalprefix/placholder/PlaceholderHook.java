package me.zhanshi123.globalprefix.placholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.zhanshi123.globalprefix.ConfigManager;
import me.zhanshi123.globalprefix.Database;
import me.zhanshi123.globalprefix.cacher.Cacher;
import me.zhanshi123.globalprefix.cacher.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PlaceholderHook extends PlaceholderExpansion {
    private final String VERSION = getClass().getPackage().getImplementationVersion();

    @Override
    public String getIdentifier() {
        return "gp";
    }

    @Override
    public String getAuthor() {
        return "Soldier";
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public String getRequiredPlugin() {
        return "GlobalPrefix";
    }

    @Override
    public boolean canRegister() {
        if (!Bukkit.getPluginManager().isPluginEnabled(getRequiredPlugin())) {
            return false;
        }
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {
        if (offlinePlayer == null || !offlinePlayer.isOnline()) {
            return null;
        }
        String name = offlinePlayer.getName();
        if (params.equalsIgnoreCase("cached_prefix")) {
            return getPrefix(Cacher.getInstance().get(name)).replace("&", "§");
        } else if (params.equalsIgnoreCase("cached_suffix")) {
            return getSuffix(Cacher.getInstance().get(name)).replace("&", "§");
        } else if (params.equalsIgnoreCase("prefix")) {
            return getPrefix(Database.getInstance().getData(name)).replace("&", "§");
        } else if (params.equalsIgnoreCase("suffix")) {
            return getSuffix(Database.getInstance().getData(name)).replace("&", "§");
        } else {
            return null;
        }
    }

    private String getPrefix(PlayerData data) {
        if (data == null) {
            return ConfigManager.getInstance().getNoData();
        } else {
            if (data.getPrefix() == null) {
                return ConfigManager.getInstance().getNoData();
            } else {
                return data.getPrefix();
            }
        }
    }

    private String getSuffix(PlayerData data) {
        if (data == null) {
            return ConfigManager.getInstance().getNoData();
        } else {
            if (data.getSuffix() == null) {
                return ConfigManager.getInstance().getNoData();
            } else {
                return data.getSuffix();
            }
        }
    }
}
