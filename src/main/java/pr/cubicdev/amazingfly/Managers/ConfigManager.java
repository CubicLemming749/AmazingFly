package pr.cubicdev.amazingfly.Managers;

import org.bukkit.configuration.file.FileConfiguration;
import pr.cubicdev.amazingfly.AmazingFly;

import java.util.List;

public class ConfigManager {
    AmazingFly main;
    FileConfiguration config;
    public ConfigManager(AmazingFly main){
        this.main = main;
        this.config = main.config();
    }
    List<String> restrictedGamemodes;
    boolean sendMessageOn;
    boolean sendMessageOff;

    public void registerConfigOptions(){
        this.restrictedGamemodes = config.getStringList("Config.options.gamemode-restriction");
        this.sendMessageOn = config.getBoolean("Config.options.send-message-to-target-on");
        this.sendMessageOff = config.getBoolean("Config.options.send-message-to-target-off");
    }

    public List<String> getRestrictedGamemodes(){
        return restrictedGamemodes;
    }

    public boolean isSendMessageOn(){
        return sendMessageOn;
    }

    public boolean isSendMessageOff(){
        return sendMessageOff;
    }

    public void reloadConfigOptions(){
        this.config = main.config();
        this.registerConfigOptions();
    }
}
