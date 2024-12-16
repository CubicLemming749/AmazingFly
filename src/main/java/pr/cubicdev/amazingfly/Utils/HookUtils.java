package pr.cubicdev.amazingfly.Utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import pr.cubicdev.amazingfly.AmazingFly;
import pr.cubicdev.amazingfly.Hooks.WorldGuardHook;
import pr.cubicdev.amazingfly.Managers.ConfigManager;

public class HookUtils {
    AmazingFly main;
    ConfigManager cm;
    public HookUtils(AmazingFly main){
        this.main = main;
        this.cm = main.getCm();
    }
    String debugPrefix = "&7[&aAmazingFly-Debug&7] ";
    public void checkPlayerFlyRegion(Player player){
        StateFlag flag = WorldGuardHook.getAmazingFly();
        Location loc = BukkitAdapter.adapt(player.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(loc);

        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);

        if(set.testState(localPlayer, WorldGuardHook.getAmazingFly())) {
            return;
        }
        player.removeMetadata("region_fly", this.main);
        FlyUtils.removeFly(player);
        if (cm.isDebugMode()) {
            MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(debugPrefix+"&aThe plugin has removed 'region_fly' metadata to &e"+player.getName()+" &abecause he left the region."));
            MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(debugPrefix+"&aThe plugin has removed region-flight mode to &e"+player.getName()+" &abecause he left the region."));
        }
    }
}
