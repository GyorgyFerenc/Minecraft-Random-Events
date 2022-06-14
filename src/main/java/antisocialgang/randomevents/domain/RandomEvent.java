package antisocialgang.randomevents.domain;

import java.lang.Runnable;
import java.util.UUID;

import antisocialgang.randomevents.RandomEventPlugin;

/**
 * RandomEvent
 */
public abstract class RandomEvent implements Runnable {

    private long currentTick;

    protected final RandomEventPlugin plugin;
    final UUID ID;

    protected RandomEvent(RandomEventPlugin plugin) {
        this.plugin = plugin;
        this.currentTick = 0;
        this.ID = UUID.randomUUID();
    }

    /**
     * It represents the a handle for the creation of the random event
     */
    public interface RandomEventHandle {
        public long getDuration();

        public String getName();

        public int getWeight();

        /**
         * It creates the random event
         * 
         * @param plugin - The plugin
         * @return The new random event
         */
        public RandomEvent create(RandomEventPlugin plugin);
    }

    /**
     * Returns the ID of the current event
     * 
     * @return long - the duration of the random event
     */
    final public UUID getID() {
        return this.ID;
    }

    /**
     * Runs on every tick
     * 
     * @param tick - The current tick, 0 is the starting tick
     */
    public abstract void tick(long tick);

    @Override
    final public void run() {
        this.tick(this.currentTick);
        this.currentTick++;
    }

    public void cleanUp() {

    }

    /**
     * Returns the handle for the random event
     * 
     * @return The specific RandomEventHandle
     */
    public abstract RandomEventHandle getHandle();
}