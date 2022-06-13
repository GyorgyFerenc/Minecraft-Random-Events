# RandomEventCommand

```java
public class RandomEventCommand implements TabExecutor {

    RandomEventController randomEventController;

    public RandomEventCommand(RandomEventPlugin plugin, RandomEventController randomEventController);

    // Command Execution
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    private boolean generatorExecute(CommandSender sender, Command command, String label, String[] args);
    private boolean startExecute(CommandSender sender, Command command, String label, String[] args);
    private boolean stopExecute(CommandSender sender, Command command, String label, String[] args);
    private boolean listExecute(CommandSender sender, Command command, String label, String[] args);

    // Command Tabbings
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args);

    private List<String> listComplete(CommandSender sender, Command command, String label, String[] args);
    private List<String> stopComplete(CommandSender sender, Command command, String label, String[] args);
    private List<String> startComplete(CommandSender sender, Command command, String label, String[] args);
    private List<String> generatorComplete(CommandSender sender, Command command, String label, String[] args);
    private List<String> noArgument(String[] args);
}

```

```yml
commands:
  randomevent:
    description: It deals with the RandomEvent plugin
    aliases: re
    usage: "Usage: /<command> <option>"

permissions:
  randomevent.use:
    description: asd
    default: true
```

The representation of the /randomevent \<option\> command.

option:

- list
- stop
- start
- generator

__list:__
>/randomevent list

Lists out the currently running random events

__stop:__
>/randomevent stop \<ID>

Stops the random event with the specified id

__start:__
>/randomevent start \<RandomEventType>

Starts a new random event with the specified type

__generator:__
>/randomevent generator \<start/stop>

Starts or stops the generating of the random events

[Java File](../../../src/main/java/antisocialgang/randomevents/commands/RandomEventCommand.java)

## Functionality

### Executing the command

```java
@Override
public boolean onCommand(CommandSender sender, Command command, String label, String[] args);

private boolean generatorExecute(CommandSender sender, Command command, String label, String[] args);
private boolean startExecute(CommandSender sender, Command command, String label, String[] args);
private boolean stopExecute(CommandSender sender, Command command, String label, String[] args);
private boolean listExecute(CommandSender sender, Command command, String label, String[] args);
```

The part which is responsible for executing the command.
The onCommand function is ran when the command is set to execute. It delegates the execution to the respective functions.

### Tabbing in the command

```java
@Override
public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args);

private List<String> listComplete(CommandSender sender, Command command, String label, String[] args);
private List<String> stopComplete(CommandSender sender, Command command, String label, String[] args);
private List<String> startComplete(CommandSender sender, Command command, String label, String[] args);
private List<String> generatorComplete(CommandSender sender, Command command, String label, String[] args);
private List<String> noArgument(String[] args);
```

The part which is responsible for the tab completion of the command.
The onTabComplete function is ran when the command is set to be tab completed. It delegates the complition to the respective functions.

__Return value:__

- It returns a list of posibble candidates
