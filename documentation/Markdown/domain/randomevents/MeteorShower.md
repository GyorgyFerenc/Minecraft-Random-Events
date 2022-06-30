# RandomEvent

```java
public class MeteorShower extends RandomEvent {

    
    public MeteorShower(RandomEventPlugin plugin);
    
    @Override
    public RandomEventHandle getHandle();
    
    @Override
    public void tick(long tick);

    private void spawnMeteorForEveryPlayer();
    private void spawnMeteor(Player player);

    // Representation

    private static final int FIREBALL_YIELD;
    private static final int SHOWER_RADIUS;
    private static final Vector METEOR_DIRECTION;
    private static final long TICKS_BETWEEN_METEORS;
    public static final RandomEventHandle handle = new RandomEventHandle();
}
```

__Handle Information:__

- Name: MeteorShower
- Duration: 2 min
- Weight: 5

It is the implementation of the [Meteor Shower](../../../RandomEventDescriptions/MeteorShower.md) random event.

Check [RandomEvent](../RandomEvent.md)

Implementation:

- For every player it picks a location in a square centered at the player with a lentgh of 2*SHOWER_RADIUS
- It spawns a fireball in every TICKS_BETWEEN_METEORS tick

[Java File](../../../../src/main/java/antisocialgang/randomevents/domain/randomevents/RandomPotionEffects.java.java)
