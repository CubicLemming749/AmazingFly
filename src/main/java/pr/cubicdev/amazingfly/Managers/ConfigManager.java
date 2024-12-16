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
    boolean worldGuardHook;
    int inmunitySeconds;
    boolean deactivateFlightModePvP;
    int restrictionSeconds;
    boolean debugMode;

    public void registerConfigOptions(){
        this.restrictedGamemodes = config.getStringList("Config.options.gamemode-restriction");
        this.sendMessageOn = config.getBoolean("Config.options.send-message-to-target-on");
        this.sendMessageOff = config.getBoolean("Config.options.send-message-to-target-off");
        this.worldGuardHook = config.getBoolean("Config.hooks.WorldGuard");
        this.inmunitySeconds = config.getInt("Config.options.inmunity-to-fall-damage");
        this.deactivateFlightModePvP = config.getBoolean("Config.options.deactivate-fly-mode-pvp.enabled");
        this.restrictionSeconds = config.getInt("Config.options.deactivate-fly-mode-pvp.time-of-restriction");
        this.debugMode = config.getBoolean("Config.options.debug");
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

    public boolean isWorldGuardHook() {
        return worldGuardHook;
    }

    public int getInmunitySeconds() {
        return inmunitySeconds;
    }

    public int getRestrictionSeconds() {
        return restrictionSeconds;
    }

    public boolean isDeactivateFlightModePvP() {
        return deactivateFlightModePvP;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void reloadConfigOptions(){
        this.config = main.config();
        this.registerConfigOptions();
    }
}
