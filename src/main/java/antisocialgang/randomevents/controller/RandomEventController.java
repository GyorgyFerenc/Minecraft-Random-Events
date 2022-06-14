package antisocialgang.randomevents.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import antisocialgang.randomevents.RandomEventPlugin;
import antisocialgang.randomevents.domain.RandomEvent;
import antisocialgang.randomevents.domain.RandomEventGenerator;
import antisocialgang.randomevents.domain.RandomEvent.RandomEventHandle;

/**
 * This is the main controller of the plugin.
 * It controlles the spawining and maintaining of the Random events
 */
final public class RandomEventController extends BukkitRunnable {

    private GeneratorState generatorState;

    private RandomEventPlugin plugin;

    private long tick;
    private long eventTick; // Represents the tick on which it needs to create a new random event
    private long eventDelay; // Delay between new events
    private RandomEventGenerator generator;

    private PriorityQueue<RandomEventWrapper> eventWrappers;

    public RandomEventController(RandomEventPlugin plugin) {
        this.plugin = plugin;
        this.tick = 0;
        this.eventTick = 0;
        this.eventDelay = 20 * 60;
        this.generatorState = GeneratorState.STOPPED;

        this.eventWrappers = new PriorityQueue<>(1, new RandomEventComparator());
        this.generator = new RandomEventGenerator(this.plugin);

        // Schedules himself to run on every tick
        this.runTaskTimer(this.plugin, 0, 0);
    }

    public void startRandomEvent(String name) {
        RandomEventHandle handle = RandomEventHandler.getHandle(name);
        this.addRandomEvent(handle.create(this.plugin));
    }

    public void stopRandomEvent(UUID ID) {
        Iterator<RandomEventWrapper> it = this.eventWrappers.iterator();

        while (it.hasNext()) {
            RandomEventWrapper eventWrapper = it.next();
            if (eventWrapper.event.getID().compareTo(ID) == 0) {
                this.cancleRandomEvent(eventWrapper);
                it.remove();
                return;
            }
        }
        throw new RuntimeException("No random event found with that id");
    }

    @Override
    public void run() {
        this.checkForExperiedEvents();

        if (this.generatorState == GeneratorState.RUNNING) {
            this.checkIfNewEventNeeded();
        }

        this.tick++;
    }

    public void startGenerator() {
        Bukkit.getConsoleSender().sendMessage("Generator started"); // DEBUG
        this.generatorState = GeneratorState.RUNNING;
    }

    public void stopGenerator() {
        Bukkit.getConsoleSender().sendMessage("Generator stopped"); // DEBUG
        this.generatorState = GeneratorState.STOPPED;
    }

    public List<String> getActiveRandomEvents() {
        List<String> l = new ArrayList<>();

        Iterator<RandomEventWrapper> it = this.eventWrappers.iterator();

        while (it.hasNext()) {
            RandomEventWrapper eventWrapper = it.next();
            String s = " -> ";
            s = eventWrapper.event.getHandle().getName() + s;
            s += eventWrapper.event.getID();
            l.add(s);
        }

        return l;
    }

    public List<String> getActiveRandomEventsID() {
        List<String> l = new ArrayList<>();

        Iterator<RandomEventWrapper> it = this.eventWrappers.iterator();

        while (it.hasNext()) {
            RandomEventWrapper eventWrapper = it.next();
            String s = "";
            s += eventWrapper.event.getID();
            l.add(s);
        }

        return l;
    }

    /**
     * Checks if new event is needed to be scheduled and schedules it via
     * addRandomEvent
     */
    private void checkIfNewEventNeeded() {
        boolean needNewEvent = this.tick >= this.eventTick;

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

        long endTime = this.tick + event.getHandle().getDuration();
        RandomEventWrapper wrapper = new RandomEventWrapper(event, endTime, task);
        this.eventWrappers.add(wrapper);
        Bukkit.getServer().broadcastMessage("Event added!"); // Debug

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

        this.cancleRandomEvent(head);

        // Check for next event
        this.checkForExperiedEvents();
    }

    private void cancleRandomEvent(RandomEventWrapper event) {
        event.task.cancel();
        Bukkit.getServer().broadcastMessage("Event cancelled!"); // Debug

        event.event.cleanUp();
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

enum GeneratorState {
    RUNNING,
    STOPPED
}