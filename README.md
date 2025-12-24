# ServerCore
A plugin made for the [Tookwar](https://tookwear.github.io) minecraft server.

Depends on the [OnteyAPI](https://github.com/Ontey6/OnteyAPI/).

# Requirements
Java Version: 21
Native Minecraft Version: 1.21
Build System: Gradle
Server Software: Paper (or higher forks)

# Features

## Spawn Command
Teleports you or the specified target to spawn.

Usage: `/spawn [target]`

Permission: `core.command.spawn`

Permission to target other players: `core.command.spawn.target`

Shows feedback in the actionbar.
Togglable in the config: `spawn.show-feedback`

## To spawn on join
Players get teleported to spawn on join.

Togglable in the config: `on-join.teleport-to-spawn`