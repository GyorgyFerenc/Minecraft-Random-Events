package antisocialgang.randomevents.domain;

import org.bukkit.Bukkit;

import antisocialgang.randomevents.RandomEventPlugin;

/**
 * TestEvent
 */
public class TestEvent extends RandomEvent {

    public final static RandomEventHandle handle = new RandomEventHandle() {
        public long getDuration() {
            return 20 * 60 * 2; // 2 minute
        }

        public String getName() {
            return "TestEvent";
        }

        public int getWeight() {
            return 5;
        }

        public RandomEvent create(RandomEventPlugin plugin) {
            return new TestEvent(plugin);
        };
    };

    @Override
    public RandomEventHandle getHandle() {
        return handle;
    }

    public TestEvent(RandomEventPlugin plugin) {
        super(plugin);
    }

    @Override
    public void tick(long tick) {
        boolean every10seconds = tick % (20 * 10) == 0;
        if (!every10seconds)
            return;

        Bukkit.getServer().broadcastMessage("This with " + this.getID());
    }

}
