package com.ontey.serverCore.listener;

import com.ontey.serverCore.command.SpawnCommand;
import com.ontey.serverCore.files.Config;
import com.ontey.serverCore.manager.SpawnInventoryManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
   
   @EventHandler
   public void onJoin(PlayerJoinEvent event) {
      if(Config.TELEPORT_TO_SPAWN_ON_JOIN) {
         SpawnCommand.execute(event.getPlayer());
         
         SpawnInventoryManager.setInventory(event.getPlayer());
      }
   }
}
