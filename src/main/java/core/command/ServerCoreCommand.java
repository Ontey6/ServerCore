package core.command;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import lombok.NonNull;
import ontey.command.*;
import ontey.command.argument.Arg;
import ontey.command.argument.LiteralArgumentBuilder;
import ontey.plugin.OnteyPlugin;

import java.util.List;
import java.util.Map;

import static core.ServerCore.plugin;
import static core.config.ConfigManager.config;
import static core.manager.LobbyManager.lobbyManager;
import static ontey.color.MiniMessageColor.colorize;
import static ontey.command.Command.SUCCESS;

@CommandName("servercore")
public class ServerCoreCommand implements MiscCommandRegisterer {
   
   private final CommandConfiguration defaults = new CommandConfiguration(
     List.of("servercore"),
     "ServerCore's Main command",
     "core.command.servercore",
     Map.of()
   );
   
   @Override
   public @NonNull Command register(@NonNull OnteyPlugin plugin, @NonNull LiteralArgumentBuilder root) {
      ConfigCommand cmd = new ConfigCommand(plugin, defaults);
      
      root
        .executes(ctx -> sendMessage(ctx.getSource()))
        .then(
          Arg.literal("reload")
            .executes(ctx -> reload(ctx.getSource()))
        );
      
      return cmd;
   }
   
   private int sendMessage(CommandSourceStack source) {
      var sender = source.getSender();
      
      sender.sendMessage(colorize("<gradient:yellow:dark_purple>-------------------------------------"));
      sender.sendMessage(colorize("<aqua>Running ServerCore <dark_aqua>v" + plugin.getVersion()));
      sender.sendMessage(colorize("<gradient:dark_purple:yellow>-------------------------------------"));
      
      return SUCCESS;
   }
   
   private int reload(CommandSourceStack source) {
      var sender = source.getSender();
      
      config.loadFields();
      lobbyManager.load();
      
      sender.sendMessage(colorize("<green>Reloaded ServerCore"));
      
      return SUCCESS;
   }
}
