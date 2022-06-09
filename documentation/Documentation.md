# Documentation

## Table of content

- [Source structure](#source-structure)
- [Plugin.yml](#plugin.yml)
- [Notes](#notes)
- [Modules and classes](#modules-and-classes)

## Source structure

``` Bash
src
├── main
│   └── java # Java sources
│       ├── antisocialgang
│       │   └── randomevents
│       │       ├── commands # For commands
│       │       ├── controller # For controllers
│       │       ├── domain # For domain
│       │       └── RandomEvents.java # Main entry point
│       └── plugin.yml # Plugin file for the spigot API
└── test # Unit and other testing
    └── java
        └── antisocialgang
            └── randomevents 
target
│
...
└── randomevents-x.x.jar # The jar file of the plugin
```

[Code Structure reference](https://cedesk.github.io/code-structure/)

## Plugin.yml

The plugin.yml is a file made to contain information about the plugin.

[Reference](https://www.spigotmc.org/wiki/plugin-yml/)

## Notes

- [Creating new random events](/documentation/Markdown/notes/CreatingNewRandomEvent.md)

## Modules and classes

- [RandomEventPlugin](./Markdown/RandomEventPlugin.md)
- [RadnomEvent](./Markdown/domain/RandomEvent.md)
- [RandomEventController](./Markdown/controller/RandomEventController.md)
- [RandomEventGenerator](./Markdown/domain/RandomEventGenerator.md)
