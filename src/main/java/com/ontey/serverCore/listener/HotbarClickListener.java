package com.ontey.serverCore.listener;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import com.ontey.serverCore.ServerCore;
import com.ontey.serverCore.manager.LobbyManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import static com.ontey.serverCore.manager.SpawnInventoryManager.*;

public class HotbarClickListener implements Listener {
   
   @EventHandler
   public void onElytraBoost(PlayerElytraBoostEvent event) {
      Player player = event.getPlayer();
      ItemStack item = event.getFirework().getItem();
      
      if(getFirework().equals(item)) {
         if(player.getGameMode() != GameMode.CREATIVE)
            Bukkit.getScheduler().runTask(ServerCore.plugin, () ->
              player.getInventory().addItem(getFirework())
            );
      }
   }
   
   @EventHandler
   public void onRightClick(PlayerInteractEvent event) {
      Action action = event.getAction();
      if(action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)
         return;
      
      ItemStack item = event.getItem();
      Player player = event.getPlayer();
      
      if(!LobbyManager.isLobby(player.getWorld()))
         return;
      
      if(getEquip().equals(item)) {
         var inv = player.getInventory();
         inv.setChestplate(new ItemStack(Material.ELYTRA));
         inv.setItem(getToggleSlot(), getUnequip());
         event.setCancelled(true);
         return;
      }
      
      if(getUnequip().equals(item)) {
         var inv = player.getInventory();
         inv.setChestplate(null);
         inv.setItem(getToggleSlot(), getEquip());
         event.setCancelled(true);
         return;
      }
      
      if(getLaunch().equals(item)) {
         Vector vel = player.getVelocity();
         if(!isOnGround(player)) {
            player.setVelocity(new Vector(vel.getX(), 0.3, vel.getZ()));
            return;
         }
         
         player.setVelocity(new Vector(vel.getX(), 1, vel.getZ()));
         
         Bukkit.getScheduler().runTaskLater(
           ServerCore.plugin,
           () -> ((CraftPlayer) player).getHandle().startFallFlying(),
           12
         );
         
         event.setCancelled(true);
      }
   }
   
   private static boolean isOnGround(Player player) {
      return player.getLocation().getBlock().getRelative(BlockFace.DOWN).isSolid();
   }
}
