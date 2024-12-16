package pr.cubicdev.amazingfly;

import com.sk89q.worldguard.WorldGuard;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import pr.cubicdev.amazingfly.Commands.AFlyCommand;
import pr.cubicdev.amazingfly.Commands.FlyCommand;
import pr.cubicdev.amazingfly.Commands.RegionFlyCommand;
import pr.cubicdev.amazingfly.Hooks.PlaceholderAPI;
import pr.cubicdev.amazingfly.Hooks.WorldGuardHook;
import pr.cubicdev.amazingfly.Listener.PlayerListener;
import pr.cubicdev.amazingfly.Managers.ConfigManager;
import pr.cubicdev.amazingfly.Managers.MessageManager;
import pr.cubicdev.amazingfly.Utils.MessageUtils;
import pr.cubicdev.amazingfly.Utils.TimerUtils;

import java.util.Objects;

public final class AmazingFly extends JavaPlugin {

    //Crear instancias
    MessageManager mm;
    ConfigManager cm;
    final String prefix = "&7[&aAmazingFly&7] ";
    final String debugPrefix = "&7[&aAmazingFly-Debug&7] ";
    MessageUtils mu = new MessageUtils(this);
    TimerUtils timerUtils;
    boolean worldGuardHook = true;

    @Override
    public void onLoad(){
        cm = new ConfigManager(this);
        mm = new MessageManager(this);
        saveDefaultConfig();
        cm.registerConfigOptions();
        mm.initializeMessages();
        if (cm.isWorldGuardHook()) {
            try {
                WorldGuardHook worldGuard = new WorldGuardHook();
                worldGuard.registerFlags();
            } catch (NoClassDefFoundError  e) {
                worldGuardHook = false;
            }
        }
    }
    @Override
    public void onEnable() {
        //Mensajes, solo eso XD
        MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(prefix+"&fAmazingFly has been activated."));

        if (cm.isDebugMode()) {
            printConfig();
        }

        //Registrar cosas xd
        timerUtils = new TimerUtils(this);
        registerCommands();
        registerEvents();
        placeholderApi();
        worldGuard();

        startMetrics();
    }

    @Override
    public void onDisable() {
    }

    public FileConfiguration config(){
        return this.getConfig();
    }

    public void registerCommands(){
        Objects.requireNonNull(this.getCommand("fly")).setExecutor(new FlyCommand(this));
        Objects.requireNonNull(this.getCommand("afly")).setExecutor(new AFlyCommand(this));
        Objects.requireNonNull(this.getCommand("afly")).setTabCompleter(new AFlyCommand(this));
    }

    public void registerEvents(){
        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    public void placeholderApi(){
        if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new PlaceholderAPI().register();
            MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(prefix+"&aPlaceholderAPI expansion registered!"));
        }
    }

    public void worldGuard(){
        if(worldGuardHook){
            MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(prefix+"&aSuccesfully hooked with worldguard."));
            Objects.requireNonNull(this.getCommand("rfly")).setExecutor(new RegionFlyCommand(this));
        }
    }
    public void startMetrics(){
        int pluginId = 24105;
        Metrics metrics = new Metrics(this, pluginId);
    }

    public void printConfig() {
        MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(debugPrefix+"&fRestricted gamemodes: "));

        if (cm.getRestrictedGamemodes().isEmpty()) {
            MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(debugPrefix+"&7- &fNot registered."));
        } else {
            for (String restrictedGamemodes : cm.getRestrictedGamemodes()) {
                MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(debugPrefix+"&7- " + restrictedGamemodes.toUpperCase()));
            }
        }

        MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(debugPrefix+"&fsend-message-to-target-on: &a"+cm.isSendMessageOn()));
        MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(debugPrefix+"&fsend-message-to-target-off: &a"+cm.isSendMessageOff()));
        MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(debugPrefix+"&finmunity-to-fall-damage: &a"+cm.getInmunitySeconds()));
        MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(debugPrefix+"&fdeactivate-fly-mode-pvp.enabled: &a"+cm.isDeactivateFlightModePvP()));
        MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(debugPrefix+"&fdeactivate-fly-mode-pvp.time-of-restriction: &a"+cm.getRestrictionSeconds()+" &fseconds"));
        MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(debugPrefix+"&fWorldGuard Hook: &a"+cm.isWorldGuardHook()));
    }

    public ConfigManager getCm(){
        return cm;
    }
    public MessageManager getMm(){
        return mm;
    }

    public boolean isWorldGuardHook() {
        return worldGuardHook;
    }

    public TimerUtils getTimerUtils() {
        return timerUtils;
    }

    public String getDebugPrefix() {
        return debugPrefix;
    }
}
