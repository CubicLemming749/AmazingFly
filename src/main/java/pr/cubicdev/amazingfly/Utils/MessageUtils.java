package pr.cubicdev.amazingfly.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import pr.cubicdev.amazingfly.AmazingFly;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtils {
    static AmazingFly main;

    public MessageUtils(AmazingFly main) {
        MessageUtils.main = main;
    }

    public static final String hexSupport = "#";

    public static void sendMessageConsole(String msg) {
        main.getServer().getConsoleSender().sendMessage(msg);
    }

    public static String getColoredMsg(String msg) {
        if(msg.contains(hexSupport)){
            Pattern pattern = Pattern.compile("(#[a-fA-F0-9]{6})");
            Matcher matcher = pattern.matcher(msg);
            while(matcher.find()){
                String hexCode = matcher.group();
                msg = msg.replace(hexCode, net.md_5.bungee.api.ChatColor.of(hexCode) + "");
            }
        }
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
