package core.listener;

import core.command.SpawnCommand;
import core.config.ConfigManager;
import core.manager.SpawnInventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import static core.manager.LobbyManager.lobbyManager;
import static core.manager.SpawnInventoryManager.spawnInventoryManager;

public class JoinListener implements Listener {
   
   @EventHandler
   public void onJoin(PlayerJoinEvent event) {
      if(ConfigManager.TELEPORT_TO_SPAWN_ON_JOIN) {
         SpawnCommand.execute(event.getPlayer());
         
         spawnInventoryManager.setInventory(event.getPlayer());
      }
   }
   
   @EventHandler
   public void onLobbyJoin(PlayerChangedWorldEvent event) {
      Player player = event.getPlayer();
      if(lobbyManager.isLobby(player.getWorld()))
         spawnInventoryManager.setInventory(player);
   }
}
