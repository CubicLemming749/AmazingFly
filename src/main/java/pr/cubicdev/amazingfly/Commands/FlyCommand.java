package pr.cubicdev.amazingfly.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pr.cubicdev.amazingfly.AmazingFly;
import pr.cubicdev.amazingfly.Managers.ConfigManager;
import pr.cubicdev.amazingfly.Managers.MessageManager;
import pr.cubicdev.amazingfly.Utils.FlyUtils;
import pr.cubicdev.amazingfly.Utils.MessageUtils;

import java.util.List;

public class FlyCommand implements CommandExecutor {
    AmazingFly main;
    ConfigManager cm;
    MessageManager mm;
    String prefix;

    public FlyCommand(AmazingFly main) {
        this.main = main;
        this.cm = main.getCm();
        this.mm = main.getMm();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        prefix = mm.getPrefix();
        if (args.length == 0) {
            if(!(commandSender instanceof Player)){
                MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(prefix+ mm.getConsoleError()));
                return true;
            } else {
                Player player = (Player) commandSender;
                if(!player.hasPermission("amazingfly.use")){
                    player.sendMessage(MessageUtils.getColoredMsg(prefix+ mm.getNoPermissions()));
                    return true;
                }else{
                    List<String> restrictedGamemodes = cm.getRestrictedGamemodes();
                    for(String gamemodes : restrictedGamemodes){
                        GameMode gameMode = GameMode.valueOf(gamemodes);
                        if(player.getGameMode() == gameMode){
                            player.sendMessage(MessageUtils.getColoredMsg(prefix+ mm.getRestrictedGamemode()));
                            return true;
                        }
                    }
                    if(FlyUtils.isFlying(player)){
                        FlyUtils.removeFly(player);
                        player.sendMessage(MessageUtils.getColoredMsg(prefix+ mm.getFlyDeactivated()));
                        return true;
                    }else{
                        FlyUtils.setFly(player);
                        player.sendMessage(MessageUtils.getColoredMsg(prefix+ mm.getFlyActivated()));
                        return true;
                    }
                }
            }
        } else if(args.length == 1){
            if(args[0].equalsIgnoreCase("reload")){
                main.reloadConfig();
                mm.reloadMessages();
                cm.reloadConfigOptions();
                commandSender.sendMessage(MessageUtils.getColoredMsg(prefix+ mm.getPluginReload()));
                return true;
            }else{
                if (commandSender.hasPermission("amazingfly.use.others")) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null){
                        if(!FlyUtils.isFlying(target)){
                            FlyUtils.setFly(target);
                            commandSender.sendMessage(MessageUtils.getColoredMsg(prefix+ mm.getFlyActivatedOther().replace("{PLAYER}", args[0])));
                            if(cm.isSendMessageOn()){
                                if(target == commandSender){
                                    return true;
                                } else {
                                target.sendMessage(MessageUtils.getColoredMsg(prefix+ mm.getOtherActivated().replace("{SENDER}", commandSender.getName())));
                            }
                                }
                            return true;
                        }else{
                            FlyUtils.removeFly(target);
                            commandSender.sendMessage(MessageUtils.getColoredMsg(prefix+ mm.getFlyDeactivatedOther().replace("{PLAYER}", args[0])));
                            if(cm.isSendMessageOff()){
                                if(target == commandSender){
                                    return true;
                                } else {
                                    target.sendMessage(MessageUtils.getColoredMsg(prefix+ mm.getOtherDeactivated().replace("{SENDER}", commandSender.getName())));
                                    return true;
                                }
                            }
                            return true;
                        }
                    }else{
                        commandSender.sendMessage(MessageUtils.getColoredMsg(prefix+ mm.getFlyOthersError().replace("{PLAYER}", args[0])));
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
