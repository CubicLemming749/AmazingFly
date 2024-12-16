package pr.cubicdev.amazingfly.Utils;

import org.bukkit.entity.Player;

public class FlyUtils {
    public static void setFly(Player player){
        player.setAllowFlight(true);
        player.setFlying(true);
    }

    public static void removeFly(Player player){
        player.setAllowFlight(false);
        player.setFlying(false);
    }

    public static boolean hasFly(Player player){
        return player.getAllowFlight();
    }

}
