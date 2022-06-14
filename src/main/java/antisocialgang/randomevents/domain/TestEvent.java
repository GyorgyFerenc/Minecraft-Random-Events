package antisocialgang.randomevents.domain;

import org.bukkit.Bukkit;

import antisocialgang.randomevents.RandomEventPlugin;

/**
 * TestEvent
 */
public class TestEvent extends RandomEvent {

    public TestEvent(RandomEventPlugin plugin) {
        super(plugin);
    }

    @Override
    public long getDuration() {
        return 20 * 60 * 2; // 2 minute
    }

    @Override
    public void tick(long tick) {
        boolean every10seconds = tick % (20 * 10) == 0;
        if (!every10seconds)
            return;

        Bukkit.getServer().broadcastMessage("This is the test event yeeeey!... Hello Mom!");
    }

    public static RandomEvent create(RandomEventPlugin plugin) {
        return new TestEvent(plugin);
    }

    @Override
    public String getName() {
        return "TestEvent";
    }
}