package core.manager;

import lombok.Getter;
import ontey.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

//TODO bug firework switches 2 -> 1 slot
public class SpawnInventoryManager {
   
   public static SpawnInventoryManager spawnInventoryManager;
   
   public SpawnInventoryManager() {
      spawnInventoryManager = this;
   }
   
   public void load() {
      // Equip Elytra
      equip = ItemBuilder.of(Material.ELYTRA)
        .unbreakable(true, true)
        .name("<aqua>Elytra anziehen")
        .build();
      
      // Unequip Elytra
      unequip = ItemBuilder.of(Material.LEATHER_CHESTPLATE)
        .name("<dark_purple>Elytra ausziehen")
        .build();
      
      // Infinite Firework
      firework = ItemBuilder.of(Material.FIREWORK_ROCKET)
        .glowing()
        //.hideTooltip() // OnteyAPI v1.3.5
        .build();
      
      // Launch feather
      launch = ItemBuilder.of(Material.FEATHER)
        .name("<gold>Launch")
        .build();
      
      // Elytra
      elytra = ItemBuilder.of(Material.ELYTRA)
        .unbreakable(true, true)
        .build();
      
      // Spawn
      cosmetics = ItemBuilder.of(Material.NETHER_STAR)
        .name("<light_purple>✨ Cosmetics Menu")
        .glowing()
        .build();
      
      spawn = ItemBuilder.of(Material.GRASS_BLOCK)
        .name("<green>Spawn")
        .build();
   }
   
   @Getter
   private ItemStack firework, equip, unequip, launch, elytra, cosmetics, spawn;
   
   @Getter
   private final int toggleSlot = 0, fireworkSlot = 2, spawnSlot = 4, launchSlot = 6, cosmeticsSlot = 8;
   
   public void setInventory(Player player) {
      PlayerInventory inv = player.getInventory();
      
      inv.clear();
      
      inv.setItem(fireworkSlot, firework);
      inv.setItem(toggleSlot, unequip);
      inv.setItem(spawnSlot, spawn);
      inv.setItem(launchSlot, launch);
      inv.setItem(cosmeticsSlot, cosmetics);
      
      inv.setChestplate(elytra);
   }
}
