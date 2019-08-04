package me.zhanshi123.globalprefix.commands.subcommands;

import me.zhanshi123.globalprefix.Database;
import me.zhanshi123.globalprefix.cacher.PlayerData;
import me.zhanshi123.globalprefix.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class UpdateCommand extends SubCommand {

    public UpdateCommand() {
        super("update");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.isOp()) {
            if (args.length != 4) {
                sender.sendMessage("§c参数不正确!");
                return false;
            } else {
                String name = args[1];
                String type = args[2];
                String value = args[3];
                PlayerData data = Database.getInstance().getData(name);
                if (data == null) {
                    if (type.equalsIgnoreCase("p")) {
                        data = new PlayerData(name, value, null);
                    } else {
                        data = new PlayerData(name, null, value);
                    }
                    Database.getInstance().insertData(data);
                } else {
                    if (type.equalsIgnoreCase("p")) {
                        data.setPrefix(value);
                    } else {
                        data.setSuffix(value);
                    }
                    Database.getInstance().updateData(data);
                }
                sender.sendMessage("§a§l完成!玩家目前称号状态如下(null代表无)");
                sender.sendMessage(data.toString());
                return true;
            }
        }
        return false;
    }
}
