package pr.cubicdev.amazingfly.Utils;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pr.cubicdev.amazingfly.AmazingFly;
import pr.cubicdev.amazingfly.Managers.ConfigManager;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TimerUtils {
    AmazingFly main;
    ConcurrentHashMap<UUID, Integer> playerTimerHashmap;
    ConfigManager cm;
    public TimerUtils(AmazingFly main){
        this.main = main;
        this.playerTimerHashmap = new ConcurrentHashMap<>();
        this.cm = main.getCm();
    }
    public void startTimer(Player player, UUID uuid, ConcurrentHashMap<UUID, Integer> hashMap, TimerType timerType){
        int initialSeconds = cm.getRestrictionSeconds();
        hashMap.put(uuid, initialSeconds);
        if(cm.isDebugMode()){
            MessageUtils.sendMessageConsole(MessageUtils.getColoredMsg(main.getDebugPrefix()+"&aNew timer created. Type: &c"+timerType));
        }
        new BukkitRunnable(){
            int remainingSeconds = hashMap.get(uuid);
            @Override
            public void run(){
                remainingSeconds--;
                hashMap.put(uuid, remainingSeconds);
                if(remainingSeconds == 0){
                    hashMap.remove(uuid);
                    cancel();
                }
            }
        }.runTaskTimer(this.main, 0, 20);
    }

    public ConcurrentHashMap<UUID, Integer> getPlayerTimerHashmap(){
        return playerTimerHashmap;
    }
    public int getRestrictedSeconds(UUID uuid, ConcurrentHashMap<UUID, Integer> hashMap){
        return hashMap.getOrDefault(uuid, 0);
    }
}
