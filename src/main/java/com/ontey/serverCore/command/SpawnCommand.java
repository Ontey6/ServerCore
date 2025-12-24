package com.ontey.serverCore.command;

import com.ontey.api.brigadier.argument.Arg;
import com.ontey.api.brigadier.command.Command;
import com.ontey.serverCore.files.Config;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SpawnCommand {
   
   public static void register() {
      Command cmd = new Command("spawn");
      
      var root =
        Arg.literal("spawn")
          .executes(ctx -> Arg.requirePlayer(ctx, "The Executed on entity can't run this command!", SpawnCommand::execute))
          .then(Arg.playersArg("target")
            .executes(ctx -> Arg.runForPlayers(Arg.getPlayers("target", ctx), SpawnCommand::execute))
            .requires(src -> src.getSender().hasPermission("core.command.spawn.target"))
          );
      
      cmd
        .setPermission("core.command.spawn")
        .setDescription("Teleports you to spawn")
        .setRoot(root)
        .register();
   }
   
   public static void execute(Player player) {
      player.teleport(Bukkit.getWorld("world").getSpawnLocation());
      
      if(Config.SHOW_FEEDBACK)
         player.sendActionBar(Component.text("Spawn", NamedTextColor.GREEN));
   }
}
