package antisocialgang.randomevents;

import org.bukkit.plugin.java.JavaPlugin;

public class RandomEvents extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("RandomEvents plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("RandomEvents plugin disabled!");
    }
}
