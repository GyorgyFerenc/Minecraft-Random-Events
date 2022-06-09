package antisocialgang.randomevents.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import antisocialgang.randomevents.RandomEventPlugin;

/**
 * RandomEventGenerator
 */
public class RandomEventGenerator {

    private RandomEventPlugin plugin;

    private List<RandomEventWrapper> list;
    private int max;

    private void initList() {
        this.addRandomEvent(() -> {
            return new TestEvent(this.plugin);
        }, 5);
    }

    public RandomEventGenerator(RandomEventPlugin plugin) {
        this.plugin = plugin;
        this.max = 0;
        this.list = new ArrayList<>();

        this.initList();
    }

    private void addRandomEvent(Creator eventCreator, int weight) {
        int x;
        if (this.list.size() == 0) {
            x = 0;
        } else {
            RandomEventWrapper wrapper = this.list.get(this.list.size() - 1);
            x = wrapper.y;
        }
        int y = x + weight;
        RandomEventWrapper eventWrapper = new RandomEventWrapper(eventCreator, x, y);
        this.list.add(eventWrapper);
        this.max += weight;
    }

    public RandomEvent getEvent() {
        Random random = new Random();
        int choosen = random.ints(0, this.max)
                .findFirst()
                .getAsInt();

        for (RandomEventWrapper eventWrapper : this.list) {
            boolean choosenInRange = eventWrapper.x <= choosen && choosen < eventWrapper.y;
            if (choosenInRange) {
                return eventWrapper.eventCreator.run();
            }
        }
        return null;
    }
}

/**
 * Creator
 */
interface Creator {

    RandomEvent run();
}

class RandomEventWrapper {
    public int x;
    public int y;
    public Creator eventCreator;

    public RandomEventWrapper(Creator event, int x, int y) {
        this.eventCreator = event;
        this.x = x;
        this.y = y;
    }

}