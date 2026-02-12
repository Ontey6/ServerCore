package core.listener;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import core.ServerCore;
import core.command.SpawnCommand;
import core.cosmetic.CosmeticsMenu;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

import static core.manager.LobbyManager.lobbyManager;
import static core.manager.SpawnInventoryManager.spawnInventoryManager;

public class HotbarClickListener implements Listener {
   
   static final Map<Inventory, CosmeticsMenu> menus = new HashMap<>();
   
   @EventHandler
   public void onElytraBoost(PlayerElytraBoostEvent event) {
      Player player = event.getPlayer();
      ItemStack item = event.getFirework().getItem();
      
      if(spawnInventoryManager.getFirework().equals(item) && lobbyManager.isLobby(player.getWorld()) && player.getGameMode() != GameMode.CREATIVE)
         Bukkit.getScheduler().runTask(ServerCore.plugin, () -> player.getInventory().setItem(spawnInventoryManager.getFireworkSlot(), spawnInventoryManager.getFirework()));
   }
   
   @EventHandler
   public void onRightClick(PlayerInteractEvent event) {
      Action action = event.getAction();
      if(action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)
         return;
      
      ItemStack item = event.getItem();
      Player player = event.getPlayer();
      
      if(!lobbyManager.isLobby(player.getWorld()))
         return;
      
      if(spawnInventoryManager.getEquip().equals(item)) {
         spawnInventoryManager.setInventory(player);
         event.setCancelled(true);
         return;
      }
      
      if(spawnInventoryManager.getUnequip().equals(item)) {
         var inv = player.getInventory();
         inv.setChestplate(null);
         inv.setItem(spawnInventoryManager.getToggleSlot(), spawnInventoryManager.getEquip());
         inv.setItem(spawnInventoryManager.getFireworkSlot(), null);
         inv.setItem(spawnInventoryManager.getLaunchSlot(), null);
         event.setCancelled(true);
         return;
      }
      
      if(spawnInventoryManager.getSpawn().equals(item)) {
         SpawnCommand.execute(player);
         event.setCancelled(true);
         return;
      }
      
      if(spawnInventoryManager.getLaunch().equals(item)) {
         Vector vel = player.getVelocity();
         if(!isOnGround(player) && ((CraftPlayer) player).getHandle().isFallFlying()) {
            player.setVelocity(new Vector(vel.getX(), 0.3, vel.getZ()));
            return;
         }
         
         player.setVelocity(new Vector(vel.getX(), 1.5, vel.getZ()));
         
         Bukkit.getScheduler().runTaskLater(
           ServerCore.plugin,
           () -> {
              ((CraftPlayer) player).getHandle().startFallFlying();
           },
           12 // Some fine-tuning brought this result.
         );
         
         event.setCancelled(true);
      }
      
      if(spawnInventoryManager.getCosmetics().equals(item)) {
         CosmeticsMenu menu = new CosmeticsMenu(player);
         player.openInventory(menu.getMenu());
         menus.put(menu.getMenu(), menu);
         event.setCancelled(true);
      }
   }
   
   private static boolean isOnGround(Player player) {
      return player.getLocation().getBlock().getRelative(BlockFace.DOWN).isSolid();
   }
}
