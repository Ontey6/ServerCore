package com.ontey.serverCore.manager;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnInventoryManager {
   
   public static void load() {
      // Firework (enchanted)
      firework = new ItemStack(Material.FIREWORK_ROCKET);
      ItemMeta fwMeta = firework.getItemMeta();
      fwMeta.addEnchant(Enchantment.UNBREAKING, 1, true);
      fwMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
      firework.setItemMeta(fwMeta);
      
      // Toggle Elytra
      equip = new ItemStack(Material.ELYTRA);
      ItemMeta equipMeta = equip.getItemMeta();
      equipMeta.displayName(Component.text("Elytra anziehen", NamedTextColor.AQUA));
      equip.setItemMeta(equipMeta);
      
      unequip = new ItemStack(Material.LEATHER_CHESTPLATE);
      ItemMeta unequipMeta = unequip.getItemMeta();
      unequipMeta.displayName(Component.text("Elytra ausziehen", NamedTextColor.DARK_PURPLE));
      unequip.setItemMeta(unequipMeta);
      
      // Launch feather
      launch = new ItemStack(Material.FEATHER);
      ItemMeta launchMeta = launch.getItemMeta();
      launchMeta.displayName(Component.text("Launch", NamedTextColor.GOLD));
      launch.setItemMeta(launchMeta);
      
      
   }
   
   @Getter
   private static ItemStack firework, equip, unequip, launch;
   
   @Getter
   private static final int fireworkSlot = 0, toggleSlot = 4, launchSlot = 8;
   
   public static void setInventory(Player player) {
      PlayerInventory inv = player.getInventory();
      
      inv.clear();
      
      inv.setItem(fireworkSlot, firework);
      inv.setItem(toggleSlot, unequip);
      inv.setItem(launchSlot, launch);
      
      inv.setChestplate(new ItemStack(Material.ELYTRA));
   }
}
