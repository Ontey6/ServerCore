package core.command;

import core.config.ConfigManager;
import lombok.NonNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import ontey.command.*;
import ontey.command.argument.LiteralArgumentBuilder;
import ontey.plugin.OnteyPlugin;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

import static core.manager.LobbyManager.lobbyManager;

@CommandName("spawn")
public class SpawnCommand implements MiscCommandRegisterer {
   
   private final CommandConfiguration defaults = new CommandConfiguration(
     List.of("spawn"),
     "Teleports you to spawn",
     null,
     Map.of()
   );
   
   @Override
   public @NonNull Command register(@NonNull OnteyPlugin plugin, @NonNull LiteralArgumentBuilder root) {
      return new TargetableConfigCommand(plugin, defaults, SpawnCommand::execute);
   }
   
   public static void execute(Player player) {
      player.teleport(lobbyManager.getWorld().getSpawnLocation());
      
      if(ConfigManager.SHOW_FEEDBACK)
         player.sendActionBar(Component.text("Spawn", NamedTextColor.GREEN));
   }
}
