package pr.cubicdev.amazingfly.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import pr.cubicdev.amazingfly.AmazingFly;
import pr.cubicdev.amazingfly.Managers.ConfigManager;
import pr.cubicdev.amazingfly.Managers.MessageManager;
import pr.cubicdev.amazingfly.Utils.FlyUtils;
import pr.cubicdev.amazingfly.Utils.MessageUtils;
import pr.cubicdev.amazingfly.Utils.TimerUtils;

import java.util.List;

public class FlyCommand implements CommandExecutor {
    AmazingFly main;
    ConfigManager cm;
    MessageManager mm;
    TimerUtils timerUtils;

    public FlyCommand(AmazingFly main) {
        this.main = main;
        this.cm = main.getCm();
        this.mm = main.getMm();
        this.timerUtils = main.getTimerUtils();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!commandSender.hasPermission("amazingfly.use")){
            commandSender.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getNoPermissions()));
            return true;
        }

        if(args.length == 0){
            if (!(commandSender instanceof Player)) {
                MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getConsoleError()));
                return true;
            }

            Player player = (Player) commandSender;

            if(timerUtils.getPlayerTimerHashmap().containsKey(player.getUniqueId())){
                int remainingSeconds = timerUtils.getRestrictedSeconds(player.getUniqueId(), timerUtils.getPlayerTimerHashmap());
                player.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getPvpRestrictionSeconds().replace("{SECONDS}", String.valueOf(remainingSeconds))));
                return true;
            }

            if(FlyUtils.hasFly(player)){
                player.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getFlyDeactivated()));
                FlyUtils.removeFly(player);
                giveFallInmunity(player, cm.getInmunitySeconds());
                return true;
            }

            List<String> restrictedGamemodes = cm.getRestrictedGamemodes();
            for(String restrictedGamemode : restrictedGamemodes){
                try {
                    GameMode gameMode = GameMode.valueOf(restrictedGamemode.toUpperCase());
                    if(player.getGameMode() == gameMode && !player.hasPermission("amazingfly.bypass")){
                        player.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getRestrictedGamemode()));
                        return true;
                    }
                } catch (IllegalArgumentException e) {
                    MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(mm.getPrefix()+"&4INVALID GAMEMODE SET IN CONFIG.YML!"));
                }
            }
            player.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getFlyActivated()));
            FlyUtils.setFly(player);
            return true;
        }
        if(args.length == 1){
            if(!commandSender.hasPermission("amazingfly.use.others")){
                commandSender.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getNoPermissions()));
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if(target != null){
                boolean isMessageActivatedOn = cm.isSendMessageOn();
                boolean isMessageActivatedOff = cm.isSendMessageOff();
                if(!FlyUtils.hasFly(target)){
                    commandSender.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getFlyActivatedOther().replace("{PLAYER}", target.getName())));
                    FlyUtils.setFly(target);
                    if (isMessageActivatedOn) {
                        target.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getOtherActivated().replace("{SENDER}", commandSender.getName())));
                        return true;
                    }
                    return true;
                }
                commandSender.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getFlyDeactivatedOther().replace("{PLAYER}", target.getName())));
                FlyUtils.removeFly(target);
                giveFallInmunity(target, cm.getInmunitySeconds());
                if (isMessageActivatedOff) {
                    target.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getOtherDeactivated().replace("{SENDER}", commandSender.getName())));
                    return true;
                }
                return true;
            }
            commandSender.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getFlyOthersError().replace("{PLAYER}", args[0])));
            return true;
        }
        return false;
    }

    public void giveFallInmunity(Player player, int seconds){
        player.setMetadata("fall_inmunity", new FixedMetadataValue(this.main, true));

        Bukkit.getScheduler().runTaskLater(this.main, () -> {
            player.removeMetadata("fall_inmunity", this.main);
        }, seconds * 20L);
    }
}
