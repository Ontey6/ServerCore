package core.listener;

import core.cosmetic.CosmeticsMenu;
import ontey.color.MinecraftColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static core.cosmetic.CosmeticsManager.cosmeticsManager;
import static core.listener.HotbarClickListener.menus;

public class CosmeticsMenuListener implements Listener {
   
   @EventHandler
   public void onMenuClick(InventoryClickEvent event) {
      ItemStack item = event.getCurrentItem();
      Inventory inv = event.getClickedInventory();
      
      if(item == null || inv == null)
         return;
      
      var menu = menus.get(inv);
      
      if(menu == null)
         return;
      
      var cosmetic = menu.getCosmetics().get(event.getSlot());
      
      if(cosmetic == null)
         return;
      
      Player player = (Player) event.getWhoClicked();
      
      if(!cosmetic.isUnlocked(player)) {
         player.sendMessage(MinecraftColor.colorize(cosmetic.name() + "&c is not unlocked!"));
         event.setCancelled(true);
         return;
      }
      
      boolean isSelected = cosmeticsManager.isSelected(player, cosmetic);
      var previous = cosmeticsManager.getSelected(player);
      cosmeticsManager.select(player, !isSelected ? cosmetic : null);
      
      inv.setItem(event.getSlot(), CosmeticsMenu.buildItem(player, cosmetic));
      
      if(previous != null && !previous.equals(cosmetic)) {
         int previousSlot = menu.getSlot(previous);
         
         if(previousSlot >= 0)
            inv.setItem(previousSlot, CosmeticsMenu.buildItem(player, previous));
      }
      
      event.setCancelled(true);
   }
}
