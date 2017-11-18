package me.zhanshi123.globalprefix.hooks;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import me.zhanshi123.globalprefix.ConfigManager;
import me.zhanshi123.globalprefix.Database;
import me.zhanshi123.globalprefix.GlobalPrefix;
import me.zhanshi123.globalprefix.cacher.Cacher;
import me.zhanshi123.globalprefix.cacher.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PrefixPlaceholder extends EZPlaceholderHook{
    String noData;
    public PrefixPlaceholder(){
        super(GlobalPrefix.getInstance(), "gp");
        noData=ConfigManager.getInstance().getNoData();
    }

    @Override
    public String onPlaceholderRequest(Player player, String s) {
        String name=player.getName();
        PlayerData data=null;
        if(s.equalsIgnoreCase("cached_prefix")){
            return getPrefix(Cacher.getInstance().get(name)).replace("&","§");
        }
        else if(s.equalsIgnoreCase("cached_suffix")){
            return getSuffix(Cacher.getInstance().get(name)).replace("&","§");
        }
        else if(s.equalsIgnoreCase("prefix")){
            return getPrefix(Database.getInstance().getData(name)).replace("&","§");
        }
        else if(s.equalsIgnoreCase("suffix")){
            return getSuffix(Database.getInstance().getData(name)).replace("&","§");
        }
        else {
            return null;
        }
    }
    private String getPrefix(PlayerData data){
        if(data==null){
            return noData;
        }
        else{
            if(data.getPrefix()==null){
                return noData;
            }
            else{
                return data.getPrefix();
            }
        }
    }
    private String getSuffix(PlayerData data){
        if(data==null){
            return noData;
        }
        else{
            if(data.getSuffix()==null){
                return noData;
            }
            else{
                return data.getSuffix();
            }
        }
    }
}
