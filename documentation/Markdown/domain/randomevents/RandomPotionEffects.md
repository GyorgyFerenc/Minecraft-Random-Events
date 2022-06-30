# RandomEvent

```java
public class RandomPotionEffects extends RandomEvent {

    

    @Override
    public RandomEventHandle getHandle();
    
    public RandomPotionEffects(RandomEventPlugin plugin);

    @Override
    public void tick(long tick);

    private void reapplyRandomEffectIfNeeded(long tick);
    private void applyNewRandomPotionEffects();
    private PotionEffectType getRandomEffectType();

    // Representation

    final int EFFECT_DURATION_IN_TICKS = 20 * 30; // 30 secs
    final int MAX_AMPLIFIER = 5; // Max is Level 5 for, amplifier 1 gives level 2

    HashMap<UUID, PotionEffectWrapper> PlayerEffects;
    public static final RandomEventHandle handle = new RandomEventHandle();
}
```

__Handle Information:__

- Name: RandomPotionEffects
- Duration: 2 min
- Weight: 5

It is the implementation of the [Random potion effect](../../../RandomEventDescriptions/RandomPotionEffect.md) random event.

Check [RandomEvent](../RandomEvent.md)

Implementation:

- At every correct tick create a random effect for each player
- At evety tick check if the same effect is on the player, if not reapply for the correct duration

[Java File](../../../../src/main/java/antisocialgang/randomevents/domain/randomevents/RandomPotionEffects.java.java)

## Functions

### Get Random Effect Type

```java
private PotionEffectType getRandomEffectType();
```

Gets a random potion effect from all the possible effects.
