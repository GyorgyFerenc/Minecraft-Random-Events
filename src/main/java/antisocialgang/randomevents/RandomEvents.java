package antisocialgang.randomevents;

import org.bukkit.plugin.java.JavaPlugin;

public class RandomEvents extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Hello from blank Plugin!");
    }

    @Override
    public void onDisable() {
        getLogger().info("See you again from blank Plugin!");
    }
}
