package antisocialgang.randomevents.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

import antisocialgang.randomevents.RandomEventPlugin;
import antisocialgang.randomevents.controller.RandomEventController;
import antisocialgang.randomevents.controller.RandomEventHandler;
import antisocialgang.randomevents.domain.RandomEvent.RandomEventHandle;

/**
 * RandomEventCommand
 */
public class RandomEventCommand implements TabExecutor {

    RandomEventController randomEventController;

    public RandomEventCommand(RandomEventPlugin plugin, RandomEventController randomEventController) {
        this.randomEventController = randomEventController;
        PluginCommand p = plugin.getCommand("randomevent");
        p.setExecutor(this);
        p.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }
        switch (args[0]) {
            case "list":
                return this.listExecute(sender, command, label, args);

            case "stop":
                return this.stopExecute(sender, command, label, args);

            case "start":
                return this.startExecute(sender, command, label, args);

            case "generator":
                return this.generatorExecute(sender, command, label, args);

            default:
                return false;
        }
    }

    private boolean generatorExecute(CommandSender sender, Command command, String label, String[] args) {
        switch (args[1]) {
            case "stop":
                this.randomEventController.stopGenerator();
                return true;

            case "start":
                this.randomEventController.startGenerator();
                return true;
            default:
                break;
        }
        return false;
    }

    private boolean startExecute(CommandSender sender, Command command, String label, String[] args) {

        try {
            String name = args[1];
            sender.sendMessage(name);
            this.randomEventController.startRandomEvent(name);
            sender.sendMessage("Scheduled event");
        } catch (Exception e) {
            sender.sendMessage(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean stopExecute(CommandSender sender, Command command, String label, String[] args) {

        try {
            UUID ID = UUID.fromString(args[1]);
            this.randomEventController.stopRandomEvent(ID);
            sender.sendMessage("Event stoped");
        } catch (Exception e) {
            sender.sendMessage(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean listExecute(CommandSender sender, Command command, String label, String[] args) {
        List<String> l = this.randomEventController.getActiveRandomEvents();
        StringBuilder builder = new StringBuilder();
        for (String string : l) {
            builder.append(string);
            builder.append('\n');
        }
        sender.sendMessage(builder.toString());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (args[0] == "") {
            return noArgument(args);
        }

        switch (args[0]) {
            case "list":
                return this.listComplete(sender, command, label, args);

            case "stop":
                return this.stopComplete(sender, command, label, args);

            case "start":
                return this.startComplete(sender, command, label, args);

            case "generator":
                return this.generatorComplete(sender, command, label, args);

            default:
                break;
        }

        List<String> l = new ArrayList<>();
        return l;
    }

    private List<String> listComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> l = new ArrayList<>();
        return l;
    }

    private List<String> stopComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> l = this.randomEventController.getActiveRandomEventsID();
        return l;
    }

    private List<String> startComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> l = new ArrayList<>();
        List<RandomEventHandle> handles = RandomEventHandler.getEventHandles();
        for (RandomEventHandle handle : handles) {
            l.add(handle.getName());
        }
        return l;
    }

    private List<String> generatorComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> l = new ArrayList<>();
        l.add("start");
        l.add("stop");
        return l;
    }

    private List<String> noArgument(String[] args) {
        List<String> l = new ArrayList<>();

        l.add("list");
        l.add("stop");
        l.add("start");
        l.add("generator");

        return l;
    }
}
