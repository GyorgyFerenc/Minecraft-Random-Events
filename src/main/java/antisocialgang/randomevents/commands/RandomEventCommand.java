package antisocialgang.randomevents.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

import antisocialgang.randomevents.RandomEventPlugin;
import antisocialgang.randomevents.controller.RandomEventController;

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
        return false;
    }

    private boolean stopExecute(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }

    private boolean listExecute(CommandSender sender, Command command, String label, String[] args) {
        return false;
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
        List<String> l = new ArrayList<>();
        l.add("[ID of the random event]");
        return l;
    }

    private List<String> startComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> l = new ArrayList<>();
        l.add("[EventType]");
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
