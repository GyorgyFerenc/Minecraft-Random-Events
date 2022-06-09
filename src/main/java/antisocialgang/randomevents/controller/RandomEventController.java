package antisocialgang.randomevents.controller;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import antisocialgang.randomevents.RandomEvents;
import antisocialgang.randomevents.domain.RandomEvent;
import antisocialgang.randomevents.domain.RandomEventGenerator;

/**
 * This is the main controller of the plugin.
 * It controlles the spawining and maintaining of the Random events
 * 
 */
public class RandomEventController extends BukkitRunnable {

    private RandomEvents plugin;

    private long tick;
    private long eventTick; // Represents the tick on which it needs to create a new random event
    private long eventDelay; // Delay between new events
    private RandomEventGenerator generator;

    private PriorityQueue<RandomEventWrapper> eventWrappers;

    public RandomEventController(RandomEvents plugin) {
        this.plugin = plugin;
        this.tick = 0;
        this.eventTick = 0;
        this.eventDelay = 20 * 60;

        this.eventWrappers = new PriorityQueue<>(1, new RandomEventComparator());
        this.generator = new RandomEventGenerator(this.plugin);

        // Schedules himself to run on every tick
        this.runTaskTimer(this.plugin, 0, 0);
    }

    @Override
    public void run() {
        this.checkForExperiedEvents();
        this.checkIfNewEventNeeded();
        this.tick++;
    }

    /**
     * Checks if new event is needed to be scheduled and schedules it via
     * addRandomEvent
     */
    private void checkIfNewEventNeeded() {
        boolean needNewEvent = this.tick == this.eventTick;

        if (!needNewEvent) {
            return;
        }

        this.eventTick += this.eventDelay;

        RandomEvent event = this.generator.getEvent();
        this.addRandomEvent(event);
    }

    /**
     * Schedules the event
     * 
     * @param event - event to be scheduled
     */
    private void addRandomEvent(RandomEvent event) {
        // Schedule the event
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(this.plugin, event, 0, 0);

        long endTime = this.tick + event.Duration();
        RandomEventWrapper wrapper = new RandomEventWrapper(event, endTime, task);
        this.eventWrappers.add(wrapper);
        Bukkit.getServer().broadcastMessage("Event added!");

    }

    /**
     * Checks if an event is experied and cancels it
     */
    private void checkForExperiedEvents() {
        RandomEventWrapper head = this.eventWrappers.peek();
        boolean emptyQueue = head == null;
        if (emptyQueue) {
            return;
        }

        boolean headExpired = head.endTime <= this.tick;
        if (!headExpired) {
            return;
        }

        // Remove head
        this.eventWrappers.poll();

        head.task.cancel();
        Bukkit.getServer().broadcastMessage("Event cancelled!");

        // Check for next event
        this.checkForExperiedEvents();
    }
}

class RandomEventWrapper {
    public RandomEvent event;
    public long endTime;
    public BukkitTask task;

    RandomEventWrapper(RandomEvent event, long endTime, BukkitTask task) {
        this.event = event;
        this.endTime = endTime;
        this.task = task;
    }
}

class RandomEventComparator implements Comparator<RandomEventWrapper> {

    public int compare(RandomEventWrapper r1, RandomEventWrapper r2) {
        if (r1.endTime < r2.endTime) {
            return -1;
        } else if (r1.endTime > r2.endTime) {
            return 1;
        }
        return 0;
    }
}