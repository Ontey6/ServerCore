package com.ontey.serverCore.listener;

import com.ontey.serverCore.manager.LobbyManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class LobbyPreventionsListener implements Listener {
   
   @EventHandler
   public void onLobbyDamage(EntityDamageEvent event) {
      if(LobbyManager.isActive("damage") && (event.getEntity() instanceof final Player p) && LobbyManager.isLobby(p.getWorld())) {
         event.setCancelled(true);
         
         if(event.getCause() == EntityDamageEvent.DamageCause.VOID)
            p.teleport(LobbyManager.getSpawnpoint());
      }
   }
   
   @EventHandler
   public void onLobbyHungerLoss(FoodLevelChangeEvent event) {
      if(LobbyManager.isActive("hunger") && event.getEntity() instanceof final Player p && LobbyManager.isLobby(p.getWorld()))
         event.setCancelled(true);
   }
   
   @EventHandler
   public void onLobbyBlockBreak(BlockBreakEvent event) {
      if(shouldCancel("block_break", event.getPlayer()))
         event.setCancelled(true);
   }
   
   @EventHandler
   public void onLobbyBlockPlace(BlockPlaceEvent event) {
      if(shouldCancel("block_place", event.getPlayer()))
         event.setCancelled(true);
   }
   
   @EventHandler
   public void onLobbyItemDrop(PlayerDropItemEvent event) {
      if(shouldCancel("drop", event.getPlayer()))
         event.setCancelled(true);
   }
   
   @EventHandler
   public void onLobbyInventoryMove(InventoryClickEvent event) {
      if(event.getWhoClicked() instanceof Player p && shouldCancel("inventory_move", p))
         event.setCancelled(true);
   }
   
   @EventHandler
   public void onLobbyInventoryMove(InventoryDragEvent event) {
      if(event.getWhoClicked() instanceof Player p && shouldCancel("inventory_move", p))
         event.setCancelled(true);
   }
   
   @EventHandler
   public void onLobbyBlockIgnite(BlockIgniteEvent event) {
      if(event.getPlayer() != null && shouldCancel("block_ignite", event.getPlayer()))
         event.setCancelled(true);
   }
   
   @EventHandler
   public void onLobbyBlockBurn(BlockBurnEvent event) {
      if(shouldCancel("block_burn", event.getBlock().getWorld()))
         event.setCancelled(true);
   }
   
   @EventHandler
   public void onLobbyBlockFromTo(BlockFromToEvent event) {
      if(shouldCancel("block_from_to", event.getBlock().getWorld()))
         event.setCancelled(true);
   }
   
   @EventHandler
   public void onLobbyBlockFade(BlockFadeEvent event) {
      if(shouldCancel("block_fade", event.getBlock().getWorld()))
         event.setCancelled(true);
   }
   
   @EventHandler
   public void onLobbyBlockPhysics(BlockPhysicsEvent event) {
      if(shouldCancel("block_physics", event.getBlock().getWorld()))
         event.setCancelled(true);
   }
   
   @EventHandler
   public void onLobbyBlockSpread(BlockSpreadEvent event) {
      if(shouldCancel("block_spread", event.getBlock().getWorld()))
         event.setCancelled(true);
   }
   
   @EventHandler
   public void onLobbyEntityExplode(EntityExplodeEvent event) {
      if(shouldCancel("entity_explode", event.getEntity().getWorld()))
         event.blockList().clear();
   }
   
   @EventHandler
   public void onLobbyEntityChangeBlock(EntityChangeBlockEvent event) {
      if(shouldCancel("entity_induced_block_change", event.getBlock().getWorld()))
         event.setCancelled(true);
   }
   
   // helper methods
   
   private static boolean shouldCancel(String name, Player player) {
      return LobbyManager.isActive(name) && !player.hasPermission("core.spawn.bypass." + name) && LobbyManager.isLobby(player.getWorld());
   }
   
   private static boolean shouldCancel(String name, World world) {
      return LobbyManager.isActive(name) && LobbyManager.isLobby(world);
   }
}
