package me.zhanshi123.globalprefix.commands;

import me.zhanshi123.globalprefix.commands.subcommands.QueryCommand;
import me.zhanshi123.globalprefix.commands.subcommands.RemoveCommand;
import me.zhanshi123.globalprefix.commands.subcommands.SetCommand;
import me.zhanshi123.globalprefix.commands.subcommands.UpdateCommand;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class Commands
{
    private static Commands instance=null;

    public static Commands getInstance() {
        return instance;
    }


    private List<SubCommand> commands= new ArrayList<SubCommand>();
    public Commands()
    {
        Bukkit.getPluginCommand("gp").setExecutor(new CommandsExecutor());
        commands.add(new QueryCommand());
        commands.add(new UpdateCommand());
        commands.add(new SetCommand());
        commands.add(new RemoveCommand());
        instance=this;
    }

    public boolean isSubCommand(String cmd)
    {
        boolean fact=false;
        for(SubCommand command:commands)
        {
            if(command.getName().equalsIgnoreCase(cmd))
            {
                fact=true;
                break;
            }
        }
        return fact;
    }
    public SubCommand getSubCommand(String cmd)
    {
        SubCommand fact=null;
        for(SubCommand command:commands)
        {
            if(command.getName().equalsIgnoreCase(cmd))
            {
                fact=command;
                break;
            }
        }
        return fact;
    }
}
