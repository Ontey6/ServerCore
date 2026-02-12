package core.cosmetic;

import lombok.Getter;
import lombok.SneakyThrows;
import ontey.check.Nullity;
import ontey.config.Config;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.File;
import java.util.*;

import static core.ServerCore.plugin;

public class CosmeticsManager extends Config {
   
   public static CosmeticsManager cosmeticsManager;
   
   private BukkitTask task;
   
   @Getter
   private final Set<@NonNull Cosmetic> cosmetics = new HashSet<>();
   
   private final Map<@NonNull UUID, @NonNull Cosmetic> selected = new HashMap<>();
   
   @SneakyThrows
   public CosmeticsManager() {
      super(plugin, new File(plugin.getDataFolder(), "cosmetics.yml"));
      cosmeticsManager = this;
      
      // Auto-unlock check every 60s
      enable();
   }
   
   private void registerCosmetics() {
      var keys = getKeys(false);
      
      for(var key : keys)
         registerCosmetic(Nullity.nonNull(getSerializable(key, Cosmetic.class)));
   }
   
   public void reload() {
      load();
      disable();
      enable();
   }
   
   void registerCosmetic(Cosmetic cosmetic) {
      cosmetics.add(cosmetic);
   }
   
   public void select(Player player, Cosmetic cosmetic) {
      selected.put(player.getUniqueId(), cosmetic);
   }
   
   public boolean isSelected(Player player, Cosmetic cosmetic) {
      return cosmetic.equals(getSelected(player));
   }
   
   @Nullable
   public Cosmetic getSelected(Player player) {
      return selected.computeIfAbsent(player.getUniqueId(), uuid -> null);
   }
   
   @Nullable
   private Cosmetic getSelected(UUID uuid) {
      return selected.computeIfAbsent(uuid, u -> null);
   }
   
   public void enable() {
      registerCosmetics();
      this.task = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
         for(var player : plugin.getServer().getOnlinePlayers()) {
            var cosmetic = getSelected(player);
            
            if(cosmetic != null)
               cosmetic.apply(player);
         }
      }, 0L, 5L);
   }
   
   public void disable() {
      task.cancel();
      
      cosmetics.clear();
      selected.clear();
   }
}
