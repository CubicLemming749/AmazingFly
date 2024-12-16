package pr.cubicdev.amazingfly.Commands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import pr.cubicdev.amazingfly.AmazingFly;
import pr.cubicdev.amazingfly.Hooks.WorldGuardHook;
import pr.cubicdev.amazingfly.Managers.ConfigManager;
import pr.cubicdev.amazingfly.Managers.MessageManager;
import pr.cubicdev.amazingfly.Utils.FlyUtils;
import pr.cubicdev.amazingfly.Utils.MessageUtils;
import pr.cubicdev.amazingfly.Utils.TimerUtils;

public class RegionFlyCommand implements CommandExecutor {
    AmazingFly main;
    ConfigManager cm;
    MessageManager mm;
    TimerUtils timerUtils;
    public RegionFlyCommand(AmazingFly main){
        this.main = main;
        this.cm = main.getCm();
        this.mm = main.getMm();
        this.timerUtils = main.getTimerUtils();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getConsoleError()));
            return true;
        }

        Player player = (Player) sender;
        if(!player.hasPermission("amazingfly.use.regions")){
            player.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getNoPermissions()));
            return true;
        }

        if(timerUtils.getPlayerTimerHashmap().containsKey(player.getUniqueId())){
            int remainingSeconds = timerUtils.getRestrictedSeconds(player.getUniqueId(), timerUtils.getPlayerTimerHashmap());
            player.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getPvpRestrictionSeconds().replace("{SECONDS}", String.valueOf(remainingSeconds))));
            return true;
        }

        StateFlag flag = WorldGuardHook.getAmazingFly();
        Location loc = BukkitAdapter.adapt(player.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(loc);

        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);

        if(set.testState(localPlayer, WorldGuardHook.getAmazingFly())){
            if (!FlyUtils.hasFly(player)) {
                FlyUtils.setFly(player);
                player.setMetadata("region_fly", new FixedMetadataValue(this.main, true));
                if(cm.isDebugMode() && player.hasMetadata("region_fly")){
                    MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(main.getDebugPrefix()+"&aPlayer: &6"+player.getName()+" &ahas now 'region_fly' metadata."));
                }
                player.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getFlyActivated()));
                return true;
            } else {
                FlyUtils.removeFly(player);
                if (player.hasMetadata("region_fly")) {
                    player.removeMetadata("region_fly", this.main);
                    if(cm.isDebugMode()){
                        MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(main.getDebugPrefix()+"&aPlayer: &6"+player.getName()+" &ahas now 'region_fly' metadata removed."));
                    }
                }
                player.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getFlyDeactivated()));
                giveFallInmunity(player, cm.getInmunitySeconds());
                return true;
            }
        }else{
            player.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getFlyWorldGuardError()));
            return true;
        }
    }

    public void giveFallInmunity(Player player, int seconds){
        player.setMetadata("fall_inmunity", new FixedMetadataValue(this.main, true));

        Bukkit.getScheduler().runTaskLater(this.main, () -> {
            player.removeMetadata("fall_inmunity", this.main);
        }, seconds * 20L);
    }
}
