package antisocialgang.randomevents.domain.randomevents;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import antisocialgang.randomevents.RandomEventPlugin;
import antisocialgang.randomevents.domain.RandomEvent;

/**
 * MeteorShower
 */
public class MeteorShower extends RandomEvent {

    private static final int SHOWER_RADIUS = 64;
    private static final Vector METEOR_DIRECTION = new Vector(.1, -1, 0);
    private static final long TICKS_BETWEEN_METEORS = 10; // .5 sec

    public static final RandomEventHandle handle = new RandomEventHandle() {
        public long getDuration() {
            return 20 * 60 * 2; // 2 min
        };

        public String getName() {
            return "MeteorShower";
        };

        public int getWeight() {
            return 5;
        };

        public RandomEvent create(RandomEventPlugin plugin) {
            return new MeteorShower(plugin);
        };
    };

    public MeteorShower(RandomEventPlugin plugin) {
        super(plugin);
    }

    @Override
    public void tick(long tick) {
        boolean correctTick = (tick % TICKS_BETWEEN_METEORS == 0);
        if (correctTick) {
            spawnMeteorForEveryPlayer();
        }
    }

    private void spawnMeteorForEveryPlayer() {
        for (Player player : this.plugin.getServer().getOnlinePlayers()) {
            spawnMeteor(player);
        }
    }

    private void spawnMeteor(Player player) {
        Location l = player.getLocation();
        World world = l.getWorld();

        int x = ThreadLocalRandom.current().nextInt(-SHOWER_RADIUS, SHOWER_RADIUS);
        int z = ThreadLocalRandom.current().nextInt(-SHOWER_RADIUS, SHOWER_RADIUS);

        l.add(x, 0, z);
        l.setY(world.getMaxHeight());
        l.setDirection(METEOR_DIRECTION);

        Fireball fireball = (Fireball) world.spawnEntity(l, EntityType.FIREBALL);
        fireball.setYield(6);
    }

    @Override
    public RandomEventHandle getHandle() {
        return handle;
    }

}