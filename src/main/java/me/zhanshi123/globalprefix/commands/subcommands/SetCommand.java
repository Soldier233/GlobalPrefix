package me.zhanshi123.globalprefix.commands.subcommands;

import me.zhanshi123.globalprefix.Database;
import me.zhanshi123.globalprefix.GlobalPrefix;
import me.zhanshi123.globalprefix.cacher.PlayerData;
import me.zhanshi123.globalprefix.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommand extends SubCommand {
    public SetCommand() {
        super("set");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("gp.set") && sender instanceof Player) {
            Player player = (Player) sender;
            String name = GlobalPrefix.getInstance().getPlayerName(player);
            if (args.length != 3) {
                sender.sendMessage("§c参数不正确!");
                return true;
            } else {
                String type = args[1];
                String value = args[2];
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
                sender.sendMessage("§a§l成功为你自己修改称号");
                return true;
            }
        }
        return true;

    }
}
