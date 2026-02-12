package core.cosmetic;

import lombok.Getter;
import ontey.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static core.cosmetic.CosmeticsManager.cosmeticsManager;

public class CosmeticsMenu {
   
   private static final int MAX_SIZE = 6;
   private static final int ROW_SIZE = 9;
   private static final int CENTER_SLOT = ROW_SIZE / 2;
   
   @Getter
   private final Inventory menu;
   
   @Getter
   private final Map<Integer, Cosmetic> cosmetics;
   
   public CosmeticsMenu(Player player) {
      var cosmetics = new ArrayList<>(cosmeticsManager.getCosmetics());
      cosmetics.sort(Comparator.comparingLong(Cosmetic::unlockMinutes));
      
      int count = cosmetics.size();
      int size = Math.max(1, count / ROW_SIZE + (count % ROW_SIZE == 0 ? 0 : 1));
      
      if(size > MAX_SIZE)
         throw new IndexOutOfBoundsException("Too many cosmetics, max is " + MAX_SIZE * ROW_SIZE + ", found " + count);
      
      this.menu = Bukkit.createInventory(null, size * ROW_SIZE);
      this.cosmetics = new HashMap<>(count);
      
      var slots = generateSlots(count);
      int i = 0;
      for(Cosmetic cosmetic : cosmetics) {
         int slot = slots.get(i);
         this.cosmetics.put(slot, cosmetic);
         
         menu.setItem(slot, buildItem(player, cosmetic));
         
         i++;
      }
   }
   
   private static List<Integer> generateSlots(int count) {
      var slots = new ArrayList<Integer>(count);
      int rows = count / ROW_SIZE + (count % ROW_SIZE == 0 ? 0 : 1);
      
      for(int row = 0; row < rows; row++) {
         int rowStart = row * ROW_SIZE;
         int rowCount = Math.min(ROW_SIZE, count - rowStart);
         
         slots.addAll(generateCenteredRowSlots(rowStart, rowCount));
      }
      
      return slots;
   }
   
   private static List<Integer> generateCenteredRowSlots(int rowStart, int rowCount) {
      var rowSlots = new ArrayList<Integer>(rowCount);
      
      if(rowCount == ROW_SIZE) {
         for(int column = 0; column < ROW_SIZE; column++)
            rowSlots.add(rowStart + column);
         
         return rowSlots;
      }
      
      if(rowCount % 2 != 0)
         rowSlots.add(CENTER_SLOT);
      
      for(int offset = 1; rowSlots.size() < rowCount; offset++) {
         int left = CENTER_SLOT - offset;
         int right = CENTER_SLOT + offset;
         
         if(left >= 0)
            rowSlots.add(left);
         
         if(rowSlots.size() < rowCount && right < ROW_SIZE)
            rowSlots.add(right);
      }
      
      rowSlots.sort(Integer::compareTo);
      
      rowSlots.replaceAll(integer -> integer + rowStart);
      
      return rowSlots;
   }
   
   public int getSlot(Cosmetic cosmetic) {
      for(var entry : cosmetics.entrySet()) {
         if(entry.getValue().equals(cosmetic))
            return entry.getKey();
      }
      
      return -1;
   }
   
   public static ItemStack buildItem(Player player, Cosmetic cosmetic) {
      return ItemBuilder.of(cosmetic.display().material())
        .name(cosmetic.name())
        .lore(
          cosmetic.isUnlocked(player)
            ? "<green>Freigeschaltet - " + (cosmeticsManager.isSelected(player, cosmetic) ? "<white>an" : "<yellow>aus")
            : "<red>Gesperrt - " + calculateTime(player, cosmetic)
        )
        .build();
   }
   
   private static String calculateTime(Player player, Cosmetic cosmetic) {
      long remaining = cosmetic.getUnlockRemainingSeconds(player);
      long h = remaining / 60;
      long m = remaining % 60;
      return h + "h " + m + "m";
   }
}
