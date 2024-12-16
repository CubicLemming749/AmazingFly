package pr.cubicdev.amazingfly.Hooks;


import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

public class WorldGuardHook {
    public static StateFlag AMAZING_FLY;
    FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();

    public void registerFlags(){
        try {
            StateFlag flag = new StateFlag("amazing-fly", false);
            registry.register(flag);
            AMAZING_FLY = flag;
        } catch (FlagConflictException e) {
            Flag<?> existing = registry.get("amazing-fly");
            if(existing instanceof StateFlag){
                AMAZING_FLY = (StateFlag) existing;
            }
        }
    }

    public static StateFlag getAmazingFly() {
        return AMAZING_FLY;
    }

    public boolean isAmazingFlagRegistered(){
        return AMAZING_FLY != null;
    }
}
