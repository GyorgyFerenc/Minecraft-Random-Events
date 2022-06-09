package antisocialgang.randomevents.domain;

import java.util.UUID;

import java.lang.Runnable;

import antisocialgang.randomevents.RandomEvents;

/**
 * RandomEvent
 */
public abstract class RandomEvent implements Runnable {

    private final UUID id;
    private long currentTick;

    protected final RandomEvents plugin;

    RandomEvent(RandomEvents plugin) {
        this.plugin = plugin;
        this.id = UUID.randomUUID();
        this.currentTick = 0;
    }

    public UUID getUUID() {
        return this.id;
    }

    /**
     * Returns the duration of the random event in ticks
     * This will be called by the RandomEventController so it knows when to end the
     * random event
     * 
     * @return long - the duration of the random event
     */
    public abstract long Duration();

    /**
     * Runs on every tick
     * 
     * @param tick - The current tick, 0 is the starting tick
     */
    public abstract void tick(long tick);

    @Override
    public void run() {
        this.tick(this.currentTick);
        this.currentTick++;
    }
}