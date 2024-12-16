package pr.cubicdev.amazingfly.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pr.cubicdev.amazingfly.AmazingFly;
import pr.cubicdev.amazingfly.Managers.ConfigManager;
import pr.cubicdev.amazingfly.Managers.MessageManager;
import pr.cubicdev.amazingfly.Utils.MessageUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AFlyCommand implements CommandExecutor, TabExecutor {
    AmazingFly main;
    MessageManager mm;
    ConfigManager cm;
    public List<String> tabCompletes;
    public AFlyCommand(AmazingFly main){
        this.main = main;
        this.mm = main.getMm();
        this.cm = main.getCm();
        this.tabCompletes = new ArrayList<>();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("amazingfly.admin")){
            sender.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getNoPermissions()));
            return true;
        }

        if(args.length == 0){
            sender.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getBadUsage().replace("{command}", "afly").replace("{subcommands}", "reload")));
            return true;
        }

        String argument = args[0];
        switch (argument){
            case "reload" -> {
                main.reloadConfig();
                mm.reloadMessages();
                cm.reloadConfigOptions();
                sender.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getPluginReload()));
            }
            default -> {
                sender.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getBadUsage().replace("{command}", "afly").replace("{subcommands}", "reload")));
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(sender.hasPermission("amazingfly.admin")){
            addTabCompletes(args);
            return tabCompletes;
        }
        return null;
    }

    public void addTabCompletes(String[] args){
        tabCompletes.clear();
        if(args.length == 0){
            tabCompletes.add("reload");
        }
    }
}
