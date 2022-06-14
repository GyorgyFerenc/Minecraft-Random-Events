# RandomEventHandler

```java
public class RandomEventHandler {

    static List<RandomEventHandle> list = new ArrayList<>();

    private RandomEventHandler() // To prevent instantiation

    public static void registerRandomEvent(RandomEventHandle eventHandle);
    public static RandomEventHandle getHandle(String name);
    public static List<RandomEventHandle> getEventHandles();
}
```

## Register Random Event

```java
public static void registerRandomEvent(RandomEventHandle eventHandle);
```

Registers the random event by the eventHandle

## Get Handle

```java
public static RandomEventHandle getHandle(String name);
```

Returns the handle of the event, which was registered by this name

__throws:__

- RuneTimeException: If no handle was registered to that name

## Get Handles

```java
public static List<RandomEventHandle> getEventHandles();
```

Returns the handles of all the registered events
