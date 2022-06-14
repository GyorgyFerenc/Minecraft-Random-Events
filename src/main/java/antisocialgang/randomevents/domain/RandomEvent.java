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
     * Returns the duration of the random event in ticks
     * This will be called by the RandomEventController so it knows when to end the
     * random event
     * 
     * @return long - the duration of the random event
     */
    public abstract long getDuration();

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

    public abstract String getName();

}