package me.zhanshi123.globalprefix.commands.subcommands;

import me.zhanshi123.globalprefix.ConfigManager;
import me.zhanshi123.globalprefix.Database;
import me.zhanshi123.globalprefix.cacher.PlayerData;
import me.zhanshi123.globalprefix.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QueryCommand extends SubCommand{

    public QueryCommand() {
        super("query");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player=(Player) sender;
            String name=player.getName();
            PlayerData data=Database.getInstance().getData(name);
            if(data==null){
                player.sendMessage("§a§l你没有称号");
            } else{
              player.sendMessage("§a你的前缀:"+getPrefix(data).replace("&","§"));
              player.sendMessage("§a你的后缀:"+getSuffix(data).replace("&","§"));
            }
        }
        return true;

    }
    String noData= ConfigManager.getInstance().getNoData();
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
