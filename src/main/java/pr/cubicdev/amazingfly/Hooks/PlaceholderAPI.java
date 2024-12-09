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
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.equalsIgnoreCase("isflying")) {
            return String.valueOf(FlyUtils.isFlying(player));
        }else if(params.startsWith("isflying_")){
            String targetName = params.substring("flymode".length());
            Player target = Bukkit.getPlayer(targetName);
            if(target == null){
                return "Player is not online";
            }else{
                return String.valueOf(FlyUtils.isFlying(target));
            }
        }

        return null;
    }
}
