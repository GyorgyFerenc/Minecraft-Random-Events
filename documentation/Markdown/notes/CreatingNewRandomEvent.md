# Creating New Random Event

First you want to inherit from the RandomEvent

```java
public class TestEvent extends RandomEvent{

}
```

Next step create a constructor

```java
public TestEvent(RandomEventPlugin plugin) {
    super(plugin);
}
```

The constructor needs to get the RandomEventPlugin as its parameter and call super(plugin) on it, other then that no restriction

Next step you want to override 2 functions:

- public long duration()
- public void tick(long tick)

see: [Random Event](../domain/RandomEvent.md)

```java
@Override
public long duration() { // Duration of the event
    return 20 * 60 * 2; // 2 minute
}

@Override
public void tick(long tick) { //Runs on every tick
    boolean every10seconds = tick % (20 * 10) == 0;
    if (!every10seconds)
        return;

    Bukkit.getServer().broadcastMessage("This is the test event yeeeey!... Hello Mom!");
}
```

Next step is to tell the [Random Event Generator](../domain/RandomEventGenerator.md) that the random event exist.
The method for this is the following:

```java
public class RandomEventGenerator {
    ...
    private void initList() {
        ...
        this.addRandomEvent(() -> {
            return new TestEvent(this.plugin);
        }, 5);
    }
    ...
}
```

In the initList function of the RandomEventGenerator call the [addRandomEventFunction](../domain/RandomEventGenerator.md#add-random-event) with a lambda expression of no argument which returns a new RandomEvent, and a weight.

## Important notes

- If the creation of the event is complex create a static function called create and wrap that around with the lambda

```Java
class TestEvent extends RandomEvent{
    ...
    public static RandomEvent create(RandomEventPlugin plugin) {
        return new TestEvent(plugin);
    }
}

public class RandomEventGenerator {
    ...
    private void initList() {
        ...
        this.addRandomEvent(() -> {
            return TestEvent.create(this.plugin);
        }, 5);
    }
    ...
}
```

- If the event has things to clean up before destruction override the function [cleanUp](../domain/RandomEvent.md#clean-up)
