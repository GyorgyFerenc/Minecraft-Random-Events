package antisocialgang.randomevents.domain.randomevents;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import antisocialgang.randomevents.RandomEventPlugin;
import antisocialgang.randomevents.domain.RandomEvent;

/**
 * RandomPotionEffects
 */
public class RandomPotionEffects extends RandomEvent {

    final int EFFECT_DURATION_IN_TICKS = 20 * 30; // 30 secs
    final int MAX_AMPLIFIER = 5; // Max is Level 5 for, amplifier 1 gives level 2

    HashMap<UUID, PotionEffectWrapper> PlayerEffects;

    public static final RandomEventHandle handle = new RandomEventHandle() {
        public long getDuration() {
            return 20 * 60 * 2;
        };

        public String getName() {
            return "RandomPotionEffects";
        };

        public int getWeight() {
            return 5;
        };

        public RandomEvent create(RandomEventPlugin plugin) {
            return new RandomPotionEffects(plugin);
        };
    };

    @Override
    public RandomEventHandle getHandle() {
        return handle;
    }

    public RandomPotionEffects(RandomEventPlugin plugin) {
        super(plugin);
        this.PlayerEffects = new HashMap<>();
    }

    @Override
    public void tick(long tick) {
        boolean correctTick = (tick % EFFECT_DURATION_IN_TICKS == 0);

        if (correctTick) {
            applyNewRandomPotionEffects();
        }
        reapplyRandomEffectIfNeeded(tick);

    }

    private void reapplyRandomEffectIfNeeded(long tick) {
        this.PlayerEffects.forEach((ID, effectWrapper) -> {

            PotionEffectType type = effectWrapper.type;
            int amplifier = effectWrapper.amplifier;

            Player p = Bukkit.getPlayer(ID);
            PotionEffect effectOnPlayer = p.getPotionEffect(type);

            boolean sameEffect = (effectOnPlayer != null && effectOnPlayer.getAmplifier() == amplifier);

            if (!sameEffect) {

                long tickPassed = tick % EFFECT_DURATION_IN_TICKS;
                long tickRemaining = EFFECT_DURATION_IN_TICKS - tickPassed;

                PotionEffect randomEffect = new PotionEffect(type, (int) tickRemaining, amplifier);
                randomEffect.apply(p);
            }
        });
    }

    private void applyNewRandomPotionEffects() {
        for (Player p : this.plugin.getServer().getOnlinePlayers()) {
            PotionEffectType randomEffectType = getRandomEffectType();

            int randomAmplifier = ThreadLocalRandom.current().nextInt(MAX_AMPLIFIER);

            PotionEffect randomEffect = new PotionEffect(randomEffectType, EFFECT_DURATION_IN_TICKS, randomAmplifier);

            randomEffect.apply(p);

            PotionEffectWrapper wrapper = new PotionEffectWrapper(randomEffectType, randomAmplifier);
            this.PlayerEffects.put(p.getUniqueId(), wrapper);
        }
    }

    private PotionEffectType getRandomEffectType() {
        PotionEffectType[] list = PotionEffectType.values();

        int r = ThreadLocalRandom.current().nextInt(list.length);
        return list[r];
    }

}

class PotionEffectWrapper {

    public PotionEffectType type;
    public int amplifier;

    public PotionEffectWrapper(PotionEffectType type, int amplifier) {
        this.type = type;
        this.amplifier = amplifier;
    }
}