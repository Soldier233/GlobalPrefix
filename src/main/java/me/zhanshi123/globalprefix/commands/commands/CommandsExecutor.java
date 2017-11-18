package me.zhanshi123.globalprefix.commands.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandsExecutor implements CommandExecutor
{
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(args.length==0)
        {
            sendHelp(commandSender);
            return false;
        }
        else
        {
            String arg=args[0];
            SubCommand subCommand=Commands.getInstance().getSubCommand(arg);
            if(subCommand==null)
            {
                subCommand=Commands.getInstance().getSubCommand("*");
            }
            return subCommand.onCommand(commandSender,command,label,args);
        }
    }
    private void sendHelp(CommandSender sender) {
        sender.sendMessage(new String[]{
                "§6§l命令帮助:",
                "§a/gp query §c查看你自己的称号情况"
        });
        if(sender.isOp()){
            sender.sendMessage(new String[]{
                    "§6§lOP命令帮助:",
                    "§a/gp update <玩家> <p/s> <值> §c更新某玩家的称号情况，p为前缀，s为后缀"
            });
        }
    }
}
