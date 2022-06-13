package antisocialgang.randomevents;

import org.bukkit.plugin.java.JavaPlugin;

import antisocialgang.randomevents.commands.RandomEventCommand;
import antisocialgang.randomevents.controller.RandomEventController;

public class RandomEventPlugin extends JavaPlugin {
    // private RandomEventController controller;

    RandomEventController randomEventController;

    @Override
    public void onEnable() {
        getLogger().info("RandomEvents plugin enabled!");
        this.randomEventController = new RandomEventController(this);
        RandomEventCommand.setUp(this, randomEventController);
    }

    @Override
    public void onDisable() {
        getLogger().info("RandomEvents plugin disabled!");
    }
}
