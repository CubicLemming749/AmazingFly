package pr.cubicdev.amazingfly.Managers;

import org.bukkit.configuration.file.FileConfiguration;
import pr.cubicdev.amazingfly.AmazingFly;

public class MessageManager {
    AmazingFly main;
    FileConfiguration configManager;
    public MessageManager(AmazingFly main){
        this.main = main;
        this.configManager = main.config();
    }

    String prefix;
    String restrictedGamemode;
    String noPermissions;
    String pluginReload;
    String flyActivated;
    String flyActivatedOther;
    String flyDeactivated;
    String flyDeactivatedOther;
    String consoleError;
    String flyOthersError;
    String otherActivated;
    String otherDeactivated;
    String badUsage;
    String flyWorldGuardError;
    String tempFlyActivated;
    String tempFlyDeactivated;
    String tempFlyActivedOther;
    String tempFlyDeactivedOther;
    String pvpDeactivatedFlight;
    String pvpRestrictionSeconds;

    public void initializeMessages(){
        this.prefix = configManager.getString("Config.lang.prefix");
        this.restrictedGamemode = configManager.getString("Config.lang.restricted-gamemode");
        this.noPermissions = configManager.getString("Config.lang.no-permissions");
        this.pluginReload = configManager.getString("Config.lang.plugin-reload");
        this.flyActivated = configManager.getString("Config.lang.fly-activated");
        this.flyActivatedOther = configManager.getString("Config.lang.fly-activated-other");
        this.flyDeactivated = configManager.getString("Config.lang.fly-deactivated");
        this.flyDeactivatedOther = configManager.getString("Config.lang.fly-deactivated-other");
        this.consoleError = configManager.getString("Config.lang.console-error");
        this.flyOthersError = configManager.getString("Config.lang.fly-others-error");
        this.otherActivated = configManager.getString("Config.lang.fly-activated-target");
        this.otherDeactivated = configManager.getString("Config.lang.fly-deactivated-target");
        this.badUsage = configManager.getString("Config.lang.command-bad-usage");
        this.flyWorldGuardError = configManager.getString("Config.lang.fly-worldguard-flag-error");
        this.tempFlyActivated = configManager.getString("Config.lang.temp-fly-activated");
        this.tempFlyDeactivated = configManager.getString("Config.lang.temp-fly-deactivated");
        this.tempFlyActivedOther = configManager.getString("Config.lang.temp-fly-activated-other");
        this.tempFlyDeactivedOther = configManager.getString("Config.lang.temp-fly-deactivated-other");
        this.pvpRestrictionSeconds = configManager.getString("Config.lang.restriction-seconds");
        this.pvpDeactivatedFlight = configManager.getString("Config.lang.pvp-deactivate");
    }

    public String getPrefix(){
        return prefix;
    }
    public String getNoPermissions() {
        return noPermissions;
    }

    public String getRestrictedGamemode(){
        return restrictedGamemode;
    }

    public String getPluginReload() {
        return pluginReload;
    }

    public String getFlyActivated() {
        return flyActivated;
    }

    public String getFlyDeactivated() {
        return flyDeactivated;
    }

    public String getConsoleError() {
        return consoleError;
    }

    public String getFlyActivatedOther() {
        return flyActivatedOther;
    }

    public String getFlyDeactivatedOther() {
        return flyDeactivatedOther;
    }

    public String getFlyOthersError(){
        return flyOthersError;
    }

    public String getOtherActivated(){
        return otherActivated;
    }

    public String getOtherDeactivated(){
        return otherDeactivated;
    }

    public String getBadUsage() {
        return badUsage;
    }

    public String getFlyWorldGuardError() {
        return flyWorldGuardError;
    }

    public String getTempFlyActivated() {
        return tempFlyActivated;
    }

    public String getTempFlyDeactivated() {
        return tempFlyDeactivated;
    }

    public String getTempFlyActivedOther() {
        return tempFlyActivedOther;
    }

    public String getTempFlyDeactivedOther() {
        return tempFlyDeactivedOther;
    }

    public String getPvpRestrictionSeconds() {
        return pvpRestrictionSeconds;
    }

    public String getPvpDeactivatedFlight() {
        return pvpDeactivatedFlight;
    }

    public void reloadMessages(){
        this.configManager = main.config();
        this.initializeMessages();
    }

}
