package me.zhanshi123.globalprefix.commands.subcommands;

import me.zhanshi123.globalprefix.Database;
import me.zhanshi123.globalprefix.cacher.PlayerData;
import me.zhanshi123.globalprefix.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RemoveCommand extends SubCommand {
    public RemoveCommand() {
        super("remove");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.isOp()) {
            if (args.length != 2) {
                sender.sendMessage("§c参数不正确!");
                return true;
            }
            String name = args[1];
            PlayerData data = Database.getInstance().getData(name);
            if (data != null) {
                data.setPrefix(null);
                data.setSuffix(null);
            }
            Database.getInstance().deleteData(name);
            sender.sendMessage("§a完成");
        }
        return true;

    }
}
