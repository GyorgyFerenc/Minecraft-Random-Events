# RandomEvent

```java
public class ZombieApocalypse extends RandomEvent implements Listener {

    //Public methods

    @Override
    public RandomEventHandle getHandle();

    public ZombieApocalypse(RandomEventPlugin plugin);

    @Override
    public void tick(long tick);

    @Override
    public void cleanUp();

    //Private methods

    private void startWave();
    private void spawnPack();
    private void spawnZombie(Location l, Location spawnable);
    private void setToNormalZombie(Zombie zombie);
    private void setToExplosiveZombie(Zombie zombie);
    private void setToStrongZombie(Zombie zombie);
    private void setToSpeedyZombie(Zombie zombie);
    private Location generatePossibleLocation(Location l);
    private Location getSpawnable(Location location);
    private boolean isSpawnable(Location location);

    @EventHandler
    void onZombieDeathEvent(EntityDeathEvent deathEvent);

    //Representation
    
    private static final Vec2<Integer> PACK_SIZE;
    private static final Vec2<Integer> WAVE_SIZE;
    private static final List<String> NAMES;

    private static final int SPEED_CHANCE;
    private static final int STRENGHT_CHANCE;
    private static final int EXPLOSIVE_CHANCE;

    private static final int NUMBER_OF_TRIES_FOR_SPAWING;

    private boolean canceled;

    private HashSet<UUID> explodingZombies;

    public static final RandomEventHandle handle = new RandomEventHandle();

}
```

__Handle Information:__

- Name: ZombieApocalypse
- Duration: 5 min
- Weight: 5

It is the implementation of the [Zombie Apocalypse](../../../RandomEventDescriptions/ZombieApocalypse.md) random event.

Check [RandomEvent](../RandomEvent.md)

Implementation of the exploding zombie follows:

- Register this to event listeners  
- When the Zombie is choosen stores the id in a HashSet named explodingZombies.
- onZombieDeathEvent is fired when an entity dies.
  - Check if the id is in the HashSet if yes spawn an explosion
  - If the event is canceled and there is no more exploding zombies unregsiter this listener

Implementation of the zombie spawning follows

- Pick a block around the player with a heigh which is greater with 20 blocks
- Check if we can spawn there, if yes we are done
- Go down with 1 block until we can
- If we didn't find any spawnable place then retry again with a new position
- We try to spawn NUMBER_OF_TRIES_FOR_SPAWING times

[Java File](../../../../src/main/java/antisocialgang/randomevents/domain/randomevents/ZombieApocalypse.java)

## Functions

### Generate Possible Location

```java
private Location generatePossibleLocation(Location l);
```

Generates possible location for spawning a pack around the player
