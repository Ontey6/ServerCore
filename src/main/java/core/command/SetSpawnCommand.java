package core.command;

import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.resolvers.FinePositionResolver;
import io.papermc.paper.math.FinePosition;
import lombok.NonNull;
import ontey.check.Nullity;
import ontey.command.*;
import ontey.command.argument.Arg;
import ontey.command.argument.LiteralArgumentBuilder;
import ontey.plugin.OnteyPlugin;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static core.manager.LobbyManager.lobbyManager;
import static ontey.command.Command.SUCCESS;

@CommandName("setspawn")
public class SetSpawnCommand implements MiscCommandRegisterer {
   
   private final CommandConfiguration defaults = new CommandConfiguration(
     List.of("setspawn"),
     "Sets the spawn of this world",
     "core.command.setspawn",
     Map.of()
   );
   
   @Override
   public @NonNull Command register(@NonNull OnteyPlugin plugin, @NonNull LiteralArgumentBuilder root) {
      ConfigCommand cmd = new ConfigCommand(plugin, defaults);
      
      root.then(
        Arg.location("location")
          .executes(ctx -> setSpawn(ctx, ctx.getArgument("location", FinePositionResolver.class).resolve(ctx.getSource())))
      );
      
      return cmd;
   }
   
   private static int setSpawn(CommandContext<CommandSourceStack> source, @Nullable FinePosition pos) {
      World world = lobbyManager.getWorld();
      var location = Nullity.nonNullOr(pos, p -> p.toLocation(world), source.getSource().getLocation());
      
      world.setSpawnLocation(location);
      
      return SUCCESS;
   }
}
