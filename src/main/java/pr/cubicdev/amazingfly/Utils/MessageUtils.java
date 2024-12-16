package pr.cubicdev.amazingfly.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import pr.cubicdev.amazingfly.AmazingFly;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MessageUtils {
    static AmazingFly main;

    public MessageUtils(AmazingFly main) {
        MessageUtils.main = main;
    }

    public static Logger logger = Bukkit.getLogger();

    public static void sendMessageConsole(String msg) {
        main.getServer().getConsoleSender().sendMessage(msg);
    }

    public static String getColoredMsg(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public String convertTimeMeasures(String msg, int seconds) {
        TimeUnit timeUnit;
        return null;
    }
}
