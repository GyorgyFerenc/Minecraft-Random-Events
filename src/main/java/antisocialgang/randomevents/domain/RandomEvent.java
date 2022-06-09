package antisocialgang.randomevents.domain;

import java.lang.Runnable;

import antisocialgang.randomevents.RandomEvents;

/**
 * RandomEvent
 */
public abstract class RandomEvent implements Runnable {

    private long currentTick;

    protected final RandomEvents plugin;

    protected RandomEvent(RandomEvents plugin) {
        this.plugin = plugin;
        this.currentTick = 0;
    }

    /**
     * Returns the duration of the random event in ticks
     * This will be called by the RandomEventController so it knows when to end the
     * random event
     * 
     * @return long - the duration of the random event
     */
    public abstract long duration();

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
}