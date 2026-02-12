package core.playtime;

import lombok.NonNull;
import ontey.config.Config;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static core.ServerCore.*;

public class PlaytimeManager extends Config {
   
   public static PlaytimeManager playtimeManager;
   
   private final Map<UUID, Short> playtimes = new ConcurrentHashMap<>();
   
   public PlaytimeManager() {
      super(plugin, new File(plugin.getDataFolder(), "playtime.yml"));
      playtimeManager = this;
      
      enable();
      startAutoIncrement();
   }
   
   public void enable() {
      var keys = getKeys(false);
      
      for(var key : keys)
         playtimes.put(UUID.fromString(key), (short) getInt(key, (short) 0));
   }
   
   public short getPlaytimeSeconds(@NonNull UUID uuid) {
      return playtimes.getOrDefault(uuid, (short) 0);
   }
   
   public short getPlaytimeSeconds(@NonNull Player player) {
      return getPlaytimeSeconds(player.getUniqueId());
   }
   
   public void addSecond(@NonNull UUID uuid) {
      short cur = getPlaytimeSeconds(uuid);
      if(cur != Short.MAX_VALUE)
         playtimes.put(uuid, cur++);
   }
   
   public void disable() {
      for(var entry : playtimes.entrySet())
         set(entry.getKey().toString(), entry.getValue());
      
      super.save();
   }
   
   private void startAutoIncrement() {
      // update playtime every minute
      plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
         for(Player p : plugin.getServer().getOnlinePlayers())
            addSecond(p.getUniqueId());
      }, 0, 60 * 20);
   }
}
