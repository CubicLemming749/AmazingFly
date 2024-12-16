package pr.cubicdev.amazingfly.Hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pr.cubicdev.amazingfly.Utils.FlyUtils;

public class PlaceholderAPI extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "AmazingFly";
    }

    @Override
    public @NotNull String getAuthor() {
        return "CubicLemming749";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.2.1";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.equalsIgnoreCase("isflying")) {
            return String.valueOf(FlyUtils.hasFly(player));
        }else if(params.startsWith("isflying_")){
            String targetName = params.substring("isflying_".length());
            Player target = Bukkit.getPlayer(targetName);
            if(target == null){
                return "Player is not online";
            }else{
                return String.valueOf(FlyUtils.hasFly(target));
            }
        }else if(params.startsWith("canfly")){
            return String.valueOf(player.hasPermission("amazingfly.use"));
        }else if(params.startsWith("canfly_")){
            String targetName = params.substring("canfly_".length());
            Player target = Bukkit.getPlayer(targetName);
            if(target == null){
                return "Player is not online";
            }else{
                return String.valueOf(target.hasPermission("amazingfly.use"));
            }
        }

        return null;
    }
}
