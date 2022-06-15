package antisocialgang.randomevents.domain.randomevents;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import antisocialgang.randomevents.RandomEventPlugin;
import antisocialgang.randomevents.domain.RandomEvent;
import antisocialgang.randomevents.util.Vec2;

/**
 * ZombieApocalypse
 */
public class ZombieApocalypse extends RandomEvent implements Listener {

    private static final Vec2<Integer> PACK_SIZE = new Vec2<>(6, 9);
    private static final Vec2<Integer> WAVE_SIZE = new Vec2<>(3, 7);
    private static final List<String> NAMES = Arrays.asList("John", "Elfrieda", "Retha", "Earnestine", "Joesph",
            "Dereck", "Katlyn", "Karli", "Jazmin", "Beaulah", "Jeramy");

    private static final int SPEED_CHANCE = 10;
    private static final int STRENGHT_CHANCE = 10;
    private static final int EXPLOSIVE_CHANCE = 5;

    private boolean canceled = false;

    private HashSet<UUID> explodingZombies;

    public static final RandomEventHandle handle = new RandomEventHandle() {
        public long getDuration() {
            return 60 * 20 * 5;
            // return 2;
        };

        public String getName() {
            return "ZombieApocalypse";
        };

        public int getWeight() {
            return 5;
        };

        public RandomEvent create(RandomEventPlugin plugin) {
            return new ZombieApocalypse(plugin);
        };
    };

    @Override
    public RandomEventHandle getHandle() {
        return handle;
    }

    public ZombieApocalypse(RandomEventPlugin plugin) {
        super(plugin);
        Bukkit.getPluginManager().registerEvents(this, plugin);

        this.explodingZombies = new HashSet<>();

    }

    @Override
    public void cleanUp() {
        // HandlerList.unregisterAll(this);
        this.canceled = true;
    }

    private void startWave() {

        int waveSize = ThreadLocalRandom.current().nextInt(WAVE_SIZE.x, WAVE_SIZE.y + 1);

        for (int i = 0; i < waveSize; i++) {
            spawnPack();
        }

    }

    private void spawnPack() {
        int packSize = ThreadLocalRandom.current().nextInt(PACK_SIZE.x, PACK_SIZE.y + 1);

        for (Player p : this.plugin.getServer().getOnlinePlayers()) {
            Location l = p.getLocation();

            for (int i = 0; i < 50; i++) {

                Location possibble = generatePossibleLocation(l);
                Location spawnable = getSpawnable(possibble);

                if (spawnable != null) {
                    for (int j = 0; j < packSize; j++) {
                        spawnZombie(l, spawnable);
                    }
                    break;
                }

            }

        }
    }

    private void spawnZombie(Location l, Location spawnable) {
        int nameIndex = ThreadLocalRandom.current().nextInt(0, NAMES.size());
        World world = l.getWorld();
        Zombie zombie = (Zombie) world.spawnEntity(spawnable, EntityType.ZOMBIE);
        zombie.setCustomName(NAMES.get(nameIndex));

        int chance = ThreadLocalRandom.current().nextInt(0, 100 + 1);
        if (chance < SPEED_CHANCE) {
            setToSpeedyZombie(zombie);
        } else if (chance < SPEED_CHANCE + STRENGHT_CHANCE) {
            setToStrongZombie(zombie);
        } else if (chance < SPEED_CHANCE + STRENGHT_CHANCE + EXPLOSIVE_CHANCE) {
            setToExplosiveZombie(zombie);
        } else {
            setToNormalZombie(zombie);
        }

    }

    private void setToNormalZombie(Zombie zombie) {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(Color.GREEN);
        helmet.setItemMeta(helmetMeta);
        zombie.getEquipment().setHelmet(helmet);
    }

    private void setToExplosiveZombie(Zombie zombie) {
        this.explodingZombies.add(zombie.getUniqueId());

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(Color.fromBGR(0x9c178e));
        helmet.setItemMeta(helmetMeta);
        zombie.getEquipment().setHelmet(helmet);
    }

    private void setToStrongZombie(Zombie zombie) {
        PotionEffect speed = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2, true);
        speed.apply(zombie);

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(Color.RED);
        helmet.setItemMeta(helmetMeta);
        zombie.getEquipment().setHelmet(helmet);
    }

    private void setToSpeedyZombie(Zombie zombie) {
        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2, true);
        speed.apply(zombie);

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(Color.fromRGB(0x00f7ff)); // Nice blue
        helmet.setItemMeta(helmetMeta);

        zombie.getEquipment().setHelmet(helmet);
    }

    private Location generatePossibleLocation(Location l) {
        int radius = ThreadLocalRandom.current().nextInt(24, 32 + 1);

        int x = ThreadLocalRandom.current().nextInt(-radius, radius + 1);

        int z = radius - Math.abs(x);

        int sign = ThreadLocalRandom.current().nextInt(0, 1 + 1);

        if (sign == 1) {
            z *= -1;
        }

        int y = 0;

        Bukkit.getConsoleSender().sendMessage(x + " " + z);

        Location possibble = l.clone().add(x, y, z);
        return possibble;
    }

    @Override
    public void tick(long tick) {
        boolean every30sec = tick % (20 * 30) == 0;
        if (every30sec) {
            startWave();
        }
    }

    /**
     * Tries to find a spawnable location, starting from y+20 to y-20.
     * Returns null if id didnt find one
     * 
     * @param location - starting location
     * @return location if found, null otherwise
     */
    private Location getSpawnable(Location location) {
        Location possibleLocation = location.clone();
        possibleLocation.add(0, 20, 0);
        while (possibleLocation.getBlockY() > -64) {
            if (isSpawnable(possibleLocation)) {
                return possibleLocation;
            }
            possibleLocation.subtract(0, 1, 0);
        }
        return null;
    }

    private boolean isSpawnable(Location location) {
        World world = location.getWorld();

        String s = "";

        Location copy = location.clone();
        copy.subtract(0, 1, 0);

        Block b = world.getBlockAt(copy);
        if (!b.getState().getType().isOccluding()) {
            return false;
        }

        copy.add(0, 1, 0);
        b = world.getBlockAt(copy);
        if (b.getState().getType().isSolid()) {
            return false;
        }

        copy.add(0, 1, 0);
        b = world.getBlockAt(copy);
        if (b.getState().getType().isSolid()) {
            return false;
        }

        Bukkit.getConsoleSender().sendMessage(s);

        return true;
    }

    @EventHandler
    void onZombieDeathEvent(EntityDeathEvent deathEvent) {
        Entity e = deathEvent.getEntity();
        UUID id = e.getUniqueId();
        if (this.explodingZombies.contains(id)) {
            this.explodingZombies.remove(id);

            Location location = e.getLocation();
            e.getWorld().createExplosion(location, 4f, false, true);

            if (this.explodingZombies.isEmpty() && this.canceled) {
                HandlerList.unregisterAll(this);
            }
        }

    }

}
