package pr.cubicdev.amazingfly.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pr.cubicdev.amazingfly.AmazingFly;
import pr.cubicdev.amazingfly.Hooks.WorldGuardHook;
import pr.cubicdev.amazingfly.Managers.ConfigManager;
import pr.cubicdev.amazingfly.Managers.MessageManager;
import pr.cubicdev.amazingfly.Utils.*;

public class PlayerListener implements Listener {
    AmazingFly main;
    ConfigManager cm;
    MessageManager mm;
    TimerUtils timerUtils;
    final String debugPrefix = "&7[&aAmazingFly-Debug&7] ";
    HookUtils hookUtils;
    public PlayerListener(AmazingFly main){
        this.main = main;
        this.cm = main.getCm();
        this.mm = main.getMm();
        this.timerUtils = main.getTimerUtils();
        this.hookUtils = new HookUtils(this.main);
    }

    @EventHandler
    public void onPlayerMovement(PlayerMoveEvent e){
        if(!FlyUtils.hasFly(e.getPlayer()) || !e.getPlayer().hasMetadata("region_fly")) return;
        hookUtils.checkPlayerFlyRegion(e.getPlayer());
        giveFallInmunity(e.getPlayer(), cm.getInmunitySeconds());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if(e.getCause() == EntityDamageEvent.DamageCause.FALL && player.hasMetadata("fall_inmunity")){
                e.setCancelled(true);
            }else if(cm.isDeactivateFlightModePvP()){
                if((e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) && FlyUtils.hasFly(player)){
                    if(cm.getRestrictionSeconds() > 0 && !timerUtils.getPlayerTimerHashmap().containsKey(player.getUniqueId())){
                        if(player.hasPermission("amazingfly.bypass.pvp-restriction")){
                            if(cm.isDebugMode()){
                                MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(main.getDebugPrefix()+"&a"+player.getName()+"&a entered in PvP, but has &6amazingfly.bypass.pvp-restriction &apermission. No timer created."));
                            }
                            return;
                        }
                        FlyUtils.removeFly(player);
                        player.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getPvpDeactivatedFlight()));
                        this.timerUtils.startTimer(player, player.getUniqueId(), timerUtils.getPlayerTimerHashmap(), TimerType.PVP_RESTRICTION);
                    }
                }
            }
        }else if(e.getDamager() instanceof Player){
            Player player = (Player) e.getDamager();
            if(cm.isDeactivateFlightModePvP()){
                if(cm.getRestrictionSeconds() > 0 && !timerUtils.getPlayerTimerHashmap().containsKey(player.getUniqueId())){
                    if(player.hasPermission("amazingfly.bypass.pvp-restriction")){
                        if(cm.isDebugMode()){
                            MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(main.getDebugPrefix()+"&a"+player.getName()+"&a entered in PvP, but has &6amazingfly.bypass.pvp-restriction &apermission. No timer created."));
                        }
                        return;
                    }
                    FlyUtils.removeFly(player);
                    player.sendMessage(MessageUtils.getColoredMsg(mm.getPrefix()+mm.getPvpDeactivatedFlight()));
                    this.timerUtils.startTimer(player, player.getUniqueId(), timerUtils.getPlayerTimerHashmap(), TimerType.PVP_RESTRICTION);
                }
            }
        }
    }

    public void giveFallInmunity(Player player, int seconds){
        player.setMetadata("fall_inmunity", new FixedMetadataValue(this.main, true));

        Bukkit.getScheduler().runTaskLater(this.main, () -> {
            player.removeMetadata("fall_inmunity", this.main);
        }, seconds * 20L);
    }
}
