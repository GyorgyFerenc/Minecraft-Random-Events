package antisocialgang.randomevents;

import org.bukkit.plugin.java.JavaPlugin;

import antisocialgang.randomevents.controller.RandomEventController;

public class RandomEvents extends JavaPlugin {
    // private RandomEventController controller;

    @Override
    public void onEnable() {
        getLogger().info("RandomEvents plugin enabled!");
        new RandomEventController(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("RandomEvents plugin disabled!");
    }
}
