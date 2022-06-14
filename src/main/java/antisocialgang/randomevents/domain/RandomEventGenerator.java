package antisocialgang.randomevents.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Range;

import antisocialgang.randomevents.RandomEventPlugin;
import antisocialgang.randomevents.controller.RandomEventHandler;
import antisocialgang.randomevents.domain.RandomEvent.RandomEventHandle;

/**
 * RandomEventGenerator
 */
public class RandomEventGenerator {

    private RandomEventPlugin plugin;

    private List<RandomEventWrapper> list;
    private int max;

    private void initList() {
        List<RandomEventHandle> list = RandomEventHandler.getEventHandles();
        for (RandomEventHandle eventHandle : list) {
            this.addRandomEvent(eventHandle);
        }
    }

    public RandomEventGenerator(RandomEventPlugin plugin) {
        this.plugin = plugin;
        this.max = 0;
        this.list = new ArrayList<>();

        this.initList();
    }

    private void addRandomEvent(RandomEventHandle eventHandle) {
        int weight = eventHandle.getWeight();

        Range<Integer> randomRange = this.createRandomRangeForRandomEvent(weight);
        RandomEventWrapper eventWrapper = new RandomEventWrapper(eventHandle, randomRange);

        this.list.add(eventWrapper);

        this.max += weight;
    }

    private Range<Integer> createRandomRangeForRandomEvent(int weight) {
        int x;
        if (this.list.size() == 0) {
            x = 0;
        } else {
            RandomEventWrapper wrapper = this.list.get(this.list.size() - 1);
            x = wrapper.randomRange.upperEndpoint();
        }
        int y = x + weight;
        Range<Integer> randomRange = Range.closedOpen(x, y);
        return randomRange;
    }

    public RandomEvent getEvent() {
        Random random = new Random();
        int choosen = random.ints(0, this.max)
                .findFirst()
                .getAsInt();

        for (RandomEventWrapper eventWrapper : this.list) {
            boolean choosenInRange = eventWrapper.randomRange.contains(choosen);
            if (choosenInRange) {
                return eventWrapper.eventHandle.create(this.plugin);
            }
        }
        throw new RuntimeException("Could not generate random event!");
    }
}

/**
 * Creator
 */
interface Creator {

    RandomEvent run();
}

class RandomEventWrapper {
    Range<Integer> randomRange;
    public RandomEventHandle eventHandle;

    public RandomEventWrapper(RandomEventHandle eventHandle, Range<Integer> randomRange) {
        this.eventHandle = eventHandle;
        this.randomRange = randomRange;
    }

}