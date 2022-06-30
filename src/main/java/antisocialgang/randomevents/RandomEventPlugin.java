package antisocialgang.randomevents;

import org.bukkit.plugin.java.JavaPlugin;

import antisocialgang.randomevents.commands.RandomEventCommand;
import antisocialgang.randomevents.controller.RandomEventController;
import antisocialgang.randomevents.controller.RandomEventHandler;
import antisocialgang.randomevents.domain.TestEvent;
import antisocialgang.randomevents.domain.randomevents.MeteorShower;
import antisocialgang.randomevents.domain.randomevents.RandomPotionEffects;
import antisocialgang.randomevents.domain.randomevents.ZombieApocalypse;

public class RandomEventPlugin extends JavaPlugin {
    // private RandomEventController controller;

    RandomEventController randomEventController;

    @Override
    public void onEnable() {
        getLogger().info("RandomEvents plugin enabled!");

        // Register events
        RandomEventHandler.registerRandomEvent(TestEvent.handle);
        RandomEventHandler.registerRandomEvent(ZombieApocalypse.handle);
        RandomEventHandler.registerRandomEvent(RandomPotionEffects.handle);
        RandomEventHandler.registerRandomEvent(MeteorShower.handle);

        this.randomEventController = new RandomEventController(this);
        new RandomEventCommand(this, randomEventController);
    }

    @Override
    public void onDisable() {
        getLogger().info("RandomEvents plugin disabled!");
    }
}
