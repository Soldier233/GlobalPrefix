package me.zhanshi123.globalprefix.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandsExecutor implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(commandSender);
            return false;
        } else {
            String arg = args[0];
            SubCommand subCommand = Commands.getInstance().getSubCommand(arg);
            if (subCommand == null) {
                subCommand = Commands.getInstance().getSubCommand("*");
            }
            if (subCommand == null) {
                return true;
            }
            return subCommand.onCommand(commandSender, command, label, args);
        }
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(new String[]{
                "§6§l命令帮助:",
                "§a/gp query §c查看你自己的称号情况",
                "§a/gp set <p/s> <值> §c更新自己的称号情况，p为前缀，s为后缀（需要权限）"
        });
        if (sender.isOp()) {
            sender.sendMessage(new String[]{
                    "§6§lOP命令帮助:",
                    "§a/gp update <玩家> <p/s> <值> §c更新某玩家的称号情况，p为前缀，s为后缀",
                    "§a/gp remove <玩家> §c删除该玩家的称号"
            });
        }
    }
}
