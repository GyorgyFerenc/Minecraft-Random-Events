package antisocialgang.randomevents.controller;

import java.util.ArrayList;
import java.util.List;

import antisocialgang.randomevents.domain.RandomEvent.RandomEventHandle;

/**
 * RandomEventHandler
 */
public class RandomEventHandler {

    static List<RandomEventHandle> list = new ArrayList<>();

    private RandomEventHandler() {
    }

    public static void registerRandomEvent(RandomEventHandle eventHandle) {
        list.add(eventHandle);
    }

    public static RandomEventHandle getHandle(String name) {
        for (RandomEventHandle randomEventHandle : list) {
            if (name == randomEventHandle.getName()) {
                return randomEventHandle;
            }
        }
        throw new RuntimeException("No handle found");
    }

    public static List<RandomEventHandle> getEventHandles() {
        return list;
    }

}