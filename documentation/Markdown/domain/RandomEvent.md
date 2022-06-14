# RandomEvent

```java
public abstract class RandomEvent implements Runnable {

    //public methods
    public abstract long getDuration();
    public abstract void tick(long tick);
    @Override
    final public void run();
    public void cleanUp();
    final public UUID getID();
    public abstract String getName();

    //protected methods
    protected RandomEvent(RandomEvents plugin);

    // Representation
    private long currentTick;
    protected final RandomEvents plugin;
}
```

It represents a random event that can happen.
Every radnom event must extend this class

[Java File](../../../src/main/java/antisocialgang/randomevents/domain/RandomEvent.java)

## Functions

### Constructor

```java
protected RandomEvent(RandomEvents plugin);
```

It is for creating the an event. Notice that it is protected, so a RandomEvent cannot be instantiated.
It can be only invoked from child classes

### Clean up

```java
public void cleanUp();
```

It is called by the [RandomEventController](../controller/RandomEventController.md) when the event ends.

### Duration

```java
public abstract long getDuration();
```

It should return the duration of the event in ticks.
It is invoked by the [RandomEventController](../controller/RandomEventController.md).

### ID

```java
final public UUID getID();
```

It returns the ID of the RandomEvent

### Name

```java
public abstract String getName();
```

It returns the Name of the RandomEvent

### Tick

```java
public abstract void tick(long tick);
```

It runs on every tick during the random event's lifecycle.

__Parameters:__

- tick - It is the current tick, the starting tick is 0

### Run

```java
@Override
final public void run();
```

It is invoked by the [BukkitScheduler](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/scheduler/BukkitScheduler.html)
to start the event's lifecycle.
__Important__ note this cannot be overriden.
