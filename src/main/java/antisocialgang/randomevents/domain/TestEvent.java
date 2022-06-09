package antisocialgang.randomevents.domain;

import org.bukkit.Bukkit;

import antisocialgang.randomevents.RandomEvents;

/**
 * TestEvent
 */
public class TestEvent extends RandomEvent {

    public TestEvent(RandomEvents plugin) {
        super(plugin);
    }

    @Override
    public long Duration() {
        return 20 * 60 * 2; // 2 minute
    }

    @Override
    public void tick(long tick) {
        boolean every10seconds = tick % (20 * 10) == 0;
        if (!every10seconds)
            return;

        Bukkit.getServer().broadcastMessage("This is the test event yeeeey!... Hello Mom!");
    }

    public static RandomEvent create(RandomEvents plugin) {
        return new TestEvent(plugin);
    }
}