package pr.cubicdev.amazingfly;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import pr.cubicdev.amazingfly.Commands.FlyCommand;
import pr.cubicdev.amazingfly.Hooks.PlaceholderAPI;
import pr.cubicdev.amazingfly.Managers.ConfigManager;
import pr.cubicdev.amazingfly.Managers.MessageManager;
import pr.cubicdev.amazingfly.Utils.MessageUtils;

import java.util.Objects;

public final class AmazingFly extends JavaPlugin {

    //Crear instancias
    MessageManager mm;
    ConfigManager cm;
    MessageUtils mu = new MessageUtils(this);
    @Override
    public void onEnable() {
        //Mensajes, solo eso XD
        MessageUtils.logger.info("Activating AmazingFly plugin...");
        MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg("AmazingFly was activated."));

        //Instancias
        mm = new MessageManager(this);
        cm = new ConfigManager(this);

        //Crear config y registrar "cosas importantes"
        saveDefaultConfig();
        cm.registerConfigOptions();
        mm.initializeMessages();

        //Registrar cosas xd
        registerCommands();

        if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new PlaceholderAPI().register();
        }
    }

    @Override
    public void onDisable() {
    }

    public FileConfiguration config(){
        return this.getConfig();
    }

    public void registerCommands(){
        Objects.requireNonNull(this.getCommand("fly")).setExecutor(new FlyCommand(this));
    }

    public ConfigManager getCm(){
        return cm;
    }
    public MessageManager getMm(){
        return mm;
    }
}
