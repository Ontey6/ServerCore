package com.ontey.serverCore.command;

import com.ontey.api.brigadier.argument.Arg;
import com.ontey.api.brigadier.command.Command;
import com.ontey.serverCore.manager.LobbyManager;
import com.ontey.serverCore.ServerCore;
import com.ontey.serverCore.files.Config;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.util.List;

import static com.ontey.api.brigadier.command.Command.SUCCESS;
import static com.ontey.api.color.MiniMessageColor.colorize;

public class ServerCoreCommand {
   
   public static void register() {
      Command cmd = new Command("servercore");
      
      var root =
        Arg.literal("servercore")
          .executes(ctx -> sendMessage(ctx.getSource()))
          .then(
            Arg.literal("reload")
              .executes(ctx -> reload(ctx.getSource()))
          );
      
      cmd
        .addAliases(List.of("core"))
        .setPermission("core.command.servercore")
        .setDescription("Main command of the ServerCore plugin")
        .setRoot(root)
        .register();
   }
   
   public static int sendMessage(CommandSourceStack source) {
      var sender = source.getSender();
      
      sender.sendMessage(colorize("<gradient:yellow:dark_purple>-------------------------------------"));
      sender.sendMessage(colorize("<aqua>Running ServerCore <dark_aqua>v" + ServerCore.version));
      sender.sendMessage(colorize("<gradient:dark_purple:yellow>-------------------------------------"));
      
      return SUCCESS;
   }
   
   public static int reload(CommandSourceStack source) {
      var sender = source.getSender();
      
      Config.load();
      LobbyManager.load();
      
      sender.sendMessage(colorize("<green>Reloaded ServerCore"));
      
      return SUCCESS;
   }
}
