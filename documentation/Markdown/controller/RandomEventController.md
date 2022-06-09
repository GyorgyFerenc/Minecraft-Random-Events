# RandomEventController

```java

final public class RandomEventController extends BukkitRunnable {

    //Public methods
    public RandomEventController(RandomEventPlugin plugin);
    @Override
    public void run();

    //Private methods
    private void checkIfNewEventNeeded();
    private void addRandomEvent(RandomEvent event);
    private void checkForExperiedEvents();
    
    //Representation
    private RandomEventPlugin plugin;

    private long tick;
    private long eventTick; // Represents the tick on which it needs to create a new random event
    private long eventDelay; // Delay between new events
    private RandomEventGenerator generator;

    private PriorityQueue<RandomEventWrapper> eventWrappers;
}

class RandomEventWrapper{
    public RandomEvent event;
    public long endTime;
    public BukkitTask task;

    RandomEventWrapper(RandomEvent event, long endTime, BukkitTask task);
}
class RandomEventComparator implements Comparator<RandomEventWrapper>;
```

This is the main controller of the plugin.
It controlles the spawining and maintaining of the random events

Tracking the events is done by using a priority queue where the events are sorted ascendingly by the it's endtime.
Meaning the ones which end faster will be on top. This is achieved by two helper class the RandomEventWrapper which wraps the event for easier use.
The other one is the RandomEventComparator which used to configure the priority queue see: [PriorityQueue](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/PriorityQueue.html), [Comparator](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Comparator.html).

[Source file](../../../src/main/java/antisocialgang/randomevents/controller/RandomEventController.java)

## Functions

### Constructor

```java
public RandomEventController(RandomEventPlugin plugin);
```

Creates a new controller.
__Important note__ this should be created once by the RandomEventPlugin

### Run

```java
@Override
public void run();
```

It is called by the [BukkitScheduler](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/scheduler/BukkitScheduler.html)
in every tick.

### Check if new event is needed

```java
private void checkIfNewEventNeeded();
```

Checks if the time came for picking and spawning a new random event

### Add random event

```java
private void addRandomEvent(RandomEvent event);
```

It adds the event to the currently tracked events and scheduling it.

### Check for expired events

```java
private void checkForExperiedEvents();
```

Checks if an event is ended it's lifecycle, meaning the duration ellapsed.
It cancels and cleans it up from the internal tracking.
