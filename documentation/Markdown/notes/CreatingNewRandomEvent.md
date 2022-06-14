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

Next step you want to create and assign to the variable handle a new RandomEventHandle.
see: [Random Event](../domain/RandomEvent.md)

```java
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
```

see: [Anonymous Inner Class Documentation](https://docs.oracle.com/javase/tutorial/java/javaOO/anonymousclasses.html)

see: [Anonymous Inner Class Youtube Video](https://www.youtube.com/watch?v=DwtPWZn6T1A)

Note that this is one way to do it, using anonnymous inner classes, but you can do it via a new class and assign it that way.

```java
class TestEventHandle implements RandomEventHandle {

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
}
public class TestEvent extends RandomEvent {
    ...
    public final static RandomEventHandle handle = new TestEventHandle();
    ...
}
```

You need to override the getHandle() function:

```java
@Override
public RandomEventHandle getHandle() {
    return handle;
}
```

Next step is to register the RandomEventHandle to the RandomEventHandler.
It is done in the plugin's onEnable function.

```java
public class RandomEventPlugin extends JavaPlugin {
    ...
    @Override
    public void onEnable() {
        ...
        // Register events
        RandomEventHandler.registerRandomEvent(TestEvent.handle);
        ...
    }
}
```

## Important notes

- If the event has things to clean up before destruction override the function [cleanUp](../domain/RandomEvent.md#clean-up)
